package ru.mail.track.comands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.mail.track.AuthorizationService;
import ru.mail.track.message.LoginMessage;
import ru.mail.track.message.Message;
import ru.mail.track.message.User;
import ru.mail.track.message.UserStore;
import ru.mail.track.net.SessionManager;
import ru.mail.track.session.Session;

/**
 * Выполняем авторизацию по этой команде
 */
public class LoginCommand implements Command {

    static Logger log = LoggerFactory.getLogger(LoginCommand.class);

    private AuthorizationService authorizationService;
    private SessionManager sessionManager;

    public LoginCommand(AuthorizationService authorizationService, SessionManager sessionManager) {
        this.authorizationService = authorizationService;
        this.sessionManager = sessionManager;
    }


    @Override
    public CommandResultImpl execute(Session session, Message msg) {
        LoginMessage loginMsg = (LoginMessage) msg;
        CommandType messageType = msg.getType();
        String name = loginMsg.getLogin();
        String password = loginMsg.getPass();

        switch(messageType) {
            case USER_LOGIN:
                if (session.getSessionUser() != null) {
                    log.info("User {} already logged in.", session.getSessionUser());
                    return new CommandResultImpl(CommandResult.Status.FAILED, "User is already logged");
                } else {
                    if (!(msg instanceof LoginMessage)) {
                        return null;
                    }

                    //            User user = userStore.getUser(loginMsg.getLogin(), loginMsg.getPass());
                    User user = authorizationService.login(name, password);

                    if (user == null) {
                        return new CommandResultImpl(CommandResult.Status.FAILED, "Wrong login or password, check it");
                    }

                    session.setSessionUser(user);
                    sessionManager.registerUser(user.getId(), session.getId());
                    log.info("Success login: {}", user);

                    return new CommandResultImpl(CommandResult.Status.OK, "Successful login");
                }

            case CREAT_USER:
                User newUser = authorizationService.creatUser(name, password);
                if (newUser == null) {
                    log.info("creat_user: The user with this name has already existed.");
                    return new CommandResultImpl(CommandResult.Status.FAILED, "The user with this name has already existed.");
                } else {
                    log.info("Success creat_user: {}", newUser);
                    return new CommandResultImpl(CommandResult.Status.FAILED, "The new user successfully was created.");
                }
            default:
                log.info("Wrong argType: {}", loginMsg.getType());
        }
        /*
        А эта часть у нас уже реализована
        1 проверим, есть ли у нас уже юзер сессии
        2 посмотрим на аргументы команды
        3 пойдем в authorizationService и попробуем получить юзера
         */

        return null;
    }
}
