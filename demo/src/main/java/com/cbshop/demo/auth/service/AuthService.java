package com.cbshop.demo.auth.service;

import com.cbshop.demo.auth.mailsender.MailSenderService;
import com.cbshop.demo.auth.mapper.RegistrationRequestToUserMapper;
import com.cbshop.demo.auth.model.ChangeUserPasswordRequest;
import com.cbshop.demo.auth.model.LoginRequest;
import com.cbshop.demo.auth.model.RegistrationRequest;
import com.cbshop.demo.exceptions.InvalidDataException;
import com.cbshop.demo.exceptions.InvalidUserCredentialsException;
import com.cbshop.demo.exceptions.ItemNotFoundException;
import com.cbshop.demo.exceptions.ServerError;
import com.cbshop.demo.forgotpasswordcode.ForgotPasswordCode;
import com.cbshop.demo.forgotpasswordcode.ForgotPasswordCodeRepo;
import com.cbshop.demo.session.SessionManager;
import com.cbshop.demo.token.model.VerificationToken;
import com.cbshop.demo.token.repository.VerificationTokenRepository;
import com.cbshop.demo.user.model.User;
import com.cbshop.demo.user.repository.UserRepository;
import com.cbshop.demo.user.role.Role;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
@EnableTransactionManagement
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthService {

    private final UserRepository userRepository;
    private final RegistrationRequestToUserMapper registrationRequestToUserMapper;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository verificationTokenRepository;
    private final ForgotPasswordCodeRepo forgotPasswordCodeRepo;
    private final SessionManager sessionManager;
    private final MailSenderService mailSenderService;
    public static final boolean VERIFIED = true;
    public static final boolean NON_VERIFIED = false;

    private void managerAuthentication(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException ex) {
            throw new AccessDeniedException("incorrect login or password");
        }
    }

    @Transactional
    public void forgotPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidUserCredentialsException("User with such email is not exists"));
        if (user.getPassword() != null && user.isVerified()) {
            ForgotPasswordCode forgotPasswordCode = ForgotPasswordCode.builder()
                    .forgotPasswordCode(RandomStringUtils.randomAlphanumeric(50))
                    .user(user)
                    .build();
            checkIfVerificationCodeAlreadyExists(user);
            forgotPasswordCodeRepo.save(forgotPasswordCode);
            mailSenderService.sendForgotPasswordVerificationCodeToEmail(user, forgotPasswordCode.getForgotPasswordCode());
        } else {
            throw new AccessDeniedException("You were registered with google or your account is not verified");
        }
    }

    @Transactional
    public void resetPassword(ChangeUserPasswordRequest request) {
        ForgotPasswordCode forgotPasswordCode = forgotPasswordCodeRepo.findByForgotPasswordCode(request.getForgotPasswordCode())
                .orElseThrow(() -> new ItemNotFoundException("Invalid verificationCode."));
        if (forgotPasswordCode.isValid()) {
            User user = forgotPasswordCode.getUser();
            forgotPasswordCodeRepo.deleteById(forgotPasswordCode.getId());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            userRepository.save(user);
        } else {
            throw new InvalidDataException("Token already expired");
        }
    }

    private void checkIfVerificationCodeAlreadyExists(User user) {
        Optional<ForgotPasswordCode> verificationCodeFromDB = forgotPasswordCodeRepo.findByUserId(user.getId());
        verificationCodeFromDB.ifPresent(forgotPasswordCode -> forgotPasswordCodeRepo.deleteById(forgotPasswordCode.getId()));
    }

    public void loginUser(LoginRequest loginRequest, HttpSession session) {
        User userByEmail = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new ItemNotFoundException("There are no account with this email"));

        if (!userByEmail.isVerified()) {
            throw new ServerError("Verify your account");
        }

        managerAuthentication(loginRequest);

        sessionManager.addSessionsParam(session, userByEmail);
    }

    private void generateVerificationTokenAndSend(User user) {

        VerificationToken verificationToken = VerificationToken
                .builder()
                .token(RandomStringUtils.randomAlphanumeric(133))
                .user(user)
                .build();

        verificationTokenRepository.save(verificationToken);

        mailSenderService.sendEmailConfirmation(user, verificationToken);
    }

    @Transactional
    public Map<String, ?> registerUser(RegistrationRequest registrationRequest) {
        if (!userRepository.existsByEmail(registrationRequest.getEmail())) {
            if (registrationRequest.getPassword()
                    .equals(registrationRequest.getPasswordRepeat())) {

                User newUser = registrationRequestToUserMapper
                        .getUserFromRegistrationRequest(registrationRequest);
                newUser.setRole(Role.USER);
                newUser.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
                newUser.setVerified(NON_VERIFIED);

                userRepository
                        .save(newUser);

                generateVerificationTokenAndSend(newUser);
                return Map.of("message", "Verify email due to complete verification");
            }
            throw new ServerError("Two passwords are not similar");

        } else {
            VerificationToken verificationToken = verificationTokenRepository
                    .findByUser_Email(registrationRequest.getEmail())
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

    public Map<String, ?> verifyVerificationToken(String token, HttpSession session) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new ItemNotFoundException("Invalid token"));

        if (verifyTokenConfirmation(verificationToken)) {
            User user = verificationToken.getUser();

            user.setVerified(VERIFIED);

            userRepository.save(user);

            sessionManager.addSessionsParam(session, user);
            verificationTokenRepository.delete(verificationToken);

            return Map.of("message", "Successfully completed");
        }
        throw new ServerError("Invalid token");
    }
}
