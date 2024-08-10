package com.zazsona.mobnegotiation.controller2;

import com.zazsona.mobnegotiation.MobNegotiationPlugin;
import com.zazsona.mobnegotiation.model.Negotiation;
import com.zazsona.mobnegotiation.model.NegotiationResponse;
import org.bukkit.ChatColor;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import view2.chat.NegotiationActionButton;
import view2.chat.NegotiationChatPanel;
import view2.chat.NegotiationMobMessageTextField;
import view2.lib.chat.control.layout.ChatBoxLayout;
import view2.lib.chat.interact.ITriggerListener;
import view2.lib.chat.interact.PlayerTriggerEvent;
import view2.world.NegotiationWorldState;

import java.util.*;

public class NegotiationController
{
    private Map<UUID, NegotiationSession> negotiationSessionsByPlayerId;
    private NegotiationTextFormatter textFormatter;

    public NegotiationController()
    {
        this.negotiationSessionsByPlayerId = new HashMap<>();
        this.textFormatter = new NegotiationTextFormatter();
    }

    /**
     * Detects when a valid mob is hit, and if parameters are valid, begins a negotiation session.
     * @param e the event
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onMobAttack(EntityDamageByEntityEvent e)
    {
        if (!(e.getDamager() instanceof Player) && !(e.getEntity() instanceof Mob))
            return;

        Player player = (Player) e.getDamager();
        Mob mob = (Mob) e.getEntity();

        // TODO: Verify eligibility of player & mob for negotiation
        // TODO: Create Negotiation
    }

    private void renderNegotiation(Negotiation negotiation)
    {
        Player player = negotiation.getPlayer();
        UUID playerId = player.getUniqueId();
        NegotiationSession session = negotiationSessionsByPlayerId.get(playerId);
        if (session == null)
        {
            NegotiationWorldState worldState = new NegotiationWorldState(negotiation.getMob(), player);
            session = new NegotiationSession(negotiation, worldState);
            negotiationSessionsByPlayerId.put(playerId, session);
        }

        renderNegotiationToChat(negotiation);
        // TODO: Render World
    }

    private void renderNegotiationToChat(Negotiation negotiation)
    {
        NegotiationChatPanel chatPanel = new NegotiationChatPanel();
        NegotiationMobMessageTextField mobMessageTextField = chatPanel.getMobMessageField();
        ChatBoxLayout actionButtonLayout = chatPanel.getNegotiationActionButtonLayout();

        mobMessageTextField.setMobTag(String.format("%s %s", negotiation.getMobPersonality().toString(), negotiation.getMob().getType().toString()));
        mobMessageTextField.setMobMessage(negotiation.getCurrentPrompt().getMobMessage());

        // TODO: Get responses & loop
    }

    private NegotiationActionButton createNegotiationActionButton(Negotiation negotiation, NegotiationResponse responseOption)
    {
        String text = responseOption.getText();
        List<ChatColor> formattingOptions = textFormatter.getResponseTypeFormatting(responseOption.getResponseType());
        List<ChatColor> textFormattingOptions = new ArrayList<>();
        textFormattingOptions.addAll(textFormatter.getTextTypeFontStyle(responseOption.getTextType()));
        textFormattingOptions.add(textFormatter.getResponseTypeColour(responseOption.getResponseType()));
        ITriggerListener callback = e -> onNegotiationActionButtonSelected((PlayerTriggerEvent) e, responseOption);

        NegotiationActionButton negotiationActionButton = new NegotiationActionButton();
        negotiationActionButton.setNegotiationAction(text, formattingOptions, textFormattingOptions, callback);
        return negotiationActionButton;
    }

    private void onNegotiationActionButtonSelected(PlayerTriggerEvent playerTriggerEvent, NegotiationResponse responseOption) {
        Player player = playerTriggerEvent.getTriggeringPlayer();
        UUID playerId = player.getUniqueId();
        NegotiationSession negotiationSession = negotiationSessionsByPlayerId.get(playerId);
        if (negotiationSession == null)
        {
            MobNegotiationPlugin.getInstance().getLogger().warning(String.format("Player %s selected a Negotiation Response, but has no active Negotiation.", player.getName()));
            return;
        }

        Negotiation negotiation = negotiationSession.getNegotiation();
        negotiation.nextPrompt(responseOption);
    }
}