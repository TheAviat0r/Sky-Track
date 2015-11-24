package ru.mail.track.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mail.track.message.Message;
import ru.mail.track.session.Session;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Scanner;

/**
 * Вывести помощь
 */
public class HelpCommand implements Command {

    static Logger log = LoggerFactory.getLogger(HelpCommand.class);

    private Map<CommandType, Command> commands;
    private String answer;
    private BaseCommandResult commandResult;
    private Path manualPath;
    private String manualContent;

    public HelpCommand(Map<CommandType, Command> commands, String manualPath) throws IOException {
        this.commands = commands;
        commandResult = new BaseCommandResult();
        commandResult.setStatus(CommandResult.Status.OK);
        this.manualPath = Paths.get(manualPath);

        manualContent = new String(Files.readAllBytes(this.manualPath));
    }


    @Override
    public Message execute(Session session, Message msg) {
        /**
         * В простом случае просто выводим данные на консоль
         * Если будем работать через сеть, то команде придется передать также объект для работы с сетью
         */

//        for (Map.Entry<CommandType, Command> entry : commands.entrySet()) {
//            commandResult.appendNewLine(entry.getKey().toString());
//        }
//
//        return commandResult;

        commandResult.appendNewLine(manualContent);

        return null;
    }
}
