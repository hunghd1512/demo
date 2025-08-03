package com.example.demoSocket.controller;

import com.example.demoSocket.dto.Greeting;
import com.example.demoSocket.dto.HelloMessage;
import com.example.demoSocket.service.SendMessageToQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

@RestController
@RequestMapping("")
public class WebSocketController {
    @Autowired
    private SendMessageToQueue sendMessageToQueue;
//
//    @MessageMapping("/hello")
//    @SendTo("/topic/greetings")
//    public Greeting greeting(HelloMessage message) throws Exception {
//        Thread.sleep(2000);
//        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
//    }

//    @MessageMapping("/hello")
//    @SendTo("/user/queue/hung")
//    public Greeting sendUser(HelloMessage message) throws Exception {
//        Thread.sleep(2000);
//        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
//    }

    @GetMapping("/send")
    public String send() {
        sendMessageToQueue.sendMessage(("Hello World"));
        return "send";
    }
}