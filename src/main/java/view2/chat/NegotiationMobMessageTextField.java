package view2.chat;

import net.md_5.bungee.api.chat.BaseComponent;
import org.apache.commons.lang.NotImplementedException;
import view2.lib.chat.control.output.ChatTextField;

import java.util.List;

public class NegotiationMobMessageTextField extends ChatTextField {

    private String mobTag;
    private String mobMessage;

    public NegotiationMobMessageTextField(String mobTag, String mobMessage)
    {
        this.mobTag = mobTag;
        this.mobMessage = mobMessage;
    }

    public NegotiationMobMessageTextField()
    {
        this.mobTag = "Unknown Creature";
        this.mobMessage = "...";
    }

    public String getMobTag()
    {
        return mobTag;
    }

    public void setMobTag(String mobTag)
    {
        this.mobTag = mobTag;
    }

    public String getMobMessage()
    {
        return mobMessage;
    }

    public void setMobMessage(String message)
    {
        this.mobMessage = mobMessage;
    }

    public String getMobMessageWithTag()
    {
        return String.format("<%s> %s", mobTag, mobMessage);
    }

    @Override
    public void setChatComponent(String text) {
        throw new NotImplementedException("Use setMobTag and setMobMessage functions.");
    }

    @Override
    public void setChatComponent(List<BaseComponent> components) {
        throw new NotImplementedException("Use setMobTag and setMobMessage functions.");
    }

    @Override
    public void setChatComponent(BaseComponent component) {
        throw new NotImplementedException("Use setMobTag and setMobMessage functions.");
    }

    @Override
    public BaseComponent render() {
        super.setChatComponent(getMobMessageWithTag());
        return super.render();
    }
}