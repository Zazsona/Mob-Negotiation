package com.zazsona.mobnegotiation.model.script;

import java.util.ArrayList;
import java.util.List;

public class NegotiationScript
{
    private static final String UNDEFINED_MSG = "No text.";

    private ScriptTextPersonalityVariant greetingMessage = new ScriptTextPersonalityVariant(UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG);
    private ScriptTextPersonalityVariant idleWarningMessage = new ScriptTextPersonalityVariant(UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG);
    private ScriptTextPersonalityVariant idleTimeoutMessage = new ScriptTextPersonalityVariant(UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG);
    private ScriptTextPersonalityVariant initialItemOfferMessage = new ScriptTextPersonalityVariant(UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG);
    private ScriptTextPersonalityVariant furtherItemOfferMessage = new ScriptTextPersonalityVariant(UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG);
    private ScriptTextPersonalityVariant furtherItemOfferMessageVariant = new ScriptTextPersonalityVariant(UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG);
    private ScriptTextPersonalityVariant acceptedItemOfferMessage = new ScriptTextPersonalityVariant(UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG);
    private ScriptTextPersonalityVariant refuseItemDemandMessage = new ScriptTextPersonalityVariant(UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG);

    private ScriptTextPersonalityVariant initialMoneyOfferMessage = new ScriptTextPersonalityVariant(UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG);
    private ScriptTextPersonalityVariant furtherMoneyOfferMessage = new ScriptTextPersonalityVariant(UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG);
    private ScriptTextPersonalityVariant furtherMoneyOfferMessageVariant = new ScriptTextPersonalityVariant(UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG);
    private ScriptTextPersonalityVariant acceptedMoneyOfferMessage = new ScriptTextPersonalityVariant(UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG);
    private ScriptTextPersonalityVariant refuseMoneyDemandMessage = new ScriptTextPersonalityVariant(UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG);

    private ScriptTextPersonalityVariant powerSuccessMessage = new ScriptTextPersonalityVariant(UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG);
    private List<NegotiationScriptNode> powerTrees = new ArrayList<>();

    public NegotiationScript()
    {

    }

    public NegotiationScript(ScriptTextPersonalityVariant greetingMessage, ScriptTextPersonalityVariant idleWarningMessage, ScriptTextPersonalityVariant idleTimeoutMessage,
                             ScriptTextPersonalityVariant initialItemOfferMessage, ScriptTextPersonalityVariant furtherItemOfferMessage, ScriptTextPersonalityVariant furtherItemOfferMessageVariant, ScriptTextPersonalityVariant acceptedItemOfferMessage, ScriptTextPersonalityVariant refuseItemDemandMessage,
                             ScriptTextPersonalityVariant initialMoneyOfferMessage, ScriptTextPersonalityVariant furtherMoneyOfferMessage, ScriptTextPersonalityVariant furtherMoneyOfferMessageVariant, ScriptTextPersonalityVariant acceptedMoneyOfferMessage, ScriptTextPersonalityVariant refuseMoneyDemandMessage,
                             ScriptTextPersonalityVariant powerSuccessMessage, List<NegotiationScriptNode> powerTrees)
    {
        this.greetingMessage = greetingMessage;
        this.idleWarningMessage = idleWarningMessage;
        this.idleTimeoutMessage = idleTimeoutMessage;
        this.initialItemOfferMessage = initialItemOfferMessage;
        this.furtherItemOfferMessage = furtherItemOfferMessage;
        this.furtherItemOfferMessageVariant = furtherItemOfferMessageVariant;
        this.acceptedItemOfferMessage = acceptedItemOfferMessage;
        this.refuseItemDemandMessage = refuseItemDemandMessage;

        this.initialMoneyOfferMessage = initialMoneyOfferMessage;
        this.furtherMoneyOfferMessage = furtherMoneyOfferMessage;
        this.furtherMoneyOfferMessageVariant = furtherMoneyOfferMessageVariant;
        this.acceptedMoneyOfferMessage = acceptedMoneyOfferMessage;
        this.refuseMoneyDemandMessage = refuseMoneyDemandMessage;

        this.powerSuccessMessage = powerSuccessMessage;
        this.powerTrees = powerTrees;
    }

    /**
     * Gets greetingMessage
     * @return greetingMessage
     */
    public ScriptTextPersonalityVariant getGreetingMessage()
    {
        return greetingMessage;
    }

    /**
     * Sets greetingMessage
     */
    public void setGreetingMessage(ScriptTextPersonalityVariant greetingMessage)
    {
        this.greetingMessage = greetingMessage;
    }

    /**
     * Gets idleWarningMessage
     * @return idleWarningMessage
     */
    public ScriptTextPersonalityVariant getIdleWarningMessage()
    {
        return idleWarningMessage;
    }

    /**
     * Sets idleWarningMessage
     */
    public void setIdleWarningMessage(ScriptTextPersonalityVariant idleWarningMessage)
    {
        this.idleWarningMessage = idleWarningMessage;
    }

    /**
     * Gets idleTimeoutMessage
     * @return idleTimeoutMessage
     */
    public ScriptTextPersonalityVariant getIdleTimeoutMessage()
    {
        return idleTimeoutMessage;
    }

    /**
     * Sets idleTimeoutMessage
     */
    public void setIdleTimeoutMessage(ScriptTextPersonalityVariant idleTimeoutMessage)
    {
        this.idleTimeoutMessage = idleTimeoutMessage;
    }

    /**
     * Gets initialItemOfferMessage
     * @return initialItemOfferMessage
     */
    public ScriptTextPersonalityVariant getInitialItemOfferMessage()
    {
        return initialItemOfferMessage;
    }

    /**
     * Sets initialItemOfferMessage
     */
    public void setInitialItemOfferMessage(ScriptTextPersonalityVariant initialItemOfferMessage)
    {
        this.initialItemOfferMessage = initialItemOfferMessage;
    }

    /**
     * Gets furtherItemOfferMessage
     * @return furtherItemOfferMessage
     */
    public ScriptTextPersonalityVariant getFurtherItemOfferMessage()
    {
        return furtherItemOfferMessage;
    }

    /**
     * Sets furtherItemOfferMessage
     */
    public void setFurtherItemOfferMessage(ScriptTextPersonalityVariant furtherItemOfferMessage)
    {
        this.furtherItemOfferMessage = furtherItemOfferMessage;
    }

    /**
     * Gets furtherItemOfferMessageVariant
     * @return furtherItemOfferMessageVariant
     */
    public ScriptTextPersonalityVariant getFurtherItemOfferMessageVariant()
    {
        return furtherItemOfferMessageVariant;
    }

    /**
     * Sets furtherItemOfferMessageVariant
     */
    public void setFurtherItemOfferMessageVariant(ScriptTextPersonalityVariant furtherItemOfferMessageVariant)
    {
        this.furtherItemOfferMessageVariant = furtherItemOfferMessageVariant;
    }

    /**
     * Gets acceptedItemOfferMessage
     * @return acceptedItemOfferMessage
     */
    public ScriptTextPersonalityVariant getAcceptedItemOfferMessage()
    {
        return acceptedItemOfferMessage;
    }

    /**
     * Sets acceptedItemOfferMessage
     */
    public void setAcceptedItemOfferMessage(ScriptTextPersonalityVariant acceptedItemOfferMessage)
    {
        this.acceptedItemOfferMessage = acceptedItemOfferMessage;
    }

    /**
     * Gets refuseItemDemandMessage
     * @return refuseItemDemandMessage
     */
    public ScriptTextPersonalityVariant getRefuseItemDemandMessage()
    {
        return refuseItemDemandMessage;
    }

    /**
     * Sets refuseItemDemandMessage
     */
    public void setRefuseItemDemandMessage(ScriptTextPersonalityVariant refuseItemDemandMessage)
    {
        this.refuseItemDemandMessage = refuseItemDemandMessage;
    }

    /**
     * Gets initialMoneyOfferMessage
     * @return initialMoneyOfferMessage
     */
    public ScriptTextPersonalityVariant getInitialMoneyOfferMessage()
    {
        return initialMoneyOfferMessage;
    }

    /**
     * Sets initialMoneyOfferMessage
     */
    public void setInitialMoneyOfferMessage(ScriptTextPersonalityVariant initialMoneyOfferMessage)
    {
        this.initialMoneyOfferMessage = initialMoneyOfferMessage;
    }

    /**
     * Gets furtherMoneyOfferMessage
     * @return furtherMoneyOfferMessage
     */
    public ScriptTextPersonalityVariant getFurtherMoneyOfferMessage()
    {
        return furtherMoneyOfferMessage;
    }

    /**
     * Sets furtherMoneyOfferMessage
     */
    public void setFurtherMoneyOfferMessage(ScriptTextPersonalityVariant furtherMoneyOfferMessage)
    {
        this.furtherMoneyOfferMessage = furtherMoneyOfferMessage;
    }

    /**
     * Gets furtherMoneyOfferMessageVariant
     * @return furtherMoneyOfferMessageVariant
     */
    public ScriptTextPersonalityVariant getFurtherMoneyOfferMessageVariant()
    {
        return furtherMoneyOfferMessageVariant;
    }

    /**
     * Sets furtherMoneyOfferMessageVariant
     */
    public void setFurtherMoneyOfferMessageVariant(ScriptTextPersonalityVariant furtherMoneyOfferMessageVariant)
    {
        this.furtherMoneyOfferMessageVariant = furtherMoneyOfferMessageVariant;
    }

    /**
     * Gets acceptedMoneyOfferMessage
     * @return acceptedMoneyOfferMessage
     */
    public ScriptTextPersonalityVariant getAcceptedMoneyOfferMessage()
    {
        return acceptedMoneyOfferMessage;
    }

    /**
     * Sets acceptedMoneyOfferMessage
     */
    public void setAcceptedMoneyOfferMessage(ScriptTextPersonalityVariant acceptedMoneyOfferMessage)
    {
        this.acceptedMoneyOfferMessage = acceptedMoneyOfferMessage;
    }

    /**
     * Gets refuseMoneyDemandMessage
     * @return refuseMoneyDemandMessage
     */
    public ScriptTextPersonalityVariant getRefuseMoneyDemandMessage()
    {
        return refuseMoneyDemandMessage;
    }

    /**
     * Sets refuseMoneyDemandMessage
     */
    public void setRefuseMoneyDemandMessage(ScriptTextPersonalityVariant refuseMoneyDemandMessage)
    {
        this.refuseMoneyDemandMessage = refuseMoneyDemandMessage;
    }

    /**
     * Gets powerSuccessMessage
     * @return powerSuccessMessage
     */
    public ScriptTextPersonalityVariant getPowerSuccessMessage()
    {
        return powerSuccessMessage;
    }

    /**
     * Sets powerSuccessMessage
     */
    public void setPowerSuccessMessage(ScriptTextPersonalityVariant powerSuccessMessage)
    {
        this.powerSuccessMessage = powerSuccessMessage;
    }

    /**
     * Gets powerTrees
     * @return powerTrees
     */
    public List<NegotiationScriptNode> getPowerTrees()
    {
        return powerTrees;
    }
}
