package com.zazsona.mobnegotiation.controller;

import com.zazsona.mobnegotiation.model.*;
import com.zazsona.mobnegotiation.repository.ICooldownRespository;
import com.zazsona.mobnegotiation.repository.INegotiationRepository;
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
    private INegotiationRepository negotiationRepo;
    private ICooldownRespository cooldownRepo;
    private INegotiationEntityEligibilityChecker eligibilityChecker;
    private IViewInteractionExecutor interactionExecutor;
    private HashMap<String, NegotiationMenu> negotiationIdMenuMap;
    private Random random;

    public NegotiationController(INegotiationRepository negotiationRepo, ICooldownRespository cooldownRepo, INegotiationEntityEligibilityChecker eligibilityChecker, IViewInteractionExecutor interactionExecutor)
    {
        this.negotiationRepo = negotiationRepo;
        this.cooldownRepo = cooldownRepo;
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
                NegotiationStage stage = negotiation.start();
                if (stage != null)
                {
                    displayAlertTitle(player);
                    NegotiationMenu menu = convertNegotiationStageToMenu(stage, player);
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

    private NegotiationMenu convertNegotiationStageToMenu(NegotiationStage stage, Player player) // TODO: Make text red on failure, green on success.
    {
        NegotiationMenu negotiationMenu = new NegotiationMenu(interactionExecutor);
        String headerText = (stage.getMobMessage() != null) ? formatMobMessageToHeader(stage) : null;
        negotiationMenu.setHeaderText(headerText);

        ArrayList<NegotiationResponse> responses = stage.getResponses();
        for (NegotiationResponse response : responses)
        {
            String icon = getResponseTypeIcon(response.getType());
            ChatColor colour = getResponseTypeColour(response.getType());
            NegotiationButton button = new NegotiationButton(response.getText(), icon, colour, negotiationMenu);
            negotiationMenu.addChild(button);
            button.addListener(clickedButton ->
                               {
                                   String negotiationId = stage.getNegotiationId();
                                   Negotiation negotiation = negotiationRepo.getNegotiation(negotiationId);
                                   NegotiationMenu menu = negotiationIdMenuMap.get(negotiationId);
                                   if (negotiation != null && menu != null)
                                   {
                                       for (INegotiationView child : menu.getChildren())
                                       {
                                           if (child instanceof IClickableNegotiationView)
                                               ((IClickableNegotiationView) child).clearListeners();
                                       }

                                       NegotiationStage nextStage = negotiation.nextStage(response);
                                       if (nextStage != null)
                                       {
                                           NegotiationMenu nextMenu = convertNegotiationStageToMenu(nextStage, player);
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

    private String formatMobMessageToHeader(NegotiationStage stage)
    {
        String mobChatTag = String.format("<%s %s> ", WordUtils.capitalizeFully(stage.getMobPersonality().toString()), stage.getMobName());
        String mobMessage = stage.getMobMessage();
        String[] lines = mobMessage.split("\n");
        StringBuilder stringBuilder = new StringBuilder();
        for (String line : lines)
            stringBuilder.append(mobChatTag).append(line).append("\n");
        return stringBuilder.toString().trim();
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
