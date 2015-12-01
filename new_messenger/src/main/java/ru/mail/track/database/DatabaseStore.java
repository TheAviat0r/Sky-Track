package ru.mail.track.database;

/**
 * Created by Aviator on 24.11.2015.
 */
public class DatabaseStore {
    private MessageStoreDatabase messageDao;
    private UserStoreDatabase userDatabaseStore;

    public DatabaseStore() {
        messageDao = new MessageStoreDatabase();
        userDatabaseStore = new UserStoreDatabase();
    }

    public MessageStoreDatabase getMessagesDao() {
        return messageDao;
    }

    public UserStoreDatabase getUsersDao() {
        return userDatabaseStore;
    }
}
