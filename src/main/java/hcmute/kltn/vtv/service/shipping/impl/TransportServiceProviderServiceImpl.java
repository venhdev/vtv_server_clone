package hcmute.kltn.vtv.service.shipping.impl;

import hcmute.kltn.vtv.repository.shipping.TransportServiceProviderRepository;
import hcmute.kltn.vtv.service.shipping.ITransportServiceProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransportServiceProviderServiceImpl implements ITransportServiceProviderService {
    private final TransportServiceProviderRepository repository;


}