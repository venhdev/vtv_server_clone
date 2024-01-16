package hcmute.kltn.vtv.service.location.impl;

import hcmute.kltn.vtv.util.exception.BadRequestException;
import hcmute.kltn.vtv.model.data.location.ListWardResponse;
import hcmute.kltn.vtv.model.dto.location.WardDTO;
import hcmute.kltn.vtv.model.entity.location.Ward;
import hcmute.kltn.vtv.repository.location.WardRepository;
import hcmute.kltn.vtv.service.location.IWardService;
import hcmute.kltn.vtv.util.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WardServiceImpl implements IWardService {
    private final WardRepository wardRepository;

    @Override
    public String getWardNameByWardCode(String wardCode) {
        Ward ward = wardRepository.findByWardCode(wardCode)
                .orElseThrow(() -> new NotFoundException(
                        "Không tìm thấy phường xã nào có mã là: " + wardCode));
        return ward.getName();
    }

    @Override
    public ListWardResponse getAllWardByDistrictCode(String districtCode) {
        List<Ward> wards = wardRepository.findAllByDistrict_DistrictCode(districtCode)
                .orElseThrow(() -> new BadRequestException(
                        "Không tìm thấy phường xã nào có mã quận huyện là " + districtCode));
        List<WardDTO> wardDTOs = new ArrayList<>();
        if (!wards.isEmpty()) {
            wardDTOs = WardDTO.convertEntitiesToDTOs(wards);
        }
        ListWardResponse response = new ListWardResponse();
        response.setWardDTOs(wardDTOs);
        response.setCount(response.getWardDTOs().size());
        response.setDistrictCode(districtCode);
        response.setCode(200);
        response.setMessage("Lấy danh sách phường xã thành công.");
        response.setStatus("OK");
        return response;

    }

}
