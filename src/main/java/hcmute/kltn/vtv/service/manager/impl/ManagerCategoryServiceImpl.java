package hcmute.kltn.vtv.service.manager.impl;

import hcmute.kltn.vtv.repository.CategoryRepository;
import hcmute.kltn.vtv.service.manager.IManagerCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManagerCategoryServiceImpl implements IManagerCategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

}
