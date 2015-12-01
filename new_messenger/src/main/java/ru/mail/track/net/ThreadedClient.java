package ru.mail.track.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.mail.track.commands.CommandType;
import ru.mail.track.message.*;
import ru.mail.track.net.input.*;
import ru.mail.track.serialization.JsonProtocol;
import ru.mail.track.serialization.Protocol;
import ru.mail.track.session.Session;
import ru.mail.track.net.input.InputHandler;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


/**
 * Клиентская часть
 */
public class ThreadedClient implements MessageListener {

    static Logger log = LoggerFactory.getLogger(ThreadedClient.class);

    public static final int PORT = 19050;
    public static final String HOST = "localhost";

    ConnectionHandler connectionHandler;
    private Protocol protocol = new JsonProtocol();

    private Map<String, InputHandler> inputHandlerMap = new HashMap();
    private String helpFilePath = "input.txt";
    private Session session;

    public ThreadedClient() {
        try {
            inputHandlerMap.put("help", new HelpHandler(helpFilePath));
            inputHandlerMap.put("login", new LoginHandler());
            inputHandlerMap.put("user_info", new UserInfoHandler());
            inputHandlerMap.put("user_pass", new UserPassHandler());
            inputHandlerMap.put("logout", new LogoutHandler());
            inputHandlerMap.put("chat_list", new ChatListHandler());
            inputHandlerMap.put("chat_create", new ChatCreateHandler());
            inputHandlerMap.put("chat_history", new ChatHistoryHandler());
            inputHandlerMap.put("chat_find", new ChatFindHandler());
            inputHandlerMap.put("chat_send", new ChatSendHandler());


            Socket socket = new Socket(HOST, PORT);
            session = new Session();
            connectionHandler = new SocketConnectionHandler(protocol, session, socket);

            // Этот класс будет получать уведомления от socket connectionHandler
            connectionHandler.addListener(this);

            Thread socketHandler = new Thread(connectionHandler);
            socketHandler.start();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static void main(String[] args) throws Exception {
        ThreadedClient client = new ThreadedClient();
        Scanner scanner = new Scanner(System.in);
        System.out.println("$");

        while (true) {
            String input = scanner.nextLine();
            if ("q".equals(input)) {
                return;
            }
            client.processInput(input);
        }
    }

    public void processInput(String line) throws IOException {
        String[] tokens = line.split(" ");
        log.info("Tokens: {}", Arrays.toString(tokens));

        InputHandler handler = inputHandlerMap.get(tokens[0]);

        if (handler == null) {
            System.out.println("Wrong input, type help to get the messenger's user guide");
            return;
        }

        Message msg = handler.handleInput(tokens);

        if (msg == null) {
            System.out.println("Wrong command: [" + tokens[0] + "]. Type help for the list of available commands and try again");
            return;
        }

        if (msg.getType() == CommandType.CLIENT_SIDE) {
            ClientSideMessage message = (ClientSideMessage) msg;
            message.showInfo();
        } else {
           connectionHandler.send(msg);
        }
//        switch (cmdType) {
//            case "login":
//                LoginMessage loginMessage = new LoginMessage();
//                loginMessage.setType(CommandType.USER_LOGIN);
//                switch (tokens.length) {
//                    case 3:
//                        loginMessage.setArgType(LoginMessage.ArgType.LOGIN);
//                        loginMessage.setLogin(tokens[1]);
//                        loginMessage.setPass(tokens[2]);
//                        connectionHandler.send(loginMessage);
//                        break;
//                    case 1:
//                        loginMessage.setArgType(LoginMessage.ArgType.CREAT_USER);
//                        System.out.println("Write your new login and password:");
//                        Scanner scanner = new Scanner(System.in);
//                        String[] args = scanner.nextLine().split(" ");
//                        loginMessage.setLogin(args[0]);
//                        loginMessage.setPass(args[1]);
//                        connectionHandler.send(loginMessage);
//                        break;
//                    default:
//                        System.out.println("Wrong amount of arguments. Try <help>");
//                }
//                break;
//            case "send":
//                SendMessage sendMessage = new SendMessage();
//                sendMessage.setType(CommandType.CHAT_SEND);
//                sendMessage.setChatId(Long.valueOf(tokens[1]));
//
//                StringBuilder messageBuilder = new StringBuilder();
//
//                for (int i = 2; i < tokens.length; i++) {
//                    messageBuilder.append(tokens[i]);
//                    messageBuilder.append(" ");
//                }
//
//                log.info("message was created: {}", messageBuilder.toString());
//
//                sendMessage.setMessage(messageBuilder.toString());
//                connectionHandler.send(sendMessage);
//                break;
//            case "help":
//                SendMessage helpMessage = new SendMessage();
//                helpMessage.setType(CommandType.USER_HELP);
//                connectionHandler.send(helpMessage);
//                break;
//            case "user_info":
//                LoginMessage userInfoMessage = new LoginMessage();
//                userInfoMessage.setType(CommandType.USER_INFO);
//                switch (tokens.length) {
//                    case 1:
//                        userInfoMessage.setArgType(LoginMessage.ArgType.SELF_INFO);
//                        userInfoMessage.setUserId(0L);
//                        connectionHandler.send(userInfoMessage);
//                        break;
//                    case 2:
//                        userInfoMessage.setArgType(LoginMessage.ArgType.ID_INFO);
//                        userInfoMessage.setUserId(Long.parseLong(tokens[1]));
//                        connectionHandler.send(userInfoMessage);
//                        break;
//                    default:
//                        System.out.println("Wrong amount of arguments. Try <help>");
//                }
//                break;
//            case "user_pass":
//                SendMessage userPassMessage = new SendMessage();
//                userPassMessage.setType(CommandType.USER_PASS);
//                switch (tokens.length) {
//                    case 3:
//                        userPassMessage.setMessage(tokens[1] + ">" + tokens[2]);
//                        connectionHandler.send(userPassMessage);
//                        break;
//                    default:
//                        System.out.println("Wrong amount of arguments. Try <help>");
//                }
//                break;
//            case "chat_list":
//                SendMessage chatListMessage = new SendMessage();
//                chatListMessage.setType(CommandType.CHAT_LIST);
//                connectionHandler.send(chatListMessage);
//                break;
//            case "chat_create":
//                SendMessage chatCreateMessage = new SendMessage();
//                chatCreateMessage.setType(CommandType.CHAT_CREATE);
//                switch (tokens.length) {
//                    case 2:
//                        chatCreateMessage.setMessage(tokens[1]);
//                        connectionHandler.send(chatCreateMessage);
//                        break;
//                    default:
//                        System.out.println("Wrong amount of arguments. Try <help>");
//                }
//                break;
//            case "chat_history":
//                SendMessage chatHistoryMessage = new SendMessage();
//                chatHistoryMessage.setType(CommandType.CHAT_HISTORY);
//                switch (tokens.length) {
//                    case 2:
//                        chatHistoryMessage.setMessage(tokens[1]);
//                        connectionHandler.send(chatHistoryMessage);
//                        break;
//                    default:
//                        System.out.println("Wrong amount of arguments. Try <help>");
//                }
//                break;
//            case "chat_find":
//                SendMessage chatFindMessage = new SendMessage();
//                chatFindMessage.setType(CommandType.CHAT_FIND);
//                switch (tokens.length) {
//                    case 3:
//                        chatFindMessage.setMessage(tokens[1] + ">" + tokens[2]);
//                        connectionHandler.send(chatFindMessage);
//                        break;
//                    default:
//                        System.out.println("Wrong amount of arguments. Try <help>");
//                }
//            default:
//                System.out.println("Invalid input: " + line);
//        }


    }

    @Override
    public void onMessage(Session session, Message msg) {
        System.out.printf("\n%s\n", ((SendMessage) msg).getMessage());
    }
}
