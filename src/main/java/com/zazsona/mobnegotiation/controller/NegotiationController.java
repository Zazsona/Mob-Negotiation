package com.zazsona.mobnegotiation.controller;

import com.zazsona.mobnegotiation.model.*;
import com.zazsona.mobnegotiation.model.action.IAction;
import com.zazsona.mobnegotiation.model.action.PowerNegotiationAction;
import com.zazsona.mobnegotiation.repository.ICooldownRespository;
import com.zazsona.mobnegotiation.repository.INegotiationRepository;
import com.zazsona.mobnegotiation.repository.IPersonalityNamesRepository;
import com.zazsona.mobnegotiation.view.NegotiationButton;
import com.zazsona.mobnegotiation.view.NegotiationMenu;
import com.zazsona.mobnegotiation.view.interfaces.IClickableNegotiationView;
import com.zazsona.mobnegotiation.view.interfaces.INegotiationView;
import com.zazsona.mobnegotiation.view.interfaces.IViewInteractionExecutor;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang.WordUtils;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class NegotiationController implements Listener
{
    private static final ChatColor DEFAULT_CHAT_COLOUR = ChatColor.WHITE;
    private static final String PERSONALITY_PLACEHOLDER_TEXT = "%PERSONALITY%";
    private static final String NAME_PLACEHOLDER_TEXT = "%NAME%";
    private static final String POWER_PLACEHOLDER_TEXT = "%POWER%";
    private static final String ITEM_PLACEHOLDER_TEXT = "%ITEM%";

    private INegotiationRepository negotiationRepo;
    private ICooldownRespository cooldownRepo;
    private IPersonalityNamesRepository personalityNamesRepo;
    private INegotiationEntityEligibilityChecker eligibilityChecker;
    private IViewInteractionExecutor interactionExecutor;
    private HashMap<String, NegotiationMenu> negotiationIdMenuMap;
    private Random random;

    public NegotiationController(INegotiationRepository negotiationRepo, ICooldownRespository cooldownRepo, IPersonalityNamesRepository personalityNamesRepo, INegotiationEntityEligibilityChecker eligibilityChecker, IViewInteractionExecutor interactionExecutor)
    {
        this.negotiationRepo = negotiationRepo;
        this.cooldownRepo = cooldownRepo;
        this.personalityNamesRepo = personalityNamesRepo;
        this.eligibilityChecker = eligibilityChecker;
        this.interactionExecutor = interactionExecutor;
        this.negotiationIdMenuMap = new HashMap<>();
        this.random = new Random();
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
            double roll = (random.nextDouble() * 100);
            if (roll < PluginConfig.getNegotiationRate() && !cooldownRepo.isPlayerInCooldown(player) && eligibilityChecker.canEntitiesNegotiate(player, mob))
            {
                e.setDamage(0.0f);
                Negotiation negotiation = new Negotiation(player, mob, eligibilityChecker, cooldownRepo);
                negotiationRepo.addNegotiation(negotiation);
                negotiation.start();
                if (!negotiation.getState().isErroneous())
                {
                    displayAlertTitle(player);
                    NegotiationMenu menu = convertNegotiationToMenu(negotiation, player);
                    negotiationIdMenuMap.put(negotiation.getNegotiationId(), menu);
                    player.spigot().sendMessage(menu.getFormattedComponent());
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

    private NegotiationMenu convertNegotiationToMenu(Negotiation negotiation, Player player)
    {
        String negotiationId = negotiation.getNegotiationId();
        NegotiationPrompt prompt = negotiation.getCurrentPrompt();
        NegotiationMenu negotiationMenu = new NegotiationMenu(interactionExecutor);
        String headerText = (prompt.getMobMessage() != null) ? createHeaderText(negotiation) : null;
        negotiationMenu.setHeaderText(headerText);

        ArrayList<NegotiationResponse> responses = prompt.getResponses();
        for (NegotiationResponse response : responses)
        {
            String icon = getResponseTypeIcon(response.getType());
            ChatColor colour = getResponseTypeColour(response.getType());
            NegotiationButton button = new NegotiationButton(response.getText(), icon, colour, negotiationMenu);
            negotiationMenu.addChild(button);
            button.addListener(clickedButton ->
                               {
                                   NegotiationMenu menu = negotiationIdMenuMap.get(negotiationId);
                                   if (negotiation != null && menu != null)
                                   {
                                       for (INegotiationView child : menu.getChildren())
                                       {
                                           if (child instanceof IClickableNegotiationView)
                                               ((IClickableNegotiationView) child).clearListeners();
                                       }

                                       NegotiationPrompt nextPrompt = negotiation.nextPrompt(response);
                                       if (nextPrompt != null)
                                       {
                                           NegotiationMenu nextMenu = convertNegotiationToMenu(negotiation, player);
                                           negotiationIdMenuMap.put(negotiationId, nextMenu);
                                           player.spigot().sendMessage(nextMenu.getFormattedComponent());
                                       }
                                       else
                                           negotiationIdMenuMap.remove(negotiationId);
                                   }
                               });
        }
        return negotiationMenu;
    }

    private String createHeaderText(Negotiation negotiation)
    {
        String mobChatTagTemplate = PluginConfig.getMobChatTag();
        String mobChatTag = fillPlaceholderValues(mobChatTagTemplate, negotiation).trim() + " ";
        String mobMessage = negotiation.getCurrentPrompt().getMobMessage();
        String[] lines = mobMessage.split("\n");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < lines.length; i++)
        {
            String line = lines[i];
            ChatColor lineColour = getHeaderLineColour(line, negotiation);
            line = line.replace(POWER_PLACEHOLDER_TEXT, ChatColor.GOLD + POWER_PLACEHOLDER_TEXT + lineColour);
            line = line.replace(ITEM_PLACEHOLDER_TEXT, ChatColor.GOLD + ITEM_PLACEHOLDER_TEXT + lineColour);
            line = fillPlaceholderValues(line, negotiation);
            stringBuilder.append(mobChatTag).append(lineColour).append(line).append("\n");
        }
        return stringBuilder.toString().trim();
    }

    private ChatColor getHeaderLineColour(String line, Negotiation negotiation)
    {
        if (negotiation.getState().isTerminating())
        {
            String[] mobMessageLines = negotiation.getCurrentPrompt().getMobMessage().split("\n");
            boolean isFinalLine = (line.equals(mobMessageLines[mobMessageLines.length - 1]));
            if (isFinalLine)
            {
                return switch (negotiation.getState())
                        {
                            case FINISHED_POWER_GIVEN -> ChatColor.GREEN;
                            case FINISHED_POWER_REJECTED -> ChatColor.RED;
                            default -> ChatColor.WHITE;
                        };
            }
        }
        return ChatColor.WHITE;
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
        return line;
    }

    private ChatColor getResponseTypeColour(NegotiationResponseType type)
    {
        return switch (type)
        {
            case SPEECH -> ChatColor.WHITE;
            case ATTACK -> ChatColor.RED;
            case CANCEL -> ChatColor.GRAY;
        };
    }

    private String getResponseTypeIcon(NegotiationResponseType type)
    {
        return "\u2794";
    }
}
