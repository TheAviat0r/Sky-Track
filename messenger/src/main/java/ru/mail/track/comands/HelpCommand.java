package ru.mail.track.comands;

import java.util.Map;

import ru.mail.track.message.Message;
import ru.mail.track.session.Session;

/**
 * Вывести помощь
 */
public class HelpCommand implements Command {

    private Map<CommandType, Command> commands;

    public HelpCommand(Map<CommandType, Command> commands) {
        this.commands = commands;
    }


    @Override
    public CommandResultImpl execute(Session session, Message msg) {
        System.out.println("Executing help");
        /*
        В простом случае просто выводим данные на консоль
        Если будем работать чере сеть, то команде придется передать также объект для работы с сетью

         */

        StringBuilder builder = new StringBuilder();

        for (Map.Entry<CommandType, Command> entry: commands.entrySet()) {
            builder.append(entry.getKey().toString());
        }

        return new CommandResultImpl(CommandResult.Status.OK, builder.toString());
    }
}
