package com.cbshop.demo.user.auth.controller;

import com.cbshop.demo.user.auth.model.LoginRequest;
import com.cbshop.demo.user.auth.model.RegistrationRequest;
import com.cbshop.demo.user.auth.service.AuthService;
import com.cbshop.demo.user.model.User;
import com.cbshop.demo.utils.DataValidation;
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
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        DataValidation.validateLoginUser(loginRequest);

        authService.loginUser(loginRequest);

        return ResponseEntity.ok().build();

    }

    @PostMapping("/registration")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequest registrationRequest) {
        DataValidation.isUserValidForRegistration(registrationRequest);

        return new ResponseEntity<>(authService.registerUser(registrationRequest), HttpStatus.CREATED);
    }

    @GetMapping("/verification")
    public ResponseEntity<?> verifyVerificationToken(@RequestParam("token") String verificationToken) {
        DataValidation.isVerificationTokenValid(verificationToken);

        return new ResponseEntity<>(authService
                .verifyVerificationToken(verificationToken), HttpStatus.CREATED);
    }
}
