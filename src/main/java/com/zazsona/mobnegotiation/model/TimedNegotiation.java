package com.zazsona.mobnegotiation.model;

import com.zazsona.mobnegotiation.MobNegotiationPlugin;
import com.zazsona.mobnegotiation.repository.ICooldownRespository;
import org.bukkit.Bukkit;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import javax.naming.ConfigurationException;

public class TimedNegotiation extends Negotiation
{
    protected BukkitTask idleTimer;
    protected int idleTimerTicks;

    public TimedNegotiation(Player player, Mob mob, INegotiationEntityEligibilityChecker eligibilityChecker, ICooldownRespository cooldownRespository, int idleTimerTicks)
    {
        super(player, mob, eligibilityChecker, cooldownRespository);
        this.idleTimerTicks = idleTimerTicks;
    }

    @Override
    protected void beginNegotiation() throws ConfigurationException
    {
        stopIdleTimer();
        super.beginNegotiation();
        if (state.isTerminating() && prompt.getResponses().size() > 0)
            startIdleTimer();
    }

    @Override
    public void nextPrompt(NegotiationResponse response)
    {
        stopIdleTimer();
        super.nextPrompt(response);
        if (!state.isTerminating() && prompt.getResponses().size() > 0)
            startIdleTimer();
    }

    @Override
    protected void stop(NegotiationState terminatingState)
    {
        stopIdleTimer();
        super.stop(terminatingState);
    }

    /**
     * (Re)starts the idle timer for ending the negotiation if no response is received.
     */
    private void startIdleTimer()
    {
        stopIdleTimer();
        MobNegotiationPlugin plugin = MobNegotiationPlugin.getInstance();
        idleTimer = Bukkit.getScheduler().runTaskLater(plugin, () ->
        {
            stop(NegotiationState.FINISHED_TIMEOUT);
        }, idleTimerTicks);
    }

    /**
     * Stops the idle timer.
     */
    private void stopIdleTimer()
    {
        if (idleTimer != null && !idleTimer.isCancelled())
            idleTimer.cancel();
    }
}
