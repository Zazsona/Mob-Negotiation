package com.zazsona.mobnegotiation.model.script;

import java.util.ArrayList;
import java.util.List;

public class NegotiationScript
{
    private static final String UNDEFINED_MSG = "No text.";

    private PersonalityVariant<String> greetingMessage = new PersonalityVariant<>(UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG);
    private PersonalityVariant<String> initialItemOfferMessage = new PersonalityVariant<>(UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG);
    private PersonalityVariant<String> furtherItemOfferMessage = new PersonalityVariant<>(UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG);
    private PersonalityVariant<String> acceptedItemOfferMessage = new PersonalityVariant<>(UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG);
    private PersonalityVariant<String> refuseItemDemandMessage = new PersonalityVariant<>(UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG);

    private PersonalityVariant<String> initialMoneyOfferMessage = new PersonalityVariant<>(UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG);
    private PersonalityVariant<String> furtherMoneyOfferMessage = new PersonalityVariant<>(UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG);
    private PersonalityVariant<String> acceptedMoneyOfferMessage = new PersonalityVariant<>(UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG);
    private PersonalityVariant<String> refuseMoneyDemandMessage = new PersonalityVariant<>(UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG);

    private PersonalityVariant<String> powerSuccessMessage = new PersonalityVariant<>(UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG);
    private List<NegotiationScriptNode> powerTrees = new ArrayList<>();

    public NegotiationScript()
    {

    }

    public NegotiationScript(PersonalityVariant<String> greetingMessage,
                             PersonalityVariant<String> initialItemOfferMessage, PersonalityVariant<String> furtherItemOfferMessage, PersonalityVariant<String> acceptedItemOfferMessage, PersonalityVariant<String> refuseItemDemandMessage,
                             PersonalityVariant<String> initialMoneyOfferMessage, PersonalityVariant<String> furtherMoneyOfferMessage, PersonalityVariant<String> acceptedMoneyOfferMessage, PersonalityVariant<String> refuseMoneyDemandMessage,
                             PersonalityVariant<String> powerSuccessMessage, List<NegotiationScriptNode> powerTrees)
    {
        this.greetingMessage = greetingMessage;
        this.initialItemOfferMessage = initialItemOfferMessage;
        this.furtherItemOfferMessage = furtherItemOfferMessage;
        this.acceptedItemOfferMessage = acceptedItemOfferMessage;
        this.refuseItemDemandMessage = refuseItemDemandMessage;

        this.initialMoneyOfferMessage = initialMoneyOfferMessage;
        this.furtherMoneyOfferMessage = furtherMoneyOfferMessage;
        this.acceptedMoneyOfferMessage = acceptedMoneyOfferMessage;
        this.refuseMoneyDemandMessage = refuseMoneyDemandMessage;

        this.powerSuccessMessage = powerSuccessMessage;
        this.powerTrees = powerTrees;
    }

    /**
     * Gets greetingMessage
     * @return greetingMessage
     */
    public PersonalityVariant<String> getGreetingMessage()
    {
        return greetingMessage;
    }

    /**
     * Sets greetingMessage
     */
    public void setGreetingMessage(PersonalityVariant<String> greetingMessage)
    {
        this.greetingMessage = greetingMessage;
    }

    /**
     * Gets initialItemOfferMessage
     * @return initialItemOfferMessage
     */
    public PersonalityVariant<String> getInitialItemOfferMessage()
    {
        return initialItemOfferMessage;
    }

    /**
     * Sets initialItemOfferMessage
     */
    public void setInitialItemOfferMessage(PersonalityVariant<String> initialItemOfferMessage)
    {
        this.initialItemOfferMessage = initialItemOfferMessage;
    }

    /**
     * Gets furtherItemOfferMessage
     * @return furtherItemOfferMessage
     */
    public PersonalityVariant<String> getFurtherItemOfferMessage()
    {
        return furtherItemOfferMessage;
    }

    /**
     * Sets furtherItemOfferMessage
     */
    public void setFurtherItemOfferMessage(PersonalityVariant<String> furtherItemOfferMessage)
    {
        this.furtherItemOfferMessage = furtherItemOfferMessage;
    }

    /**
     * Gets acceptedItemOfferMessage
     * @return acceptedItemOfferMessage
     */
    public PersonalityVariant<String> getAcceptedItemOfferMessage()
    {
        return acceptedItemOfferMessage;
    }

    /**
     * Sets acceptedItemOfferMessage
     */
    public void setAcceptedItemOfferMessage(PersonalityVariant<String> acceptedItemOfferMessage)
    {
        this.acceptedItemOfferMessage = acceptedItemOfferMessage;
    }

    /**
     * Gets refuseItemDemandMessage
     * @return refuseItemDemandMessage
     */
    public PersonalityVariant<String> getRefuseItemDemandMessage()
    {
        return refuseItemDemandMessage;
    }

    /**
     * Sets refuseItemDemandMessage
     */
    public void setRefuseItemDemandMessage(PersonalityVariant<String> refuseItemDemandMessage)
    {
        this.refuseItemDemandMessage = refuseItemDemandMessage;
    }

    /**
     * Gets initialMoneyOfferMessage
     * @return initialMoneyOfferMessage
     */
    public PersonalityVariant<String> getInitialMoneyOfferMessage()
    {
        return initialMoneyOfferMessage;
    }

    /**
     * Sets initialMoneyOfferMessage
     */
    public void setInitialMoneyOfferMessage(PersonalityVariant<String> initialMoneyOfferMessage)
    {
        this.initialMoneyOfferMessage = initialMoneyOfferMessage;
    }

    /**
     * Gets furtherMoneyOfferMessage
     * @return furtherMoneyOfferMessage
     */
    public PersonalityVariant<String> getFurtherMoneyOfferMessage()
    {
        return furtherMoneyOfferMessage;
    }

    /**
     * Sets furtherMoneyOfferMessage
     */
    public void setFurtherMoneyOfferMessage(PersonalityVariant<String> furtherMoneyOfferMessage)
    {
        this.furtherMoneyOfferMessage = furtherMoneyOfferMessage;
    }

    /**
     * Gets acceptedMoneyOfferMessage
     * @return acceptedMoneyOfferMessage
     */
    public PersonalityVariant<String> getAcceptedMoneyOfferMessage()
    {
        return acceptedMoneyOfferMessage;
    }

    /**
     * Sets acceptedMoneyOfferMessage
     */
    public void setAcceptedMoneyOfferMessage(PersonalityVariant<String> acceptedMoneyOfferMessage)
    {
        this.acceptedMoneyOfferMessage = acceptedMoneyOfferMessage;
    }

    /**
     * Gets refuseMoneyDemandMessage
     * @return refuseMoneyDemandMessage
     */
    public PersonalityVariant<String> getRefuseMoneyDemandMessage()
    {
        return refuseMoneyDemandMessage;
    }

    /**
     * Sets refuseMoneyDemandMessage
     */
    public void setRefuseMoneyDemandMessage(PersonalityVariant<String> refuseMoneyDemandMessage)
    {
        this.refuseMoneyDemandMessage = refuseMoneyDemandMessage;
    }

    /**
     * Gets powerSuccessMessage
     * @return powerSuccessMessage
     */
    public PersonalityVariant<String> getPowerSuccessMessage()
    {
        return powerSuccessMessage;
    }

    /**
     * Sets powerSuccessMessage
     */
    public void setPowerSuccessMessage(PersonalityVariant<String> powerSuccessMessage)
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
