package hcmute.kltn.vtv.service.location.impl;

import hcmute.kltn.vtv.model.data.location.ListProvinceResponse;
import hcmute.kltn.vtv.model.dto.location_dto.ProvinceDTO;
import hcmute.kltn.vtv.model.entity.location.Province;
import hcmute.kltn.vtv.repository.location.ProvinceRepository;
import hcmute.kltn.vtv.service.location.IProvinceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProvinceServiceImpl implements IProvinceService {
    private final ProvinceRepository provinceRepository;

    @Override
    public ListProvinceResponse getAllProvince() {

        List<Province> provinces = provinceRepository.findAll();
        List<ProvinceDTO> provinceDTOs = new ArrayList<>();
        if (!provinces.isEmpty()) {
            provinceDTOs = ProvinceDTO.convertEntitiesToDTOs(provinces);
        }

        ListProvinceResponse response = new ListProvinceResponse();
        response.setProvinceDTOs(provinceDTOs);
        response.setCount(response.getProvinceDTOs().size());
        response.setCode(200);
        response.setMessage("Lấy danh sách tỉnh thành phố thành công.");
        response.setStatus("OK");
        return response;
    }

}
