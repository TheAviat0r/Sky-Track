package ru.mail.track.net.input;

import ru.mail.track.commands.CommandType;
import ru.mail.track.message.Message;
import ru.mail.track.message.SendMessage;
import ru.mail.track.message.User;
import ru.mail.track.message.UserErrorMessage;
import ru.mail.track.session.Session;

/**
 * Created by Aviator on 26.11.2015.
 */
public class UserPassHandler implements InputHandler {

    public UserPassHandler() {
    }

    @Override
    public Message handleInput(String[] tokens) {
        if (tokens.length != 3) {
            return new UserErrorMessage("Wrong argument format! Read help (type help in console) and try again");
        }

        SendMessage userPassMessage = new SendMessage(CommandType.USER_PASS, tokens[1] + ">" + tokens[2]);

        return userPassMessage;
    }
}
