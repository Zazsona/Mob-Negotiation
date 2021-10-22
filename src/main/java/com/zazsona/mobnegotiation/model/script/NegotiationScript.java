package com.zazsona.mobnegotiation.model.script;

import java.util.ArrayList;
import java.util.List;

public class NegotiationScript
{
    private static final String UNDEFINED_MSG = "No text.";

    private PersonalityVariant<String> greetingMessage = new PersonalityVariant<>(UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG);
    private PersonalityVariant<String> initialItemOfferMessage = new PersonalityVariant<>(UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG);
    private PersonalityVariant<String> furtherItemOfferMessage = new PersonalityVariant<>(UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG);
    private PersonalityVariant<String> refuseItemDemandMessage = new PersonalityVariant<>(UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG);
    private PersonalityVariant<String> powerSuccessMessage = new PersonalityVariant<>(UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG);
    private List<NegotiationScriptNode> powerTrees = new ArrayList<>();

    public NegotiationScript()
    {

    }

    public NegotiationScript(PersonalityVariant<String> greetingMessage, PersonalityVariant<String> initialItemOfferMessage, PersonalityVariant<String> furtherItemOfferMessage, PersonalityVariant<String> refuseItemDemandMessage, PersonalityVariant<String> powerSuccessMessage, List<NegotiationScriptNode> powerTrees)
    {
        this.greetingMessage = greetingMessage;
        this.initialItemOfferMessage = initialItemOfferMessage;
        this.furtherItemOfferMessage = furtherItemOfferMessage;
        this.refuseItemDemandMessage = refuseItemDemandMessage;
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
