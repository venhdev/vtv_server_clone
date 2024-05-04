package hcmute.kltn.vtv.controller.chat;


import hcmute.kltn.vtv.model.data.chat.request.ListChatMessagesPageRequest;
import hcmute.kltn.vtv.model.data.chat.response.ListMessagesPageResponse;
import hcmute.kltn.vtv.service.chat.IMessageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/chat/message")
@RequiredArgsConstructor
public class MessageController {

    private final IMessageService messageService;


    @GetMapping("/list/room-chat/{roomChatId}/page/{page}/size/{size}")
    public ResponseEntity<ListMessagesPageResponse> getListMessageByRoomChatId( @PathVariable UUID roomChatId,
                                                                                @PathVariable int page,
                                                                                @PathVariable int size,
                                                                                HttpServletRequest servletRequest) {

        String username = (String) servletRequest.getAttribute("username");
        messageService.checkRequestPageParams(page, size);
        return ResponseEntity.ok(messageService.getListChatMessagesPage(username, roomChatId, page, size));
    }


}
