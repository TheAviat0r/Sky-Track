package ru.mail.track.net;

/**
 * Created by aviator on 12/15/15.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class KeyboardInput extends Thread {
    private static Logger log = LoggerFactory.getLogger(KeyboardInput.class);

    private final static String EXIT_COMMAND = "exit";

    private Server server;

    public KeyboardInput(Server server) {
        this.server = server;
    }

    @Override
    public void run() {
        Scanner input = new Scanner(System.in);

        while(true) {
            //System.out.print("admin: $");
            String serverCommand = input.nextLine();
            serverCommand.toLowerCase();

            if (EXIT_COMMAND.equals(serverCommand)) {
                log.info("EXIT command");
                break;
            }
        }

        log.info("Exiting right now");

        server.stopServer();
    }
}
