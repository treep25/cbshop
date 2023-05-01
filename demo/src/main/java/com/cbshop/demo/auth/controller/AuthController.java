package com.cbshop.demo.auth.controller;

import com.cbshop.demo.auth.service.AuthService;
import com.cbshop.demo.auth.model.LoginRequest;
import com.cbshop.demo.auth.model.RegistrationRequest;
import com.cbshop.demo.utils.DataValidation;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        DataValidation.validateLoginUser(loginRequest);
        authService.loginUser(loginRequest, session);

        return ResponseEntity.ok().build();

    }

    @PostMapping("/registration")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequest registrationRequest) {
        DataValidation.isUserValidForRegistration(registrationRequest);

        return new ResponseEntity<>(authService.registerUser(registrationRequest), HttpStatus.CREATED);
    }

    @GetMapping("/verification")
    public ResponseEntity<?> verifyVerificationToken(@RequestParam("token") String verificationToken, HttpSession session) {
        DataValidation.isVerificationTokenValid(verificationToken);

        return new ResponseEntity<>(authService
                .verifyVerificationToken(verificationToken, session), HttpStatus.CREATED);
    }
}
