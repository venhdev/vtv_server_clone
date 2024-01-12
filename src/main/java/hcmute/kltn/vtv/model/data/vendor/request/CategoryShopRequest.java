package hcmute.kltn.vtv.model.data.vendor.request;

import hcmute.kltn.vtv.util.exception.BadRequestException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@ToString
@RequiredArgsConstructor
public class CategoryShopRequest {

    private Long categoryId;
    private String name;
    private String description;
    private String image;
    private String username;
    private Long parentId;

    public void validate() {
        if (this.name == null || this.name.isEmpty()) {
            throw new BadRequestException("Tên danh mục không được để trống!");
        }

        if (this.description == null || this.description.isEmpty()) {
            throw new BadRequestException("Mô tả danh mục không được để trống!");
        }

        if (this.image == null || this.image.isEmpty()) {
            throw new BadRequestException("Hình ảnh danh mục không được để trống!");
        }

        if (this.parentId == null) {
            throw new BadRequestException("Mã danh mục cha không được để trống!");
        }

        trim();
    }

    public void validateUpdate() {
        if (this.categoryId == null) {
            throw new BadRequestException("Mã danh mục không được để trống!");
        }

        validate();
    }

    public void trim() {
        this.name = this.name.trim();
        this.description = this.description.trim();
        this.image = this.image.trim();
        this.username = this.username.trim();
    }

}
