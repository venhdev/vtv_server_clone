package hcmute.kltn.vtv.model.dto.chat;

import hcmute.kltn.vtv.model.entity.chat.RoomChat;
import jakarta.persistence.Column;
import lombok.*;

import java.util.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RoomChatDTO {

    private UUID romChatId;

    private String senderUsername;

    private String receiverUsername;


    private String lastMessage;

    private Date lastDate;

    private boolean senderDelete;

    private boolean receiverDelete;

    private boolean senderSeen;

    private boolean receiverSeen;


    public static RoomChatDTO convertEntityToDTO(RoomChat roomChat) {
        RoomChatDTO roomChatDTO = new RoomChatDTO();
        roomChatDTO.setRomChatId(roomChat.getRomChatId());
        roomChatDTO.setSenderUsername(roomChat.getSenderUsername());
        roomChatDTO.setReceiverUsername(roomChat.getReceiverUsername());
        roomChatDTO.setLastMessage(roomChat.getLastMessage());
        roomChatDTO.setLastDate(roomChat.getLastDate());
        roomChatDTO.setSenderDelete(roomChat.isSenderDelete());
        roomChatDTO.setReceiverDelete(roomChat.isReceiverDelete());
        roomChatDTO.setSenderSeen(roomChat.isSenderSeen());
        roomChatDTO.setReceiverSeen(roomChat.isReceiverSeen());

        return roomChatDTO;
    }


    public static List<RoomChatDTO> convertEntityToDTO(List<RoomChat> roomChats) {
        List<RoomChatDTO> roomChatDTOs = new ArrayList<>();
        for (RoomChat roomChat : roomChats) {
            if (roomChat.isSenderDelete() && roomChat.isReceiverDelete()) {
                continue;
            }
            roomChatDTOs.add(convertEntityToDTO(roomChat));
        }
        roomChatDTOs.sort(Comparator.comparing(RoomChatDTO::getLastDate));

        return roomChatDTOs;
    }


}
