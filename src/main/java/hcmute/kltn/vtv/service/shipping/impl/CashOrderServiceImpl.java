package hcmute.kltn.vtv.service.shipping.impl;

import hcmute.kltn.vtv.repository.shipping.CashOrderRepository;
import hcmute.kltn.vtv.service.shipping.ICashOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CashOrderServiceImpl implements ICashOrderService {

    private final CashOrderRepository cashOrderRepository;



}

