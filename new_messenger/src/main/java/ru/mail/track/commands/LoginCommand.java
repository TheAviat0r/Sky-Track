package ru.mail.track.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mail.track.AuthorizationService;
import ru.mail.track.message.LoginMessage;
import ru.mail.track.message.Message;
import ru.mail.track.message.User;
import ru.mail.track.net.SessionManager;
import ru.mail.track.session.Session;

/**
 * Выполняем авторизацию по этой команде
 */
public class LoginCommand implements Command {

    static Logger log = LoggerFactory.getLogger(LoginCommand.class);

    private AuthorizationService authService;
    private SessionManager sessionManager;
    ServerResponse commandResult;

    public LoginCommand(AuthorizationService authService, SessionManager sessionManager) {
        this.authService = authService;
        this.sessionManager = sessionManager;
        commandResult = new ServerResponse();
        commandResult.setStatus(CommandResult.Status.OK);
    }


    @Override
    public ServerResponse execute(Session session, Message msg) {

        LoginMessage loginMsg = (LoginMessage) msg;
        String name = loginMsg.getLogin();
        String password = loginMsg.getPass();

        switch (loginMsg.getArgType()) {
            case LOGIN:
                User user = authService.login(name, password);

                if (user == null) {
                    log.info("login: Wrong login or password.");
                    return new ServerResponse("Wrong login or password");
                } else {
                    session.setSessionUser(user);
                    sessionManager.registerUser(user.getId(), session.getId());
                    log.info("Success login: {}", user);
                    UserInfoCommand userInfoCommand = new UserInfoCommand(authService.getUserStore());

                    return new ServerResponse("Login is OK: " + name + " " + password);
                }
            case CREAT_USER:
                User newUser = authService.creatUser(name, password);
                if (newUser == null) {
                    log.info("creat_user: The user with this name has already existed.");

                    return new ServerResponse("This user already exists");
                } else {
                    log.info("Success creat_user: {}", newUser);

                    return new ServerResponse("User is successfully created");
                }
            default:
                log.info("Wrong argType: {}", loginMsg.getArgType());
        }

        return commandResult;
    }
}
