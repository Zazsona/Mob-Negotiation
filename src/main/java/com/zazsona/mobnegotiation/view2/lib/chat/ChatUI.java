package com.zazsona.mobnegotiation.view2.lib.chat;

import com.zazsona.mobnegotiation.view2.lib.chat.interact.cursor.ChatClickCommandEventEmitter;
import com.zazsona.mobnegotiation.view2.lib.chat.interact.message.ChatTextInputEventEmitter;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

import static org.bukkit.Bukkit.getServer;

public class ChatUI {
    private static boolean IS_INITIALISED = false;

    protected static ChatClickCommandEventEmitter clickCommandEventEmitter;
    protected static ChatTextInputEventEmitter textInputEventEmitter;

    public static void initialise(@NonNull JavaPlugin plugin, String clickActionCommandKey) {

        if (IS_INITIALISED)
            throw new IllegalStateException("ChatUI has already been initialised.");
        // We can't just set it to a default as the command key needs to be registered w/ Bukkit via plugin.yml
        if (clickActionCommandKey == null)
            plugin.getLogger().warning("Plugin \"" + plugin.getName() + "\" has not provided a clickActionCommandKey. Some ChatUI actions may not function properly.");

        if (clickActionCommandKey != null) {
            clickCommandEventEmitter = new ChatClickCommandEventEmitter(clickActionCommandKey);
            plugin.getCommand(clickActionCommandKey).setExecutor(clickCommandEventEmitter);
        }
        textInputEventEmitter = new ChatTextInputEventEmitter();
        getServer().getPluginManager().registerEvents(textInputEventEmitter, plugin);

        IS_INITIALISED = true;
    }

    public static ChatClickCommandEventEmitter getClickCommandEventEmitter() {
        return clickCommandEventEmitter;
    }

    public static ChatTextInputEventEmitter getTextInputEventEmitter() {
        return textInputEventEmitter;
    }
}
