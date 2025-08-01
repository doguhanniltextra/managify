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
@Service
public class MessageServicesImp implements MessageService {

    private final UserService userService;
    private final ProjectService projectService;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    public MessageServicesImp(UserService userService, ProjectService projectService, UserRepository userRepository, MessageRepository messageRepository) {
        this.userService = userService;
        this.projectService = projectService;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    @Override
    public Message sendMessage(Long senderId, Long projectId, String content) throws MessageException {
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

    @Override
    public List<Message> getMessagesByProjectId(Long projectId) throws MessageException {
        Chat chat = projectService.getChatByProjectId(projectId);
        return messageRepository.findByChatIdOrderByCreatedAtAsc(chat.getId());
    }
}
