package ru.mail.track.database;

/**
 * Created by Aviator on 24.11.2015.
 */
public class DatabaseStore {
    private MessageDao messageDao;
    private UserDao userDatabaseStore;

    public DatabaseStore() {
        messageDao = new MessageDao();
        userDatabaseStore = new UserDao();
    }

    public MessageDao getMessagesDao() {
        return messageDao;
    }

    public UserDao getUsersDao() {
        return userDatabaseStore;
    }
}
