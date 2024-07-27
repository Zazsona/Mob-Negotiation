package view2.lib.world.entity.state;

import view2.lib.RenderState;

import java.util.ArrayList;

public abstract class RenderListenableEntityState implements IEntityState {

    private RenderState renderState = RenderState.INITIALISED;
    private ArrayList<IEntityStateRenderedListener> listeners;

    public boolean addListener(IEntityStateRenderedListener listener)
    {
        return this.listeners.add(listener);
    }

    public boolean removeListener(IEntityStateRenderedListener listener)
    {
        return this.listeners.remove(listener);
    }

    @Override
    public void render() {
        updateRenderState(RenderState.RENDERED);
    }

    @Override
    public void destroy() {
        updateRenderState(RenderState.DESTROYED);
    }

    protected void updateRenderState(RenderState newState)
    {
        RenderState prevState = this.renderState;
        renderState = newState;
        callListeners(prevState, newState);
    }

    protected void callListeners(RenderState prevState, RenderState newState)
    {
        for (IEntityStateRenderedListener listener : listeners)
            listener.onEntityStateRenderChange(this, prevState, newState);
    }
}
