package hcmute.kltn.vtv.service.user;

import hcmute.kltn.vtv.model.data.user.UserSocket;
import hcmute.kltn.vtv.model.data.user.request.MessengerRequest;
import hcmute.kltn.vtv.model.data.user.response.MessengersResponse;

public interface IMessengerService {
    void saveMessenger(MessengerRequest request);

    MessengersResponse findAllByRomChatId(Long id, String username);

    UserSocket chatVsUser(UserSocket user);
}
