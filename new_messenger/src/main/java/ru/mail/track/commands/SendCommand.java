package ru.mail.track.commands;

import ru.mail.track.message.Chat;
import ru.mail.track.message.Message;
import ru.mail.track.message.MessageStore;
import ru.mail.track.message.SendMessage;
import ru.mail.track.net.SessionManager;
import ru.mail.track.session.Session;

import java.io.IOException;
import java.util.List;

/**
 *
 */
public class SendCommand implements Command {

    private MessageStore messageStore;
    private SessionManager sessionManager;
    private ServerResponse commandResult;

    public SendCommand(SessionManager sessionManager, MessageStore messageStore) {
        this.sessionManager = sessionManager;
        this.messageStore = messageStore;
        commandResult = new ServerResponse();
        commandResult.setStatus(CommandResult.Status.OK);
    }

    @Override
    public ServerResponse execute(Session session, Message message) {
        SendMessage sendMessage = (SendMessage) message;

        Long chatId = sendMessage.getChatId();
        Chat chat = messageStore.getChatById(chatId);
        messageStore.addMessage(chatId, sendMessage);

        List<Long> parts = chat.getParticipantIds();

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

        return commandResult;
    }
}