package hcmute.kltn.vtv.service.user.impl;

import hcmute.kltn.vtv.model.data.user.UserSocket;
import hcmute.kltn.vtv.model.data.user.request.MessengerRequest;
import hcmute.kltn.vtv.model.data.user.response.MessengersResponse;
import hcmute.kltn.vtv.model.dto.MessengerDTO;
import hcmute.kltn.vtv.model.entity.vtc.Customer;
import hcmute.kltn.vtv.model.entity.vtc.Messenger;
import hcmute.kltn.vtv.model.entity.vtc.RomChat;
import hcmute.kltn.vtv.model.extra.Status;
import hcmute.kltn.vtv.repository.MessengerRepository;
import hcmute.kltn.vtv.service.user.ICustomerService;
import hcmute.kltn.vtv.service.user.IMessengerService;
import hcmute.kltn.vtv.service.user.IRomChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessengerServiceImpl implements IMessengerService {

    private String usernameReceiver;
    private Long romChatId;
    RomChat romChat;

    @Autowired
    MessengerRepository messengerRepository;
    @Autowired
    ICustomerService customerService;
    @Autowired
    IRomChatService romChatService;
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void saveMessenger(MessengerRequest request) {
        Customer customer = customerService.getCustomerByUsername(request.getSender());
        RomChat romChat = romChatService.findRomChat(request.getSender(), request.getReceiver());
        this.romChatId = romChat.getRomChatId();
        Messenger messenger = new Messenger();
        messenger.setContent(request.getContent());
        messenger.setCustomer(customer);
        messenger.setRomChat(romChat);
        messenger.setStatus(Status.ACTIVE);
        messenger.setDate(new Date());

        try {
            messengerRepository.save(messenger);
            simpMessagingTemplate.convertAndSend("/topic/public/" +
                    this.romChatId,
                    request.getSender() + " : " +
                            request.getContent());
        } catch (Exception e) {
            throw new RuntimeException("Không thể gửi tin nhắn");
        }
    }

    @Override
    public MessengersResponse findAllByRomChatId(Long id, String username) {
        List<Messenger> messengers = messengerRepository.findAllByRomChatRomChatId(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tin nhắn"));

        MessengersResponse response = new MessengersResponse();
        response.setMessengerDTOs(MessengerDTO.convertEntitiesToDTOs(messengers));
        response.setRomChatId(id);
        response.setCount(messengers.size());
        response.setUsername(username);

        return response;
    }

    @Override
    public UserSocket chatVsUser(UserSocket user) {
        this.usernameReceiver = user.getReceiver();
        this.romChat = romChatService.findRomChat(user.getSender(), user.getReceiver());
        this.romChatId = romChat.getRomChatId();
        return user;
    }

}
