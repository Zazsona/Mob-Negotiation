package view2.lib;

import java.util.ArrayList;

public abstract class RenderListenable {

    private RenderState renderState = RenderState.INITIALISED;
    private ArrayList<IRenderStateListener<RenderListenable>> listeners;

    public RenderListenable()
    {
        this.listeners = new ArrayList<>();
    }

    public boolean addListener(IRenderStateListener<RenderListenable> listener)
    {
        return this.listeners.add(listener);
    }

    public boolean removeListener(IRenderStateListener<RenderListenable> listener)
    {
        return this.listeners.remove(listener);
    }

    public void render() {
        updateRenderState(RenderState.RENDERED);
    }

    public void destroy() {
        updateRenderState(RenderState.DESTROYED);
    }

    protected void updateRenderState(RenderState newState)
    {
        RenderState prevState = this.renderState;
        renderState = newState;
        callRenderStateListeners(prevState, newState);
    }

    protected void callRenderStateListeners(RenderState prevState, RenderState newState)
    {
        for (IRenderStateListener<RenderListenable> listener : listeners)
            listener.onRenderStateChanged(this, prevState, newState);
    }
}
