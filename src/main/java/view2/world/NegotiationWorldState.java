package view2.world;

import com.zazsona.mobnegotiation.MobNegotiationPlugin;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import view2.lib.world.entity.state.freeze.StaticEntityState;
import view2.lib.world.entity.state.freeze.StaticEntityStateFactory;
import view2.lib.world.entity.state.freeze.StaticPlayerState;
import view2.world.state.NegotiationStaticEntityStateFactory;

import java.security.InvalidParameterException;

public class NegotiationWorldState {

    private Plugin plugin;
    private Mob negotiatingMob;
    private Player negotiatingPlayer;

    private StaticEntityState staticMobState;
    private StaticEntityState staticPlayerState;

    public NegotiationWorldState(Mob negotiatingMob, Player negotiatingPlayer)
    {
        this.plugin = MobNegotiationPlugin.getInstance();
        this.negotiatingMob = negotiatingMob;
        this.negotiatingPlayer = negotiatingPlayer;
    }

    public Mob getNegotiatingMob()
    {
        return negotiatingMob;
    }

    public Player getNegotiatingPlayer()
    {
        return negotiatingPlayer;
    }

    public void render()
    {
        if (staticMobState.isRendered())
            staticMobState.destroy();
        if (staticPlayerState.isRendered())
            staticPlayerState.destroy();

        StaticEntityStateFactory stateFactory = new StaticEntityStateFactory();

        // Build Player Location
        Location playerLocationTarget = negotiatingPlayer.getLocation();
        Location playerLocation = getEntityNegotiationLocation(negotiatingPlayer, playerLocationTarget);

        staticPlayerState = stateFactory.create(plugin, negotiatingPlayer);
        staticPlayerState.setLocation(playerLocation);

        // Build Mob Location
        Location mobLocationTarget = negotiatingMob.getLocation();
        Vector mobToPlayerDirection = playerLocationTarget.toVector().subtract(mobLocationTarget.toVector());
        mobLocationTarget.setDirection(mobToPlayerDirection);
        mobLocationTarget.setPitch(50); // Look down; Sad expression
        Location mobLocation = getEntityNegotiationLocation(negotiatingMob, mobLocationTarget);

        staticMobState = stateFactory.create(plugin, negotiatingMob);
        staticMobState.setLocation(mobLocation);

        // Render
        staticPlayerState.render();
        staticMobState.render();

        // TODO: Invincibility, Invalidation (e.g Player disconnect)
    }

    public void destoy()
    {

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
