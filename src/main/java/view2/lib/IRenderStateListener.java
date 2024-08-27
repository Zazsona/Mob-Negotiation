package view2.lib;

public interface IRenderStateListener<T> {
    void onRenderStateChanged(T listenable, RenderState previousState, RenderState newState);
}
