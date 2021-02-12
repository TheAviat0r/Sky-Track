package ru.mail.track.comands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.mail.track.message.*;
import ru.mail.track.session.Session;
/**
 * Created by aviator on 17.11.15.
 */
public class ChatFindCommand implements Command {

    static Logger log = LoggerFactory.getLogger(ChatFindCommand.class);

    private MessageStore messageStore;

    public ChatFindCommand(MessageStore messageStore) {
        this.messageStore = messageStore;
    }

    @Override
    public CommandResultImpl execute(Session session, Message message) {
        CommandResultImpl commandResult = new CommandResultImpl(CommandResult.Status.OK);

        if (session.getSessionUser() == null) {
            commandResult.setStatus(CommandResult.Status.NOT_LOGGED);
        }

        if (message != null && !(message instanceof SendMessage)) {
            commandResult.setStatus(CommandResult.Status.FAILED);
            return commandResult;
        }

        SendMessage findMessage = (SendMessage) message;

        if (session.getSessionUser() == null) {
            commandResult.setStatus(CommandResult.Status.NOT_LOGGED);
            return commandResult;
        }

        String[] findArguments = findMessage.getMessage().split(" ");

        if (findArguments.length != 3) {
            commandResult.setStatus(CommandResult.Status.FAILED);
            commandResult.setServerResponse("WRong Argument Format!");
            commandResult.finishServerResponse();
            return commandResult;
        }

        Chat chat = messageStore.getChatById(Long.parseLong(findArguments[1]));

        if (chat == null) {
            commandResult.setStatus(CommandResult.Status.NO_CHATS);
            commandResult.addToServerResponse("Chat " + findArguments[1] + " doesn't exist");
            commandResult.finishServerResponse();
            return commandResult;
        }

        for (Long messageId: chat.getMessageIds()) {
            SendMessage msg = (SendMessage) messageStore.getMessageById(messageId);

            if (msg.getMessage().contains(findArguments[2])) {
                commandResult.addToServerResponse(msg.getMessage());
            }

            log.info("[{}] Founded", msg.getMessage());
        }

        if (commandResult.getServerResponse().isEmpty()) {
            commandResult.addToServerResponse("Unable to find any messages");
            log.info("Empty message set");
        }

        commandResult.finishServerResponse();
        return commandResult;
    }
}






