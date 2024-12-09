package com.zazsona.mobnegotiation.view2.chat;

import com.zazsona.mobnegotiation.view2.lib.chat.control.input.ChatButton;
import com.zazsona.mobnegotiation.view2.lib.chat.interact.ITriggerListener;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.apache.commons.lang.NotImplementedException;
import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.List;

public class NegotiationActionButton extends ChatButton {

    private String buttonText;
    private List<ChatColor> buttonFormatting;
    private List<ChatColor> buttonTextFormatting;

    public void setNegotiationAction(String text, List<ChatColor> formattingOptions, List<ChatColor> textFormattingOptions, ITriggerListener callback)
    {
        this.buttonText = text;
        this.buttonFormatting = formattingOptions;
        this.buttonTextFormatting = textFormattingOptions;
        this.addListener(callback);
    }

    @Override
    public void setChatComponent(List<BaseComponent> components) {
        throw new NotImplementedException("Use setNegotiationAction.");
    }

    @Override
    public void setChatComponent(BaseComponent component) {
        throw new NotImplementedException("Use setNegotiationAction.");
    }

    @Override
    public BaseComponent render() {
        StringBuilder buttonFormatBuilder = new StringBuilder();
        buttonFormatBuilder.append(ChatColor.RESET);
        for (ChatColor formattingOption : buttonFormatting)
            buttonFormatBuilder.append(formattingOption);
        String buttonFormatting = buttonFormatBuilder.toString();

        StringBuilder buttonTextFormatBuilder = new StringBuilder();
        buttonTextFormatBuilder.append(ChatColor.RESET);
        for (ChatColor formattingOption : buttonTextFormatting)
            buttonTextFormatBuilder.append(formattingOption);
        String buttonTextFormatting = buttonTextFormatBuilder.toString();

        ComponentBuilder componentBuilder = new ComponentBuilder();
        componentBuilder
                .append(buttonFormatting)
                .append("[")
                .append(buttonTextFormatting)
                .append(buttonText)
                .append(buttonFormatting)
                .append("]");
        super.setChatComponent(Arrays.asList(componentBuilder.create()));
        return super.render();
    }

}