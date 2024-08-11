package com.zazsona.mobnegotiation.controller2;

import com.zazsona.mobnegotiation.model.Negotiation;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class MobCombatController implements Listener {

    private INegotiationEligibilityChecker eligibilityChecker;

    public MobCombatController(INegotiationEligibilityChecker eligibilityChecker)
    {
        this.eligibilityChecker = eligibilityChecker;
    }

    /**
     * Detects when a valid mob is hit, and if parameters are valid, begins a negotiation session.
     * @param e the event
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onMobAttack(EntityDamageByEntityEvent e)
    {
        if (!(e.getDamager() instanceof Player) && !(e.getEntity() instanceof Mob))
            return;

        Player player = (Player) e.getDamager();
        Mob mob = (Mob) e.getEntity();
        if (!eligibilityChecker.canEntitiesNegotiate(player, mob))
            return;

        Negotiation negotiation = new Negotiation(player, mob, eligibilityChecker, WhyIsTheEligibilityChecker&CooldownNeededHere?/ShouldWeUseAFactory?);
        NegotiationController negotiationController = new NegotiationController(negotiation);
        negotiationController.renderNegotiation();
    }
}
