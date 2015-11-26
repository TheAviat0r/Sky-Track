package ru.mail.track.message;

import ru.mail.track.commands.CommandType;

/**
 * Created by Aviator on 25.11.2015.
 */
public abstract class ClientSideMessage extends Message {
    private String messageText;

    public ClientSideMessage(String messageText) {
        this.messageText = messageText;
    }

    public ClientSideMessage() {
        super.setType(CommandType.CLIENT_SIDE);
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public void showInfo() {
        System.out.println(messageText);
    }
}
