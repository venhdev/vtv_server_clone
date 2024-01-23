package hcmute.kltn.vtv.controller.chat;

import hcmute.kltn.vtv.model.data.chat.request.ChatMessageRequest;
import hcmute.kltn.vtv.model.data.chat.response.ChatMessageResponse;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/send-message/{romChatId}")
    @SendTo("/topic/{romChatId}/public")
    public ChatMessageResponse sendMessage(ChatMessageRequest chatMessage) {


        return ChatMessageResponse.convertRequestToResponse(chatMessage);
    }
}
