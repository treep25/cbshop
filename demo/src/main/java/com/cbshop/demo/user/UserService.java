package com.cbshop.demo.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Base64;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private final UserRepository userRepository;
    public void loginUser(User user){
        User userByEmail = userRepository.findByEmail(user.getEmail())
                .orElseThrow(RuntimeException::new);

        if(!userByEmail.getPassword()
                .equals(Arrays.toString(Base64.getDecoder().decode(user.getPassword())))){
            throw new RuntimeException();
        }
    }
}
