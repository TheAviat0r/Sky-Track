package ru.mail.track.comands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mail.track.message.*;
import ru.mail.track.session.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aviator on 17.11.15.
 */
public class ChatCreateCommand implements Command {

    static Logger log = LoggerFactory.getLogger(ChatCreateCommand.class);

    private MessageStore messageStore;
    private UserStore userStore;


    @Override
    public CommandResultImpl execute(Session session, Message message) {
        CommandResultImpl commandResult = new CommandResultImpl(CommandResult.Status.OK);

        if (!(message instanceof SendMessage)) {
            commandResult.setStatus(CommandResult.Status.FAILED);
            return commandResult;
        }

        SendMessage chatCreateMessage = (SendMessage) message;

        if (session.getSessionUser() == null) {
            commandResult.setStatus(CommandResult.Status.NOT_LOGGED);
        } else {
            List<Long> chatParticipants = new ArrayList<>();

            Long creatorId = session.getSessionUser().getId();

            chatParticipants.add(creatorId); // add chat creator to the chat

            String[] participantsIds = chatCreateMessage.getMessage().split(", ");

            if (participantsIds.length == 2) {
                Long receiverId = Long.parseLong(participantsIds[1]);
                if (messageStore.getChatById(receiverId) == null) {
                    chatParticipants.add(receiverId);
                    messageStore.createChat(chatParticipants);
                }

                commandResult.setServerCode(ServerCode.CHAT_CREATED);
                return commandResult;
            }

            for (String userId: participantsIds) {
                Long Id = Long.parseLong(userId);

                User user = userStore.getUserById(Id);

                if (user == null) {
                    commandResult.setStatus(CommandResult.Status.NOT_EXIST);
                    commandResult.setServerResponse("User [" + Id + "] doesn't exist");
                    return commandResult;
                } else if (user.getId() != creatorId) {
                    chatParticipants.add(Id);
                }

                Chat chat = messageStore.createChat(chatParticipants);
                commandResult.setServerCode(ServerCode.CHAT_CREATED);
                log.info("Chat {} has been created", chat);
            }
        }


        return commandResult;
    }
}
