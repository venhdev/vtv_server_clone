package hcmute.kltn.vtv.controller.chat;

import hcmute.kltn.vtv.util.exception.BadRequestException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import hcmute.kltn.vtv.model.data.chat.request.ChatMessageRequest;
import hcmute.kltn.vtv.model.dto.chat.MessageDTO;
import hcmute.kltn.vtv.service.chat.IChatService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class WebSocketController {

    private final IChatService chatService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    // @MessageMapping("/message")
    // @SendTo("/topic/{romChatId}/public")
    // public ChatMessageResponse sendMessage(@Payload ChatMessageRequest
    // chatMessageRequest,
    // Principal principal) {

    // // chatMessageRequest.setSenderUsername(principal.getName());

    // chatService.saveMessage(chatMessageRequest);

    // simpMessagingTemplate.convertAndSendToUser(
    // chatMessageRequest.getReceiverUsername(),
    // "/topic/" + chatMessageRequest.getRoomChatId() + "/public",
    // chatMessageRequest);

    // return ChatMessageResponse.convertRequestToResponse(chatMessageRequest);
    // }

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessageRequest chatMessageRequest, HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        String token = (String) request.getAttribute("Authorization");
        System.out.println("Token: " + token + " Username: " + username);

        if (username == null || username.isEmpty()) {
            throw new BadRequestException("Thiếu thông tin người gửi");
        }
        chatMessageRequest.validate(chatMessageRequest);
        MessageDTO msgDTO = chatService.saveChatMessage(username, chatMessageRequest);
        simpMessagingTemplate.convertAndSendToUser(msgDTO.getRoomChatId().toString(), "/chat", msgDTO);
    }
}
