package com.zazsona.mobnegotiation.controller2;

import com.zazsona.mobnegotiation.model.Negotiation;
import com.zazsona.mobnegotiation.model.NegotiationResponse;
import org.bukkit.ChatColor;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
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
    private Negotiation negotiation;
    private NegotiationWorldState negotiationWorldState;
    private NegotiationChatPanel negotiationChatPanel;

    private NegotiationTextFormatter textFormatter;

    public NegotiationController(Negotiation negotiation)
    {
        this.negotiation = negotiation;
        this.textFormatter = new NegotiationTextFormatter();
    }

    public void renderNegotiation()
    {
        if (this.negotiationWorldState == null || !this.negotiationWorldState.isRendered())
            this.negotiationWorldState = renderNegotiationToWorld();

        if (this.negotiationChatPanel != null)
            this.negotiationChatPanel.destroy();
        this.negotiationChatPanel = renderNegotiationToChat();
    }

    private NegotiationChatPanel renderNegotiationToChat()
    {
        NegotiationChatPanel chatPanel = new NegotiationChatPanel();
        NegotiationMobMessageTextField mobMessageTextField = chatPanel.getMobMessageField();
        ChatBoxLayout actionButtonLayout = chatPanel.getNegotiationActionButtonLayout();

        mobMessageTextField.setMobTag(String.format("%s %s", negotiation.getMobPersonality().toString(), negotiation.getMob().getType().toString()));
        mobMessageTextField.setMobMessage(negotiation.getCurrentPrompt().getMobMessage());

        // TODO: Get responses & loop
        // The Negotiation object currently doesn't expose responses... What crackpot devised that stupid API?
        // Oh...

        return chatPanel;
    }

    private NegotiationWorldState renderNegotiationToWorld()
    {
        Player player = negotiation.getPlayer();
        Mob mob = negotiation.getMob();
        NegotiationWorldState worldState = new NegotiationWorldState(mob, player);
        worldState.render();
        return worldState;
    }

    private NegotiationActionButton createNegotiationActionButton(NegotiationResponse responseOption)
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
        negotiation.nextPrompt(responseOption);
    }
}