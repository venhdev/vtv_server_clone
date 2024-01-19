package hcmute.kltn.vtv.controller.admin;

import hcmute.kltn.vtv.model.data.manager.response.ListManagerPageResponse;
import hcmute.kltn.vtv.model.data.manager.response.ManagerResponse;
import hcmute.kltn.vtv.model.extra.Status;
import hcmute.kltn.vtv.service.admin.IAdminRoleManagerService;
import hcmute.kltn.vtv.util.exception.BadRequestException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/manage")
@RequiredArgsConstructor
public class AdminManagerController {

    @Autowired
    private IAdminRoleManagerService managerAdminService;

    @PostMapping("/add-role-manager")
    public ResponseEntity<ManagerResponse> addRoleManager(@RequestParam String usernameCustomer,
            HttpServletRequest servletRequest) {
        String username = (String) servletRequest.getAttribute("username");
        ManagerResponse managerResponse = managerAdminService.addRoleManager(username, usernameCustomer.trim());
        return ResponseEntity.ok(managerResponse);
    }

    @PostMapping("/remove-role-manager")
    public ResponseEntity<ManagerResponse> removeRoleManager(@RequestParam String usernameCustomer,
            HttpServletRequest servletRequest) {
        String username = (String) servletRequest.getAttribute("username");
        ManagerResponse managerResponse = managerAdminService.removeRoleManager(username, usernameCustomer.trim());
        return ResponseEntity.ok(managerResponse);
    }

    @GetMapping("/get-list-manager")
    public ResponseEntity<ListManagerPageResponse> getListManager(@RequestParam int size,
            @RequestParam int page) {
        managerAdminService.checkRequestPageParams(size, page);
        return ResponseEntity.ok(managerAdminService.getManagerPage(page, size));
    }

    @GetMapping("/find-manager")
    public ResponseEntity<ListManagerPageResponse> findManagerPageByUsername(@RequestParam int size,
            @RequestParam int page,
            @RequestParam String username) {
        managerAdminService.checkRequestPageParams(size, page);
        if (username.trim().isEmpty()) {
            throw new BadRequestException("Tên tài khoản không được để trống!");
        }
        return ResponseEntity.ok(managerAdminService.getManagerPageByUsername(page, size, username.trim()));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ListManagerPageResponse> getListManagerByStatus(@PathVariable Status status,
            @RequestParam int size,
            @RequestParam int page) {
        managerAdminService.checkRequestPageParams(size, page);
        return ResponseEntity.ok(managerAdminService.getManagerPageByStatus(page, size, status));
    }

}
