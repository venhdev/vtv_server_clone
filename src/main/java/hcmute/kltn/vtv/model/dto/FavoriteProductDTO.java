package hcmute.kltn.vtv.model.dto;

import hcmute.kltn.vtv.model.entity.vtc.FavoriteProduct;
import hcmute.kltn.vtv.model.entity.vtc.Product;
import hcmute.kltn.vtv.model.entity.vtc.ProductVariant;
import hcmute.kltn.vtv.model.extra.Status;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteProductDTO {

    private Long favoriteProductId;

    private ProductDTO productDTO;

    public static FavoriteProductDTO convertToDTO(FavoriteProduct favoriteProduct) {
        ModelMapper modelMapper = new ModelMapper();
        FavoriteProductDTO favoriteProductDTO = new FavoriteProductDTO();
        favoriteProductDTO.setFavoriteProductId(favoriteProduct.getFavoriteProductId());
        favoriteProductDTO.setProductDTO(modelMapper.map(favoriteProduct.getProduct(), ProductDTO.class));
        return favoriteProductDTO;
    }

    public static List<FavoriteProductDTO> convertToListDTO(List<FavoriteProduct> favoriteProducts) {

        List<FavoriteProductDTO> favoriteProductDTOS = new ArrayList<>();
        for (FavoriteProduct favoriteProduct : favoriteProducts) {
            FavoriteProductDTO favoriteProductDTO = new FavoriteProductDTO();
            favoriteProductDTO.setFavoriteProductId(favoriteProduct.getFavoriteProductId());
            favoriteProductDTO.setProductDTO(getProductToDTO(favoriteProduct.getProduct()));
            favoriteProductDTOS.add(favoriteProductDTO);
        }
        return favoriteProductDTOS;

    }

    private static ProductDTO getProductToDTO(Product product) {
        ModelMapper modelMapper = new ModelMapper();
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        List<ProductVariant> activeProductVariants = product.getProductVariants().stream()
                .filter(productVariant -> productVariant.getStatus() != Status.DELETED)
                .toList();

        productDTO.setProductVariantDTOs(ProductVariantDTO.convertToListDTO(activeProductVariants));
        return productDTO;
    }

}
