package view2.lib.chat;

import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import view2.lib.chat.interact.cursor.ChatClickCommandEventEmitter;
import view2.lib.chat.interact.message.ChatTextInputEventEmitter;

import static org.bukkit.Bukkit.getServer;

public class ChatUI {

    // TODO: I was rewriting the "UI" layer of MobNegotiation (trying to make it as generic as possible), as though to treat Minecraft itself as a front-end framework
    // TO DO:
    // Create "Panels" (Forms) that hold Components
    // Create "World" package for organising objects in the world (Notably, positioning entities and locking them into place)
    // Create the MobNegotiation stuff to tie it all together
    // Remove all Minecraft references from the Model
    // Update the Controller to suit


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
