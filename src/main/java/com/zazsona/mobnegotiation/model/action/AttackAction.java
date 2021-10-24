package com.zazsona.mobnegotiation.model.action;

import com.zazsona.mobnegotiation.MobNegotiationPlugin;
import com.zazsona.mobnegotiation.model.entitystate.EntityActionLockListener;
import com.zazsona.mobnegotiation.model.entitystate.EntityInvalidatedListener;
import com.zazsona.mobnegotiation.model.entitystate.EntityInvincibilityListener;
import org.bukkit.*;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.Random;

public class AttackAction extends Action
{
    private EntityActionLockListener playerActionLock;
    private EntityInvincibilityListener mobInvincibility;
    private EntityInvalidatedListener mobInvalidator;

    private boolean active;
    private Location originalPlayerLocation;
    private boolean originalPlayerVisibility;
    private BukkitTask slashTask;
    private BukkitTask finalSlashTask;

    public AttackAction(Player player, Mob mob)
    {
        super(player, mob);
        this.playerActionLock = null;
        this.mobInvincibility = null;
        this.mobInvalidator = null;
        this.active = false;
    }

    public AttackAction(Player player, Mob mob, EntityActionLockListener playerActionLock, EntityInvincibilityListener mobInvincibility, EntityInvalidatedListener mobInvalidator)
    {
        super(player, mob);
        this.playerActionLock = playerActionLock;
        this.mobInvincibility = mobInvincibility;
        this.mobInvalidator = mobInvalidator;
        this.active = false;
    }

    /**
     * Executes the all out attack
     */
    @Override
    public void execute()
    {
        if (active)
            throw new IllegalCallerException("This action is already active.");
        active = true;
        runOnStartListeners();
        originalPlayerLocation = player.getLocation();
        originalPlayerVisibility = player.isInvisible();
        player.setInvisible(true);
        final int ticksInterval = 8; // Do not set this too high, or the anti-cheat fly checker will throw a fit.
        final int slashes = 3;
        final double offsetMaxHorizRange = 2.0f;
        final double offsetMinHorizRange = 0.75f;
        final double offsetMaxVertRange = mob.getHeight() * 1.5f;
        final double offsetMinVertRange = 0.25f;
        for (int i = 0; i < slashes; i++)
            slashTask = Bukkit.getScheduler().runTaskLater(MobNegotiationPlugin.getInstance(), () -> runAttackSlash(ticksInterval, offsetMaxHorizRange, offsetMinHorizRange, offsetMaxVertRange, offsetMinVertRange), i * ticksInterval);
        finalSlashTask = Bukkit.getScheduler().runTaskLater(MobNegotiationPlugin.getInstance(), this::runFinalAttackExplosion, slashes * ticksInterval);
    }

    /**
     * Stops the action at its current state, and calls all listeners.
     * This resets the player to their original location and visibility, but does not kill the mob.
     */
    @Override
    public void stop()
    {
        if (active)
        {
            Location targetPlayerLocation = originalPlayerLocation.clone();
            Location mobLocation = mob.getLocation();
            Vector gradientDirection = mobLocation.toVector().subtract(targetPlayerLocation.toVector());
            targetPlayerLocation.setDirection(gradientDirection);
            if (playerActionLock != null)
                playerActionLock.setLockedLocation(targetPlayerLocation);
            player.teleport(targetPlayerLocation);
            player.setInvisible(originalPlayerVisibility);
            slashTask.cancel();
            finalSlashTask.cancel();
            active = false;
            runOnCompleteListeners();
        }
    }

    /**
     * Gets if this action is currently running.
     * @return true on running.
     */
    @Override
    public boolean isActive()
    {
        return active;
    }

    /**
     * Performs a "slash" animation for the All Out Attack
     * @param tickTime the time in which to animate the slash
     * @param offsetMaxHorizRange the maximum horizontal range of the slash from the mob
     * @param offsetMinHorizRange the minimum horizontal range of the slash from the mob
     * @param offsetMaxVertRange the maximum vertical range of the slash from the mob
     * @param offsetMinVertRange the minimum vertical range of the slash from the mob
     */
    private void runAttackSlash(int tickTime, double offsetMaxHorizRange, double offsetMinHorizRange, double offsetMaxVertRange, double offsetMinVertRange)
    {
        Random r = new Random();
        World world = mob.getWorld();
        Location mobLocation = mob.getLocation();
        Location mobMidpoint = mobLocation.clone();
        mobMidpoint.setY(mobMidpoint.getY() + (mob.getHeight() / 2.0f));

        Location playerLocation = player.getLocation();
        Location playerMidpoint = playerLocation.clone();
        playerMidpoint.setY(playerMidpoint.getY() + (player.getHeight() / 2.0f));

        float mobYaw = mobLocation.getYaw();
        float directionRadius = 45;
        boolean negativeX = playerLocation.getX() > mobLocation.getX();
        boolean negativeZ = playerLocation.getZ() > mobLocation.getZ();
        if ((mobYaw < 90 + directionRadius && mobYaw > 90 - directionRadius) || (mobYaw < 270 + directionRadius && mobYaw > 270 - directionRadius))
            negativeX = r.nextBoolean();
        else                                // Make sure we always have an opposing side for better visuals, but still random for variety
            negativeZ = r.nextBoolean();
        double xOffset = ((r.nextFloat() * (offsetMaxHorizRange - offsetMinHorizRange)) + offsetMinHorizRange) * (negativeX ? -1 : 1); // Generate a random number in the range, then randomise negative flip.
        double yOffset = ((r.nextFloat() * (offsetMaxVertRange - offsetMinVertRange)) + offsetMinVertRange); // No negatives to prevent underground values
        double zOffset = ((r.nextFloat() * (offsetMaxHorizRange - offsetMinHorizRange)) + offsetMinHorizRange) * (negativeZ ? -1 : 1); // Generate a random number in the range, then randomise negative flip.
        Location attackLocation = new Location(mobLocation.getWorld(), mobLocation.getX() + xOffset, mobLocation.getY() + yOffset, mobLocation.getZ() + zOffset);

        Location targetPlayerLoc = attackLocation.clone();
        targetPlayerLoc.setY(targetPlayerLoc.getY() - (player.getHeight() / 2.0f));
        Vector gradientDirection = mobLocation.toVector().subtract(targetPlayerLoc.toVector());
        targetPlayerLoc.setDirection(gradientDirection);

        Location targetPlayerMidpoint = targetPlayerLoc.clone();
        targetPlayerMidpoint.setY(targetPlayerMidpoint.getY() + (player.getHeight() / 2.0f));

        world.spawnParticle(Particle.REDSTONE, attackLocation, 5, 0.3f, 0.3f, 0.3f, new Particle.DustOptions(Color.GRAY, 2.0f));
        world.spawnParticle(Particle.EXPLOSION_LARGE, mobMidpoint, 1);
        world.playEffect(attackLocation, Effect.BLAZE_SHOOT, null);
        drawAttackLine(tickTime, 0.1f, world, playerMidpoint.toVector(), mobMidpoint.toVector(), targetPlayerMidpoint.toVector());
        if (playerActionLock != null)
            playerActionLock.setLockedLocation(targetPlayerLoc);
        player.teleport(targetPlayerLoc);
    }

    /**
     * Performs the final slash of the All Out Attack, killing the mob.
     */
    private void runFinalAttackExplosion()
    {
        World world = mob.getWorld();
        Location mobLocation = mob.getLocation();
        Location mobMidpoint = mobLocation.clone();
        mobMidpoint.setY(mobMidpoint.getY() + (mob.getHeight() / 2.0f));

        world.spawnParticle(Particle.EXPLOSION_LARGE, mobMidpoint, 1);
        world.playSound(mobMidpoint, Sound.ENTITY_GENERIC_EXPLODE, 1.0f, 1.0f);

        if (mobInvincibility != null)
            mobInvincibility.stop();
        if (mobInvalidator != null)
            mobInvalidator.stop();
        mob.damage(mob.getHealth());

        stop();
    }

    /**
     * Draws the line effect for an ALl Out Attack
     * @param drawTimeTicks the time it takes to draw the line over all points
     * @param particleStep a step value between 0.0 and 1.0 for how frequently particles should be placed
     * @param world the world to spawn in
     * @param points the vectors defining the line
     */
    private void drawAttackLine(int drawTimeTicks, double particleStep, World world, Vector... points)
    {
        for (int i = 0; i < points.length - 1; i++)
        {
            Vector lineStart = points[i];
            Vector lineEnd = points[i + 1];
            Vector lineDifference = lineEnd.clone().subtract(lineStart);
            int ticksPerStep = Math.round((float) (drawTimeTicks * particleStep));
            for (double step = 0.0f; step < 1.0f; step += particleStep)
            {
                Vector position = lineDifference.clone().multiply(step).add(lineStart);
                Bukkit.getScheduler().runTaskLater(
                        MobNegotiationPlugin.getInstance(),
                        () -> world.spawnParticle(Particle.REDSTONE, position.getX(), position.getY(), position.getZ(), 1, new Particle.DustOptions(Color.BLACK, 2.0f)),
                        ticksPerStep);
            }
        }
    }
}
