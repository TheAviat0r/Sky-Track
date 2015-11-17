package ru.mail.track.comands;

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
    private CommandResultImpl commandResult;

    public UserInfoCommand(UserStore userStore) {
        this.userStore = userStore;
        commandResult = new CommandResultImpl(CommandResult.Status.OK);
    }

    @Override
    public CommandResultImpl execute(Session session, Message msg) {
        LoginMessage userInfoMsg = (LoginMessage) msg;
        switch (userInfoMsg.getArgType()) {
            case SELF_INFO:
                if (session.getSessionUser() != null) {
                    commandResult.addToServerResponse("login: " + session.getSessionUser().getName());
                    commandResult.addToServerResponse("password: " + session.getSessionUser().getPass());
                    log.info("Success self_info: {}", session.getSessionUser());
                } else {
                    commandResult.setStatus(CommandResult.Status.NOT_LOGGED);
                    log.info("User isn't logged in.");
                }
                break;
            case ID_INFO:
                User user = userStore.getUserById(userInfoMsg.getUserId());
                if (user != null) {
                    commandResult.addToServerResponse("login: " + user.getName());
                    commandResult.addToServerResponse("password: " + user.getPass());
                    log.info("Success id_info: {}", userStore.getUserById(userInfoMsg.getUserId()));
                } else {
                    log.info("Wrong userId: {}", userInfoMsg.getUserId());
                    commandResult.addToServerResponse("Wrong user id.");
                }
                break;
            default:
                log.info("Wrong argType: {}", userInfoMsg.getArgType());
        }


        return commandResult;
    }
}
