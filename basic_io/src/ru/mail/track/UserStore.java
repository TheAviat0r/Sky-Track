package ru.mail.track;

import java.util.ArrayList;

public class UserStore {

    private ArrayList<User> userStorage;
    // Вам нужно выбрать, как вы будете хранить ваших пользователей, например в массиве User users[] = new User[100];

    // проверить, есть ли пользователь с таким именем
    // если есть, вернуть true
    private boolean isUserExist(String name) {
        for (int i = 0; i < userStorage.size(); i++) {
            if (userStorage.get(i).equals(name))
                return true;
        }

        return false;
    }

    // Добавить пользователя в хранилище
    public boolean addUser(User user) {
        if (user == null) {
            System.err.println(String.format("Failed to add user: %s", user));
            return false;
        }

        if (isUserExist(user.getName())) {
            System.out.print("Error! User already exists: " + user.getName());
            return false;
        }

        userStorage.add(user);
        return true;
    }

    public UserStore() {
        userStorage = new ArrayList<User>();
        userStorage.ensureCapacity(100);
    }
    // Получить пользователя по имени и паролю
    public User getUser(String name, String pass) {
        for (int i = 0; i < userStorage.size(); i++){
            if (userStorage.get(i).getName().equals(name)) {
                if (userStorage.get(i).getPass().equals(pass)) {
                    return userStorage.get(i);
                }
                else {
                    return new User("", ""); //wrong password
                }
            }
        }

        return null;
    }
}
