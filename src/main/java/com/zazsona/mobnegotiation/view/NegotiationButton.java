package com.zazsona.mobnegotiation.view;

import com.zazsona.mobnegotiation.view.interfaces.ISelectableNegotiationView;
import com.zazsona.mobnegotiation.view.interfaces.INegotiationView;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NegotiationButton implements ISelectableNegotiationView
{
    private INegotiationView parent;
    private String itemId;
    private String text;
    private String icon;
    private boolean italic;
    private boolean bold;
    private boolean underline;
    private boolean strikethrough;
    private ChatColor colour;
    private HoverEvent hoverEvent;
    private List<NegotiationButtonSelectListener> listeners;

    public NegotiationButton(String text, String icon, ChatColor colour, INegotiationView parent, HoverEvent hoverEvent)
    {
        this.itemId = UUID.randomUUID().toString();
        this.text = text;
        this.icon = icon;
        this.colour = colour;
        this.parent = parent;
        this.hoverEvent = hoverEvent;
        this.listeners = new ArrayList<>();
    }

    public NegotiationButton(String text, String icon, ChatColor colour, INegotiationView parent)
    {
        this.itemId = UUID.randomUUID().toString();
        this.text = text;
        this.icon = icon;
        this.colour = colour;
        this.parent = parent;
        this.hoverEvent = null;
        this.listeners = new ArrayList<>();
    }

    /**
     * Adds a listener that gets fired when this button is selected
     * @param listener the listener to add
     */
    public void addListener(NegotiationButtonSelectListener listener)
    {
        listeners.add(listener);
    }

    /**
     * Removes a listener that gets fired when this button is selected
     * @param listener the listener to remove
     * @return boolean on removed
     */
    public boolean removeListener(NegotiationButtonSelectListener listener)
    {
        return listeners.remove(listener);
    }

    /**
     * Removes all listeners
     * @return the # of removed listeners
     */
    @Override
    public int clearListeners()
    {
        int listenerCount = listeners.size();
        listeners.clear();
        return listenerCount;
    }

    /**
     * Activates the button.
     */
    public void select()
    {
        for (int i = listeners.size() - 1; i > -1; i--)
            listeners.get(i).onButtonSelected(this);
    }

    /**
     * Gets the parent view.
     * @return the parent view, or null if root.
     */
    public INegotiationView getParent()
    {
        return parent;
    }

    /**
     * Gets itemId
     * @return itemId
     */
    public String getId()
    {
        return itemId;
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
     * Gets the prefixing icon associated this button
     * @return the icon to print this item with.
     */
    public String getIcon()
    {
        return icon;
        //return "\u2794";
    }

    /**
     * Sets the prefixing icon associated this button
     * @param icon the icon to print this item with.
     */
    public void setIcon(String icon)
    {
        this.icon = icon;
    }

    /**
     * Gets the colour of this button
     * @return the button colour
     */
    public ChatColor getColour()
    {
        return colour;
    }


    /**
     * Sets the colour of this button
     * @param colour the colour to set
     */
    public void setColour(ChatColor colour)
    {
        this.colour = colour;
    }

    /**
     * Gets italic
     * @return italic
     */
    public boolean isItalic()
    {
        return italic;
    }

    public void setItalic(boolean italic)
    {
        this.italic = italic;
    }

    /**
     * Gets bold
     * @return bold
     */
    public boolean isBold()
    {
        return bold;
    }

    public void setBold(boolean bold)
    {
        this.bold = bold;
    }

    /**
     * Gets underline
     * @return underline
     */
    public boolean isUnderline()
    {
        return underline;
    }

    public void setUnderline(boolean underline)
    {
        this.underline = underline;
    }

    /**
     * Gets strikethrough
     * @return strikethrough
     */
    public boolean isStrikethrough()
    {
        return strikethrough;
    }

    public void setStrikethrough(boolean strikethrough)
    {
        this.strikethrough = strikethrough;
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
                .append(getIcon())
                .append(" ")
                .append("[")
                .italic(italic)
                .bold(bold)
                .strikethrough(strikethrough)
                .underlined(underline)
                .append(getText() + ChatColor.RESET + getColour() + "]", ComponentBuilder.FormatRetention.FORMATTING);

        if (getHoverEvent() != null)
            componentBuilder.event(getHoverEvent());

        return componentBuilder.create();
    }
}
