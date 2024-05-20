package com.zazsona.mobnegotiation.view;

import com.zazsona.mobnegotiation.view.interfaces.IContainerNegotiationView;
import com.zazsona.mobnegotiation.view.interfaces.INegotiationView;
import com.zazsona.mobnegotiation.view.interfaces.IViewInteractionExecutor;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.*;

public class NegotiationMenu implements IContainerNegotiationView
{
    private String id;
    private ArrayList<INegotiationView> children;
    private String headerText;
    private ChatColor headerTextColour;
    private IViewInteractionExecutor interactionExecutor;

    public NegotiationMenu(IViewInteractionExecutor interactionExecutor)
    {
        this.id = UUID.randomUUID().toString();
        this.children = new ArrayList<>();
        this.headerText = null;
        this.headerTextColour = ChatColor.WHITE;
        this.interactionExecutor = interactionExecutor;
        this.interactionExecutor.addView(this);
    }

    @Override
    public INegotiationView getParent()
    {
        return null;
    }

    @Override
    public String getId()
    {
        return id;
    }

    @Override
    public INegotiationView getChild(String id)
    {
        for (INegotiationView view : children)
        {
            if (view.getId().equals(id))
                return view;
        }
        return null;
    }

    @Override
    public ArrayList<INegotiationView> getChildren()
    {
        return children;
    }

    @Override
    public boolean hasChild(String id)
    {
        return getChild(id) != null;
    }

    @Override
    public boolean hasChild(INegotiationView view)
    {
        return hasChild(view.getId());
    }

    @Override
    public boolean addChild(INegotiationView child)
    {
        children.add(child);
        return true;
    }

    @Override
    public boolean removeChild(INegotiationView child)
    {
        return children.remove(child);
    }

    /**
     * Sets the header text to display above the items.
     * @param text the text to display
     * @return self
     */
    public NegotiationMenu setHeaderText(String text)
    {
        this.headerText = text;
        return this;
    }

    /**
     * Sets the colour for the header text to display above the items.
     * @param colour the header text colour
     * @return self
     */
    public NegotiationMenu setHeaderTextColour(ChatColor colour)
    {
        this.headerTextColour = colour;
        return this;
    }

    /**
     * Creates a representation of this prompt as an array of BaseComponents for presentation to users
     * @return a {@link BaseComponent} array
     */
    public BaseComponent[] getFormattedComponent()
    {
        return getFormattedComponent(false);
    }

    /**
     * Creates a representation of this prompt as an array of BaseComponents for presentation to users
     * @param includeInstructions - Sets whether to include the GUI usage instructions
     * @return a {@link BaseComponent} array
     */
    public BaseComponent[] getFormattedComponent(boolean includeInstructions)
    {
        ComponentBuilder promptBuilder = new ComponentBuilder();
        boolean hasElements = !getChildren().isEmpty();

        if (headerText != null)
        {
            TextComponent headerComponent = new TextComponent();
            String verticalSpacer = (hasElements) ? "\n" : "";
            headerComponent.setColor(headerTextColour);
            headerComponent.setText(String.format("%s%s%s", verticalSpacer, headerText, verticalSpacer));
            promptBuilder.append(headerComponent, ComponentBuilder.FormatRetention.NONE);
        }

        if (!hasElements)
            return promptBuilder.create();

        if (includeInstructions)
        {
            TextComponent instructionsComponent = new TextComponent();
            instructionsComponent.setItalic(true);
            instructionsComponent.setColor(ChatColor.GRAY);
            instructionsComponent.setText("To select an option, click it or enter the matched number in chat...");
            promptBuilder.append("\n").append(instructionsComponent, ComponentBuilder.FormatRetention.NONE);
        }

        ArrayList<INegotiationView> childViews = getChildren();
        for (int i = 0; i < childViews.size(); i++)
        {
            INegotiationView childView = childViews.get(i);
            String command = String.format("/%s %s %s", NegotiationViewInteractionExecutor.COMMAND_KEY, id, childView.getId());

            ComponentBuilder buttonBuilder = new ComponentBuilder();
            buttonBuilder
                    .color(ChatColor.GRAY)
                    .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command))
                    .append(String.format("[%d] ", (i + 1)))
                    .append(childView.getFormattedComponent(), ComponentBuilder.FormatRetention.FORMATTING);
            promptBuilder.append("\n").append(buttonBuilder.create(), ComponentBuilder.FormatRetention.NONE);
        }
        promptBuilder.append("\n");
        return promptBuilder.create();
    }
}
