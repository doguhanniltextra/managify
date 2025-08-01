package com.econ.managify.interfaces;

import com.econ.managify.exceptions.MessageException;
import com.econ.managify.models.Message;

import java.util.List;

public interface MessageService {
    Message sendMessage(Long senderId, Long projectId, String content) throws MessageException;
    List<Message> getMessagesByProjectId(Long projectId) throws MessageException;
}
