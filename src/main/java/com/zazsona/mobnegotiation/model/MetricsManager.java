package com.zazsona.mobnegotiation.model;

import com.zazsona.mobnegotiation.MobNegotiationPlugin;
import org.apache.commons.lang.WordUtils;
import org.bstats.bukkit.Metrics;
import org.bukkit.entity.EntityType;

import java.util.HashMap;
import java.util.Map;

public class MetricsManager
{
    private static final int BSTATS_PLUGIN_ID = 13208;
    private static final String NEGOTIATIONS_TRIGGERED_KEY = "negotiations_triggered";
    private static final String NEGOTIATIONS_TYPE_KEY = "negotiation_type";
    private static final String NEGOTIATIONS_MOB_KEY = "negotiation_mob";
    private static final String NEGOTIATIONS_TRIGGER_RATE_KEY = "negotiation_trigger_rate";
    private static final String NEGOTIATIONS_HP_THRESHOLD_KEY = "negotiation_hp_threshold";

    private static MetricsManager instance;
    private Metrics metrics;
    private int negotiationsTriggeredSinceLastUpdate = 0;
    private HashMap<EntityType, Integer> mobFrequencyMap = new HashMap<>();
    private HashMap<String, Integer> negotiationTypeMap = new HashMap<>();

    private MetricsManager()
    {
        metrics = new Metrics(MobNegotiationPlugin.getInstance(), BSTATS_PLUGIN_ID);
        if (metrics.isEnabled())
        {
            addNegotiationsTriggeredChart(metrics);
            addNegotiationsTypeChart(metrics);
            addNegotiationsMobChart(metrics);
            addNegotiationsTriggerRateChart(metrics);
            addNegotiationsHPThresholdChart(metrics);
        }
    }

    /**
     * Gets the MetricsManager instance.
     * @return the instance
     */
    public static MetricsManager getInstance()
    {
        if (instance == null)
            instance = new MetricsManager();
        return instance;
    }

    /**
     * Logs the statistics for this negotiation
     * @param negotiation the negotiation to track
     */
    public void trackNegotiation(Negotiation negotiation)
    {
        if (!metrics.isEnabled())
            return;

        if (negotiation.getState().isTerminating())
            logNegotiation(negotiation);
        else
        {
            negotiation.addListener(new NegotiationStateListener()
            {
                @Override
                public void onNegotiationStateUpdate(Negotiation negotiation)
                {
                    if (negotiation.getState().isTerminating())
                    {
                        logNegotiation(negotiation);
                        negotiation.removeListener(this);
                    }
                }
            });
        }
    }

    /**
     * Performs all logging duties for the negotiation
     * @param negotiation the negotiation to log
     */
    private void logNegotiation(Negotiation negotiation)
    {
        negotiationsTriggeredSinceLastUpdate++;
        logNegotiationMob(negotiation);
        logNegotiationType(negotiation);
    }

    /**
     * Records the mob associated with this negotiation
     * @param negotiation the negotiation
     */
    private void logNegotiationMob(Negotiation negotiation)
    {
        EntityType mobType = negotiation.getMob().getType();
        int existingMobCount = mobFrequencyMap.getOrDefault(mobType, 0);
        mobFrequencyMap.put(mobType, existingMobCount + 1);
    }

    /**
     * Records the type of negotiation
     * @param negotiation the negotiation
     */
    private void logNegotiationType(Negotiation negotiation)
    {
        final String powerKey = "Power";
        final String itemKey = "Item";
        final String moneyKey = "Money";
        final String attackKey = "All out Attack";

        String key = switch (negotiation.getState())
                {
                    case ACTIVE_POWER, FINISHED_POWER -> powerKey;
                    case ACTIVE_ITEM, FINISHED_ITEM -> itemKey;
                    case ACTIVE_MONEY, FINISHED_MONEY -> moneyKey;
                    case ACTIVE_ATTACK, FINISHED_ATTACK -> attackKey;
                    default -> null;
                };

        if (key != null)
        {
            int existingCount = negotiationTypeMap.getOrDefault(key, 0);
            negotiationTypeMap.put(key, existingCount + 1);
        }
    }

    /**
     * Creates a new chart displaying the number of negotiations since the last upload
     * @param metrics the metrics context
     */
    private void addNegotiationsTriggeredChart(Metrics metrics)
    {
        metrics.addCustomChart(new Metrics.SingleLineChart(NEGOTIATIONS_TRIGGERED_KEY, () ->
        {
            int negotiationsCount = negotiationsTriggeredSinceLastUpdate;
            negotiationsTriggeredSinceLastUpdate = 0;
            return negotiationsCount;
        }));
    }

    /**
     * Creates a new chart displaying the type of negotiations since the last upload
     * @param metrics the metrics context
     */
    private void addNegotiationsTypeChart(Metrics metrics)
    {
        metrics.addCustomChart(new Metrics.AdvancedPie(NEGOTIATIONS_TYPE_KEY, () ->
        {
            Map<String, Integer> pieMap = new HashMap<>(negotiationTypeMap);
            negotiationTypeMap.clear();
            return pieMap;
        }));
    }

    /**
     * Creates a new chart displaying the mobs of negotiations since the last upload
     * @param metrics the metrics context
     */
    private void addNegotiationsMobChart(Metrics metrics)
    {
        metrics.addCustomChart(new Metrics.AdvancedPie(NEGOTIATIONS_MOB_KEY, () ->
        {
            Map<String, Integer> pieMap = new HashMap<>();
            for (Map.Entry<EntityType, Integer> mobEntry : mobFrequencyMap.entrySet())
            {
                String mobName = WordUtils.capitalizeFully(mobEntry.getKey().toString().replace("_", " "));
                pieMap.put(mobName, mobEntry.getValue());
            }
            mobFrequencyMap.clear();
            return pieMap;
        }));
    }

    /**
     * Creates a new chart displaying the chance of negotiations to trigger
     * @param metrics the metrics context
     */
    private void addNegotiationsTriggerRateChart(Metrics metrics)
    {
        metrics.addCustomChart(new Metrics.SimplePie(NEGOTIATIONS_TRIGGER_RATE_KEY, () ->
        {
            double triggerRate = PluginConfig.getNegotiationRate();
            return String.valueOf(triggerRate);
        }));
    }

    /**
     * Creates a new chart displaying the health threshold for negotiations to trigger
     * @param metrics the metrics context
     */
    private void addNegotiationsHPThresholdChart(Metrics metrics)
    {
        metrics.addCustomChart(new Metrics.SimplePie(NEGOTIATIONS_HP_THRESHOLD_KEY, () ->
        {
            double healthThreshold = PluginConfig.getNegotiationHealthThreshold();
            return String.valueOf(healthThreshold);
        }));
    }
}
