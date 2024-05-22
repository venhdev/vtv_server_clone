package hcmute.kltn.vtv.service.chat.impl;

import hcmute.kltn.vtv.model.data.chat.request.ChatMessageRequest;
import hcmute.kltn.vtv.model.data.chat.response.ListMessagesPageResponse;
import hcmute.kltn.vtv.model.dto.chat.MessageDTO;
import hcmute.kltn.vtv.model.entity.chat.Message;
import hcmute.kltn.vtv.model.entity.chat.RoomChat;
import hcmute.kltn.vtv.model.extra.Status;
import hcmute.kltn.vtv.repository.chat.MessageRepository;
import hcmute.kltn.vtv.service.chat.IMessageService;
import hcmute.kltn.vtv.service.chat.IRoomChatService;
import hcmute.kltn.vtv.service.user.ICustomerService;
import hcmute.kltn.vtv.util.exception.BadRequestException;
import hcmute.kltn.vtv.util.exception.InternalServerErrorException;
import hcmute.kltn.vtv.util.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements IMessageService {

    private final ICustomerService customerService;
    private final IRoomChatService roomChatService;
    private final MessageRepository messageRepository;

    @Override
    @Transactional
    public Message addNewMessage(ChatMessageRequest chatMessageRequest) {
        Message message = new Message();
        message.setContent(chatMessageRequest.getContent());
        message.setSenderUsername(chatMessageRequest.getSenderUsername());
        message.setDate(chatMessageRequest.getDate());
        message.setUsernameSenderDelete(false);
        message.setUsernameReceiverDelete(false);
        message.setRoomChat(roomChatService.getRoomChatById(chatMessageRequest.getRoomChatId()));
        message.setStatus(Status.ACTIVE);

        try {
            return messageRepository.save(message);
        } catch (Exception e) {
            throw new InternalServerErrorException("Lỗi hệ thống tin nhắn! Vui lòng thử lại sau.");
        }
    }

    @Override
    public ListMessagesPageResponse getListChatMessagesPage(String username, UUID roomChatId, int page, int size) {

        Page<Message> messages = messageRepository
                .findByRoomChatRomChatId(roomChatId, PageRequest.of(page - 1, size))
                .orElseThrow(() -> new NotFoundException("Không tìm danh sách tin nhắn!"));

        String message = "Lấy danh sách tin nhắn theo phòng chat thành công!";

        return listMessagesPageResponse(messages, message, page, size);
    }

    @Override
    public ListMessagesPageResponse getListChatMessagesPageByUsername(String senderUsername, String receiverUsername,
            int page, int size) {

        RoomChat roomChat = roomChatService.getRoomChatBySenderUsernameAndReceiverUsername(senderUsername,
                receiverUsername);

        Page<Message> messages = messageRepository
                .findByRoomChatRomChatId(roomChat.getRomChatId(), PageRequest.of(page - 1, size))
                .orElseThrow(() -> new NotFoundException("Không tìm danh sách tin nhắn!"));

        String message = "Lấy danh sách tin nhắn thành công!";

        return listMessagesPageResponse(messages, message, page, size);
    }

    @Override
    public void checkRequestPageParams(int page, int size) {
        if (page < 0) {
            throw new BadRequestException("Số trang phải lớn hơn 0");
        }
        if (size < 0) {
            throw new BadRequestException("Số lượng phải lớn hơn 0");
        }

        if (size > 100) {
            throw new BadRequestException("Số lượng phải nhỏ hơn 100");
        }
    }

    private ListMessagesPageResponse listMessagesPageResponse(Page<Message> messagesPage, String message, int page,
            int size) {
        ListMessagesPageResponse response = new ListMessagesPageResponse();
        try {
            response.setMessageDTOs(MessageDTO.convertEntitiesToDTOs(messagesPage.getContent()));
            response.setCount(messagesPage.getNumberOfElements());
            response.setTotalPage(messagesPage.getTotalPages());
            response.setPage(page);
            response.setSize(size);
            response.setMessage(message);
            response.setStatus("OK");
            response.setCode(200);

            return response;
        } catch (Exception e) {
            throw new InternalServerErrorException("Lỗi hệ thống lấy danh sách tin nhắn! Vui lòng thử lại sau.");
        }

    }

}
