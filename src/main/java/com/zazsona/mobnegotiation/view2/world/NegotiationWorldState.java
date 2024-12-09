package com.zazsona.mobnegotiation.view2.world;

import com.zazsona.mobnegotiation.MobNegotiationPlugin;
import com.zazsona.mobnegotiation.view2.lib.IRenderStateListener;
import com.zazsona.mobnegotiation.view2.lib.RenderListenable;
import com.zazsona.mobnegotiation.view2.lib.RenderState;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.security.InvalidParameterException;

public class NegotiationWorldState extends RenderListenable {

    private Plugin plugin;
    private Mob negotiatingMob;
    private Player negotiatingPlayer;

    private NegotiationEntityState negotiationMobState;
    private NegotiationEntityState negotiationPlayerState;
    private IRenderStateListener<RenderListenable> entityStateDestroyedListener;

    public NegotiationWorldState(Mob negotiatingMob, Player negotiatingPlayer)
    {
        super();
        this.plugin = MobNegotiationPlugin.getInstance();
        this.negotiatingMob = negotiatingMob;
        this.negotiatingPlayer = negotiatingPlayer;
        this.entityStateDestroyedListener = (entityState, prevState, newState) -> {
            if (newState == RenderState.DESTROYED)
                destoy();
        };
    }

    public Mob getNegotiatingMob()
    {
        return negotiatingMob;
    }

    public Player getNegotiatingPlayer()
    {
        return negotiatingPlayer;
    }

    public boolean isRendered()
    {
        return negotiationPlayerState != null && negotiationMobState != null
            && negotiationPlayerState.isRendered() && negotiationMobState.isRendered();
    }

    public void render()
    {
        if (negotiationMobState.isRendered())
            negotiationMobState.destroy();
        if (negotiationPlayerState.isRendered())
            negotiationPlayerState.destroy();

        // Build Player Location
        Location playerLocation = negotiatingPlayer.getLocation();
        Location playerLocationTarget = getEntityNegotiationLocation(negotiatingPlayer, playerLocation);
        negotiationPlayerState = new NegotiationEntityState(plugin, negotiatingPlayer, playerLocationTarget);
        negotiationPlayerState.addListener(entityStateDestroyedListener);

        // Build Mob Location
        Location mobLocation = negotiatingMob.getLocation();
        Vector mobToPlayerDirection = playerLocationTarget.toVector().subtract(mobLocation.toVector());
        mobLocation.setDirection(mobToPlayerDirection);
        mobLocation.setPitch(50); // Look down; Sad expression
        Location mobLocationTarget = getEntityNegotiationLocation(negotiatingMob, mobLocation);
        negotiationMobState = new NegotiationEntityState(plugin, negotiatingMob, mobLocationTarget);
        negotiationMobState.addListener(entityStateDestroyedListener);

        // Render
        negotiationPlayerState.render();
        negotiationMobState.render();
        super.render();
    }

    public void destoy()
    {
        negotiationPlayerState.destroy();
        negotiationMobState.destroy();
        super.destroy();
    }

    /**
     * Teleports the entity to a valid position within their current X/Z co-ordinates for negotiation.
     * @param entity the entity to position
     * @param location the approximate location the entity should be (normally the entity's current location)
     * @throws InvalidParameterException no valid position could be found for the entity
     */
    private Location getEntityNegotiationLocation(Entity entity, Location location)
    {
        World world = entity.getWorld();
        int entityY = location.getBlockY();
        for (int yIndex = entityY; yIndex >= entity.getWorld().getMinHeight(); yIndex--)
        {
            Block block = world.getBlockAt(location.getBlockX(), yIndex, location.getBlockZ());
            if (block != null && block.getType().isSolid())
                return new Location(world, location.getX(), (yIndex + 1), location.getZ(), location.getYaw(), location.getPitch());
        }
        throw new InvalidParameterException(String.format("Entity %s is not in a valid negotiating position (%s)", entity.getName(), entity.getLocation()));
    }
}
