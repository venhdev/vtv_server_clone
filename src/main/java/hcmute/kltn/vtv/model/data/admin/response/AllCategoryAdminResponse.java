package hcmute.kltn.vtv.model.data.admin.response;

import hcmute.tlcn.vtc.model.data.admin.CategoryAdminDTO;
import hcmute.tlcn.vtc.model.extra.ResponseAbstract;
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
