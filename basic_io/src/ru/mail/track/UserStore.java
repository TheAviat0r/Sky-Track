package ru.mail.track;

public class UserStore {

    private User[] userStorage;
    private int lastElement;
    // Вам нужно выбрать, как вы будете хранить ваших пользователей, например в массиве User users[] = new User[100];

    // проверить, есть ли пользователь с таким именем
    // если есть, вернуть true
    boolean isUserExist(String name) {
        for (User element: userStorage) {
            if (element.getName().equals(name))
                return true;
        }

        return false;
    }

    // Добавить пользователя в хранилище
    void addUser(User user) {
        assert user != null;

        User searchUserResult = getUser(user.getName(), user.getPass());

        if (searchUserResult.getName().length() != 0) {
            System.out.println("Error! User already exists!");
            return;
        }

        userStorage[lastElement] = new User(user.getName(), user.getPass());
        ++lastElement;
    }

    UserStore() {
        userStorage = new User[100];
        lastElement = 0;
    }
    // Получить пользователя по имени и паролю
    User getUser(String name, String pass) {
        if (userStorage[0] != null) {
            for (int i = 0; i < lastElement; i++) {
                if (userStorage[i].getName().equals(name)) {
                    if (userStorage[i].getPass().equals(pass))
                        return userStorage[i];

                    return new User("err", "");
                }
            }
        }

        return new User(new String(""), new String (""));
    }
}
