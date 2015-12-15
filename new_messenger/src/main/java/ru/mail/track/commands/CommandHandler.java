package ru.mail.track.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mail.track.message.Message;
import ru.mail.track.message.SendMessage;
import ru.mail.track.net.MessageListener;
import ru.mail.track.session.Session;

import java.io.IOException;
import java.util.Map;

/**
 *
 */
public class CommandHandler implements MessageListener {

    static Logger log = LoggerFactory.getLogger(CommandHandler.class);

    Map<CommandType, Command> commands;

    public CommandHandler(Map<CommandType, Command> commands) {
        this.commands = commands;
    }

    @Override
    public void onMessage(Session session, Message message) {
        Command cmd = commands.get(message.getType());
        log.info("onMessage: {} type {}", message, message.getType());
        ServerResponse commandResult = cmd.execute(session, message);

        switch (commandResult.getStatus()) {
            case OK:
                break;
            case NOT_LOGGINED:
                commandResult.setResponse("You must log in.");
                break;
            case LOGGED_OUT:
                return;
            case FAILED:
                break;
            default:
        }

        // Отправить текстовый результат выполнения команды
        try {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setType(CommandType.CHAT_SEND);
            sendMessage.setChatId(0L);
            sendMessage.setMessage(commandResult.getResponse());
            session.getConnectionHandler().send(sendMessage);

            if (commandResult.getStatus() == CommandResult.Status.LOGGED_OUT) {
                session.logOut();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
