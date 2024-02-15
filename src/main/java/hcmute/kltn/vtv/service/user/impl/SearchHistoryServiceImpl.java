package hcmute.kltn.vtv.service.user.impl;


import hcmute.kltn.vtv.repository.user.SearchHistoryRepository;
import hcmute.kltn.vtv.service.user.ISearchHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchHistoryServiceImpl implements ISearchHistoryService {


    @Autowired
    private final SearchHistoryRepository searchHistoryRepository;


}
