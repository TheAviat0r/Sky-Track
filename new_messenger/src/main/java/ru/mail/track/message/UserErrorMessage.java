package ru.mail.track.message;

import ru.mail.track.commands.CommandType;

/**
 * Created by Aviator on 25.11.2015.
 */
public class UserErrorMessage extends ClientSideMessage {
    public UserErrorMessage(String errorText) {
        setMessageText(errorText);
        setType(CommandType.CLIENT_SIDE);
    }
}
