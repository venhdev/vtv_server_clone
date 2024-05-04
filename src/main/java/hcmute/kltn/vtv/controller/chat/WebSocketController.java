package hcmute.kltn.vtv.controller.chat;

import hcmute.kltn.vtv.model.data.chat.request.ChatMessageRequest;
import hcmute.kltn.vtv.model.data.chat.response.ChatMessageResponse;
import hcmute.kltn.vtv.service.chat.IChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class WebSocketController {


    private final IChatService chatService;
    SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/message")
    @SendTo("/topic/{romChatId}/public")
    public ChatMessageResponse sendMessage(@Payload ChatMessageRequest chatMessageRequest,
                                           Principal principal) {

//        chatMessageRequest.setSenderUsername(principal.getName());

        chatService.saveMessage(chatMessageRequest);

        simpMessagingTemplate.convertAndSendToUser(
                chatMessageRequest.getReceiverUsername(),
                "/topic/" + chatMessageRequest.getRomChatId() + "/public",
                chatMessageRequest
        );

        return ChatMessageResponse.convertRequestToResponse(chatMessageRequest);
    }
}
