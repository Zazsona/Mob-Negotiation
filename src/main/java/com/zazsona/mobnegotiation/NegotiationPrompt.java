package com.zazsona.mobnegotiation;

import com.zazsona.mobnegotiation.NegotiationPromptItem.NegotiationPromptItemType;
import com.zazsona.mobnegotiation.command.NegotiationResponseCommand;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NegotiationPrompt
{
    private String promptId;
    private List<NegotiationPromptItem> speechItems;
    private List<NegotiationPromptItem> attackItems;
    private List<NegotiationPromptItem> cancelItems;
    private String headerText;
    private ChatColor headerTextColour;

    public NegotiationPrompt()
    {
        this.promptId = UUID.randomUUID().toString();
        this.speechItems = new ArrayList<>();
        this.attackItems = new ArrayList<>();
        this.cancelItems = new ArrayList<>();
        this.headerText = null;
        this.headerTextColour = ChatColor.GRAY;
    }

    /**
     * Gets promptId
     * @return promptId
     */
    public String getPromptId()
    {
        return promptId;
    }

    /**
     * Gets the item denoted by the itemId, or null.
     * @param itemId the item's id
     * @return the item, or null if unavailable.
     */
    public NegotiationPromptItem getItem(String itemId)
    {
        List<NegotiationPromptItem> items = new ArrayList<>(speechItems);
        items.addAll(attackItems);
        items.addAll(cancelItems);
        for (NegotiationPromptItem item : items)
        {
            if (item.getItemId().equals(itemId))
                return item;
        }
        return null;
    }

    /**
     * Checks if the item with the specified id belongs to this prompt
     * @param itemId the id to check
     * @return true on item in prompt
     */
    public boolean hasItem(String itemId)
    {
        return getItem(itemId) != null;
    }

    /**
     * Adds the specified {@link NegotiationPromptItem} to this prompt.
     * Note that ordering is based on {@link NegotiationPromptItemType}, with insertion order only retained within same-type entries.
     * @param item the item to add
     * @return self
     */
    public NegotiationPrompt addItem(NegotiationPromptItem item)
    {
        switch (item.getItemType())
        {
            case SPEECH -> speechItems.add(item);
            case ATTACK -> attackItems.add(item);
            case CANCEL -> cancelItems.add(item);
        }
        return this;
    }

    /**
     * Removes the specified {@link NegotiationPromptItem} from this prompt, if it exists.
     * @param item the item to remove
     * @return self
     */
    public NegotiationPrompt removeItem(NegotiationPromptItem item)
    {
        switch (item.getItemType())
        {
            case SPEECH -> speechItems.remove(item);
            case ATTACK -> attackItems.remove(item);
            case CANCEL -> cancelItems.remove(item);
        }
        return this;
    }

    /**
     * Checks if the specified {@link NegotiationPromptItem} is on this prompt.
     * @param item the item to check
     * @return true on has
     */
    public boolean hasItem(NegotiationPromptItem item)
    {
        return switch (item.getItemType())
                {
                    case SPEECH -> speechItems.contains(item);
                    case ATTACK -> attackItems.contains(item);
                    case CANCEL -> cancelItems.contains(item);
                };
    }

    /**
     * Sets the header text to display above the items.
     * @param text the text to display
     * @return self
     */
    public NegotiationPrompt setHeaderText(String text)
    {
        this.headerText = text;
        return this;
    }

    /**
     * Sets the colour for the header text to display above the items.
     * @param colour the header text colour
     * @return self
     */
    public NegotiationPrompt setHeaderTextColour(ChatColor colour)
    {
        this.headerTextColour = colour;
        return this;
    }

    /**
     * Creates a representation of this prompt as an array of BaseComponents for presentation to users
     * @param negotiationId The negotiation to associate item ClickEvents with.
     * @return a {@link BaseComponent} array
     */
    public BaseComponent[] getFormattedComponent(String negotiationId)
    {
        ComponentBuilder promptBuilder = new ComponentBuilder().append("\n");
        if (headerText != null)
            promptBuilder.append("  ").append(headerText).color(headerTextColour).reset();

        List<NegotiationPromptItem> items = new ArrayList<>(speechItems);
        items.addAll(attackItems);
        items.addAll(cancelItems);

        for (NegotiationPromptItem item : items)
        {
            promptBuilder
                    .append("  ")
                    .append(item.getFormattedComponent(), ComponentBuilder.FormatRetention.ALL)
                    .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %s %s %s", NegotiationResponseCommand.COMMAND_KEY, negotiationId, promptId, item.getItemId())))
                    .append("\n")
                    .reset();
        }
        return promptBuilder.create();
    }
}
