package hcmute.kltn.vtv.service.user.impl;

import hcmute.kltn.vtv.util.exception.BadRequestException;
import hcmute.kltn.vtv.model.data.user.response.ForgotPasswordResponse;
import hcmute.kltn.vtv.model.dto.user.MailDTO;
import hcmute.kltn.vtv.model.entity.user.Customer;
import hcmute.kltn.vtv.service.user.ICustomerService;
import hcmute.kltn.vtv.service.user.IMailService;
import hcmute.kltn.vtv.service.user.IOtpService;
import hcmute.kltn.vtv.util.exception.InternalServerErrorException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MailServiceImpl implements IMailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private IOtpService otpService;

    private void sendEmail(MailDTO mail) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8"); // Specify UTF-8 encoding
            mimeMessageHelper.setSubject(mail.getMailSubject());
            mimeMessageHelper.setFrom(new InternetAddress(mail.getMailFrom()));
            mimeMessageHelper.setText(mail.getMailContent(), true); // Set text content with HTML support
            mimeMessageHelper.setTo(mail.getMailTo());
            mimeMessageHelper.setSentDate(new Date());
            mimeMessageHelper.setValidateAddresses(true);
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException e) {
            throw new BadRequestException("Gửi email thất bại. " + e.getMessage());
        }
    }

    @Override
    public ForgotPasswordResponse sendMailForgotPassword(String username) {
        Customer customer = customerService.getCustomerByUsername(username);
        String otp = otpService.generateOtp(username);

        MailDTO mail = new MailDTO();
        mail.setMailFrom("conc5288@gmail.com");
        mail.setMailTo(customer.getEmail());
        mail.setMailSubject("Lấy lại mật khẩu trên hệ thống VTV.");

        // Styling for the email content
        StringBuilder mailContent = new StringBuilder();
        mailContent.append("<div style=\"background-color: #f0f0f0; padding: 20px; border-radius: 10px; margin: 20px;\">");
        mailContent.append("<h2 style=\"color: #333333;\">Xin chào ").append(customer.getUsername()).append("!</h2>");
        mailContent.append("<p style=\"color: #555555;\">Bạn đã yêu cầu lấy lại mật khẩu trên hệ thống VTV. Dưới đây là mã OTP của bạn:</p>");

        // Styling for OTP digits
        mailContent.append("<div style=\"display: flex; justify-content: center;\">");
        for (char digit : otp.toCharArray()) {
            mailContent.append("<span style=\"background-color: #4CAF50; color: white; font-weight: bold; padding: 10px; margin: 5px; border-radius: 5px;\">").append(digit).append("</span>");
        }
        mailContent.append("</div>");

        mailContent.append("<p style=\"color: #555555;\">Mã OTP có hiệu lực trong vòng 5 phút.</p>");
        mailContent.append("</div>");

        mail.setMailContent(mailContent.toString());

        try {
            sendEmail(mail);
        } catch (BadRequestException e) {
            throw new InternalServerErrorException("Có lỗi xảy ra khi gửi email: " + e.getMessage());
        }

        ForgotPasswordResponse response = new ForgotPasswordResponse();
        response.setEmail(customer.getEmail());
        response.setUsername(customer.getUsername());
        response.setTimeValid(otpService.getTimeValid(username));
        response.setMessage("Thông báo: Gửi mã OTP lấy lại mật khẩu về email: "
                + customer.getEmail()
                + " của tài khoản "
                + customer.getUsername()
                + " thành công. Mã OTP có hiệu lực trong vòng 5 phút.");
        response.setStatus("Success");
        response.setCode(200);

        return response;
    }


    @Override
    public boolean sendNewPasswordToEmail(String newPassword, String email, String username) {
        MailDTO mail = new MailDTO();
        mail.setMailFrom("conc5288@gmail.com");
        mail.setMailTo(email);
        mail.setMailSubject("Thông báo mật khẩu mới của tài khoản "
                + username
                + " trên hệ thống sàn bách hóa VTV.");
        mail.setMailContent("Mật khẩu mới của bạn là: " + newPassword);

        try {
            sendEmail(mail);
        } catch (BadRequestException e) {
            throw new InternalServerErrorException("Có lỗi xảy ra khi gửi email: " + e.getMessage());
        }
        return true;
    }

}
