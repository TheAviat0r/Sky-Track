package ru.mail.track.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mail.track.message.*;
import ru.mail.track.net.SessionManager;
import ru.mail.track.session.Session;

import java.io.IOException;
import java.util.List;

/**
 *
 */
public class SendCommand implements Command {

    private Logger log = LoggerFactory.getLogger(SendCommand.class);

    private MessageStore messageStore;
    private SessionManager sessionManager;
    private UserStore userStore;
    //private ServerResponse commandResult;

    public SendCommand(SessionManager sessionManager, MessageStore messageStore, UserStore userStore) {
        this.sessionManager = sessionManager;
        this.messageStore = messageStore;
        this.userStore = userStore;
    }

    @Override
    public ServerResponse execute(Session session, Message message) {
        if (session.getSessionUser() == null) {
            return new ServerResponse("You have to login");
        }

        ServerResponse commandResult = new ServerResponse();
        commandResult.setStatus(CommandResult.Status.OK);

        SendMessage sendMessage = (SendMessage) message;

//        session.getSessionUser();

        log.info("session id is setted: {}", session.getSessionUser().getId());

        sendMessage.setSender(session.getSessionUser().getId());

        Long chatId = sendMessage.getChatId();
        Chat chat = messageStore.getChatById(chatId);

        if (chat == null) {
            commandResult.setResponse("unable to find chat id: " + chatId);
            return commandResult;
        }

        messageStore.addMessage(chatId, sendMessage);

        List<Long> parts = chat.getParticipantIds();

        sendMessage.setMessage("[" + chat.getId() + "]" +
                            "[" + session.getSessionUser().getName() + "]" + sendMessage.getMessage());

        try {
            for (Long userId : parts) {
                Session userSession = sessionManager.getSessionByUser(userId);
                if (userSession != null) {
                    userSession.getConnectionHandler().send(message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ServerResponse("OK");
    }
}
