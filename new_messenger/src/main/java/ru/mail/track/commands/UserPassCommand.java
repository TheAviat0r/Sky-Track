package ru.mail.track.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mail.track.message.Message;
import ru.mail.track.message.SendMessage;
import ru.mail.track.message.User;
import ru.mail.track.message.UserStore;
import ru.mail.track.session.Session;

/**
 * Поменять пароль
 */
public class UserPassCommand implements Command {

    static Logger log = LoggerFactory.getLogger(UserPassCommand.class);

    private UserStore userStore;
    private ServerResponse commandResult;

    public UserPassCommand(UserStore userStore) {
        this.userStore = userStore;
    }

    @Override
    public ServerResponse execute(Session session, Message msg) {
        commandResult = new ServerResponse();
        commandResult.setStatus(CommandResult.Status.OK);

        SendMessage userPassMsg = (SendMessage) msg;

        User currentUser = session.getSessionUser();

        if (currentUser == null) {
            commandResult.setResponse("You are not logged in");
            log.info("Not logged in");
            return commandResult;
        }

        String[] args = userPassMsg.getMessage().split(">");

        if (args.length != 2) {
            commandResult.setResponse("Wrong amount of arguments: " + args);
            log.info("Wrong args: {}", args);
            return commandResult;
        }

        if (!currentUser.getPass().matches(args[0])) {
            commandResult.setResponse("Wrong current password: [" + args[0]);
            log.info("Wrong pass {}", args[0]);
            return commandResult;
        }

        currentUser.setPass(args[1]);
        userStore.updateUser(currentUser);

        log.info("success set pass: [{}]", userStore.getUserById(currentUser.getId()).getPass());

        commandResult.setResponse("Password was successfully changed to " + userPassMsg.getMessage());
        /*if (session.getSessionUser() != null) {
            String[] args = userPassMsg.getMessage().split(">");
            if (session.getSessionUser().getPass().equals(args[0])) {
                session.getSessionUser().setPass(args[1]);
                commandResult.setResponse("The password changed to " + args[1]);
                log.info("Success set_pass: {}", session.getSessionUser());
            } else {
                commandResult.setResponse("Wrong old password.");
                log.info("set_pass: Wrong old password.");
            }
        } else {
            commandResult.setStatus(CommandResult.Status.NOT_LOGGINED);
            log.info("User isn't logged in.");
        }*/

        return commandResult;
    }
}
