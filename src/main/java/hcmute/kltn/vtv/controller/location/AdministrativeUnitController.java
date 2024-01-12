package hcmute.kltn.vtv.controller.location;

import hcmute.kltn.vtv.service.location.IAdministrativeUnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/location/administrative-unit")
@RequiredArgsConstructor
public class AdministrativeUnitController {

    private final IAdministrativeUnitService administrativeUnitService;

}
