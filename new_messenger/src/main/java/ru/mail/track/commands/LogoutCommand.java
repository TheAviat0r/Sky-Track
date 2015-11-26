package ru.mail.track.commands;

import ru.mail.track.message.Message;
import ru.mail.track.session.Session;

/**
 * Created by Aviator on 26.11.2015.
 */
public class LogoutCommand implements Command {
    public LogoutCommand() {
    }

    @Override
    public ServerResponse execute(Session session, Message message) {
        return new ServerResponse(CommandResult.Status.LOGGED_OUT, "You have successfully logged out");
    }
}
