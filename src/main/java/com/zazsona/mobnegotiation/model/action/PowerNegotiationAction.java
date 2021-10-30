package com.zazsona.mobnegotiation.model.action;

import com.zazsona.mobnegotiation.model.Mood;
import com.zazsona.mobnegotiation.model.PersonalityType;
import com.zazsona.mobnegotiation.model.PluginConfig;
import com.zazsona.mobnegotiation.model.script.NegotiationScript;
import com.zazsona.mobnegotiation.model.script.NegotiationScriptNode;
import com.zazsona.mobnegotiation.model.script.NegotiationScriptResponseNode;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PowerNegotiationAction extends Action
{
    private NegotiationScript script;
    private PersonalityType mobPersonality;

    private boolean active;
    private NegotiationScriptNode scriptNode;
    private Random rand;
    private ArrayList<PotionEffect> givenPowers;

    public PowerNegotiationAction(Player player, Mob mob, NegotiationScript script, PersonalityType mobPersonality)
    {
        super(player, mob);
        this.script = script;
        this.mobPersonality = mobPersonality;
        this.rand = new Random();
        this.givenPowers = new ArrayList<>();
        this.active = false;
    }

    /**
     * Gets the current script node.
     * @return the script node.
     */
    public NegotiationScriptNode getCurrentNode()
    {
        return scriptNode;
    }

    /**
     * Loads the next scripted node, based on the response
     * @param responseText the response to select
     * @return the next node, or null if none is available.
     */
    public NegotiationScriptNode nextNode(String responseText)
    {
        if (this.scriptNode.getResponses() == null || this.scriptNode.getText() == null)
            return null;

        NegotiationScriptResponseNode selectedResponse = null;
        for (NegotiationScriptResponseNode responseNode : scriptNode.getResponses())
        {
            if (responseNode.getText().equals(responseText))
                selectedResponse = responseNode;
        }
        if (selectedResponse == null)
            throw new IllegalArgumentException(String.format("Unrecognised response: %s", responseText));

        double successRateModifier = PluginConfig.getNegotiationPowerSuccessRate() / 100.0f;
        double successRate = selectedResponse.getSuccessRates().getVariant(mobPersonality) * successRateModifier;
        double roll = rand.nextDouble() * 100;
        if (roll < successRate)
        {
            List<NegotiationScriptNode> children = this.scriptNode.getChildren();
            if (children.size() > 0) // Negotiation On-going
            {
                String responseSuccess = selectedResponse.getSuccessResponses().getVariant(mobPersonality);
                NegotiationScriptNode childNode = children.get(rand.nextInt(children.size()));
                String mobMessage = responseSuccess + "\n" + childNode.getText();
                NegotiationScriptNode node = new NegotiationScriptNode(mobMessage, childNode.getResponses(), childNode.getMood(), childNode.getChildren());
                this.scriptNode = node;
                runNodeLoadListeners(this.scriptNode);
            }
            else // Negotiation Successful
            {
                String responseSuccess = selectedResponse.getSuccessResponses().getVariant(mobPersonality);
                String powerSuccess = this.script.getPowerSuccessMessage().getVariant(mobPersonality);
                String mobMessage = responseSuccess + "\n" + powerSuccess;
                NegotiationScriptNode node = new NegotiationScriptNode(mobMessage, null, Mood.HAPPY, null);
                this.scriptNode = node;
                givePower();
                runNodeLoadListeners(this.scriptNode);
                stop();
            }
        }
        else // Negotiation Failed
        {
            String mobMessage = selectedResponse.getFailureResponses().getVariant(mobPersonality);
            NegotiationScriptNode node = new NegotiationScriptNode(mobMessage, null, Mood.ANGRY, null);
            this.scriptNode = node;
            runNodeLoadListeners(this.scriptNode);
            stop();
        }
        return this.scriptNode;
    }

    /**
     * Gets the powers given to the associated player through this power negotiation
     * @return the bestowed powers.
     */
    public List<PotionEffect> getGivenPowers()
    {
        return givenPowers;
    }

    @Override
    public void execute()
    {
        if (active)
            throw new IllegalCallerException("This action is already active.");
        List<NegotiationScriptNode> trees = this.script.getPowerTrees();
        this.scriptNode = trees.get(rand.nextInt(trees.size()));
        this.active = true;
        runOnStartListeners();
    }

    @Override
    public void stop()
    {
        if (active)
        {
            this.active = false;
            runOnCompleteListeners();
        }
    }

    @Override
    public boolean isActive()
    {
        return active;
    }

    /**
     * Gives the player the mob's power(s) and adds to givenPowers.
     */
    private PotionEffect givePower()
    {
        PotionEffectType powerType = PluginConfig.getOfferedPower(mob.getType());
        if (powerType != null)
        {
            int ticks = PluginConfig.getPowerDurationTicks();
            PotionEffect power = new PotionEffect(powerType, ticks, 0);
            player.addPotionEffect(power);
            givenPowers.add(power);
            return power;
        }
        return null;
    }

    /**
     * Runs all listeners for alerting when a new node is loaded.
     */
    protected void runNodeLoadListeners(NegotiationScriptNode node)
    {
        for (int i = listeners.size() - 1; i > -1; i--)
        {
            IActionListener listener = listeners.get(i);
            if (listener instanceof IPowerNegotiationActionListener)
            {
                ((IPowerNegotiationActionListener) listener).onNodeLoaded(node);
            }
        }
    }
}
