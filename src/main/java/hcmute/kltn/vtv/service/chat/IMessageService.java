package hcmute.kltn.vtv.service.chat;

import hcmute.kltn.vtv.model.data.chat.request.ChatMessageRequest;
import hcmute.kltn.vtv.model.data.chat.request.ListChatMessagesPageByUsernameRequest;
import hcmute.kltn.vtv.model.data.chat.request.ListChatMessagesPageRequest;
import hcmute.kltn.vtv.model.data.chat.response.ListMessagesPageResponse;
import hcmute.kltn.vtv.model.entity.chat.Message;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface IMessageService {
    @Transactional
    Message addNewMessage(String username, ChatMessageRequest chatMessageRequest);


    ListMessagesPageResponse getListChatMessagesPage(String username, UUID roomChatId, int page, int size);

    ListMessagesPageResponse getListChatMessagesPageByUsername(String senderUsername, String receiverUsername, int page, int size);

    void checkRequestPageParams(int page, int size);
}
