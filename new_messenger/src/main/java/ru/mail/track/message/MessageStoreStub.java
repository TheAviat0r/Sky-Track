package ru.mail.track.message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 */
public class MessageStoreStub implements MessageStore {

    public static final AtomicLong counter = new AtomicLong(0);

    static Map<Long, Message> messages = new HashMap<>();

    static Map<Long, Chat> chats = new HashMap<>();

    static {
        Chat chat1 = new Chat();
        chat1.addParticipant(0L);
        chat1.addParticipant(2L);

        Chat chat2 = new Chat();
        chat2.addParticipant(1L);
        chat2.addParticipant(2L);
        chat2.addParticipant(3L);

        chats.put(1L, chat1);
        chats.put(2L, chat2);
    }

    @Override
    public List<Long> getChatsByUserId(Long userId) {
        List<Long> chatIds = new ArrayList<>();

        for (Map.Entry<Long, Chat> entry : chats.entrySet()) {
            for (Long ParticipantId : entry.getValue().getParticipantIds()) {
                if (ParticipantId.equals(userId)) {
                    chatIds.add(entry.getKey());
                }
            }
        }
        return chatIds;
    }

    @Override
    public Chat getChatById(Long chatId) {
        return chats.get(chatId);
    }

    @Override
    public List<Long> getMessagesFromChat(Long chatId) {
        return chats.get(chatId).getMessageIds();
    }

    @Override
    public Message getMessageById(Long messageId) {
        return messages.get(messageId);
    }

    @Override
    public void addMessage(Long chatId, Message message) {
        message.setId(counter.getAndIncrement());
        chats.get(chatId).addMessage(message.getId());
        messages.put(message.getId(), message);
    }

    @Override
    public void addUserToChat(Long userId, Long chatId) {

    }

    @Override
    public Chat addChat(Chat chat) {
        chats.put(chats.size() + 1L, chat);
        return chat;
    }

    @Override
    public Chat addChat(List<Long> users) {
        Chat chat = new Chat();
        for (Long id : users) {
            chat.addParticipant(id);
        }
        return addChat(chat);
    }
}
