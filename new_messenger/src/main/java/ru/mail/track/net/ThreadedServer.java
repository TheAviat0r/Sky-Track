package ru.mail.track.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mail.track.commands.*;
import ru.mail.track.database.DatabaseStore;
import ru.mail.track.message.MessageStore;
//import ru.mail.track.message.MessageStoreStub;
import ru.mail.track.message.UserStore;
import ru.mail.track.serialization.Protocol;
import ru.mail.track.serialization.JsonProtocol;
import ru.mail.track.AuthorizationService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 */
public class ThreadedServer {

    public static final int PORT = 19050;
    static Logger log = LoggerFactory.getLogger(ThreadedServer.class);
    private volatile boolean isRunning;
    private Map<Long, ConnectionHandler> handlers = new HashMap<>();
    private AtomicLong internalCounter = new AtomicLong(0);
    private ServerSocket sSocket;
    private Protocol protocol;
    private SessionManager sessionManager;
    private CommandHandler commandHandler;
    private static final String manualFileName = "input.txt";



    public ThreadedServer(Protocol protocol, SessionManager sessionManager, CommandHandler commandHandler) {
        try {
            this.protocol = protocol;
            this.sessionManager = sessionManager;
            this.commandHandler = commandHandler;
            sSocket = new ServerSocket(PORT);
            sSocket.setReuseAddress(true);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        Protocol protocol = new JsonProtocol();
        SessionManager sessionManager = new SessionManager();

        DatabaseStore messengerDatabase = new DatabaseStore();
        UserStore userStore = messengerDatabase.getUsersDao();
        MessageStore messageStore = messengerDatabase.getMessagesDao();

        AuthorizationService authService = new AuthorizationService(userStore);

        Map<CommandType, Command> cmds = new HashMap<>();
        cmds.put(CommandType.USER_LOGIN, new LoginCommand(authService, sessionManager));
        cmds.put(CommandType.USER_INFO, new UserInfoCommand(userStore));
        cmds.put(CommandType.USER_PASS, new UserPassCommand(userStore));
        cmds.put(CommandType.CHAT_LIST, new ChatListCommand(messageStore));
        cmds.put(CommandType.CHAT_HISTORY, new ChatHistoryCommand(messageStore));
        cmds.put(CommandType.CHAT_FIND, new ChatFindCommand(messageStore));
        cmds.put(CommandType.CHAT_CREATE, new ChatCreateCommand(userStore, messageStore));
        cmds.put(CommandType.CHAT_SEND, new SendCommand(sessionManager, messageStore));
        cmds.put(CommandType.LOGOUT, new LogoutCommand());

        try {
            cmds.put(CommandType.USER_HELP, new HelpCommand(cmds, manualFileName));
        } catch (IOException e) {
            System.err.println("Unable to open manual file: " + manualFileName);
            System.err.println("Try again and reboot server, shutting down now");
            System.exit(1);
        }

        CommandHandler handler = new CommandHandler(cmds);

        ThreadedServer server = new ThreadedServer(protocol, sessionManager, handler);

        try {
            server.startServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startServer() throws Exception {
        log.info("Started, waiting for connection");

        isRunning = true;
        while (isRunning) {
            Socket socket = sSocket.accept();
            log.info("Accepted. " + socket.getInetAddress());

            ConnectionHandler handler = new SocketConnectionHandler(protocol, sessionManager.createSession(), socket);
            handler.addListener(commandHandler);

            handlers.put(internalCounter.incrementAndGet(), handler);
            Thread thread = new Thread(handler);
            thread.start();
        }
    }

    public void stopServer() {
        isRunning = false;
        for (ConnectionHandler handler : handlers.values()) {
            handler.stop();
        }
    }
}

