package hcmute.kltn.vtv.service.user;

import hcmute.kltn.vtv.model.data.user.response.SearchHistoryPageResponse;
import hcmute.kltn.vtv.model.dto.user.SearchHistoryDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface ISearchHistoryService {

    SearchHistoryPageResponse getSearchHistoryByUsername(String username, int page, int size);

    @Transactional
    SearchHistoryPageResponse addNewSearchHistory(String username, String search);

    @Transactional
    SearchHistoryPageResponse deleteSearchHistory(String username, UUID searchHistoryId);

    String toStringSearchHistoryByUsername(String username, int size, int page);
}
