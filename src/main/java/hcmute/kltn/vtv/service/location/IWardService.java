package hcmute.kltn.vtv.service.location;

import hcmute.kltn.vtv.model.data.location.ListWardResponse;

public interface IWardService {
    ListWardResponse getAllWardByDistrictCode(String districtCode);
}
