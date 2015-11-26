package ru.mail.track.net.input;

import ru.mail.track.commands.CommandType;
import ru.mail.track.message.Message;
import ru.mail.track.message.SendMessage;

/**
 * Created by Aviator on 26.11.2015.
 */
public class LogoutHandler implements InputHandler {

    public LogoutHandler() {
    }

    @Override
    public Message handleInput(String[] tokens) {
        return new SendMessage(CommandType.LOGOUT);
    }
}
