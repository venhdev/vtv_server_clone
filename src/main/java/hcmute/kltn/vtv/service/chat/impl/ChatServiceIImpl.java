package hcmute.kltn.vtv.service.chat.impl;

import hcmute.kltn.vtv.model.data.chat.request.ChatMessageRequest;
import hcmute.kltn.vtv.model.data.chat.response.ChatMessageResponse;
import hcmute.kltn.vtv.model.dto.chat.MessageDTO;
import hcmute.kltn.vtv.model.entity.chat.Message;
import hcmute.kltn.vtv.service.chat.IChatService;
import hcmute.kltn.vtv.service.chat.IMessageService;
import hcmute.kltn.vtv.service.chat.IRoomChatService;
import hcmute.kltn.vtv.util.exception.InternalServerErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatServiceIImpl implements IChatService {

    private final IMessageService messageService;
    private final IRoomChatService roomChatService;

    @Override
    @Transactional
    public ChatMessageResponse saveMessage(ChatMessageRequest request) {
        try {
            Message message = messageService.addNewMessage(request);
            roomChatService.updateDateRoomChatById(message.getRoomChat().getRomChatId(), message.getDate(),
                    message.getContent(), message.getSenderUsername());

            return chatMessageResponse(request);
        } catch (Exception e) {
            throw new InternalServerErrorException("Lỗi hệ thống tin nhắn! Vui lòng thử lại sau. " + e.getMessage());
        }
    }

    private ChatMessageResponse chatMessageResponse(ChatMessageRequest chatMessageRequest) {
        ChatMessageResponse response = ChatMessageResponse.convertRequestToResponse(chatMessageRequest);
        response.setStatus("Success");
        response.setMessage("Gửi tin nhắn thành công!");
        response.setCode(200);

        return response;
    }

    @Override
    public MessageDTO saveChatMessage(ChatMessageRequest request) {
        try {
            Message msg = messageService.addNewMessage(request);
            roomChatService.updateDateRoomChatById(msg.getRoomChat().getRomChatId(), msg.getDate(), msg.getContent(),
                    msg.getSenderUsername());

            return MessageDTO.convertEntityToDTO(msg);

        } catch (Exception e) {
            throw new InternalServerErrorException("Lỗi hệ thống tin nhắn! Vui lòng thử lại sau. " + e.getMessage());
        }
    }

}
