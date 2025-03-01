package hcmute.kltn.vtv.service.user.impl;

import hcmute.kltn.vtv.model.extra.OrderStatus;
import hcmute.kltn.vtv.util.exception.BadRequestException;
import hcmute.kltn.vtv.model.data.user.response.FavoriteProductResponse;
import hcmute.kltn.vtv.model.data.user.response.ListFavoriteProductResponse;
import hcmute.kltn.vtv.model.data.guest.ProductResponse;
import hcmute.kltn.vtv.model.entity.user.Customer;
import hcmute.kltn.vtv.model.entity.user.FavoriteProduct;
import hcmute.kltn.vtv.repository.user.FavoriteProductRepository;
import hcmute.kltn.vtv.service.guest.IProductService;
import hcmute.kltn.vtv.service.user.ICustomerService;
import hcmute.kltn.vtv.service.user.IFavoriteProductService;
import hcmute.kltn.vtv.util.exception.InternalServerErrorException;
import hcmute.kltn.vtv.util.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteProductServiceImpl implements IFavoriteProductService {

    private final ICustomerService customerService;
    private final IProductService productService;
    private final FavoriteProductRepository favoriteProductRepository;

    @Override
    @Transactional
    public FavoriteProductResponse addNewFavoriteProduct(Long productId, String username) {
        checkExistFavoriteProductByProductIdAndUsername(productId, username);
        FavoriteProduct favoriteProduct = createFavoriteProductByProductIdAndUsername(productId, username);
        try {
            favoriteProductRepository.save(favoriteProduct);

            return FavoriteProductResponse.favoriteProductResponse(favoriteProduct,
                    "Thêm sản phẩm vào danh sách yêu thích thành công!", "Success");
        } catch (Exception e) {
            throw new InternalServerErrorException("Thêm sản phẩm vào danh sách yêu thích thất bại!");
        }
    }

    @Override
    public FavoriteProductResponse checkExistFavoriteProduct(Long productId, String username) {
        if (favoriteProductRepository.existsByCustomerUsernameAndProductProductId(username, productId)) {
            FavoriteProduct favoriteProduct = favoriteProductRepository.findByCustomerUsernameAndProductProductId(username, productId)
                    .orElseThrow(() -> new NotFoundException("Sản phẩm yêu thích không tồn tại."));

            return FavoriteProductResponse.favoriteProductResponse(favoriteProduct,
                    "Sản phẩm đã có trong danh sách yêu thích của bạn.", "OK");
        }

        return FavoriteProductResponse.favoriteProductResponse(null,
                "Sản phẩm chưa có trong danh sách yêu thích của bạn.", "Not Found");
    }


    @Override
    public ProductResponse getProductByFavoriteProductId(Long favoriteProductId, String username) {
        FavoriteProduct favoriteProduct = getFavoriteProductById(favoriteProductId);

        int countOrder = productService.countOrdersByProductId(favoriteProduct.getProduct().getProductId(), OrderStatus.COMPLETED);

        return ProductResponse.productResponse(favoriteProduct.getProduct(), countOrder,
                "Lấy thông tin sản phẩm yêu thích thành công.", "OK");
    }

    @Override
    public ListFavoriteProductResponse getListFavoriteProduct(String username) {

        Customer customer = customerService.getCustomerByUsername(username);
        List<FavoriteProduct> listFavoriteProduct = favoriteProductRepository.findByCustomer(customer)
                .orElseThrow(() -> new BadRequestException("Không có sản phẩm yêu thích nào!"));

        return ListFavoriteProductResponse.listFavoriteProductResponse(customer, listFavoriteProduct,
                "Lấy danh sách sản phẩm yêu thích thành công.", "OK");
    }

    @Override
    public FavoriteProductResponse deleteFavoriteProduct(Long favoriteProductId, String username) {
        checkExistFavoriteProductByFavoriteProductIdAndUsername(favoriteProductId, username);
        FavoriteProduct favoriteProduct = getFavoriteProductById(favoriteProductId);

        if (!favoriteProduct.getCustomer().getUsername().equals(username)) {
            throw new BadRequestException("Sản phẩm yêu thích không thuộc sở hữu của bạn.");
        }

        try {
            favoriteProductRepository.delete(favoriteProduct);

            return FavoriteProductResponse.favoriteProductResponse(null,
                    "Xóa sản phẩm yêu thích thành công!", "Success");
        } catch (Exception e) {
            throw new BadRequestException("Xóa sản phẩm yêu thích thất bại.");
        }
    }


    private FavoriteProduct createFavoriteProductByProductIdAndUsername(Long productId, String username) {
        FavoriteProduct favoriteProduct = new FavoriteProduct();
        favoriteProduct.setCustomer(customerService.getCustomerByUsername(username));
        favoriteProduct.setProduct(productService.getProductById(productId));
        favoriteProduct.setCreateAt(LocalDateTime.now());

        return favoriteProduct;
    }

    private void checkExistFavoriteProductByProductIdAndUsername(Long productId, String username) {
        if (favoriteProductRepository.existsByCustomerUsernameAndProductProductId(username, productId)) {
            throw new BadRequestException("Sản phẩm đã có trong danh sách yêu thích của bạn.");
        }
    }


    private void checkExistFavoriteProductByFavoriteProductIdAndUsername(Long favoriteProductId, String username) {
        checkExistFavoriteProductId(favoriteProductId);
        if (!favoriteProductRepository.existsByFavoriteProductIdAndCustomerUsername(favoriteProductId, username)) {
            throw new BadRequestException("Mã sản phẩm yêu thích không tồn tại trong danh sách của bạn.");
        }
    }

    private void checkExistFavoriteProductId(Long favoriteProductId) {
        if (!favoriteProductRepository.existsById(favoriteProductId)) {
            throw new BadRequestException("Mã sản phẩm yêu thích không tồn tại.");
        }
    }


    private FavoriteProduct getFavoriteProductById(Long favoriteProductId) {
        return favoriteProductRepository.findById(favoriteProductId)
                .orElseThrow(() -> new NotFoundException("Sản phẩm yêu thích không tồn tại."));
    }


}
