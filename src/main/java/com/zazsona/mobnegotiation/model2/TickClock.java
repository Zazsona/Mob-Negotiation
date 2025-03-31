package com.zazsona.mobnegotiation.model2;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class TickClock
{
    private Plugin plugin;
    private BukkitTask counterTask;
    private long totalTicks = 0;

    public TickClock(Plugin plugin)
    {
        this.plugin = plugin;
    }

    /**
     * Starts or resumes the tick timer, if it isn't already running.
     * @return false if the timer was already running
     */
    public boolean start()
    {
        if (!isRunning())
        {
            counterTask = plugin.getServer().getScheduler().runTaskTimer(plugin, () -> totalTicks++, 1, 1);
            return true;
        }
        return false;
    }

    /**
     * Stops the tick timer.
     * @return false if the timer was already stopped
     */
    public boolean stop()
    {
        if (isRunning())
        {
            counterTask.cancel();
            return true;
        }
        return false;
    }

    /**
     * Resets the tick timer to zero.
     * Note that the timer may or may not have counted the current tick yet if it is still running.
     */
    public void reset()
    {
        totalTicks = 0;
    }

    /**
     * Returns if the tick clock is currently ticking
     * @return true on ticking
     */
    public boolean isRunning()
    {
        return (counterTask != null && !counterTask.isCancelled());
    }

    /**
     * Gets the total number of ticks that have occurred on this timer
     * @return the ticks since the last start() call, or 0.
     */
    public long getTicks()
    {
        return totalTicks;
    }
}
