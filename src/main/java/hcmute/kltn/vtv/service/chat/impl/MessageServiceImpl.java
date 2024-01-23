package hcmute.kltn.vtv.service.chat.impl;

import hcmute.kltn.vtv.model.data.chat.request.ChatMessageRequest;
import hcmute.kltn.vtv.model.data.chat.request.ListChatMessagesPageByUsernameRequest;
import hcmute.kltn.vtv.model.data.chat.request.ListChatMessagesPageRequest;
import hcmute.kltn.vtv.model.data.chat.response.ListMessagesPageResponse;
import hcmute.kltn.vtv.model.dto.chat.MessageDTO;
import hcmute.kltn.vtv.model.entity.chat.Message;
import hcmute.kltn.vtv.model.entity.chat.RoomChat;
import hcmute.kltn.vtv.model.extra.Status;
import hcmute.kltn.vtv.repository.chat.MessageRepository;
import hcmute.kltn.vtv.service.chat.IMessageService;
import hcmute.kltn.vtv.service.chat.IRoomChatService;
import hcmute.kltn.vtv.service.user.ICustomerService;
import hcmute.kltn.vtv.util.exception.InternalServerErrorException;
import hcmute.kltn.vtv.util.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


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
        message.setDate(new Date());
        message.setUsernameSenderDelete(false);
        message.setUsernameReceiverDelete(false);
        message.setRoomChat(roomChatService.getRoomChatById(chatMessageRequest.getRomChatId()));
        message.setStatus(Status.ACTIVE);

        try {
            return messageRepository.save(message);
        } catch (Exception e) {
            throw new InternalServerErrorException("Lỗi hệ thống tin nhắn! Vui lòng thử lại sau.");
        }
    }


    @Override
    public ListMessagesPageResponse getListChatMessagesPage(ListChatMessagesPageRequest request) {
        Page<Message> messages = messageRepository
                .findByRoomChatRomChatId(request.getRoomChatId(),
                        PageRequest.of(request.getPage() - 1, request.getSize()))
                .orElseThrow(() -> new NotFoundException("Không tìm danh sách tin nhắn!"));

        String message = "Lấy danh sách tin nhắn thành công!";

        return listMessagesPageResponse(messages, message, request.getPage(), request.getSize());
    }


    @Override
    public ListMessagesPageResponse getListChatMessagesPageBySenderUsernameAndReceiverUsername(ListChatMessagesPageByUsernameRequest request) {

        RoomChat roomChat = roomChatService.getRoomChatBySenderUsernameAndReceiverUsername(request.getSenderUsername(), request.getReceiverUsername());

        Page<Message> messages = messageRepository
                .findByRoomChatRomChatId(roomChat.getRomChatId(),
                        PageRequest.of(request.getPage() - 1, request.getSize()))
                .orElseThrow(() -> new NotFoundException("Không tìm danh sách tin nhắn!"));

        String message = "Lấy danh sách tin nhắn thành công!";

        return listMessagesPageResponse(messages, message, request.getPage(), request.getSize());
    }


    private ListMessagesPageResponse listMessagesPageResponse(Page<Message> messagesPage, String message, int page, int size) {
        ListMessagesPageResponse response = new ListMessagesPageResponse();
        try {
            response.setMessageDTOs(MessageDTO.convertEntitiesToDTOs(messagesPage.getContent()));
            response.setCount(messagesPage.getNumberOfElements());
            response.setTotalPage(messagesPage.getTotalPages());
            response.setPage(page);
            response.setSize(size);
            response.setMessage(message);
            response.setStatus("Success");
            response.setCode(200);

            return response;
        } catch (Exception e) {
            throw new InternalServerErrorException("Lỗi hệ thống lấy danh sách tin nhắn! Vui lòng thử lại sau.");
        }

    }

}
