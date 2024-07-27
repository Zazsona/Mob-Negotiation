package view2.lib;

public enum RenderState {
    INITIALISED(false),
    RENDERED(true),
    DESTROYED(false);

    private boolean isRenderedState;

    RenderState(boolean isRenderedState)
    {
        this.isRenderedState = isRenderedState;
    }

    public boolean isRenderedState()
    {
        return isRenderedState;
    }
}
