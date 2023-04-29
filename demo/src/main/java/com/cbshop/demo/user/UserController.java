package com.cbshop.demo.user;

import com.cbshop.demo.utils.DataValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        if (!DataValidation.validateLoginUser(user)) {
            userService.loginUser(user);

            return ResponseEntity.ok().build();
        }
        throw new RuntimeException();
    }

    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        DataValidation.isUserValidForRegistration(user);
        User createdUser = userService.registerUser(user);
        return new ResponseEntity<>(Map.of("user", createdUser), HttpStatus.CREATED);
    }
}
