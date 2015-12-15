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

    public ThreadedClient(int port) {           // for testing only
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
//            connectionHandler.addListener(this);

            Thread socketHandler = new Thread(connectionHandler);

            socketHandler.start();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static ThreadedClient start() {
        return new ThreadedClient(1337);
    }

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

    public ConnectionHandler getHandler() {
        return connectionHandler;
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
    }

    @Override
    public void onMessage(Session session, Message msg) {
        SendMessage incomingMessage = (SendMessage) msg;

        if (incomingMessage.getMessage().length() > 0) {
            System.out.printf("\n------------------\n%s\n------------------\n", incomingMessage.getMessage());
        }
    }
}
