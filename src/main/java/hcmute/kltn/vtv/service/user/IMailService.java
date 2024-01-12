package hcmute.kltn.vtv.service.user;

import hcmute.kltn.vtv.model.data.user.response.ForgotPasswordResponse;

public interface IMailService {

    ForgotPasswordResponse sendMailForgotPassword(String username);

    boolean sendNewPasswordToEmail(String newPassword, String email, String username);
}
