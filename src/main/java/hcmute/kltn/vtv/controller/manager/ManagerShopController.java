package hcmute.kltn.vtv.controller.manager;

import hcmute.kltn.vtv.model.data.manager.response.ListShopManagerResponse;
import hcmute.kltn.vtv.model.data.manager.response.ManagerShopResponse;
import hcmute.kltn.vtv.model.data.manager.response.ShopsResponse;
import hcmute.kltn.vtv.model.extra.Status;
import hcmute.kltn.vtv.service.manager.IManagerShopService;
import hcmute.kltn.vtv.service.vtv.IPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/manager/shop")
@RequiredArgsConstructor
public class ManagerShopController {

    private final IManagerShopService managerShopService;
    private final IPageService pageService;

    @GetMapping("/manager-shop/id/{shopId}")
    public ResponseEntity<ManagerShopResponse> getShopById(@PathVariable Long shopId) {
        return ResponseEntity.ok(managerShopService.getShopById(shopId));
    }

    @GetMapping("/manager-shop/list/status/{status}")
    public ResponseEntity<ListShopManagerResponse> getListShop(@RequestParam int page,
                                                               @RequestParam int size,
                                                               @PathVariable Status status) {
        pageService.validatePageNumberAndSize(page, size);

        return ResponseEntity.ok(managerShopService.getListShop(size, page, status));
    }

    @GetMapping("/manager-shop/list/name/{name}/status/{status}")
    public ResponseEntity<ListShopManagerResponse> getListShopByName(@RequestParam int page,
                                                                     @RequestParam int size,
                                                                     @PathVariable String name,
                                                                     @PathVariable Status status) {
        pageService.validatePageNumberAndSize(page, size);

        return ResponseEntity.ok(managerShopService.getListShopByName(size, page, name, status));
    }


    @GetMapping("/shops/status/{status}")
    public ResponseEntity<ShopsResponse> getShopsByStatus(@RequestParam int page,
                                                          @RequestParam int size,
                                                          @PathVariable Status status) {
        pageService.validatePageNumberAndSize(page, size);

        return ResponseEntity.ok(managerShopService.getShopsByStatus(page, size, status));
    }


    @GetMapping("/shops/search/name/{name}/status/{status}")
    public ResponseEntity<ShopsResponse> getShopsByNameAndStatus(@RequestParam int page,
                                                                   @RequestParam int size,
                                                                   @PathVariable String name,
                                                                   @PathVariable Status status) {
        pageService.validatePageNumberAndSize(page, size);

        return ResponseEntity.ok(managerShopService.getShopsByNameAndStatus(page, size, name, status));
    }


}
