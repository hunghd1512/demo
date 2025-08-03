package com.example.demoSocket.service;

import com.example.demoSocket.entity.MessageDetail;
import com.example.demoSocket.entity.MessageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class SendToSocket {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/hello")
    public void sendSpecific(
            @Payload MessageInfo msg,
            @Header("simpSessionId") String sessionId) throws Exception {
        MessageDetail out = new MessageDetail(
                msg.getFrom(),
                msg.getTo(),
                new SimpleDateFormat("HH:mm").format(new Date()));
        simpMessagingTemplate.convertAndSendToUser(
                msg.getTo(), "/secured/user/queue/specific-user", out);
    }
}
