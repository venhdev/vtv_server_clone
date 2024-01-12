package hcmute.kltn.vtv.model.data.admin.request;

import hcmute.kltn.vtv.util.exception.BadRequestException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@ToString
@RequiredArgsConstructor
public class CategoryAdminRequest {

    private Long categoryId;
    private String name;
    private String description;
    private String image;

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

        trim();
    }

    public void trim() {
        this.name = this.name.trim();
        this.description = this.description.trim();
        this.image = this.image.trim();
    }
}
