package view2.world;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;


// TODO: Use in NegotiationWorldState
public class NegotiationEntityInvalidationEventListener implements Listener {

    private ArrayList<INegotiationEntityInvalidationListener> listeners;
    private Entity entity;

    public NegotiationEntityInvalidationEventListener(Entity entity) {
        this.listeners = new ArrayList<>();
        this.entity = entity;
    }

    private void runListeners() {
        for (INegotiationEntityInvalidationListener listener : listeners)
            listener.onEntityInvalidated(entity);
    }

    public void addListener(INegotiationEntityInvalidationListener listener)
    {
        this.listeners.add(listener);
    }

    public void removeListener(INegotiationEntityInvalidationListener listener)
    {
        this.listeners.remove(listener);
    }

    public void clearListeners()
    {
        this.listeners.clear();
    }
}
