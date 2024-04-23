package hcmute.kltn.vtv.service.guest.impl;

import hcmute.kltn.vtv.model.data.guest.ShopDetailResponse;
import hcmute.kltn.vtv.model.entity.vendor.Shop;
import hcmute.kltn.vtv.model.extra.Status;
import hcmute.kltn.vtv.repository.vendor.CategoryShopRepository;
import hcmute.kltn.vtv.repository.vtv.ShopRepository;
import hcmute.kltn.vtv.service.guest.*;
import hcmute.kltn.vtv.util.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShopGuestServiceImpl implements IShopGuestService {

    private final ShopRepository shopRepository;
    private final IFollowedGuestService followedGuestService;
    private final IProductService productService;
    private final IReviewService reviewService;
    private final CategoryShopRepository categoryShopRepository;


    @Override
    public ShopDetailResponse getShopDetailByShopId(Long shopId) {

        Shop shop = getShopById(shopId);
        int countFollowed = followedGuestService.countFollowedByShop(shopId);
        int countProduct = productService.countProductByShopId(shopId);
        int countCategoryShop = countCategoryShopByShopId(shopId);
        float averageRating = reviewService.averageRatingByShopId(shopId);


//        List<Product> products = productRepository.findByShopShopIdAndStatus(shopId, Status.ACTIVE)
//                .orElseThrow(() -> new NotFoundException("Không tìm thấy sản phẩm nào trong cửa hàng này!"));
//        if (products.isEmpty()) {
//            throw new NotFoundException("Không tìm thấy sản phẩm nào trong cửa hàng này!");
//        }
//        if (products.size() > 20) {
//            products = products.subList(products.size() - 20, products.size());
//        }
//        products.sort(Comparator.comparing(Product::getCreateAt).reversed());
//
//        List<Category> categories = categoryRepository.findAllByShopShopIdAndStatus(shopId, Status.ACTIVE)
//                .orElseThrow(() -> new NotFoundException("Không tìm thấy danh mục nào trong cửa hàng này!"));
//        if (categories.isEmpty()) {
//            throw new NotFoundException("Không tìm thấy danh mục nào trong cửa hàng này!!");
//        }
//        categories.sort(Comparator.comparing(Category::getName));
//
//        boolean isFollow = followedShopRepository.existsByCustomerUsernameAndShopShopId(username, shopId);
//
//        ShopDetailResponse response = new ShopDetailResponse();
//        // response.setShopDTO(modelMapper.map(shop, ShopDTO.class));
//        response.setShopDTO(ShopDTO.convertEntityToDTO(shop));
//        response.setProductDTOs(ProductDTO.convertToListDTO(products));
//        response.setTotalProduct(products.size());
//        response.setCategoryDTOs(CategoryDTO.convertToListDTO(categories));
//        response.setTotalCategory(categories.size());
//        response.setFollowed(isFollow);
//        response.setMessage("Lấy thông tin cửa hàng thành công!");
//        response.setStatus("ok");
//        response.setCode(200);

        return null;
    }


    @Override
    public Shop getShopById(Long shopId) {
        checkExistsById(shopId);

        Shop shop =  shopRepository.findById(shopId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy cửa hàng theo mã cửa hàng!"));

        if (shop.getStatus().equals(Status.DELETED)) {
            throw new NotFoundException("Cửa hàng đã bị xóa!");
        }
        if (shop.getStatus().equals(Status.LOCKED)) {
            throw new NotFoundException("Cửa hàng đã bị khóa!");
        }

        return shop;
    }


    @Override
    public void checkExistsById(Long shopId) {
        if (!shopRepository.existsById(shopId)) {
            throw new NotFoundException("Không tìm thấy cửa hàng có mã: " + shopId);
        }
    }


    private int countCategoryShopByShopId(Long shopId) {
        return categoryShopRepository.countCategoryShopByShopShopId(shopId);
    }
}
