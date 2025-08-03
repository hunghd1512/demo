package com.example.demoSocket.service;

import com.example.demoSocket.entity.MessageDetail;
import com.example.demoSocket.entity.MessageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ReceiveMessage {
    @Autowired
    private SendToSocket sendToSocket;

    @KafkaListener(topics = "topicName", groupId = "foo")
    public void listenGroupFoo(String message) throws Exception {
        System.out.println("Received Message in group foo: " + message);
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setFrom("hung");
        messageInfo.setTo("hung");
        String id = UUID.randomUUID().toString();
        sendToSocket.sendSpecific(messageInfo,id);
    }
}
