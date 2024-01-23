package hcmute.kltn.vtv.service.chat;

import hcmute.kltn.vtv.model.data.chat.request.ChatMessageRequest;
import hcmute.kltn.vtv.model.data.chat.request.ListChatMessagesPageByUsernameRequest;
import hcmute.kltn.vtv.model.data.chat.request.ListChatMessagesPageRequest;
import hcmute.kltn.vtv.model.data.chat.response.ListMessagesPageResponse;
import hcmute.kltn.vtv.model.entity.chat.Message;
import org.springframework.transaction.annotation.Transactional;

public interface IMessageService {
    @Transactional
    Message addNewMessage(ChatMessageRequest chatMessageRequest);

    ListMessagesPageResponse getListChatMessagesPage(ListChatMessagesPageRequest request);

    ListMessagesPageResponse getListChatMessagesPageBySenderUsernameAndReceiverUsername(ListChatMessagesPageByUsernameRequest request);
}
