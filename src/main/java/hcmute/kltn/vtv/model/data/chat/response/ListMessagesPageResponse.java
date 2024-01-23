package hcmute.kltn.vtv.model.data.chat.response;


import hcmute.kltn.vtv.model.dto.chat.MessageDTO;
import hcmute.kltn.vtv.model.extra.ResponseAbstract;
import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ListMessagesPageResponse extends ResponseAbstract {

    private int count;
    private int page;
    private int size;
    private int totalPage;
    private List<MessageDTO> messageDTOs;

}
