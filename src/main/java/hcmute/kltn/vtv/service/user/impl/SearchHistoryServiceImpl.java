package hcmute.kltn.vtv.service.user.impl;


import hcmute.kltn.vtv.model.data.user.response.SearchHistoryPageResponse;
import hcmute.kltn.vtv.model.dto.user.SearchHistoryDTO;
import hcmute.kltn.vtv.model.entity.user.SearchHistory;
import hcmute.kltn.vtv.repository.user.SearchHistoryRepository;
import hcmute.kltn.vtv.service.user.ISearchHistoryService;
import hcmute.kltn.vtv.util.exception.InternalServerErrorException;
import hcmute.kltn.vtv.util.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SearchHistoryServiceImpl implements ISearchHistoryService {


    @Autowired
    private final SearchHistoryRepository searchHistoryRepository;


    @Override
    public SearchHistoryPageResponse getSearchHistoryByUsername(String username, int size, int page) {
        Page<SearchHistory> searchHistories = searchHistoryPage(username, size, page);

        return searchHistoryPageResponse(searchHistories, size, "Lấy lịch sử tìm kiếm thành công!", "OK");
    }


    @Override
    @Transactional
    public SearchHistoryPageResponse addNewSearchHistory(String username, String search) {
        SearchHistory searchHistory = new SearchHistory();

        if (searchHistoryRepository.existsByUsernameAndSearch(username, search)) {
            searchHistory = searchHistoryRepository.findByUsernameAndSearch(username, search)
                    .orElseThrow(() -> new NotFoundException("Không tìm thấy lịch sử tìm kiếm!"));
        } else {
            searchHistory.setSearch(search);
            searchHistory.setUsername(username);
        }
        searchHistory.setCreateAt(LocalDateTime.now());

        try {
            searchHistoryRepository.save(searchHistory);
            Page<SearchHistory> searchHistories = searchHistoryPage(username, 10, 1);

            return searchHistoryPageResponse(searchHistories, 10,
                    "Thêm lịch sử tìm kiếm thành công!", "Success");
        } catch (Exception e) {
            throw new InternalServerErrorException("Lỗi khi thêm lịch sử tìm kiếm!");
        }
    }


    @Override
    @Transactional
    public SearchHistoryPageResponse deleteSearchHistory(String username, UUID searchHistoryId) {

//        searchHistoryRepository.findByUsernameAndSearchHistoryId(username, searchHistoryId)
//                .orElseThrow(() -> new NotFoundException("Không tìm thấy lịch sử tìm kiếm!"));
        try {
            searchHistoryRepository.deleteByUsernameAndSearchHistoryId(username, searchHistoryId);
            Page<SearchHistory> searchHistories = searchHistoryPage(username, 10, 1);

            return searchHistoryPageResponse(searchHistories, 10,
                    "Xóa lịch sử tìm kiếm thành công!", "Success");
        } catch (Exception e) {
            throw new InternalServerErrorException("Lỗi khi xóa lịch sử tìm kiếm!");
        }
    }

    @Override
    public String toStringSearchHistoryByUsername(String username, int size, int page) {

        Page<SearchHistory> searchHistories = searchHistoryPage(username, size, page);

        StringBuilder searchHistoriesToString = new StringBuilder();
        for (SearchHistory searchHistory : searchHistories.getContent()) {
            searchHistoriesToString.append(searchHistory.getSearch()).append(" ");
        }

        return searchHistoriesToString.toString();


    }


    private SearchHistoryPageResponse searchHistoryPageResponse(Page<SearchHistory> searchHistories, int size, String message, String status) {

        SearchHistoryPageResponse response = new SearchHistoryPageResponse();
        response.setCount(searchHistories.getContent().size());
        response.setPage(searchHistories.getNumber() + 1);
        response.setSize(size);
        response.setTotalPage(searchHistories.getTotalPages());
        response.setCode(200);
        response.setMessage(message);
        response.setStatus(status);
        response.setSearchHistoryDTOs(SearchHistoryDTO.convertEntitiesToDTOs(searchHistories.getContent()));

        return response;
    }

    private Page<SearchHistory> searchHistoryPage(String username, int size, int page) {
        return searchHistoryRepository.findByUsernameOrderByCreateAtDesc(username, PageRequest.of(page - 1, size))
                .orElseThrow(() -> new NotFoundException("Không tìm thấy lịch sử tìm kiếm!"));
    }


}
