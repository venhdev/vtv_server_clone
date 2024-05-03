package hcmute.kltn.vtv.controller.manager;

import hcmute.kltn.vtv.service.vtv.IPageService;
import hcmute.kltn.vtv.util.exception.BadRequestException;
import hcmute.kltn.vtv.model.data.manager.response.ListManagerProductResponse;
import hcmute.kltn.vtv.model.data.manager.response.ManagerProductResponse;
import hcmute.kltn.vtv.service.manager.IManagerProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/manager/product")
@RequiredArgsConstructor
public class ManagerProductController {

    private final IManagerProductService managerProductService;
    private final IPageService pageService;

    @PostMapping("/lock/{productId}")
    public ResponseEntity<ManagerProductResponse> lockProductByProductId(@PathVariable Long productId,
                                                                         @RequestParam String note,
                                                                         HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        if (note == null || note.trim().isEmpty()) {
            throw new BadRequestException("Ghi chú không hợp lệ! Vui lòng nhập ghi chú");
        }

        return ResponseEntity.ok(managerProductService.lockProductByProductId(productId, username, note.trim()));
    }

    @PostMapping("/unlock/{productId}")
    public ResponseEntity<ManagerProductResponse> unLockProductByProductId(@PathVariable Long productId,
                                                                           @RequestParam String note,
                                                                           HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        if (note == null || note.trim().isEmpty()) {
            throw new BadRequestException("Ghi chú không hợp lệ! Vui lòng nhập ghi chú");
        }

        return ResponseEntity.ok(managerProductService.unLockProductByProductId(productId, username, note.trim()));
    }

    @GetMapping("/list")
    public ResponseEntity<ListManagerProductResponse> getListManagerProduct(@RequestParam int page,
                                                                            @RequestParam int size) {
        pageService.checkRequestProductPageParams(page, size);

        return ResponseEntity.ok(managerProductService.getListManagerProduct(page, size));
    }

    @GetMapping("/list/productName/{productName}")
    public ResponseEntity<ListManagerProductResponse> getListManagerProductByProductName(@RequestParam int page,
                                                                                         @RequestParam int size,
                                                                                         @PathVariable String productName) {
        pageService.checkRequestProductPageParams(page, size);

        return ResponseEntity.ok(managerProductService.getListManagerProductByProductName(page, size, productName.trim()));
    }

}
