package view2.lib.world.entity.state;

import view2.lib.RenderState;

public interface IEntityStateRenderedListener {
    void onEntityStateRenderChange(IEntityState entityState, RenderState previousState, RenderState newState);
}
