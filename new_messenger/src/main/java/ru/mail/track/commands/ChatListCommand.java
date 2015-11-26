package ru.mail.track.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mail.track.message.Message;
import ru.mail.track.message.MessageStore;
import ru.mail.track.session.Session;

import java.util.List;

/**
 * Вывести список всех чатов пользователя
 */
public class ChatListCommand implements Command {

    static Logger log = LoggerFactory.getLogger(ChatListCommand.class);

    private MessageStore messageStore;
    private ServerResponse commandResult;

    public ChatListCommand(MessageStore messageStore) {
        this.messageStore = messageStore;
        commandResult = new ServerResponse();
        commandResult.setStatus(CommandResult.Status.OK);
    }


    @Override
    public ServerResponse execute(Session session, Message msg) {
//        SendMessage chatListMsg = (SendMessage) msg;
        if (session.getSessionUser() != null) {
            List<Long> chatIds = messageStore.getChatsByUserId(session.getSessionUser().getId());
            if (chatIds.isEmpty()) {
                commandResult.setResponse("You have no any chats.");
            } else {
                StringBuilder answer = new StringBuilder("Your chats:\n");

                for (Long chatId : chatIds) {
                    answer.append("\n[chat_id: " + chatId + "]");

                    List<Long> chatMembers = messageStore.getChatById(chatId).getParticipantIds();
                    answer.append("[members]:\n");

                    for (Long chatMember: chatMembers) {
                        answer.append("\t" + chatMember + "\n");
                    }

                    answer.append("\n");
                }

                commandResult.setResponse(answer.toString());
            }
            log.info("Success chat_list: {}", session.getSessionUser());
        } else {
            commandResult.setStatus(CommandResult.Status.NOT_LOGGINED);
            log.info("User isn't logged in.");
        }

        return commandResult;
    }
}
