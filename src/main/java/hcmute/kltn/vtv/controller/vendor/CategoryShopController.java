package hcmute.kltn.vtv.controller.vendor;


import hcmute.kltn.vtv.model.data.vendor.request.CategoryShopRequest;
import hcmute.kltn.vtv.model.data.vendor.response.CategoryShopResponse;
import hcmute.kltn.vtv.service.vendor.ICategoryShopService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vendor/category-shop")
@RequiredArgsConstructor
public class CategoryShopController {

    private final ICategoryShopService categoryShopService;


    @PostMapping("/add")
    public ResponseEntity<CategoryShopResponse> addNewCategoryShop(@ModelAttribute CategoryShopRequest categoryShopRequest,
                                                                   HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        categoryShopRequest.validateCreate();

        return ResponseEntity.ok(categoryShopService.addNewCategoryShop(categoryShopRequest, username));
    }

}
