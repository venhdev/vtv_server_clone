package hcmute.kltn.vtv.model.data.paging.response;

import hcmute.kltn.vtv.model.dto.ProductDTO;
import hcmute.kltn.vtv.model.extra.ResponseAbstract;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ListProductPageResponse extends ResponseAbstract {

    private int count;
    private int page;
    private int size;
    private int totalPage;
    private List<ProductDTO> productDTOs;
}
