package com.zazsona.mobnegotiation.view2.chat;

import com.zazsona.mobnegotiation.view2.lib.chat.control.input.ChatTextInputField;
import com.zazsona.mobnegotiation.view2.lib.chat.control.layout.ChatBoxLayout;
import com.zazsona.mobnegotiation.view2.lib.chat.control.layout.VerticalChatBoxLayout;
import com.zazsona.mobnegotiation.view2.lib.chat.control.panel.ChatPanel;

public class NegotiationChatPanel extends ChatPanel {

    /**
     * <Funky Zombie> Hello!
     *
     *  [1] > [Lend me your power]
     *  [2] > [I want items]
     *  [3] > [All out Attack]
     *  [4] > [Return to Battle]
     */

    private NegotiationMobMessageTextField mobMessageField;
    private ChatTextInputField textInputField;
    private VerticalChatBoxLayout playerActionButtonLayout;

    public NegotiationChatPanel() {
        super(new VerticalChatBoxLayout());
        VerticalChatBoxLayout chatBoxLayout = (VerticalChatBoxLayout) super.getRootLayout();

        mobMessageField = new NegotiationMobMessageTextField();
        textInputField = new ChatTextInputField();
        playerActionButtonLayout = new VerticalChatBoxLayout();

        chatBoxLayout.addControl(0, mobMessageField);
        chatBoxLayout.addControl(1, playerActionButtonLayout);
        chatBoxLayout.addControl(2, textInputField);
    }

    public NegotiationMobMessageTextField getMobMessageField()
    {
        return mobMessageField;
    }

    public ChatBoxLayout getNegotiationActionButtonLayout()
    {
        return playerActionButtonLayout;
    }
}
