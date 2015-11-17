package ru.mail.track.net;

import ru.mail.track.message.Message;
import ru.mail.track.serialization.ProtocolException;

/**
 *
 */
public interface Protocol {

    Message decode(byte[] bytes) throws ProtocolException;

    byte[] encode(Message msg) throws ProtocolException;

}
