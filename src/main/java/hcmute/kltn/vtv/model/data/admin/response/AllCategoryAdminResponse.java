package hcmute.kltn.vtv.model.data.admin.response;

import hcmute.kltn.vtv.model.data.admin.CategoryAdminDTO;
import hcmute.kltn.vtv.model.extra.ResponseAbstract;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AllCategoryAdminResponse extends ResponseAbstract {

    private List<CategoryAdminDTO> categoryAdminDTOs;
}
