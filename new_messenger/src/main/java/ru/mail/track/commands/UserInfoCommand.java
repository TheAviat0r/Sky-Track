package ru.mail.track.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mail.track.message.LoginMessage;
import ru.mail.track.message.Message;
import ru.mail.track.message.User;
import ru.mail.track.message.UserStore;
import ru.mail.track.session.Session;

/**
 * Выполняем информацию о пользователе
 */
public class UserInfoCommand implements Command {

    static Logger log = LoggerFactory.getLogger(UserInfoCommand.class);

    private UserStore userStore;

    public UserInfoCommand(UserStore userStore) {
        this.userStore = userStore;
    }


    @Override
    public ServerResponse execute(Session session, Message msg) {
        ServerResponse commandResult = new ServerResponse();
        commandResult.setStatus(CommandResult.Status.OK);

        LoginMessage userInfoMsg = (LoginMessage) msg;

        User usr = session.getSessionUser();

        if (usr == null) {
            commandResult.appendNewLine("Failed. Login into system before this command");
            log.info("User isn't logged into system");
            return commandResult;

        }
        switch (userInfoMsg.getArgType()) {
            case SELF_INFO:
                commandResult.appendNewLine("login: " + usr.getName());
                commandResult.appendNewLine("password: " + usr.getPass());
                commandResult.appendNewLine("id: " + usr.getId());
                log.info("Success self_info: {}", usr);
                break;
            case ID_INFO:
                User user = userStore.getUserById(userInfoMsg.getUserId());
                if (user != null) {
                    commandResult.appendNewLine("login: " + user.getName());
                    commandResult.appendNewLine("id: " + user.getId());
                    log.info("Success id_info: {}", userStore.getUserById(userInfoMsg.getUserId()));
                } else {
                    log.info("Wrong userId: {}", userInfoMsg.getUserId());
                    commandResult.setResponse("Wrong user id.");
                }
                break;
            default:
                log.info("Wrong argType: {}", userInfoMsg.getArgType());
        }

        return commandResult;
    }
}
