package hcmute.kltn.vtv.service.guest;

import hcmute.kltn.vtv.model.data.paging.response.ListProductPageResponse;

public interface IProductPageService {

    ListProductPageResponse getListProductPage(int page, int size);

    ListProductPageResponse getListProductPageByCategoryId(Long categoryId, int page, int size);

    ListProductPageResponse getListProductsPageByShopId(Long shopId, int page, int size);

    ListProductPageResponse getListBestSellingProductsPageByShopId(Long shopId, int page, int size);

    ListProductPageResponse getListNewProductsPageByShopId(Long shopId, int page, int size);





}
