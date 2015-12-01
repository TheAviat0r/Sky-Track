package ru.mail.track.net.input;

import ru.mail.track.commands.CommandType;
import ru.mail.track.message.Message;
import ru.mail.track.message.SendMessage;
import ru.mail.track.message.UserErrorMessage;

/**
 * Created by Aviator on 26.11.2015.
 */
public class ChatSendHandler implements InputHandler{

    public ChatSendHandler() {
    }

    @Override
    public Message handleInput(String[] tokens) {
        if (tokens.length < 3) {
            return new UserErrorMessage("Wrong argument format!");
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 2; i < tokens.length; i++) {
            sb.append(tokens[i]);
            sb.append(" ");
        }

        SendMessage sendMessage = new SendMessage(CommandType.CHAT_SEND, sb.toString());
        sendMessage.setChatId(Long.parseLong(tokens[1]));

        return sendMessage;
    }
}
