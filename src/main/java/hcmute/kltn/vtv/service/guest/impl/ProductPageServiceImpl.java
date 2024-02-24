package hcmute.kltn.vtv.service.guest.impl;

import hcmute.kltn.vtv.model.data.paging.response.ListProductPageResponse;
import hcmute.kltn.vtv.model.dto.vtv.ProductDTO;
import hcmute.kltn.vtv.model.entity.vtv.Category;
import hcmute.kltn.vtv.model.entity.vtv.Product;
import hcmute.kltn.vtv.model.extra.Status;
import hcmute.kltn.vtv.repository.vtv.CategoryRepository;
import hcmute.kltn.vtv.repository.vtv.ProductRepository;
import hcmute.kltn.vtv.service.guest.IProductPageService;
import hcmute.kltn.vtv.service.vendor.IProductShopService;
import hcmute.kltn.vtv.util.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductPageServiceImpl implements IProductPageService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private IProductShopService productShopService;

    @Override
    public ListProductPageResponse getListProductPage(int page, int size) {
        int totalProduct = productRepository.countByStatus(Status.ACTIVE);
        int totalPage = (int) Math.ceil((double) totalProduct / size);

        Page<Product> productPage = productRepository.findAllByStatusOrderByName(Status.ACTIVE,
                PageRequest.of(page - 1, size))
                .orElseThrow(() -> new NotFoundException("Không tìm thấy sản phẩm nào!"));

        String message = "Lấy danh sách sản phẩm thành công!";

        return listProductPageResponse(productPage.getContent(), page, size, totalPage, message);
    }

    @Override
    public ListProductPageResponse getListProductPageByCategoryId(Long categoryId, int page, int size) {
        if (isAdminOnlyInCategory(categoryId)) {
            return getProductsByCategoryParentId(categoryId, page, size);
        } else {
            return getProductsByCategoryId(categoryId, page, size);
        }
    }

    @Override
    public ListProductPageResponse getListProductsPageByShopId(Long shopId, int page, int size) {
        int totalProduct = productRepository.countByCategoryShopShopIdAndStatus(shopId, Status.ACTIVE);
        int totalPage = (int) Math.ceil((double) totalProduct / size);

        Page<Product> productPage = productRepository.findAllByCategoryShopShopIdAndStatusOrderByCreateAt(
                shopId, Status.ACTIVE, PageRequest.of(page - 1, size))
                .orElseThrow(() -> new NotFoundException("Không tìm thấy sản phẩm nào trong cửa hàng này!"));

        String message = "Lấy danh sách sản phẩm theo cửa hàng thành công!";

        return listProductPageResponse(productPage.getContent(), page, size, totalPage, message);
    }

    @Override
    public ListProductPageResponse getListBestSellingProductsPageByShopId(Long shopId, int page, int size) {
        int totalProduct = productRepository.countByCategoryShopShopIdAndStatus(shopId, Status.ACTIVE);
        int totalPage = (int) Math.ceil((double) totalProduct / size);

        Page<Product> productPage = productRepository.findAllByCategoryShopShopIdAndStatusOrderBySoldDescNameAsc(
                shopId, Status.ACTIVE, PageRequest.of(page - 1, size))
                .orElseThrow(() -> new NotFoundException("Không tìm thấy sản phẩm nào trong cửa hàng này!"));

        String message = "Lấy danh sách sản phẩm theo cửa hàng theo thứ tự bán chạy nhất thành công!";

        return listProductPageResponse(productPage.getContent(), page, size, totalPage, message);
    }

    @Override
    public ListProductPageResponse getListNewProductsPageByShopId(Long shopId, int page, int size) {
        int totalProduct = productRepository.countByCategoryShopShopIdAndStatus(shopId, Status.ACTIVE);
        int totalPage = (int) Math.ceil((double) totalProduct / size);

        Page<Product> productPage = productRepository.findAllByCategoryShopShopIdAndStatusOrderByCreateAtDesc(
                shopId, Status.ACTIVE, PageRequest.of(page - 1, size))
                .orElseThrow(() -> new NotFoundException("Không tìm thấy sản phẩm nào trong cửa hàng này!"));

        String message = "Lấy danh sách sản phẩm theo cửa hàng theo thứ tự mới nhất thành công!";

        return listProductPageResponse(productPage.getContent(), page, size, totalPage, message);
    }

    @Override
    public ListProductPageResponse getListProductsPagePriceRange(Long minPrice, Long maxPrice, int page, int size) {
        int totalProduct = productRepository.countByProductVariantsPriceBetweenAndStatus(minPrice, maxPrice,
                Status.ACTIVE);
        int totalPage = (int) Math.ceil((double) totalProduct / size);

        Page<Product> productPage = productRepository
                .findAllByProductVariantsPriceBetweenAndStatusOrderByCreateAtDesc(
                        minPrice, maxPrice, Status.ACTIVE, PageRequest.of(page - 1, size))
                .orElseThrow(() -> new NotFoundException("Không tìm thấy sản phẩm nào trong khoảng giá này!"));

        String message = "Lấy danh sách sản phẩm theo khoảng giá thành công!";

        return listProductPageResponse(productPage.getContent(), page, size, totalPage, message);
    }

    @Override
    public ListProductPageResponse getListProductsPageByShopAndPriceRange(Long shopId, Long minPrice, Long maxPrice,
            int page, int size) {
        int totalProduct = productRepository.countByCategoryShopShopIdAndProductVariantsPriceBetweenAndStatus(
                shopId, minPrice, maxPrice, Status.ACTIVE);
        int totalPage = (int) Math.ceil((double) totalProduct / size);

        Page<Product> productPage = productRepository
                .findAllByCategoryShopShopIdAndProductVariantsPriceBetweenAndStatusOrderByCreateAtDesc(
                        shopId, minPrice, maxPrice, Status.ACTIVE, PageRequest.of(page - 1, size))
                .orElseThrow(() -> new NotFoundException("Không tìm thấy sản phẩm nào trong cửa hàng này!"));

        String message = "Lấy danh sách sản phẩm theo cửa hàng theo khoảng giá thành công!";

        return listProductPageResponse(productPage.getContent(), page, size, totalPage, message);
    }

    @Override
    public ListProductPageResponse getListProductsPageByShopAndPriceRangeAndSort(Long shopId, Long minPrice,
            Long maxPrice, int page, int size, String sort) {
        int totalProduct = productRepository.countByCategoryShopShopIdAndProductVariantsPriceBetweenAndStatus(
                shopId, minPrice, maxPrice, Status.ACTIVE);
        int totalPage = (int) Math.ceil((double) totalProduct / size);

        String message;
        Page<Product> productPage;
        switch (sort) {
            case "best-selling":
                /////////////////// LỖI COUNT ?????
                productPage = productRepository
                        .findAllByCategoryShopShopIdAndProductVariantsPriceBetweenAndStatusOrderBySoldDescNameAsc(
                                shopId, minPrice, maxPrice, Status.ACTIVE, PageRequest.of(page - 1, size))
                        .orElseThrow(() -> new NotFoundException("Không tìm thấy sản phẩm nào trong cửa hàng này!"));
                message = "Lấy danh sách sản phẩm theo cửa hàng theo thứ tự bán chạy nhất thành công!";
                break;
            case "price-asc":
                productPage = productRepository
                        .findAllByCategoryShopShopIdAndProductVariantsPriceBetweenAndStatusOrderByProductVariantsPriceAsc(
                                shopId, minPrice, maxPrice, Status.ACTIVE, PageRequest.of(page - 1, size))
                        .orElseThrow(() -> new NotFoundException("Không tìm thấy sản phẩm nào trong cửa hàng này!"));
                message = "Lấy danh sách sản phẩm theo cửa hàng theo thứ tự giá tăng dần thành công!";
                break;
            case "price-desc":
                productPage = productRepository
                        .findAllByCategoryShopShopIdAndProductVariantsPriceBetweenAndStatusOrderByProductVariantsPriceDesc(
                                shopId, minPrice, maxPrice, Status.ACTIVE, PageRequest.of(page - 1, size))
                        .orElseThrow(() -> new NotFoundException("Không tìm thấy sản phẩm nào trong cửa hàng này!"));
                message = "Lấy danh sách sản phẩm theo cửa hàng theo thứ tự giá giảm dần thành công!";
                break;
            default:
                productPage = productRepository
                        .findAllByCategoryShopShopIdAndProductVariantsPriceBetweenAndStatusOrderByCreateAtDesc(
                                shopId, minPrice, maxPrice, Status.ACTIVE, PageRequest.of(page - 1, size))
                        .orElseThrow(() -> new NotFoundException("Không tìm thấy sản phẩm nào trong cửa hàng này!"));
                message = "Lấy danh sách sản phẩm theo cửa hàng theo thứ tự mới nhất thành công!";
        }

        return listProductPageResponse(productPage.getContent(), page,
                size, totalPage, message);
    }

    @Override
    public ListProductPageResponse getListProductsPageByShopSearchSort(String search, int page, int size, String sort) {
        int totalProduct = productRepository.countByNameContainsAndStatus(search, Status.ACTIVE);
        int totalPage = (int) Math.ceil((double) totalProduct / size);

        String message;
        Page<Product> productPage;
        switch (sort) {
            case "newest":
                productPage = productRepository
                        .searchFullTextByStatusOrderByCreateAtDesc(search, Status.ACTIVE,
                                PageRequest.of(page - 1, size))
                        .orElseThrow(() -> new NotFoundException("Không tìm thấy sản phẩm nào!"));
                // message = "Lọc sản phẩm tìm kiếm theo thứ tự mới nhất thành công!";
                message = "Lọc sản phẩm tìm kiếm theo thứ tự mới nhất thành công! " + search;
                break;

            case "best-selling":
                productPage = productRepository
                        .findAllByNameContainsAndStatusOrderBySoldDescNameAsc(search, Status.ACTIVE,
                                PageRequest.of(page - 1, size))
                        .orElseThrow(() -> new NotFoundException("Không tìm thấy sản phẩm nào!"));
                message = "Lọc sản phẩm tìm kiếm theo thứ tự bán chạy nhất thành công!";

                break;
            case "price-asc":
                productPage = productRepository
                        .findAllByNameContainsAndStatusOrderByProductVariantsPriceAsc(search, Status.ACTIVE,
                                PageRequest.of(page - 1, size))
                        .orElseThrow(() -> new NotFoundException("Không tìm thấy sản phẩm nào!"));
                message = "Lọc sản phẩm tìm kiếm theo thứ tự giá tăng dần thành công!";
                break;
            case "price-desc":
                productPage = productRepository
                        .findAllByNameContainsAndStatusOrderByProductVariantsPriceDesc(search, Status.ACTIVE,
                                PageRequest.of(page - 1, size))
                        .orElseThrow(() -> new NotFoundException("Không tìm thấy sản phẩm nào!"));
                message = "Lọc sản phẩm tìm kiếm theo thứ tự giá giảm dần thành công!";
                break;
            default:
                productPage = productRepository
                        .findAllByNameContainsAndStatus(search, Status.ACTIVE, PageRequest.of(page - 1, size))
                        .orElseThrow(() -> new NotFoundException("Không tìm thấy sản phẩm nào!"));
                message = "Tìm kiếm sản phẩm thành công!";
        }

        return listProductPageResponse(productPage.getContent(), page, size, totalPage, message);
    }

    @Override
    public ListProductPageResponse getListProductsPageBySearchAndPriceSort(String search, Long minPrice, Long maxPrice,
            int page, int size, String sort) {
        int totalProduct = productRepository.countByNameContainsAndProductVariantsPriceBetweenAndStatus(search,
                minPrice, maxPrice, Status.ACTIVE);
        int totalPage = (int) Math.ceil((double) totalProduct / size);

        String message;
        Page<Product> productPage;
        message = switch (sort) {
            case "newest" -> {
                productPage = productRepository
                        .findAllByNameContainsAndProductVariantsPriceBetweenAndStatusOrderByCreateAtDesc(
                                search, minPrice, maxPrice, Status.ACTIVE, PageRequest.of(page - 1, size))
                        .orElseThrow(() -> new NotFoundException("Không tìm thấy sản phẩm nào!"));
                yield "Lọc sản phẩm tìm kiếm theo thứ tự mới nhất thành công!";
            }
            case "best-selling" -> {
                productPage = productRepository
                        .findAllByNameContainsAndProductVariantsPriceBetweenAndStatusOrderBySoldDescNameAsc(
                                search, minPrice, maxPrice, Status.ACTIVE, PageRequest.of(page - 1, size))
                        .orElseThrow(() -> new NotFoundException("Không tìm thấy sản phẩm nào!"));
                yield "Lọc sản phẩm tìm kiếm theo thứ tự bán chạy nhất thành công!";
            }
            case "price-asc" -> {
                productPage = productRepository
                        .findAllByNameContainsAndProductVariantsPriceBetweenAndStatusOrderByProductVariantsPriceAsc(
                                search, minPrice, maxPrice, Status.ACTIVE, PageRequest.of(page - 1, size))
                        .orElseThrow(() -> new NotFoundException("Không tìm thấy sản phẩm nào!"));
                yield "Lọc sản phẩm tìm kiếm theo thứ tự giá tăng dần thành công!";
            }
            case "price-desc" -> {
                productPage = productRepository
                        .findAllByNameContainsAndProductVariantsPriceBetweenAndStatusOrderByProductVariantsPriceDesc(
                                search, minPrice, maxPrice, Status.ACTIVE, PageRequest.of(page - 1, size))
                        .orElseThrow(() -> new NotFoundException("Không tìm thấy sản phẩm nào!"));
                yield "Lọc sản phẩm tìm kiếm theo thứ tự giá giảm dần thành công!";
            }
            default -> {
                productPage = productRepository
                        .findAllByNameContainsAndProductVariantsPriceBetweenAndStatus(
                                search, minPrice, maxPrice, Status.ACTIVE, PageRequest.of(page - 1, size))
                        .orElseThrow(() -> new NotFoundException("Không tìm thấy sản phẩm nào!"));
                yield "Tìm kiếm sản phẩm thành công!";
            }
        };

        return listProductPageResponse(productPage.getContent(), page, size, totalPage, message);
    }

    private ListProductPageResponse getProductsByCategoryId(Long categoryId, int page, int size) {
        int totalProduct = productRepository.countByCategoryCategoryIdAndStatus(categoryId, Status.ACTIVE);
        int totalPage = (int) Math.ceil((double) totalProduct / size);

        Page<Product> productPage = productRepository.findAllByCategoryCategoryIdAndStatusOrderByCreateAt(
                categoryId, Status.ACTIVE,
                PageRequest.of(page - 1, size))
                .orElseThrow(() -> new NotFoundException("Không tìm thấy sản phẩm nào trong danh mục cha này!"));

        String message = "Lấy danh sách sản phẩm theo danh mục con thành công!";

        return listProductPageResponse(productPage.getContent(), page, size, totalPage, message);

    }







    private ListProductPageResponse getProductsByCategoryParentId(Long categoryId, int page, int size) {
        List<Category> categories = categoryRepository.findAllByParentCategoryIdAndStatus(categoryId, Status.ACTIVE)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy danh mục nào!"));

        int totalProduct = productRepository.countByCategoryInAndStatus(categories, Status.ACTIVE);
        int totalPage = (int) Math.ceil((double) totalProduct / size);

        Page<Product> productPage = productRepository
                .findAllByCategoryInAndStatusOrderByCreateAt(categories, Status.ACTIVE,
                        PageRequest.of(page - 1, size))
                .orElseThrow(() -> new NotFoundException("Không tìm thấy sản phẩm nào trong danh mục cha này!"));

        String message = "Lấy danh sách sản phẩm theo danh mục cha thành công!";

        return listProductPageResponse(productPage.getContent(), page, size, totalPage, message);
    }

    public boolean isAdminOnlyInCategory(Long categoryId) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        return categoryOptional.map(Category::isAdminOnly).orElse(false);
    }

    public ListProductPageResponse listProductPageResponse(List<Product> products, int page,
            int size, int totalPage, String message) {
        ListProductPageResponse response = new ListProductPageResponse();
        response.setProductDTOs(ProductDTO.convertToListDTO(products));
        response.setCount(products.size());
        response.setSize(size);
        response.setPage(page);
        response.setTotalPage(totalPage);
        response.setMessage(message);
        response.setStatus("OK");
        response.setCode(200);
        return response;
    }

}
