package com.example.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@org.springframework.stereotype.Controller
public class WebSocketTextController {
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

//    @PostMapping("/send")
//    public ResponseEntity<Void> sendMessage(@RequestBody TextMessageDTO textMessageDTO) {
//        template.convertAndSend("/topic/message", textMessageDTO);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @MessageMapping("/sendMessage")
//    public void receiveMessage(@Payload TextMessageDTO textMessageDTO) {
//        // receive message from client
//    }
//
//
//    @SendTo("/topic/message")
//    public TextMessageDTO broadcastMessage(@Payload TextMessageDTO textMessageDTO) {
//        return textMessageDTO;
//    }

//    @MessageMapping("/application")
//    @SendTo("/all/messages")
//    public Message send(final Message message) throws Exception {
//        return message;
//    }

    @MessageMapping("/new-event/{userId}")
    @SendTo("/topic/new-event/{userId}")
    public String sendToSpecificUser(@DestinationVariable Long userId, @Payload Message<String> message) {
        System.out.println(userId);
        System.out.println(message);
//        simpMessagingTemplate.convertAndSend( "/new-event/" + userId, message);
        return message.getPayload();
    }

    @MessageMapping("/new-accept/{userId}")
    @SendTo("/topic/new-accept/{userId}")
    public String sendAcceptToSpecificUser(@DestinationVariable Long userId, @Payload Message<String> message) {
        System.out.println(userId);
        System.out.println(message);
//        simpMessagingTemplate.convertAndSend( "/new-event/" + userId, message);
        return message.getPayload();
    }

//    @MessageMapping("/new-event/{userId}")
//    @SendTo("/topic/new-event/{userId}")
//    public String allowConnect(@DestinationVariable Long userId, @Payload Message<String> message) {
//        System.out.println(userId);
//        System.out.println(message);
////        simpMessagingTemplate.convertAndSend( "/new-event/" + userId, message);
//        return message.getPayload();
//    }
}
