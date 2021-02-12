package ru.mail.track.comands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mail.track.message.Chat;
import ru.mail.track.message.Message;
import ru.mail.track.message.MessageStore;
import ru.mail.track.message.SendMessage;
import ru.mail.track.session.Session;

import java.util.List;

/**
 * Created by aviator on 17.11.15.
 */
public class ChatHistoryCommand implements Command {

    static Logger log = LoggerFactory.getLogger(ChatHistoryCommand.class);

    private MessageStore messageStore;

    public ChatHistoryCommand(MessageStore messageStore) {
        this.messageStore = messageStore;
    }

    @Override
    public CommandResultImpl execute(Session session, Message message) {
        CommandResultImpl commandResult = new CommandResultImpl(CommandResult.Status.OK);

        if (session.getSessionUser() == null) {
            commandResult.setStatus(CommandResult.Status.NOT_LOGGED);
            return commandResult;
        }

        SendMessage historyArgs = (SendMessage) message;

        Long chatId = Long.parseLong(historyArgs.getMessage());
        Chat chat = messageStore.getChatById(chatId);

        if (chat == null) {
            commandResult.setStatus(CommandResult.Status.NO_CHATS);
            return commandResult;
        }

        List<Long> chatMessages = chat.getMessageIds();

        for (Long msgId: chatMessages) {
            SendMessage msg = (SendMessage) messageStore.getMessageById(msgId);
            commandResult.addToServerResponse(msg.getMessage());
        }

        commandResult.finishServerResponse();
        return commandResult;
    }
}
