package ru.mail.track;

import java.util.*;
import java.io.Console;

public class AuthorizationService {
    final static int USER_LOGIN = 1;
    final static int USER_ADD = 2;
    final static int USER_EXIT = 3;

    private UserStore userStore;
    private boolean[] loginInfo;

    public AuthorizationService(UserStore userStore) {
        this.userStore = userStore;
        this.loginInfo = new boolean[100];
    }

    void startAuthorization() {
        int userChoise = 0;

        Scanner input = new Scanner(System.in);
        System.out.println("1 - login, 2 - create account, 3 - exit");

        while (userChoise != USER_EXIT) {
            if (input.hasNextInt()) {
                userChoise = input.nextInt();

                switch(userChoise) {
                    case USER_LOGIN:
                        login();
                        break;
                    case USER_ADD:
                        User newUser = creatUser();
                        if (newUser != null) userStore.addUser(newUser);
                        break;
                    case USER_EXIT:
                        System.out.println("Have a nice day!");
                        return;
                    default:
                        System.out.println("Wrong input! Try again with a proper format!");
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

        if (user.getName().length() == 0) {
            System.out.println("User doesn't exist!");
            return creatUser();
        }

        if (user.getPass().length() == 0) {
            System.out.println("Wrong password! Try again");
            return login();
        }

        System.out.println("You are welcome!");

        return user;
    }

    User creatUser() {
        // 1. Ask for name
        // 2. Ask for pass
        // 3. Add user to UserStore: userStore.addUser(user)

        System.out.println("What is your name?");

        Scanner input = new Scanner(System.in);

        String newName = input.nextLine();

        System.out.println("Type your password");
        String newPass = input.nextLine();

        User usr = new User(newName, newPass);
        System.out.println("usr: " + newName + " " + newPass);

        return usr;
    }

    boolean isLogin() {
        System.out.println("To login, type 1");
        System.out.println("To create new account, type 2");

        Scanner input = new Scanner(System.in);

        if (input.hasNextInt()) {
            int userChoise = input.nextInt();

            while (userChoise != 1 || userChoise != 2) {
                switch (userChoise) {
                    case 1:
                        return true;
                    case 2:
                        return false;
                    default:
                        System.out.println("Wrong input! Try again!");
                        break;
                }
            }
        }

        return false;
    }
}
