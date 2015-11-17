package ru.mail.track.comands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mail.track.message.Message;
import ru.mail.track.message.MessageStore;
import ru.mail.track.message.SendMessage;
import ru.mail.track.session.Session;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by aviator on 17.11.15.
 */
public class ChatListCommand implements Command {

    static Logger log = LoggerFactory.getLogger(ChatListCommand.class);

    private MessageStore messageStore;
    private CommandResultImpl commandResult;

    public ChatListCommand(MessageStore messageStore) {
        this.messageStore = messageStore;
        this.commandResult = new CommandResultImpl(CommandResult.Status.OK);
    }

    @Override
    public CommandResultImpl execute(Session session, Message message) {
        if (session.getSessionUser() != null) {
            if (message != null && !(message instanceof SendMessage)) {
                commandResult.setStatus(CommandResult.Status.FAILED);
            }

            List<Long> userChatsIds = messageStore.getChatsByUserId(session.getId());

            if (userChatsIds.isEmpty()) {
                commandResult.setStatus(CommandResult.Status.NO_CHATS);
            } else {
                StringBuilder serverAnswer = new StringBuilder("Your available chats:\n");

                for (Long chatId: userChatsIds) {
                    serverAnswer.append(chatId);
                }

                commandResult.setServerResponse(serverAnswer.toString());
            }

        } else {
            commandResult.setStatus(CommandResult.Status.NOT_LOGGED);
            log.info("User is not logged in the system");
        }

        return commandResult;
    }
}
