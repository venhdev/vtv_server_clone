package hcmute.kltn.vtv.model.data.admin.response;

import hcmute.kltn.vtv.model.data.admin.CategoryAdminDTO;
import hcmute.kltn.vtv.model.extra.ResponseAbstract;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategoryAdminResponse extends ResponseAbstract {

    private CategoryAdminDTO categoryAdminDTO;

}
