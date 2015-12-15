package ru.mail.track.intergration;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.BeforeClass;
import ru.mail.track.commands.CommandResult;
import ru.mail.track.commands.ServerResponse;
import ru.mail.track.message.Message;
import ru.mail.track.message.SendMessage;
import ru.mail.track.net.MessageListener;
import ru.mail.track.net.ThreadedClient;
import ru.mail.track.net.ThreadedServer;
import ru.mail.track.session.Session;

public class IntegrationTests implements MessageListener {

    private ThreadedClient client;
    private String result;

    private static boolean setUpStatus = false;
    private static int PORT = 19000;

    @BeforeClass
    public static void setup() throws Exception {
        if (!setUpStatus) {
            new Thread(() -> {
                ThreadedServer.main(null);
            }).start();

            setUpStatus = true;
        }
        Thread.sleep(3000);
    }

    @Before
    public void initClient() throws Exception {
        client = ThreadedClient.start();
        client.getHandler().addListener(this);
        Thread.sleep(200);
    }

    @Test
    public void BadLogin() throws Exception {
        ServerResponse commandResult = new ServerResponse("Wrong login or password");
//        assert/*that*/(gotResult(new SendMessage(-1L, "Login Ok"),/*on request*/"login A 1"));
        assert/*that*/(gotResult(commandResult,/*on request*/"login A 11"));
    }

    @Test
    public void NonExistLogin() throws Exception {
        ServerResponse commandResult = new ServerResponse("Wrong login or password");
//        assert/*that*/(gotResult(new SendMessage(-1L, "Login Ok"),/*on request*/"login A 1"));
        assert/*that*/(gotResult(commandResult,/*on request*/"login lalka 1337"));
    }

    @Test
    public void SuccessLogin() throws Exception {
        ServerResponse commandResult = new ServerResponse("Login is OK: man 1");
//        assert/*that*/(gotResult(new SendMessage(-1L, "Login Ok"),/*on request*/"login A 1"));
        assert/*that*/(gotResult(commandResult,/*on request*/"login man 1"));
    }

    @Test
    public void SuccessChatCreate() throws Exception {
        ServerResponse commandResult = new ServerResponse("The chat was created");

        client.processInput("login man 1");

        assert/*that*/(gotResult(commandResult,/*on request*/"chat_create 1,2"));
    }

    @Test
    public void SuccessChatHistory() throws Exception {
        ServerResponse commandResult = new ServerResponse("");

        client.processInput("login man 1");
        Thread.sleep(300);

        assert/*that*/(gotResult(commandResult, "chat_history 30"));
    }

    @Test
    public void SuccessChatSend() throws Exception {
        ServerResponse commandResult = new ServerResponse("OK");
        ThreadedClient anotherClient = ThreadedClient.start();
        Thread.sleep(300);

        client.processInput("login man 1");
        Thread.sleep(300);
        anotherClient.processInput("login st 15");
//
        assert/*that*/(gotResult(commandResult,/*on request*/"chat_send 21 hi"));
    }

    @Test
    public void SuccessChatAccept() throws Exception {
        ThreadedClient anotherClient = ThreadedClient.start();
        anotherClient.getHandler().addListener(anotherClient);
        Thread.sleep(100);

        client.processInput("login man 1");
        Thread.sleep(300);

        anotherClient.processInput("login st 15");
        Thread.sleep(300);

//        assert/*that*/(gotResult(new ServerResponse("hi"), "chat_send 21 hi"));
        anotherClient.processInput("chat_send 21 hi");
        Thread.sleep(500);
//        assert/*that*/(gotResult(new ServerResponse("[21] [st]hi"), ))
    }

    private boolean gotResult(ServerResponse result, String on) throws Exception {
        client.processInput(on);
        Thread.sleep(1000);

        System.out.println("this: (" + this.result + ")");
        System.out.println("source: (" + result.getResponse() + ")");

        return this.result.matches(result.getResponse());
    }

    @Override
    public void onMessage(Session session, Message message) {
        SendMessage msg = (SendMessage) message;
        this.result = msg.getMessage();

        System.out.println("Received: " + this.result);
    }

    @After
    public void close() {
        //todo
    }
}