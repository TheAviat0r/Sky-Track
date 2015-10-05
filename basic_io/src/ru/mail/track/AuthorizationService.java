package ru.mail.track;

import java.util.*;
import java.io.Console;

public class AuthorizationService {
    final String USER_LOGIN = "login";
    final String USER_CREAT = "create";
    final String USER_EXIT = "exit";

    private UserStore userStore;

    public AuthorizationService(UserStore userStore) {
        this.userStore = userStore;
    }

    void startAuthorization() {
        String userChoise = "";

        Scanner input = new Scanner(System.in);
        System.out.println("Type commands: login, create, exit");

        while (!USER_EXIT.equals(userChoise)) {
            if (input.hasNextLine()) {
                userChoise = input.nextLine();
                userChoise.toLowerCase();

                if (USER_LOGIN.equals(userChoise)) {
                    login();
                }
                else
                    if (USER_CREAT.equals(userChoise)) creatUser();
                    else System.out.println("Wrong command! Try again.");
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
            System.out.println("User doesn't exist" + userName + " . Register now!");
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
        String newName = input.nextLine();

        System.out.println("Type your password");
        String newPass = input.nextLine();

        User usr = new User(newName, newPass);
        boolean addResult = userStore.addUser(usr);

        if (addResult) System.out.println("Welcome to our system!");
        else {
            System.out.println("Unable to add - " + usr.toString());
            return null;
        }

        return usr;
    }
}
