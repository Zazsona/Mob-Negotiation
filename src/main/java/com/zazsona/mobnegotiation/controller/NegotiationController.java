package com.zazsona.mobnegotiation.controller;

import com.zazsona.mobnegotiation.Permissions;
import com.zazsona.mobnegotiation.model.*;
import com.zazsona.mobnegotiation.model.action.IAction;
import com.zazsona.mobnegotiation.model.action.ItemNegotiationAction;
import com.zazsona.mobnegotiation.model.action.MoneyNegotiationAction;
import com.zazsona.mobnegotiation.model.action.PowerNegotiationAction;
import com.zazsona.mobnegotiation.repository.ICooldownRespository;
import com.zazsona.mobnegotiation.repository.INegotiationRepository;
import com.zazsona.mobnegotiation.repository.IPersonalityNamesRepository;
import com.zazsona.mobnegotiation.repository.ITalkSoundsRepository;
import com.zazsona.mobnegotiation.view.NegotiationButton;
import com.zazsona.mobnegotiation.view.NegotiationMenu;
import com.zazsona.mobnegotiation.view.interfaces.ISelectableNegotiationView;
import com.zazsona.mobnegotiation.view.interfaces.INegotiationView;
import com.zazsona.mobnegotiation.view.interfaces.IViewInteractionExecutor;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Sound;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class NegotiationController implements Listener
{
    private static final String HOVER_HINT_TEXT = "Click to Select";
    private static final String PERSONALITY_PLACEHOLDER_TEXT = "%PERSONALITY%";
    private static final String NAME_PLACEHOLDER_TEXT = "%NAME%";
    private static final String POWER_PLACEHOLDER_TEXT = "%POWER%";
    private static final String ITEM_PLACEHOLDER_TEXT = "%ITEM%";
    private static final String MONEY_PLACEHOLDER_TEXT = "%MONEY%";

    private INegotiationRepository negotiationRepo;
    private ICooldownRespository cooldownRepo;
    private IPersonalityNamesRepository personalityNamesRepo;
    private ITalkSoundsRepository taskSoundsRepo;
    private INegotiationEntityEligibilityChecker eligibilityChecker;
    private IViewInteractionExecutor interactionExecutor;
    private HashMap<Negotiation, NegotiationMenu> menusByNegotiation;
    private HashMap<NegotiationMenu, Negotiation> negotiationsByMenu;
    private NegotiationPromptUpdateListener promptListener;
    private NegotiationStateListener stateListener;
    private Random random;

    public NegotiationController(INegotiationRepository negotiationRepo, ICooldownRespository cooldownRepo, IPersonalityNamesRepository personalityNamesRepo, ITalkSoundsRepository talkSoundsRepo, INegotiationEntityEligibilityChecker eligibilityChecker, IViewInteractionExecutor interactionExecutor)
    {
        this.negotiationRepo = negotiationRepo;
        this.cooldownRepo = cooldownRepo;
        this.personalityNamesRepo = personalityNamesRepo;
        this.taskSoundsRepo = talkSoundsRepo;
        this.eligibilityChecker = eligibilityChecker;
        this.interactionExecutor = interactionExecutor;
        this.menusByNegotiation = new HashMap<>();
        this.negotiationsByMenu = new HashMap<>();
        this.promptListener = this::displayPrompt;
        this.stateListener = this::handleNegotiationTermination;
        this.random = new Random();
    }

    public NegotiationMenu getNegotiationMenuForPlayer(Player player)
    {
        Negotiation negotiation = negotiationRepo.getNegotiationForPlayer(player);
        if (negotiation != null)
            return menusByNegotiation.get(negotiation);
        return null;
    }

    public boolean isPlayerNegotiating(Player player) {
        return negotiationRepo.getNegotiationForPlayer(player) != null;
    }

    /**
     * Detects when a valid mob is hit, and if parameters are valid, begins a negotiation session.
     * @param e the event
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onMobAttack(EntityDamageByEntityEvent e)
    {
        if (e.getDamager() instanceof Player && e.getEntity() instanceof Mob)
        {
            Player player = (Player) e.getDamager();
            Mob mob = (Mob) e.getEntity();
            double preHitMobHealth = mob.getHealth();
            double rawDamage = e.getDamage();
            double finalDamage = e.getFinalDamage();
            double mobRemainingHealth = preHitMobHealth - finalDamage;
            if (mobRemainingHealth <= PluginConfig.getNegotiationHealthThreshold())
            {
                double roll = (random.nextDouble() * 100);
                if (roll < PluginConfig.getNegotiationRate() && Permissions.hasNegotiationPermission(player) && !cooldownRepo.isPlayerInCooldown(player) && eligibilityChecker.canEntitiesNegotiate(player, mob))
                {
                    if (mobRemainingHealth <= 0.0f)
                    {
                        e.setDamage(0.0f);
                        mob.setHealth(1.0f);
                    }
                    Negotiation negotiation = new TimedNegotiation(player, mob, eligibilityChecker, cooldownRepo, PluginConfig.getNegotiationIdleTimeoutTicks());
                    negotiation.addListener(promptListener);
                    negotiation.addListener(stateListener);
                    negotiationRepo.addNegotiation(negotiation);
                    MetricsManager.getInstance().trackNegotiation(negotiation);
                    boolean successfulStart = negotiation.start();
                    if (!successfulStart)
                    {
                        mob.setHealth(preHitMobHealth);
                        e.setDamage(rawDamage);
                    }
                }
            }
        }
    }

    private void displayPrompt(Negotiation negotiation, NegotiationPrompt prompt, boolean passive)
    {
        if (prompt == null)
            return;

        NegotiationMenu negotiationMenu = new NegotiationMenu(interactionExecutor);
        String headerText = (prompt.getMobMessage() != null) ? createHeaderText(negotiation, prompt) : null;
        negotiationMenu.setHeaderText(headerText);

        if (!passive)
        {
            if (negotiation.getState() == NegotiationState.STARTED)
                displayAlertTitle(negotiation.getPlayer());

            if (prompt instanceof RespondableNegotiationPrompt)
            {
                RespondableNegotiationPrompt respondablePrompt = (RespondableNegotiationPrompt) prompt;
                ArrayList<NegotiationResponse> responses = respondablePrompt.getResponses();
                for (NegotiationResponse response : responses)
                {
                    String icon = getResponseTypeIcon(response.getResponseType());
                    ChatColor colour = getResponseTypeColour(response.getResponseType());
                    String formattedText = response.getText();
                    formattedText = fillPlaceholderValues(formattedText, negotiation);
                    formattedText = applyMinecraftFormatting(formattedText);
                    formattedText = getTextTypeFormatting(response.getTextType()) + formattedText;
                    HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(HOVER_HINT_TEXT));
                    NegotiationButton button = new NegotiationButton(formattedText, icon, colour, negotiationMenu, hoverEvent);
                    button.addListener((selectButton) -> handleButtonSelection(negotiation, response));
                    negotiationMenu.addChild(button);
                }
            }
            menusByNegotiation.put(negotiation, negotiationMenu);
            negotiationsByMenu.put(negotiationMenu, negotiation);
        }
        Mob mob = negotiation.getMob();
        Player player = negotiation.getPlayer();
        player.playSound(mob.getLocation(), taskSoundsRepo.getSound(mob.getType()), 1.0f, 1.0f);
        boolean displayInstructions = (negotiation.getState() == NegotiationState.STARTED && prompt instanceof RespondableNegotiationPrompt);
        player.spigot().sendMessage(negotiationMenu.getFormattedComponent(displayInstructions));
    }

    private void handleButtonSelection(Negotiation negotiation, NegotiationResponse response)
    {
        Player player = negotiation.getPlayer();
        NegotiationMenu menu = menusByNegotiation.get(negotiation);
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.25f, 1.0f);
        if (!negotiation.getState().isTerminating() && menu != null)
        {
            for (INegotiationView child : menu.getChildren())
            {
                if (child instanceof ISelectableNegotiationView)
                    ((ISelectableNegotiationView) child).clearListeners();
            }
        }
        negotiation.nextPrompt(response);
    }

    private void handleNegotiationTermination(Negotiation negotiation)
    {
        if (negotiation.getState().isTerminating())
        {
            negotiation.removeListener(promptListener);
            negotiation.removeListener(stateListener);

            NegotiationMenu menu = menusByNegotiation.remove(negotiation);
            if (menu != null)
            {
                negotiationsByMenu.remove(menu);
                for (INegotiationView child : menu.getChildren())
                {
                    if (child instanceof ISelectableNegotiationView)
                        ((ISelectableNegotiationView) child).clearListeners();
                }
            }
        }
    }

    private void displayAlertTitle(Player player)
    {
        List<String> alertMessages = PluginConfig.getNegotiationAlertMessages();
        String alertMessage = alertMessages.get(random.nextInt(alertMessages.size()));
        String alertFormat = "" + ChatColor.RED + ChatColor.BOLD;
        String formattedAlertMessage = alertFormat + alertMessage;
        player.sendTitle(formattedAlertMessage, null, 2, 30, 7);
    }

    private String createHeaderText(Negotiation negotiation, NegotiationPrompt prompt)
    {
        String mobMessage = prompt.getMobMessage();
        String mobChatTagTemplate = PluginConfig.getMobChatTag();
        String mobChatTag = mobChatTagTemplate.trim() + " ";
        String[] lines = mobMessage.split("\n");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < lines.length; i++)
        {
            String line = lines[i];
            String textTypeFormatting = getTextTypeFormatting(prompt.getMobMessageTextType());
            if (prompt.getMobMessageTextType() == TextType.SPEECH)
                stringBuilder.append(fillPlaceholderValues(mobChatTag, negotiation));
            String moodFormatting = (i == lines.length - 1) ? getMoodFormatting(prompt.getMobMood()) : getMoodFormatting(Mood.NEUTRAL); // Only format last line
            line = line.replace(POWER_PLACEHOLDER_TEXT, ChatColor.GOLD + POWER_PLACEHOLDER_TEXT + moodFormatting);
            line = line.replace(ITEM_PLACEHOLDER_TEXT, ChatColor.GOLD + ITEM_PLACEHOLDER_TEXT + moodFormatting);
            line = line.replace(MONEY_PLACEHOLDER_TEXT, ChatColor.GOLD + MONEY_PLACEHOLDER_TEXT + moodFormatting);
            line = fillPlaceholderValues(line, negotiation);
            line = applyMinecraftFormatting(line);
            stringBuilder.append(moodFormatting).append(textTypeFormatting).append(line).append("\n");
        }

        return stringBuilder.toString().trim();
    }

    private String getMoodFormatting(Mood mood)
    {
        return switch (mood)
                {
                    case ANGRY -> ""+ChatColor.RED;
                    case HAPPY -> ""+ChatColor.GREEN;
                    default -> ""+ChatColor.WHITE;
                };
    }

    private String getTextTypeFormatting(TextType textType)
    {
        switch (textType)
        {
            case ACTION:
                return "" + ChatColor.ITALIC;
            default:
                return "";
        }
    }

    private String applyMinecraftFormatting(String line)
    {
        // Formatting codes are the same as Minecraft default.
        // Lines should just use f& instead of the section sign to prevent formatting issues with certain save formats.
        String formatChar = ""+ChatColor.COLOR_CHAR;
        String formattedLine = line.replace("f&", formatChar);
        return formattedLine;
    }

    private String fillPlaceholderValues(String line, Negotiation negotiation)
    {
        if (line.contains(NAME_PLACEHOLDER_TEXT))
        {
            Mob mob = negotiation.getMob();
            String mobName = (mob.getCustomName() == null) ? mob.getName() : mob.getCustomName();
            line = line.replace(NAME_PLACEHOLDER_TEXT, mobName);
        }
        if (line.contains(PERSONALITY_PLACEHOLDER_TEXT))
        {
            Mob mob = negotiation.getMob();
            List<String> personalityNames = personalityNamesRepo.getNames(negotiation.getMobPersonality());
            int personalityNameIndex = (Math.abs((int) mob.getUniqueId().getMostSignificantBits()) % personalityNames.size());
            String personalityName = personalityNames.get(personalityNameIndex); // Random, but consistent for single mob
            line = line.replace(PERSONALITY_PLACEHOLDER_TEXT, personalityName);
        }
        if (line.contains(POWER_PLACEHOLDER_TEXT))
        {
            IAction action = negotiation.getAction();
            if (action instanceof PowerNegotiationAction)
            {
                PowerNegotiationAction pnAction = (PowerNegotiationAction) action;
                if (pnAction.getGivenPowers().size() > 0 && pnAction.getGivenPowers().get(0) != null)
                {
                    String powerName = pnAction.getGivenPowers().get(0).getType().getName();
                    String formattedPowerName = WordUtils.capitalizeFully(powerName.replace("_", " "));
                    line = line.replace(POWER_PLACEHOLDER_TEXT, formattedPowerName);
                }
            }
        }
        if (line.contains(ITEM_PLACEHOLDER_TEXT))
        {
            IAction action = negotiation.getAction();
            if (action instanceof ItemNegotiationAction)
            {
                ItemNegotiationAction inAction = (ItemNegotiationAction) action;
                ItemStack stack = inAction.getOfferStack();
                String formattedItemName = WordUtils.capitalizeFully(stack.getType().toString().replace("_", " "));
                String formattedItemQuantity = String.format("x%s", inAction.getCurrentOfferQuantity());
                String formattedText = formattedItemQuantity + " " + formattedItemName;
                line = line.replace(ITEM_PLACEHOLDER_TEXT, formattedText);
            }
        }
        if (line.contains(MONEY_PLACEHOLDER_TEXT))
        {
            IAction action = negotiation.getAction();
            if (action instanceof MoneyNegotiationAction)
            {
                MoneyNegotiationAction mnAction = (MoneyNegotiationAction) action;
                double money = mnAction.getCurrentOfferAmount();
                String currencyName = mnAction.getCurrencyName();
                String formattedText = String.format("%s%s", currencyName, money);
                line = line.replace(MONEY_PLACEHOLDER_TEXT, formattedText);
            }
        }
        return line;
    }

    private ChatColor getResponseTypeColour(NegotiationResponseType type)
    {
        return switch (type)
        {
            case PARLEY -> ChatColor.WHITE;
            case ATTACK -> ChatColor.RED;
            case CANCEL -> ChatColor.DARK_GRAY;
        };
    }

    private String getResponseTypeIcon(NegotiationResponseType type)
    {
        return "\u2794";
    }
}
