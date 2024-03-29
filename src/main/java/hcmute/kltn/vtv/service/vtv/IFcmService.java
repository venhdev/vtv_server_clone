package hcmute.kltn.vtv.service.vtv;

import hcmute.kltn.vtv.model.entity.user.Notification;
import org.springframework.scheduling.annotation.Async;

public interface IFcmService {
    void addNewFcmToken(String fcmToken, String username);

    @Async
    void sendNotification(Notification notice);

    void deleteFcmToken(String fcmToken);
}
