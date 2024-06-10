package view2.lib.chat.control.input;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import view2.lib.chat.ChatUI;
import view2.lib.chat.interact.ITriggerEventEmittable;
import view2.lib.chat.interact.ITriggerListener;
import view2.lib.chat.interact.PlayerTriggerEvent;
import view2.lib.chat.interact.cursor.IChatClickCommandEventEmittable;
import view2.lib.chat.interact.cursor.IChatClickCommandListener;
import view2.lib.chat.control.IChatHoverable;
import view2.lib.chat.control.IChatInteractableControl;

import java.util.*;

public class ChatButton implements IChatInteractableControl, ITriggerEventEmittable, IChatHoverable {
    private final UUID uuid;
    private boolean isUniversallyInteractable;
    private Set<Player> interactablePlayers;
    private final ArrayList<ITriggerListener> triggerListeners;
    private final TextComponent chatComponent;
    private final IChatClickCommandEventEmittable clickCommandEventEmittable;
    private final IChatClickCommandListener clickCommandListener;

    public ChatButton() {
        this(ChatUI.getClickCommandEventEmitter(), null);
    }

    public ChatButton(IChatClickCommandEventEmittable clickCommandEventEmittable) {
        this(clickCommandEventEmittable, null);
    }

    public ChatButton(Set<Player> interactablePlayers) {
        this(ChatUI.getClickCommandEventEmitter(), interactablePlayers);
    }

    public ChatButton(IChatClickCommandEventEmittable clickCommandEventEmittable, Set<Player> interactablePlayers) {

        this.triggerListeners = new ArrayList<>();
        this.uuid = UUID.randomUUID();
        this.interactablePlayers = (interactablePlayers == null) ? new HashSet<>() : interactablePlayers;
        this.isUniversallyInteractable = (interactablePlayers == null);

        this.clickCommandEventEmittable = clickCommandEventEmittable;
        this.clickCommandListener = e -> {
            boolean isSelfClick = e.getControlId().equals(uuid);
            boolean isPlayerInteraction = (e.getCommandSender() != null && e.getCommandSender() instanceof Player && (isUniversallyInteractable || interactablePlayers.contains((Player) e.getCommandSender())));

            if (isSelfClick && isPlayerInteraction)
                onButtonTriggered((Player) e.getCommandSender());
            return isSelfClick && isPlayerInteraction;
        };
        this.clickCommandEventEmittable.addListener(this.clickCommandListener);

        this.chatComponent = new TextComponent();
        this.chatComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, clickCommandEventEmittable.getFormattedCommand(this)));
    }

    public void setChatComponent(List<BaseComponent> components) {
        this.chatComponent.setExtra(components);
    }

    public void setChatComponent(BaseComponent component) {
        this.setChatComponent(List.of(component));
    }

    public List<BaseComponent> getChatComponent() {
        return this.chatComponent.getExtra();
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public void destroy() {
        this.clickCommandEventEmittable.removeListener(this.clickCommandListener);
    }

    @Override
    public void addListener(ITriggerListener listener)
    {
        triggerListeners.add(listener);
    }

    @Override
    public boolean removeListener(ITriggerListener listener)
    {
        return triggerListeners.remove(listener);
    }

    @Override
    public int clearListeners()
    {
        int listenerCount = triggerListeners.size();
        triggerListeners.clear();
        return listenerCount;
    }

    @Override
    public HoverEvent getHoverEvent() {
        return chatComponent.getHoverEvent();
    }

    @Override
    public void setHoverEvent(HoverEvent hoverEvent) {
        chatComponent.setHoverEvent(hoverEvent);
    }

    @Override
    public void addInteractablePlayer(Player player) {
        interactablePlayers.add(player);
    }

    @Override
    public void removeInteractablePlayer(Player player) {
        interactablePlayers.remove(player);
    }

    @Override
    public void clearInteractablePlayers() {
        interactablePlayers.clear();
    }

    @Override
    public void setUniversallyInteractable(boolean isUniversallyInteractable) {
        this.isUniversallyInteractable = isUniversallyInteractable;
    }

    @Override
    public boolean isUniversallyInteractable() {
        return isUniversallyInteractable;
    }

    @Override
    public BaseComponent render() {
        return chatComponent.duplicate();
    }

    private void onButtonTriggered(Player triggeringPlayer) {
        PlayerTriggerEvent event = new PlayerTriggerEvent(this, triggeringPlayer);
        for (ITriggerListener listener : triggerListeners) {
            listener.onTrigger(event);
        }
    }
}
