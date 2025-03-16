package com.zazsona.mobnegotiation.model2.scriptarchive;

import com.zazsona.mobnegotiation.model.script.NegotiationScriptNode;
import com.zazsona.mobnegotiation.model2.Personable;
import com.zazsona.mobnegotiation.model2.script.ScriptLine;

import java.util.ArrayList;
import java.util.List;

public class NegotiationScript
{
    private Personable<ScriptLine> greetingMessage;
    private Personable<ScriptLine> idleWarningMessage;
    private Personable<ScriptLine> idleTimeoutMessage;
    private Personable<ScriptLine> initialItemOfferMessage;
    private Personable<ScriptLine> furtherItemOfferMessage;
    private Personable<ScriptLine> furtherItemOfferMessageVariant;
    private Personable<ScriptLine> acceptedItemOfferMessage;
    private Personable<ScriptLine> refuseItemDemandMessage;

    private Personable<ScriptLine> initialMoneyOfferMessage;
    private Personable<ScriptLine> furtherMoneyOfferMessage;
    private Personable<ScriptLine> furtherMoneyOfferMessageVariant;
    private Personable<ScriptLine> acceptedMoneyOfferMessage;
    private Personable<ScriptLine> refuseMoneyDemandMessage;

    private Personable<ScriptLine> powerSuccessMessage;
    private List<NegotiationScriptNode> powerTrees = new ArrayList<>(); //TODO: Bad name

    public NegotiationScript()
    {


    }

    public NegotiationScript(Personable<ScriptLine> greetingMessage, Personable<ScriptLine> idleWarningMessage, Personable<ScriptLine> idleTimeoutMessage,
                             Personable<ScriptLine> initialItemOfferMessage, Personable<ScriptLine> furtherItemOfferMessage, Personable<ScriptLine> furtherItemOfferMessageVariant, Personable<ScriptLine> acceptedItemOfferMessage, Personable<ScriptLine> refuseItemDemandMessage,
                             Personable<ScriptLine> initialMoneyOfferMessage, Personable<ScriptLine> furtherMoneyOfferMessage, Personable<ScriptLine> furtherMoneyOfferMessageVariant, Personable<ScriptLine> acceptedMoneyOfferMessage, Personable<ScriptLine> refuseMoneyDemandMessage,
                             Personable<ScriptLine> powerSuccessMessage, List<NegotiationScriptNode> powerTrees)
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
    public Personable<ScriptLine> getGreetingMessage()
    {
        return greetingMessage;
    }

    /**
     * Sets greetingMessage
     */
    public void setGreetingMessage(Personable<ScriptLine> greetingMessage)
    {
        this.greetingMessage = greetingMessage;
    }

    /**
     * Gets idleWarningMessage
     * @return idleWarningMessage
     */
    public Personable<ScriptLine> getIdleWarningMessage()
    {
        return idleWarningMessage;
    }

    /**
     * Sets idleWarningMessage
     */
    public void setIdleWarningMessage(Personable<ScriptLine> idleWarningMessage)
    {
        this.idleWarningMessage = idleWarningMessage;
    }

    /**
     * Gets idleTimeoutMessage
     * @return idleTimeoutMessage
     */
    public Personable<ScriptLine> getIdleTimeoutMessage()
    {
        return idleTimeoutMessage;
    }

    /**
     * Sets idleTimeoutMessage
     */
    public void setIdleTimeoutMessage(Personable<ScriptLine> idleTimeoutMessage)
    {
        this.idleTimeoutMessage = idleTimeoutMessage;
    }

    /**
     * Gets initialItemOfferMessage
     * @return initialItemOfferMessage
     */
    public Personable<ScriptLine> getInitialItemOfferMessage()
    {
        return initialItemOfferMessage;
    }

    /**
     * Sets initialItemOfferMessage
     */
    public void setInitialItemOfferMessage(Personable<ScriptLine> initialItemOfferMessage)
    {
        this.initialItemOfferMessage = initialItemOfferMessage;
    }

    /**
     * Gets furtherItemOfferMessage
     * @return furtherItemOfferMessage
     */
    public Personable<ScriptLine> getFurtherItemOfferMessage()
    {
        return furtherItemOfferMessage;
    }

    /**
     * Sets furtherItemOfferMessage
     */
    public void setFurtherItemOfferMessage(Personable<ScriptLine> furtherItemOfferMessage)
    {
        this.furtherItemOfferMessage = furtherItemOfferMessage;
    }

    /**
     * Gets furtherItemOfferMessageVariant
     * @return furtherItemOfferMessageVariant
     */
    public Personable<ScriptLine> getFurtherItemOfferMessageVariant()
    {
        return furtherItemOfferMessageVariant;
    }

    /**
     * Sets furtherItemOfferMessageVariant
     */
    public void setFurtherItemOfferMessageVariant(Personable<ScriptLine> furtherItemOfferMessageVariant)
    {
        this.furtherItemOfferMessageVariant = furtherItemOfferMessageVariant;
    }

    /**
     * Gets acceptedItemOfferMessage
     * @return acceptedItemOfferMessage
     */
    public Personable<ScriptLine> getAcceptedItemOfferMessage()
    {
        return acceptedItemOfferMessage;
    }

    /**
     * Sets acceptedItemOfferMessage
     */
    public void setAcceptedItemOfferMessage(Personable<ScriptLine> acceptedItemOfferMessage)
    {
        this.acceptedItemOfferMessage = acceptedItemOfferMessage;
    }

    /**
     * Gets refuseItemDemandMessage
     * @return refuseItemDemandMessage
     */
    public Personable<ScriptLine> getRefuseItemDemandMessage()
    {
        return refuseItemDemandMessage;
    }

    /**
     * Sets refuseItemDemandMessage
     */
    public void setRefuseItemDemandMessage(Personable<ScriptLine> refuseItemDemandMessage)
    {
        this.refuseItemDemandMessage = refuseItemDemandMessage;
    }

    /**
     * Gets initialMoneyOfferMessage
     * @return initialMoneyOfferMessage
     */
    public Personable<ScriptLine> getInitialMoneyOfferMessage()
    {
        return initialMoneyOfferMessage;
    }

    /**
     * Sets initialMoneyOfferMessage
     */
    public void setInitialMoneyOfferMessage(Personable<ScriptLine> initialMoneyOfferMessage)
    {
        this.initialMoneyOfferMessage = initialMoneyOfferMessage;
    }

    /**
     * Gets furtherMoneyOfferMessage
     * @return furtherMoneyOfferMessage
     */
    public Personable<ScriptLine> getFurtherMoneyOfferMessage()
    {
        return furtherMoneyOfferMessage;
    }

    /**
     * Sets furtherMoneyOfferMessage
     */
    public void setFurtherMoneyOfferMessage(Personable<ScriptLine> furtherMoneyOfferMessage)
    {
        this.furtherMoneyOfferMessage = furtherMoneyOfferMessage;
    }

    /**
     * Gets furtherMoneyOfferMessageVariant
     * @return furtherMoneyOfferMessageVariant
     */
    public Personable<ScriptLine> getFurtherMoneyOfferMessageVariant()
    {
        return furtherMoneyOfferMessageVariant;
    }

    /**
     * Sets furtherMoneyOfferMessageVariant
     */
    public void setFurtherMoneyOfferMessageVariant(Personable<ScriptLine> furtherMoneyOfferMessageVariant)
    {
        this.furtherMoneyOfferMessageVariant = furtherMoneyOfferMessageVariant;
    }

    /**
     * Gets acceptedMoneyOfferMessage
     * @return acceptedMoneyOfferMessage
     */
    public Personable<ScriptLine> getAcceptedMoneyOfferMessage()
    {
        return acceptedMoneyOfferMessage;
    }

    /**
     * Sets acceptedMoneyOfferMessage
     */
    public void setAcceptedMoneyOfferMessage(Personable<ScriptLine> acceptedMoneyOfferMessage)
    {
        this.acceptedMoneyOfferMessage = acceptedMoneyOfferMessage;
    }

    /**
     * Gets refuseMoneyDemandMessage
     * @return refuseMoneyDemandMessage
     */
    public Personable<ScriptLine> getRefuseMoneyDemandMessage()
    {
        return refuseMoneyDemandMessage;
    }

    /**
     * Sets refuseMoneyDemandMessage
     */
    public void setRefuseMoneyDemandMessage(Personable<ScriptLine> refuseMoneyDemandMessage)
    {
        this.refuseMoneyDemandMessage = refuseMoneyDemandMessage;
    }

    /**
     * Gets powerSuccessMessage
     * @return powerSuccessMessage
     */
    public Personable<ScriptLine> getPowerSuccessMessage()
    {
        return powerSuccessMessage;
    }

    /**
     * Sets powerSuccessMessage
     */
    public void setPowerSuccessMessage(Personable<ScriptLine> powerSuccessMessage)
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
