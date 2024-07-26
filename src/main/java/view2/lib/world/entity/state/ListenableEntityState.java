package view2.lib.world.entity.state;

import java.util.ArrayList;

public abstract class ListenableEntityState implements IEntityState {
    private ArrayList<IEntityStateListener> listeners;

    public boolean addListener(IEntityStateListener listener)
    {
        return this.listeners.add(listener);
    }

    public boolean removeListener(IEntityStateListener listener)
    {
        return this.listeners.remove(listener);
    }

    @Override
    public void render() {
        callListeners();
    }

    @Override
    public void destroy() {
        callListeners();
    }

    protected void callListeners()
    {
        for (IEntityStateListener listener : listeners)
            listener.onEntityStateChange(this);
    }
}
