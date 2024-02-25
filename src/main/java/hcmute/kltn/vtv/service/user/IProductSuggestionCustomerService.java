package hcmute.kltn.vtv.service.user;

import hcmute.kltn.vtv.model.data.paging.response.ListProductPageResponse;

public interface IProductSuggestionCustomerService {
    ListProductPageResponse getProductSuggestionBySearchHistory(String username, int page, int size);

    ListProductPageResponse suggestByRandomly(int page, int size);
}
