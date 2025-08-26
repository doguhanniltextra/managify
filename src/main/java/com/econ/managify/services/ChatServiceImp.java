package com.econ.managify.services;

import com.econ.managify.interfaces.ChatService;
import com.econ.managify.models.Chat;
import com.econ.managify.repositories.ChatRepository;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImp  {

    private final ChatRepository chatRepository;

    public ChatServiceImp(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }


    public Chat createChat(Chat chat) {
        return chatRepository.save(chat);
    }
}
