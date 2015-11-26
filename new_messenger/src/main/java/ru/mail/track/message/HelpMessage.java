package ru.mail.track.message;

import ru.mail.track.commands.CommandType;

/**
 * Created by Aviator on 25.11.2015.
 */
public class HelpMessage extends ClientSideMessage {

    public HelpMessage(String helpText) {
        setType(CommandType.CLIENT_SIDE);
        setMessageText(helpText);
    }
}
