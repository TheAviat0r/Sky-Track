package ru.mail.track;

import java.util.*;
import java.io.Console;

public class AuthorizationService {
    final static String USER_LOGIN = "login";
    final static String USER_CREATE = "create";
    final static String USER_EXIT = "exit";

    final static int USER_LOGIN_HASH = USER_LOGIN.hashCode();
    final static int USER_CREATE_HASH = USER_CREATE.hashCode();
    final static int USER_EXIT_HASH = USER_EXIT.hashCode();

    private UserStore userStore;

    public AuthorizationService(UserStore userStore) {
        this.userStore = userStore;
    }

    void startAuthorization() {
        String userChoise = "";

        Scanner input = new Scanner(System.in);
        System.out.println("Type commands: login, create, exit");

        while (true) {
            if (input.hasNextLine()) {
                userChoise = input.nextLine();
                userChoise.toLowerCase();

                if (userChoise.hashCode() == USER_LOGIN_HASH) {
                    login();
                    continue;
                }
                if (userChoise.hashCode() == USER_CREATE_HASH) {
                    creatUser();
                    continue;
                }
                if (userChoise.hashCode() == USER_EXIT_HASH) {
                    System.out.println("See you soon!");
                    break;
                }
            }
        }
    }

    User login() {
//            1. Ask for name
//            2. Ask for password
//            3. Ask UserStore for user:  userStore.getUser(name, pass)
        System.out.println("What is your name?");

        Scanner input = new Scanner(System.in);

        String userName = input.nextLine();

        System.out.println("Enter your password");
        String userPassword = input.nextLine();

        User user = userStore.getUser(userName, userPassword);

        if (user == null) {
            System.out.println("User doesn't exist: " + userName + " Register now!");
            return creatUser();
        }

        if (user.getName().length() == 0) {
            System.out.println("Wrong password! ");
            return null;
        }

        System.out.println("You are welcome!");

        return user;
    }

    User creatUser() {
        // 1. Ask for name
        // 2. Ask for pass
        // 3. Add user to UserStore: userStore.addUser(user)
        Scanner input = new Scanner(System.in);

        System.out.println("What is your name?");
        String newUserName = input.nextLine();

        System.out.println("Type your password");
        String newUserPass = input.nextLine();

        User user = new User(newUserName, newUserPass);

        if (userStore.addUser(user)) {
            System.out.println("Welcome to our system!");
        }
        else {
            System.out.println("Unable to add - " + user.toString());
            return null;
        }

        return user;
    }
}
