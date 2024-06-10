package view2.lib.chat.control.output;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import view2.lib.chat.control.IChatControl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChatTextField implements IChatControl {
    private final UUID uuid;

    private final TextComponent chatComponent;

    public ChatTextField() {
        this.uuid = UUID.randomUUID();
        this.chatComponent = new TextComponent();
    }

    public ChatTextField(String text) {
        this();
        setChatComponent(text);
    }

    public ChatTextField(BaseComponent component) {
        this();
        setChatComponent(component);
    }

    public ChatTextField(List<BaseComponent> components) {
        this();
        setChatComponent(components);
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public BaseComponent render() {
        return chatComponent.duplicate();
    }

    @Override
    public void destroy() { }

    public void setChatComponent(String text) {
        if (!this.chatComponent.getExtra().isEmpty())
            this.chatComponent.setExtra(new ArrayList<>());
        this.chatComponent.addExtra(text);
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
}
