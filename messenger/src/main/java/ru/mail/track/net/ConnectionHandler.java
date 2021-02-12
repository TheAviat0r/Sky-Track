package ru.mail.track.net;

import java.io.IOException;

import ru.mail.track.message.Message;
import ru.mail.track.serialization.ProtocolException;

/**
 * Обработчик сокета
 */
public interface ConnectionHandler extends Runnable {

    void send(Message msg) throws IOException, ProtocolException;

    void addListener(MessageListener listener);

    void stop();
}
