package hcmute.kltn.vtv.controller.user;


import hcmute.kltn.vtv.model.data.user.response.SearchHistoryPageResponse;
import hcmute.kltn.vtv.service.user.ISearchHistoryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/customer/search-history")
@RequiredArgsConstructor
public class SearchHistoryController {


    @Autowired
    private final ISearchHistoryService searchHistoryService;

    @GetMapping("/get-page")
    public ResponseEntity<SearchHistoryPageResponse> getSearchHistoryByUsername(HttpServletRequest request) {
        String username = (String) request.getAttribute("username");

        return ResponseEntity.ok(searchHistoryService.getSearchHistoryByUsername(username, 10, 1));
    }


    @PostMapping("/add")
    public ResponseEntity<SearchHistoryPageResponse> addNewSearchHistory(@Param("search") String search,
            HttpServletRequest request) {
        String username = (String) request.getAttribute("username");

        return ResponseEntity.ok(searchHistoryService.addNewSearchHistory(username, search));
    }


    @DeleteMapping("/delete")
    public ResponseEntity<SearchHistoryPageResponse> deleteSearchHistoryById(@Param("searchHistoryId") UUID searchHistoryId,
            HttpServletRequest request) {
        String username = (String) request.getAttribute("username");

        return ResponseEntity.ok(searchHistoryService.deleteSearchHistory(username, searchHistoryId));
    }


}
