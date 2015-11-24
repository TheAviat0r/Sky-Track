package ru.mail.track.jdbc;

/**
 * Created by Aviator on 24.11.2015.
 */
public class DatabaseStore {
    private MessageDatabaseStore messageDatabaseStore;
    private UserDatabaseStore userDatabaseStore;

    public DatabaseStore() {
        messageDatabaseStore = new MessageDatabaseStore();
        userDatabaseStore = new UserDatabaseStore();
    }

    public MessageDatabaseStore getMessagesDao() {
        return messageDatabaseStore;
    }

    public UserDatabaseStore getUsersDao() {
        return userDatabaseStore;
    }
}
