package com.example.chatapp.repo;

import com.example.chatapp.chat.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<ChatMessage, Long> {


    @Query("select c.content from ChatMessage c")
    List<String> findAllMessages();
}
