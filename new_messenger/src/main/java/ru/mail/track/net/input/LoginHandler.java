package ru.mail.track.net.input;

import ru.mail.track.commands.CommandType;
import ru.mail.track.message.ClientSideMessage;
import ru.mail.track.message.LoginMessage;
import ru.mail.track.message.Message;
import ru.mail.track.message.UserErrorMessage;

import java.util.Scanner;

/**
 * Created by Aviator on 25.11.2015.
 */
public class LoginHandler implements InputHandler {
    public LoginHandler() {
    }

    @Override
    public Message handleInput(String[] tokens) {
        LoginMessage loginMessage = new LoginMessage();
        loginMessage.setType(CommandType.USER_LOGIN);
        switch (tokens.length) {
            case 3:
                loginMessage.setArgType(LoginMessage.ArgType.LOGIN);
                loginMessage.setLogin(tokens[1]);
                loginMessage.setPass(tokens[2]);
                break;
            case 1:
                loginMessage.setArgType(LoginMessage.ArgType.CREAT_USER);
                System.out.println("Write your new login and password:");
                Scanner scanner = new Scanner(System.in);
                String[] args = scanner.nextLine().split(" ");
                loginMessage.setLogin(args[0]);
                loginMessage.setPass(args[1]);
                break;
            default:
                return new UserErrorMessage("Wrong arguments format! Read help and try again");
        }

        return loginMessage;
    }
}
