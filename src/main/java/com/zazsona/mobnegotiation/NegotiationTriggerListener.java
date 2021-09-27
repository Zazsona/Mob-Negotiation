package com.zazsona.mobnegotiation;

import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;

import java.util.HashSet;
import java.util.List;

public class NegotiationTriggerListener implements Listener, NegotiationEventListener
{
    private HashSet<NegotiationProcess> negotiations;

    public NegotiationTriggerListener()
    {
        this.negotiations = new HashSet<>();
    }

    /**
     * Detects when a valid mob is hit to check if negotiations should begin
     * @param e the event
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onMobAttack(EntityDamageByEntityEvent e)
    {
        if (e.getDamager() instanceof Player && e.getEntity() instanceof Mob)
        {
            Player player = (Player) e.getDamager();
            Mob mob = (Mob) e.getEntity();
            NegotiationProcess negotiationProcess = new NegotiationProcess(player, mob);
            negotiationProcess.addEventListener(this);
            negotiationProcess.start();
        }
    }

    @Override
    public void onNegotiationStateUpdate(NegotiationProcess negotiation)
    {
        NegotiationState state = negotiation.getState();
        if (state == NegotiationState.STARTED)
            negotiations.add(negotiation);
        else if (state.isTerminating)
            negotiations.remove(negotiation);
    }
}
