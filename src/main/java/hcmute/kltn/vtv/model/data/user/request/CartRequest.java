package hcmute.kltn.vtv.model.data.user.request;

import hcmute.kltn.vtv.util.exception.BadRequestException;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartRequest {

    private String username;

    private Long productVariantId;

    private int quantity;

    public void validate() {

        if (productVariantId == null) {
            throw new BadRequestException("Mã biến thể cửa sản phẩm không được để trống.");
        }

        if (username == null || username.isEmpty()) {
            throw new BadRequestException("Tên đăng nhập không được để trống.");
        }

        if (quantity <= 0) {
            throw new BadRequestException("Số lượng sản phẩm không hợp lệ.");
        }
    }

    public void validateUpdate() {

        if (productVariantId == null) {
            throw new BadRequestException("Mã biến thể cửa sản phẩm không được để trống.");
        }

        if (username == null || username.isEmpty()) {
            throw new BadRequestException("Tên đăng nhập không được để trống.");
        }
    }

}
