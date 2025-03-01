package hcmute.kltn.vtv.service.manager.impl;

import hcmute.kltn.vtv.model.data.guest.BrandResponse;
import hcmute.kltn.vtv.model.data.guest.ResponseClass;
import hcmute.kltn.vtv.model.data.vtv.request.BrandRequest;
import hcmute.kltn.vtv.model.entity.vtv.Brand;
import hcmute.kltn.vtv.model.extra.Status;
import hcmute.kltn.vtv.repository.vtv.BrandRepository;
import hcmute.kltn.vtv.repository.vendor.ProductRepository;
import hcmute.kltn.vtv.service.manager.IManagerBrandService;
import hcmute.kltn.vtv.service.manager.IManagerCategoryService;
import hcmute.kltn.vtv.service.vtv.IImageService;
import hcmute.kltn.vtv.util.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class ManagerBrandServiceImpl implements IManagerBrandService {

    private final BrandRepository brandRepository;
    private final IImageService imageService;
    private final IManagerCategoryService categoryService;
    private final ProductRepository productRepository;


    @Override
    public boolean existsBrandUsingCategoryIdInCategories(Long categoryId) {
        return brandRepository.existsBrandUsingCategoryIdInCategories(categoryId);
    }


    @Override
    @Transactional
    public BrandResponse addNewBrandByManager(BrandRequest brandRequest, String username) {
        existsBrandByName(brandRequest.getName());
        categoryService.checkExistsCategoriesByIds(brandRequest.getCategoryIds());
        Brand brand = createBrandByBrandRequest(brandRequest, username);
        try {
            brandRepository.save(brand);

            return BrandResponse.brandResponse(brand, "Thêm thương hiệu thành công!", "Success");
        } catch (Exception e) {
            imageService.deleteImageInFirebase(brand.getImage());
            throw new BadRequestException("Thêm thương hiệu thất bại!");
        }
    }


    @Override
    @Transactional
    public BrandResponse updateBrandByManager(Long brandId, BrandRequest brandRequest, String username) {
        checkExistBrandByBrandId(brandId);
        checkExistBrandByBrandIdAndName(brandId, brandRequest.getName());
        categoryService.checkExistsCategoriesByIds(brandRequest.getCategoryIds());
        Brand brand = getBrandById(brandId);
        String oldImage = brand.getImage();
        updateBrandByBrandRequest(brand, brandRequest, username);

        try {
            brandRepository.save(brand);
            if (brandRequest.isChangeImage() ) {
                imageService.deleteImageInFirebase(oldImage);
            }

            return BrandResponse.brandResponse(brand, "Cập nhật thương hiệu thành công!", "Success");
        } catch (Exception e) {
            imageService.deleteImageInFirebase(brand.getImage());
            throw new BadRequestException("Cập nhật thương hiệu thất bại!");
        }
    }


    @Override
    @Transactional
    public ResponseClass deleteBrandByManager(Long brandId, String username) {
        checkExistBrandByBrandId(brandId);
        if (productRepository.existsByBrandBrandId(brandId)) {
            throw new BadRequestException("Thương hiệu đang chứa sản phẩm, không thể xóa!");
        }
        Brand brand = getBrandById(brandId);
        String oldImage = brand.getImage();
        try {
            brandRepository.delete(brand);
            if (oldImage != null) {
                imageService.deleteImageInFirebase(oldImage);
            }
            return ResponseClass.responseClass("Xóa thương hiệu thành công!", "Success");
        } catch (Exception e) {
            throw new BadRequestException("Xóa thương hiệu thất bại!");
        }
    }


    private void updateBrandByBrandRequest(Brand brand, BrandRequest brandRequest, String username) {

        brand.setName(brandRequest.getName());
        brand.setDescription(brandRequest.getDescription());
        brand.setInformation(brandRequest.getInformation());
        brand.setOrigin(brandRequest.getOrigin());
        brand.setImage(brandRequest.isChangeImage() ? imageService.uploadImageToFirebase(brandRequest.getImage()) : brand.getImage());
        brand.setUpdatedBy(username);
        brand.setUpdateAt(LocalDateTime.now());
        brand.setAllCategories(brandRequest.isAllCategories());
        brand.setCategories(brandRequest.isAllCategories() ? null :
                categoryService.getCategoriesByIds(brandRequest.getCategoryIds()));

    }


    private Brand createBrandByBrandRequest(BrandRequest brandRequest, String username) {
        Brand brand = new Brand();
        brand.setName(brandRequest.getName());
        brand.setDescription(brandRequest.getDescription());
        brand.setInformation(brandRequest.getInformation());
        brand.setOrigin(brandRequest.getOrigin());
        brand.setImage(imageService.uploadImageToFirebase(brandRequest.getImage()));
        brand.setCreatedBy(username);
        brand.setUpdatedBy(username);
        brand.setStatus(Status.ACTIVE);
        brand.setAllCategories(brandRequest.isAllCategories());
        brand.setCategories(brandRequest.isAllCategories() ? null :
                categoryService.getCategoriesByIds(brandRequest.getCategoryIds()));
        brand.setCreateAt(LocalDateTime.now());
        brand.setUpdateAt(LocalDateTime.now());

        return brand;
    }


    private Brand getBrandById(Long brandId) {
        return brandRepository.findById(brandId)
                .orElseThrow(() -> new BadRequestException("Không tìm thấy thương hiệu!"));
    }


    private void existsBrandByName(String name) {
        if (brandRepository.existsByName(name)) {
            throw new BadRequestException("Tên thương hiệu đã tồn tại!");
        }
    }

    private void checkExistBrandByBrandId(Long brandId) {
        if (!brandRepository.existsById(brandId)) {
            throw new BadRequestException("Không tìm thấy thương hiệu!");
        }
    }

    private void checkExistBrandByBrandIdAndName(Long brandId, String name) {
        if (!brandRepository.existsByBrandIdAndName(brandId, name) && brandRepository.existsByName(name)) {
            throw new BadRequestException("Tên thương hiệu đã tồn tại!");
        }
    }


}
