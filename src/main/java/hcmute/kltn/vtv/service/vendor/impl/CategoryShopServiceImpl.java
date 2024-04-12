package hcmute.kltn.vtv.service.vendor.impl;

import hcmute.kltn.vtv.model.data.vendor.request.CategoryShopRequest;
import hcmute.kltn.vtv.model.data.vendor.response.CategoryShopResponse;
import hcmute.kltn.vtv.model.entity.vendor.CategoryShop;
import hcmute.kltn.vtv.model.entity.vendor.Shop;
import hcmute.kltn.vtv.model.extra.Status;
import hcmute.kltn.vtv.repository.vendor.CategoryShopRepository;
import hcmute.kltn.vtv.service.vendor.ICategoryShopService;
import hcmute.kltn.vtv.service.vendor.IProductShopService;
import hcmute.kltn.vtv.service.vendor.IShopService;
import hcmute.kltn.vtv.service.vtv.IImageService;
import hcmute.kltn.vtv.util.exception.BadRequestException;
import hcmute.kltn.vtv.util.exception.InternalServerErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CategoryShopServiceImpl implements ICategoryShopService {

    private final CategoryShopRepository categoryShopRepository;
    private final IImageService imageService;
    private final IProductShopService productShopService;
    private final IShopService shopService;


    @Override
    @Transactional
    public CategoryShopResponse addNewCategoryShop(CategoryShopRequest request, String username) {
        Shop shop = shopService.getShopByUsername(username);
        checkExistsByNameAndCategoryShopIdNot(request.getName(), shop.getShopId());

        CategoryShop categoryShop = createCategoryShopByRequest(request, shop);
        try {
            categoryShopRepository.save(categoryShop);

            return CategoryShopResponse.categoryShopResponse(categoryShop, "Thêm danh mục thành công!", "success");
        }catch (Exception e) {
            throw new InternalServerErrorException("Thêm danh mục của cửa hàng thất bại! Vui lòng thử lại sau! " + e.getMessage());
        }
    }


















    private void checkExistsByNameAndCategoryShopIdNot(String name, Long shopId) {
        if (categoryShopRepository.existsByNameAndCategoryShopIdNot(name, shopId)) {
            throw new BadRequestException("Tên danh mục đã tồn tại trong cửa hàng!");
        }
    }


    private CategoryShop createCategoryShopByRequest(CategoryShopRequest categoryShopRequest, Shop shop) {
        try {
            CategoryShop categoryShop = new CategoryShop();
            categoryShop.setName(categoryShopRequest.getName());
            categoryShop.setImage(imageService.uploadImageToFirebase(categoryShopRequest.getImage()));
            categoryShop.setStatus(Status.ACTIVE);
            categoryShop.setCreateAt(LocalDateTime.now());
            categoryShop.setUpdateAt(LocalDateTime.now());
            categoryShop.setShop(shop);
            categoryShop.setProducts(new ArrayList<>());

            return categoryShop;
        }catch (Exception e) {
            throw new InternalServerErrorException("Tạo danh mục cửa hàng thất bại! Vui lòng thử lại sau!");
        }
    }
}
