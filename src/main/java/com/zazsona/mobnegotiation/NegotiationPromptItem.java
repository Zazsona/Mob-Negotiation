package com.zazsona.mobnegotiation;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;

public class NegotiationPromptItem
{
    private String text;
    private NegotiationPromptItemType type;
    private HoverEvent hoverEvent;

    public NegotiationPromptItem(String text, NegotiationPromptItemType type, HoverEvent hoverEvent)
    {
        this.text = text;
        this.type = type;
        this.hoverEvent = hoverEvent;
    }

    public NegotiationPromptItem(String text, NegotiationPromptItemType type)
    {
        this.text = text;
        this.type = type;
        this.hoverEvent = null;
    }

    /**
     * Gets the text to display for this item
     * @return item text
     */
    public String getText()
    {
        return text;
    }

    /**
     * Sets the text to display for this item
     * @param text item text
     */
    public void setText(String text)
    {
        this.text = text;
    }

    /**
     * Gets the action type of this item
     * @return action type
     */
    public NegotiationPromptItemType getItemType()
    {
        return type;
    }


    /**
     * Sets the action type of this item
     * @param type action type
     */
    public void setColour(NegotiationPromptItemType type)
    {
        this.type = type;
    }

    /**
     * Gets the hover event for this item
     * @return the hover event
     */
    public HoverEvent getHoverEvent()
    {
        return hoverEvent;
    }

    /**
     * Sets the hover event for this item
     * @param hoverEvent the hover event
     */
    public void setHoverEvent(HoverEvent hoverEvent)
    {
        this.hoverEvent = hoverEvent;
    }

    /**
     * Creates a printable BaseComponent array based on the properties of this instance for presentation to the user.
     * @return this prompt item, expressed in a printable {@link BaseComponent} array
     */
    public BaseComponent[] getFormattedComponent()
    {
        ComponentBuilder componentBuilder = new ComponentBuilder()
                .reset()
                .color(getColour())
                .append(String.format("%s ", getIcon()))
                .append(String.format("[%s]", getText()))
                .italic(type == NegotiationPromptItemType.SPEECH);

        if (getHoverEvent() != null)
            componentBuilder.event(getHoverEvent());

        return componentBuilder.create();
    }

    /**
     * Gets the text colour associated with this item type
     * @return the {@link ChatColor} to print this item in.
     */
    public ChatColor getColour()
    {
        return switch (type)
                {
                    case SPEECH -> ChatColor.WHITE;
                    case ATTACK -> ChatColor.RED;
                    case CANCEL -> ChatColor.GRAY;
                    default -> ChatColor.WHITE;
                };
    }

    /**
     * Gets the icon associated with this item type
     * @return the icon to print this item with.
     */
    private String getIcon()
    {
        switch (type)
        {
            case SPEECH:
            case ATTACK:
            case CANCEL:
            default:
                return "\u2794";
        }
    }

    /**
     * A class denoting the action type this prompt item represents
     */
    public enum NegotiationPromptItemType
    {
        SPEECH,
        ATTACK,
        CANCEL
    }
}
