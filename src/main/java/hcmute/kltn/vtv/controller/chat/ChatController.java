package hcmute.kltn.vtv.controller.chat;


import hcmute.kltn.vtv.model.data.chat.request.ChatMessageRequest;
import hcmute.kltn.vtv.service.chat.IChatService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final IChatService chatService;


    @PostMapping("/send")
    public void sendMessage(@RequestBody ChatMessageRequest chatMessageRequest, Principal principal,
                            HttpServletRequest request) {

        String username = (String) request.getAttribute("username");
        System.out.println("username" + username);

        chatMessageRequest.setSenderUsername(principal.getName());

        chatService.saveMessage(chatMessageRequest);

        messagingTemplate.convertAndSendToUser(
                chatMessageRequest.getReceiverUsername(),
                "/topic/" + chatMessageRequest.getRomChatId() + "/public",
                chatMessageRequest
        );
    }


}