package com.econ.managify.interfaces;

import com.econ.managify.exceptions.MessageException;
import com.econ.managify.models.Message;

import java.util.List;
import java.util.UUID;

public interface MessageService {

    List<Message> getMessagesByProjectId(UUID projectId) throws MessageException;

    Message sendMessage(Long senderId, UUID projectId, String content);
}
