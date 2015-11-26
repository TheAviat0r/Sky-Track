package ru.mail.track.net.input;

import ru.mail.track.commands.CommandType;
import ru.mail.track.message.Message;
import ru.mail.track.message.SendMessage;
import ru.mail.track.message.UserErrorMessage;

/**
 * Created by Aviator on 26.11.2015.
 */
public class ChatCreateHandler implements InputHandler {
    public ChatCreateHandler() {
    }

    @Override
    public Message handleInput(String[] tokens) {
        if (tokens.length != 2) {
            return new UserErrorMessage("Type users ids with those you want to create a chat (read help)");
        }

        return new SendMessage(CommandType.CHAT_CREATE, tokens[1]);
    }
}
