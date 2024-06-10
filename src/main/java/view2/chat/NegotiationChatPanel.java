package view2.chat;

import view2.lib.chat.control.input.ChatTextInputField;
import view2.lib.chat.control.layout.VerticalChatBoxLayout;
import view2.lib.chat.control.output.ChatTextField;
import view2.lib.chat.control.panel.ChatPanel;

public class NegotiationChatPanel extends ChatPanel {

    /**
     * <Funky Zombie> Hello!
     *
     *  [1] > [Lend me your power]
     *  [2] > [I want items]
     *  [3] > [All out Attack]
     *  [4] > [Return to Battle]
     */

    private ChatTextField mobMessageField;

    private ChatTextInputField textInputField;
    private VerticalChatBoxLayout playerActionButtonLayout;

    public NegotiationChatPanel() {
        super(new VerticalChatBoxLayout());
        VerticalChatBoxLayout chatBoxLayout = (VerticalChatBoxLayout) super.getRootLayout();

        mobMessageField = new ChatTextField("<Unknown Creature> ...");
        textInputField = new ChatTextInputField();
        playerActionButtonLayout = new VerticalChatBoxLayout();
    }
}
