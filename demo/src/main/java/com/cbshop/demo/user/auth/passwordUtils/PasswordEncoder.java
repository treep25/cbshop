package com.cbshop.demo.user.auth.passwordUtils;


import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class PasswordEncoder {

    private static final String salt = "akjenfwiJDWHOCFNOIAWJNXFDII44388kjdfndnj213123";
    public String decode(String password) {
        return Base64.encodeBase64String((password +salt).getBytes(StandardCharsets.UTF_8));
    }

    public boolean comparePasswords(String password1, String password2) {
        return password1
                .equals(decode(password2));
    }
}
