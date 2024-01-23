package hcmute.kltn.vtv.service.chat;

import hcmute.kltn.vtv.model.data.chat.request.ChatMessageRequest;
import hcmute.kltn.vtv.model.data.chat.response.ChatMessageResponse;
import org.springframework.transaction.annotation.Transactional;

public interface IChatService {

    @Transactional
    ChatMessageResponse saveMessage(ChatMessageRequest request);
}
