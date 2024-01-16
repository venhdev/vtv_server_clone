package hcmute.kltn.vtv.service.vendor.impl;

import hcmute.kltn.vtv.repository.user.CommentRepository;
import hcmute.kltn.vtv.repository.user.ReviewRepository;
import hcmute.kltn.vtv.service.user.ICustomerService;
import hcmute.kltn.vtv.service.vendor.ICommentShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentShopServiceImpl implements ICommentShopService {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ICustomerService customerService;
}
