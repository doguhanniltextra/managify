package com.econ.managify.services;

import com.econ.managify.exceptions.MessageException;
import com.econ.managify.interfaces.MessageService;
import com.econ.managify.interfaces.ProjectService;
import com.econ.managify.interfaces.UserService;
import com.econ.managify.models.Chat;
import com.econ.managify.models.Message;
import com.econ.managify.models.User;
import com.econ.managify.repositories.MessageRepository;
import com.econ.managify.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class MessageServicesImp {

    private final UserServiceImp userService;
    private final ProjectServiceImp projectService;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    public MessageServicesImp(UserServiceImp userService, ProjectServiceImp projectService, UserRepository userRepository, MessageRepository messageRepository) {
        this.userService = userService;
        this.projectService = projectService;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    public Message sendMessage(Long senderId, UUID projectId, String content) throws MessageException {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() ->  new MessageException("User Not Found"));

        Chat chat = projectService.getProjectById(projectId).getChat();

        Message message = new Message();
        message.setChat(chat);
        message.setContent(content);
        message.setSender(sender);
        message.setCreatedAt(LocalDateTime.now());

        Message savedMessage = messageRepository.save(message);

        chat.getMessages().add(savedMessage);
        return savedMessage;
    }


    public List<Message> getMessagesByProjectId(UUID projectId) throws MessageException {
        Chat chat = projectService.getChatByProjectId(projectId);
        return messageRepository.findByChatIdOrderByCreatedAtAsc(chat.getId());
    }
}
