package com.zazsona.mobnegotiation;

import com.zazsona.mobnegotiation.NegotiationPromptItem.NegotiationPromptItemType;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NegotiationPromptBuilder
{
    private UUID negotiationId;
    private List<NegotiationPromptItem> speechItems;
    private List<NegotiationPromptItem> attackItems;
    private List<NegotiationPromptItem> cancelItems;
    private String headerText;
    private ChatColor headerTextColour;

    public NegotiationPromptBuilder(UUID negotiationId)
    {
        this.negotiationId = negotiationId;
        this.speechItems = new ArrayList<>();
        this.attackItems = new ArrayList<>();
        this.cancelItems = new ArrayList<>();
        this.headerText = null;
        this.headerTextColour = ChatColor.GRAY;
    }

    /**
     * Adds the specified {@link NegotiationPromptItem} to this prompt.
     * Note that ordering is based on {@link NegotiationPromptItemType}, with insertion order only retained within same-type entries.
     * @param item the item to add
     * @return self
     */
    public NegotiationPromptBuilder addItem(NegotiationPromptItem item)
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
     * Sets the header text to display above the items.
     * @param text the text to display
     * @return self
     */
    public NegotiationPromptBuilder setHeaderText(String text)
    {
        this.headerText = text;
        return this;
    }

    /**
     * Sets the colour for the header text to display above the items.
     * @param colour the header text colour
     * @return self
     */
    public NegotiationPromptBuilder setHeaderTextColour(ChatColor colour)
    {
        this.headerTextColour = colour;
        return this;
    }

    /**
     * Creates a representation of this prompt as an array of BaseComponents for presentation to users
     * @return a {@link BaseComponent} array
     */
    public BaseComponent[] build()
    {
        ComponentBuilder promptBuilder = new ComponentBuilder().append("\n");
        if (headerText != null)
            promptBuilder.append("  ").append(headerText).color(headerTextColour).reset();

        List<NegotiationPromptItem> items = new ArrayList<>(speechItems);
        items.addAll(attackItems);
        items.addAll(cancelItems);

        for (int i = 0; i < items.size(); i++)
        {
            NegotiationPromptItem item = items.get(i);
            promptBuilder
                    .append("  ")
                    .append(item.getFormattedComponent(), ComponentBuilder.FormatRetention.ALL)
                    .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/%s %s %s", NegotiationResponseCommandExecutor.COMMAND_KEY, negotiationId.toString(), i))) //TODO: Add this command
                    .append("\n")
                    .reset();
        }


        return promptBuilder.create();
    }

}
