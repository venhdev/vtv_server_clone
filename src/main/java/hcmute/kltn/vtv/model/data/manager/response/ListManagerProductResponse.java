package hcmute.kltn.vtv.model.data.manager.response;

import hcmute.kltn.vtv.model.dto.manager.ManagerProductDTO;
import hcmute.kltn.vtv.model.entity.manager.ManagerProduct;
import hcmute.kltn.vtv.model.extra.ResponseAbstract;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ListManagerProductResponse extends ResponseAbstract {

    private int count;
    private int size;
    private int page;
    private int totalPage;
    private List<ManagerProductDTO> managerProductDTOs;


    public static ListManagerProductResponse listManagerProductResponse(Page<ManagerProduct> managerProductPage, String message, String status) {
        ListManagerProductResponse response = new ListManagerProductResponse();
        response.setCount(managerProductPage.getNumberOfElements());
        response.setTotalPage(managerProductPage.getTotalPages());
        response.setPage(managerProductPage.getNumber() + 1);
        response.setSize(managerProductPage.getSize());
        response.setManagerProductDTOs(ManagerProductDTO.convertEntitiesToDTOs(managerProductPage.getContent()));
        response.setMessage(message);
        response.setStatus(status);
        response.setCode(200);

        return response;
    }
}
