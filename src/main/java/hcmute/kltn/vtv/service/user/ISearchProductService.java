package hcmute.kltn.vtv.service.user;

import hcmute.kltn.vtv.model.data.vendor.response.ListProductPageResponse;

public interface ISearchProductService {
    ListProductPageResponse getProductsPageBySearchAndSort(String search, int page, int size, String sort);

    ListProductPageResponse getProductsPageBySearchAndPriceRangeAndSort(String search, int page, int size, String sort,
                                                                        Long minPrice, Long maxPrice);

    ListProductPageResponse getProductsPageBySearchAndPriceRangeAndSortOnShop(String search, Long shopId, int page, int size, String sort,
                                                                              Long minPrice, Long maxPrice);

    ListProductPageResponse getProductsPageBySearchAndSortOnShop(String search, Long shopId, int page, int size, String sort);
}
