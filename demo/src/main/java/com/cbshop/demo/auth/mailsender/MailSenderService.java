package com.cbshop.demo.auth.mailsender;

import com.cbshop.demo.token.model.VerificationToken;
import com.cbshop.demo.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSenderService {
    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String emailFrom;

    public void sendForgotPasswordVerificationCodeToEmail(User user, String verificationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailFrom);
        message.setTo(user.getEmail());
        message.setText(getForgotPasswordMailMessage(verificationCode, user));
        message.setSubject("Reset Password Code");
        mailSender.send(message);
    }

    public void sendEmailConfirmation(User user, VerificationToken verificationToken) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());

        mailMessage.setSubject("Complete registration cbshop");
        mailMessage.setText("Verification code is " + verificationToken.getToken());

        mailSender.send(mailMessage);
    }

    private String getForgotPasswordMailMessage(String verificationCode, User user) {
        return new StringBuilder()
                .append("Dear").append(user.getFirstName())
                .append(" ").append(user.getLastName()).append(",").append(System.getProperty("line.separator"))
                .append("Here is code to reset and change your password:").append(System.getProperty("line.separator"))
                .append(verificationCode).append(System.getProperty("line.separator"))
                .append("Code expire in 30 minutes.").append(System.getProperty("line.separator"))
                .append("Thank you,").append(System.getProperty("line.separator"))
                .append("CB_shop entertainment")
                .toString();
    }
}
