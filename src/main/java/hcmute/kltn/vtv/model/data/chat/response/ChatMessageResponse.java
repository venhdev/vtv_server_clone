package hcmute.kltn.vtv.model.data.chat.response;


import hcmute.kltn.vtv.model.data.chat.request.ChatMessageRequest;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageResponse {

    private String content;
    private Date date;
    private String senderUsername;
    private String receiverUsername;
    private UUID romChatId;

    public static ChatMessageResponse convertRequestToResponse(ChatMessageRequest chatMessageRequest){
        ChatMessageResponse chatMessageResponse = new ChatMessageResponse();
        chatMessageResponse.setContent(chatMessageRequest.getContent());
        chatMessageResponse.setDate(chatMessageRequest.getDate());
        chatMessageResponse.setSenderUsername(chatMessageRequest.getSenderUsername());
        chatMessageResponse.setReceiverUsername(chatMessageRequest.getReceiverUsername());
        chatMessageResponse.setRomChatId(chatMessageRequest.getRomChatId());
        return chatMessageResponse;
    }
}
