package ru.mail.track.message;

import java.util.List;

/**
 * Хранилище информации о сообщениях
 */
public interface MessageStore {

    Chat createChat(List<Long> participants);
    /**
    получаем список ид пользователей заданного чата
     */
    List<Long> getChatsByUserId(Long userId);

    /**
    получить информацию о чате
     */
    Chat getChatById(Long chatId);

    /**
     * Список сообщений из чата
     *
     */
    List<Long> getMessagesFromChat(Long chatId);

    /**
     * Получить информацию о сообщении
     */
    Message getMessageById(Long messageId);

    /**
     * Добавить сообщение в чат
     */
    void addMessage(Long chatId, Message message);

    /**
     * Добавить пользователя к чату
     */
    void addUserToChat(Long userId, Long chatId);


}
