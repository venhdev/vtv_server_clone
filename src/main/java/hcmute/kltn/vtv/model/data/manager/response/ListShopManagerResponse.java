package hcmute.kltn.vtv.model.data.manager.response;

import hcmute.kltn.vtv.model.dto.manager.ManagerShopDTO;
import hcmute.kltn.vtv.model.entity.manager.ManagerShop;
import hcmute.kltn.vtv.model.extra.ResponseAbstract;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ListShopManagerResponse extends ResponseAbstract {

    private int count;
    private int size;
    private int page;
    private int totalPage;
    private List<ManagerShopDTO> managerShopDTOs;


    public static ListShopManagerResponse listShopManagerResponse(Page<ManagerShop> managerShopPage, String message) {
        ListShopManagerResponse response = new ListShopManagerResponse();
        response.setManagerShopDTOs(ManagerShopDTO.convertEntitiesToDTOs(managerShopPage.getContent()));
        response.setSize(managerShopPage.getSize());
        response.setPage(managerShopPage.getNumber() + 1);
        response.setTotalPage(managerShopPage.getTotalPages());
        response.setCount(managerShopPage.getNumberOfElements());
        response.setMessage(message);
        response.setCode(200);
        response.setStatus("OK");

        return response;
    }
}
