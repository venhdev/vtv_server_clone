package hcmute.kltn.vtv.model.data.user.response;

import hcmute.kltn.vtv.model.dto.user.SearchHistoryDTO;
import hcmute.kltn.vtv.model.extra.ResponseAbstract;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SearchHistoryResponse extends ResponseAbstract {

    private int total; // total search history
    private String username;
    private int count; // count of search history show in page
    private List<SearchHistoryDTO> searchHistoryDTOs;


}
