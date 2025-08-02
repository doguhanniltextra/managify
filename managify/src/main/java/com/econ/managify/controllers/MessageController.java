package com.econ.managify.controllers;

import com.econ.managify.dtos.requests.MessagesSendMessageRequestDto;
import com.econ.managify.dtos.responses.MessagesGetMessageResponseDto;
import com.econ.managify.dtos.responses.MessagesSendMessageResponseDto;
import com.econ.managify.exceptions.MessageException;
import com.econ.managify.interfaces.MessageService;
import com.econ.managify.interfaces.ProjectService;
import com.econ.managify.interfaces.UserService;
import com.econ.managify.models.Chat;
import com.econ.managify.models.Message;
import com.econ.managify.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final UserService userService;
    private final ProjectService projectService;
    private final MessageService messageService;
    public MessageController(UserService userService, ProjectService projectService, MessageService messageService) {
        this.userService = userService;
        this.projectService = projectService;
        this.messageService = messageService;
    }

    @PostMapping("/send")
    public ResponseEntity<MessagesSendMessageResponseDto> sendMessage(@RequestBody MessagesSendMessageRequestDto request) throws MessageException {
        User user = userService.findUserById(request.getSenderId());

        if (user == null) throw new MessageException("No User Found");

        Chat chats = projectService.getProjectById(request.getProjectId()).getChat();

        if (chats == null) throw new MessageException("Chats Not Found");

        Message sentMessage = messageService.sendMessage(request.getSenderId(), request.getProjectId(),request.getContent());

        MessagesSendMessageResponseDto messagesSendMessageResponseDto = new MessagesSendMessageResponseDto();

        messagesSendMessageResponseDto.setContent(sentMessage.getContent());
        messagesSendMessageResponseDto.setCreatedAt(sentMessage.getCreatedAt());
        return ResponseEntity.ok(messagesSendMessageResponseDto);
    }

    @GetMapping("/chat/{projectId}")
    public ResponseEntity<List<MessagesGetMessageResponseDto>> getMessagesByChatId(@PathVariable Long projectId) throws MessageException {
        List<Message> createdMessages = messageService.getMessagesByProjectId(projectId);

        if(createdMessages.isEmpty()) throw new MessageException("Couldn't find a message");

        List<MessagesGetMessageResponseDto> dto = createdMessages
                .stream()
                .map(message -> {
                    MessagesGetMessageResponseDto messagesGetMessageResponseDto = new MessagesGetMessageResponseDto();
                    messagesGetMessageResponseDto.setContent(message.getContent());
                    messagesGetMessageResponseDto.setCreatedAt(message.getCreatedAt());

                    return messagesGetMessageResponseDto;
                }).toList();

        return ResponseEntity.ok(dto);
    }

}
