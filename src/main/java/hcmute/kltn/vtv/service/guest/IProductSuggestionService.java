package hcmute.kltn.vtv.service.guest;

import hcmute.kltn.vtv.model.data.paging.response.ListProductPageResponse;

public interface IProductSuggestionService {

    ListProductPageResponse suggestByRandomly(int page, int size);


    ListProductPageResponse suggestByRandomlyAndProductId(Long productId, int page, int size, boolean inShop);

    ListProductPageResponse suggestByCategoryAndRandomly(Long categoryId, int page, int size);
}
