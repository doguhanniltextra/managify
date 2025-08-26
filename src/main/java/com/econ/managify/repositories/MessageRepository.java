package com.econ.managify.repositories;

import com.econ.managify.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {

    public List<Message> findByChatIdOrderByCreatedAtAsc(Long chatId);
}
