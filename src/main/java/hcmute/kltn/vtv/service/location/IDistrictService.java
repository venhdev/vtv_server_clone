package hcmute.kltn.vtv.service.location;

import hcmute.kltn.vtv.model.data.location.ListDistrictResponse;

public interface IDistrictService {
    ListDistrictResponse getAllDistrictByProvinceCode(String provinceCode);
}
