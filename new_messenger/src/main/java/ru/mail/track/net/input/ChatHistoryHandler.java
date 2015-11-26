package ru.mail.track.net.input;

import ru.mail.track.commands.CommandType;
import ru.mail.track.message.Message;
import ru.mail.track.message.SendMessage;
import ru.mail.track.message.UserErrorMessage;

/**
 * Created by Aviator on 26.11.2015.
 */
public class ChatHistoryHandler implements InputHandler {
    public ChatHistoryHandler() {
    }

    @Override
    public Message handleInput(String[] tokens) {
        if (tokens.length != 2) {
            return new UserErrorMessage("Wrong argument format, read help!");
        }

        return new SendMessage(CommandType.CHAT_HISTORY, tokens[1]);
    }
}
