package ru.mail.track.net.input;

import ru.mail.track.message.Message;

/**
 * Created by Aviator on 25.11.2015.
 */
public interface InputHandler {
    Message handleInput(String[] tokens);
}
