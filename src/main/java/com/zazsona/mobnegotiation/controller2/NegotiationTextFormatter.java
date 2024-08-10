package com.zazsona.mobnegotiation.controller2;

import com.zazsona.mobnegotiation.model.NegotiationResponseType;
import com.zazsona.mobnegotiation.model.TextType;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class NegotiationTextFormatter {

    public List<ChatColor> getResponseTypeFormatting(NegotiationResponseType responseType)
    {
        List<ChatColor> style = getResponseTypeFormatting(responseType);
        ChatColor colour = getResponseTypeColour(responseType);
        style.add(colour);
        return style;
    }

    public List<ChatColor> getResponseTypeFontStyle(NegotiationResponseType responseType)
    {
        ArrayList<ChatColor> fontStyles = new ArrayList<>();
        switch (responseType)
        {
            case ATTACK:
            case CANCEL:
                fontStyles.add(ChatColor.ITALIC);
                break;
        };
        return fontStyles;
    }

    public ChatColor getResponseTypeColour(NegotiationResponseType responseType)
    {
        return switch (responseType)
        {
            case ATTACK -> ChatColor.RED;
            case CANCEL -> ChatColor.GRAY;
            default -> ChatColor.WHITE;
        };
    }

    public List<ChatColor> getTextTypeFontStyle(TextType textType)
    {
        ArrayList<ChatColor> fontStyles = new ArrayList<>();
        switch (textType)
        {
            case ACTION:
                fontStyles.add(ChatColor.ITALIC);
                break;
        };
        return fontStyles;
    }
}
