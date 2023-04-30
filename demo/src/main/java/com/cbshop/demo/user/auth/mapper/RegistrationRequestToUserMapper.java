package com.cbshop.demo.user.auth.mapper;

import com.cbshop.demo.user.auth.model.RegistrationRequest;
import com.cbshop.demo.user.model.User;
import org.springframework.stereotype.Component;

@Component
public class RegistrationRequestToUserMapper {

    public User getUserFromRegistrationRequest(RegistrationRequest registrationRequest) {
        return User
                .builder()
                .email(registrationRequest.getEmail())
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getLastName())
                .build();
    }
}
