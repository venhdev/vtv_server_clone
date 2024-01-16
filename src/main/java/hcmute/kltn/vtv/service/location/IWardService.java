package hcmute.kltn.vtv.service.location;

import hcmute.kltn.vtv.model.data.location.ListWardResponse;

public interface IWardService {
    String getWardNameByWardCode(String wardCode);

    ListWardResponse getAllWardByDistrictCode(String districtCode);
}
