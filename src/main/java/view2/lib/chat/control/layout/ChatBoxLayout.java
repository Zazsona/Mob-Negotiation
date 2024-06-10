package view2.lib.chat.control.layout;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import view2.lib.chat.control.IChatControl;
import view2.lib.chat.control.IChatLayoutControl;

import java.util.ArrayList;
import java.util.UUID;

public abstract class ChatBoxLayout implements IChatLayoutControl {
    private final UUID uuid;
    private ArrayList<IChatControl> controls;

    public ChatBoxLayout() {
        this.uuid = UUID.randomUUID();
        this.controls = new ArrayList<>();
    }

    public void appendControl(IChatControl control) {
        controls.add(control);
    }

    public void addControl(int index, IChatControl control) {
        controls.add(index, control);
    }

    public IChatControl removeControl(int index) {
        return controls.remove(index);
    }

    public boolean removeControl(IChatControl control) {
        return controls.remove(control);
    }

    @Override
    public IChatControl[] getControls() {
        return controls.toArray(new IChatControl[0]);
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    protected BaseComponent render(String separator) {
        TextComponent rootComponent = new TextComponent();
        for (IChatControl control : controls) {
            rootComponent.addExtra(control.render());
            rootComponent.addExtra(separator);
        }
        return rootComponent;
    }

    @Override
    public void destroy() {
        for (IChatControl control : controls) {
            control.destroy();
        }
        controls.clear();
    }
}
