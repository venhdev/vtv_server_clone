package hcmute.kltn.vtv.service.chat;

import hcmute.kltn.vtv.model.data.chat.request.ChatMessageRequest;
import org.springframework.transaction.annotation.Transactional;

public interface IChatService {
    @Transactional
    void saveMessage(ChatMessageRequest request);
}
