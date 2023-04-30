package com.cbshop.demo.user.auth.service;

import com.cbshop.demo.exceptions.controlleradvice.ItemNotFoundException;
import com.cbshop.demo.exceptions.controlleradvice.ServerError;
import com.cbshop.demo.token.model.VerificationToken;
import com.cbshop.demo.token.repository.VerificationTokenRepository;
import com.cbshop.demo.token.service.VerificationTokenService;
import com.cbshop.demo.user.auth.mapper.RegistrationRequestToUserMapper;
import com.cbshop.demo.user.auth.model.LoginRequest;
import com.cbshop.demo.user.auth.model.RegistrationRequest;
import com.cbshop.demo.user.auth.passwordUtils.PasswordEncoder;
import com.cbshop.demo.user.model.User;
import com.cbshop.demo.user.repository.UserRepository;
import com.cbshop.demo.user.role.Role;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthService {

    private final UserRepository userRepository;
    private final RegistrationRequestToUserMapper registrationRequestToUserMapper;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository verificationTokenRepository;
    private final VerificationTokenService verificationTokenService;
    public static final boolean VERIFICATED = true;
    public static final boolean NOT_VERIFICATED = false;


    public void loginUser(LoginRequest loginRequest) {
        User userByEmail = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new ItemNotFoundException("There are no account with this email"));

        if (passwordEncoder
                .comparePasswords(userByEmail.getPassword(), loginRequest.getPassword())) {
            if (!userByEmail.isVerificated()) {
                throw new ServerError("Verify your account");
            }
            //todo session
        } else {
            throw new ServerError("Your password is incorrect");
        }
    }

    private void sendEmailConfirmation(User user, VerificationToken verificationToken) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());

        mailMessage.setSubject("Complete registration cbshop");
        mailMessage.setText("Verification code is " + verificationToken.getToken());

        verificationTokenService.sendEmail(mailMessage);
    }

    private void generateVerificationTokenAndSend(User user) {

        VerificationToken verificationToken = VerificationToken
                .builder()
                .token(RandomStringUtils.randomAlphanumeric(133))
                .user(user)
                .build();

        verificationTokenRepository.save(verificationToken);

        sendEmailConfirmation(user, verificationToken);
    }

    public Map<String, ?> registerUser(RegistrationRequest registrationRequest) {
        if (!userRepository.existsByEmail(registrationRequest.getEmail())) {
            if (registrationRequest.getPassword()
                    .equals(registrationRequest.getPasswordRepeat())) {

                User newUser = registrationRequestToUserMapper
                        .getUserFromRegistrationRequest(registrationRequest);
                newUser.setRole(Role.USER);
                newUser.setPassword(passwordEncoder.decode(registrationRequest.getPassword()));
                newUser.setVerificated(NOT_VERIFICATED);

                userRepository
                        .save(newUser);

                generateVerificationTokenAndSend(newUser);
                return Map.of("message", "Verify email due to complete verification");
            }
            throw new ServerError("Two passwords are not similar");

        } else {
            VerificationToken verificationToken = verificationTokenRepository.findByUser_Email(registrationRequest.getEmail())
                    .orElseThrow(() -> new ServerError("User with such email already exists:" + registrationRequest.getEmail()));

            if (!verificationToken.isValid()) {
                verificationTokenRepository.delete(verificationToken);
                generateVerificationTokenAndSend(verificationToken.getUser());

                return Map.of("message", "check your email to complete verification");
            }
            return Map.of("message", "Your confirmation token is waiting for you");
        }
    }

    private boolean verifyTokenConfirmation(VerificationToken token) {
        return token.isValid();
    }

    public Map<String, ?> verifyVerificationToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new ItemNotFoundException("Invalid token"));

        if (verifyTokenConfirmation(verificationToken)) {
            User user = verificationToken.getUser();
            //todo when session

            user.setVerificated(VERIFICATED);

            userRepository.save(user);
            verificationTokenRepository.delete(verificationToken);

            return Map.of("message", "Successfully completed");
        }
        throw new ServerError("Invalid token");
    }
}
