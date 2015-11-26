package ru.mail.track.net.input;

import ru.mail.track.commands.CommandType;
import ru.mail.track.message.LoginMessage;
import ru.mail.track.message.Message;
import ru.mail.track.message.UserErrorMessage;

/**
 * Created by Aviator on 26.11.2015.
 */
public class UserInfoHandler implements InputHandler {
    public UserInfoHandler() {
    }

    @Override
    public Message handleInput(String[] tokens) {
        LoginMessage userInfoMessage = new LoginMessage(CommandType.USER_INFO);

        switch(tokens.length) {
            case 1:
                userInfoMessage.setArgType(LoginMessage.ArgType.SELF_INFO);
                userInfoMessage.setUserId(0L);
                break;
            case 2:
                userInfoMessage.setArgType(LoginMessage.ArgType.ID_INFO);
                try {
                    Long id = Long.parseLong(tokens[1]);
                    userInfoMessage.setUserId(id);
                } catch (NumberFormatException e) {
                    return new UserErrorMessage("Wrong argument format! 2nd argument should be positive int");
                }
                break;
            default:
                return new UserErrorMessage("Wrong argument format! Read help and try again");
        }

        return userInfoMessage;
    }
}
