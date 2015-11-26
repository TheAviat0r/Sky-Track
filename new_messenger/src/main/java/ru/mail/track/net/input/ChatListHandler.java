package ru.mail.track.net.input;

import ru.mail.track.commands.CommandType;
import ru.mail.track.message.Message;
import ru.mail.track.message.SendMessage;
import ru.mail.track.message.UserErrorMessage;

/**
 * Created by Aviator on 26.11.2015.
 */
public class ChatListHandler implements InputHandler {
    public ChatListHandler() {
    }

    @Override
    public Message handleInput(String[] tokens) {
        if (tokens.length != 1) {
            return new UserErrorMessage("Wrong argument amount! Read help and try again.");
        }

        return new SendMessage(CommandType.CHAT_LIST);
    }
}
