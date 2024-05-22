package hcmute.kltn.vtv.service.chat;

import hcmute.kltn.vtv.model.data.chat.request.ChatMessageRequest;
import hcmute.kltn.vtv.model.data.chat.response.ChatMessageResponse;
import hcmute.kltn.vtv.model.dto.chat.MessageDTO;

import org.springframework.transaction.annotation.Transactional;

public interface IChatService {

    @Transactional
    ChatMessageResponse saveMessage(ChatMessageRequest request);

    
    @Transactional
    MessageDTO saveChatMessage(ChatMessageRequest request);
}
