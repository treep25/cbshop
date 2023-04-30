package com.cbshop.demo.user.auth.passwordUtils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


    public String decode(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    public boolean comparePasswords(String password1, String password2) {
        // TODO: 30.04.2023
        return password1
                .equals(decode(password2));
    }
}
