package ru.mail.track.net.input;

import ru.mail.track.message.HelpMessage;
import ru.mail.track.message.Message;
import ru.mail.track.message.UserErrorMessage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Aviator on 25.11.2015.
 */
public class HelpHandler implements InputHandler {

    Path filePath;
    String helpText;

    public HelpHandler(String helpFilePath) throws IOException {
        try {
            filePath = Paths.get(helpFilePath);

            helpText = new String(Files.readAllBytes(filePath));
        } catch (IOException e) {
            // TODO: handle this IOException in a normal way
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public Message handleInput(String[] tokens) {
        if (tokens.length == 1) {
            return new HelpMessage(helpText);
        }

        return new UserErrorMessage("Wrong arguments amount! Type help without anything else and try again");
    }
}
