package hcmute.kltn.vtv.controller.chat;


import hcmute.kltn.vtv.model.data.chat.request.ListChatMessagesPageRequest;
import hcmute.kltn.vtv.model.data.chat.response.ListMessagesPageResponse;
import hcmute.kltn.vtv.service.chat.IMessageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat/message")
@RequiredArgsConstructor
public class MessageController {

    @Autowired
    private IMessageService messageService;

    @GetMapping("/list/room-chat")
    public ResponseEntity<ListMessagesPageResponse> getListMessageByRoomChatId(@RequestBody ListChatMessagesPageRequest request,
                                                                               HttpServletRequest servletRequest) {
        String username = (String) servletRequest.getAttribute("username");
        request.setUsername(username);
        request.validate();
        return ResponseEntity.ok(messageService.getListChatMessagesPage(request));
    }


}
