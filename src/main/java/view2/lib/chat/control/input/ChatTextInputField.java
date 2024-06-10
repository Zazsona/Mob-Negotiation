package view2.lib.chat.control.input;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import view2.lib.chat.ChatUI;
import view2.lib.chat.interact.ITriggerEventEmittable;
import view2.lib.chat.interact.ITriggerListener;
import view2.lib.chat.interact.TextInputPlayerTriggerEvent;
import view2.lib.chat.interact.message.ChatTextInputEventEmitter;
import view2.lib.chat.interact.message.IChatTextInputEventEmittable;
import view2.lib.chat.interact.message.IChatTextInputListener;
import view2.lib.chat.control.IChatHoverable;
import view2.lib.chat.control.IChatInteractableControl;

import java.util.*;

public class ChatTextInputField implements IChatInteractableControl, ITriggerEventEmittable, IChatHoverable {
    private final UUID uuid;
    private boolean isUniversallyInteractable;
    private Set<Player> interactablePlayers;
    private final ArrayList<ITriggerListener> triggerListeners;
    private final TextComponent chatComponent;
    private final IChatTextInputEventEmittable textInputEventEmittable;
    private final IChatTextInputListener textInputListener;

    public ChatTextInputField() {
        this(ChatUI.getTextInputEventEmitter(), null);
    }

    public ChatTextInputField(IChatTextInputEventEmittable textInputEventEmittable) {
        this(textInputEventEmittable, null);
    }

    public ChatTextInputField(Set<Player> interactablePlayers) {
        this(ChatUI.getTextInputEventEmitter(), interactablePlayers);
    }

    public ChatTextInputField(IChatTextInputEventEmittable textInputEventEmittable, Set<Player> interactablePlayers) {

        this.chatComponent = new TextComponent();
        this.triggerListeners = new ArrayList<>();
        this.uuid = UUID.randomUUID();
        this.interactablePlayers = (interactablePlayers == null) ? new HashSet<>() : interactablePlayers;
        this.isUniversallyInteractable = (interactablePlayers == null);

        this.textInputEventEmittable = textInputEventEmittable;
        this.textInputListener = e -> {
            boolean isPlayerInteraction = (e.getPlayer() != null && (isUniversallyInteractable || interactablePlayers.contains(e.getPlayer())));
            if (!isPlayerInteraction)
                return false;

            TextInputPlayerTriggerEvent event = new TextInputPlayerTriggerEvent(this, e.getPlayer(), e.getText());
            for (ITriggerListener listener : triggerListeners)
                listener.onTrigger(event);

            return true;
        };
        this.textInputEventEmittable.addListener(this.textInputListener);
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
        this.textInputEventEmittable.removeListener(this.textInputListener);
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
}
