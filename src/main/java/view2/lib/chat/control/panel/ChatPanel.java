package view2.lib.chat.control.panel;

import net.md_5.bungee.api.chat.BaseComponent;
import view2.lib.chat.control.IChatControl;
import view2.lib.chat.control.IChatLayoutControl;

import java.util.UUID;

public class ChatPanel implements IChatControl {

    private final UUID uuid;
    private IChatLayoutControl rootLayout;

    public ChatPanel(IChatLayoutControl rootLayout) {
        this.uuid = UUID.randomUUID();
        this.rootLayout = rootLayout;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public BaseComponent render() {
        return rootLayout.render();
    }

    @Override
    public void destroy() {
        rootLayout.destroy();
    }

    public IChatLayoutControl getRootLayout() {
        return rootLayout;
    }

    public void setRootLayout(IChatLayoutControl rootLayout) {
        this.rootLayout = rootLayout;
    }
}
