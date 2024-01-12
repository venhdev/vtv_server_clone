package hcmute.kltn.vtv.controller.demo;

import hcmute.kltn.vtv.model.data.user.request.ChangePasswordRequest;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
@Hidden
public class AdminController {

    @GetMapping
    @PreAuthorize("hasAuthority('admin:read')")
    public String get() {
        return "GET:: admin controller";
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
    // @Hidden
    public String post() {
        return "POST:: admin controller";
    }

    @PutMapping
    @PreAuthorize("hasAuthority('admin:update')")
    // @Hidden
    public String put(@RequestBody ChangePasswordRequest username) {

        System.out.println("username: " + username);
        return "PUT:: admin controller";
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('admin:delete')")
    // @Hidden
    public String delete(@RequestBody String username) {

        System.out.println("username: " + username);
        return "delete:: admin controller";
    }
}
