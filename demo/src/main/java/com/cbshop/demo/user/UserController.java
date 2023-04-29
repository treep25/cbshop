package com.cbshop.demo.user;

import com.cbshop.demo.utils.UtilsValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        if (!UtilsValidation.validateLoginUser(user)) {
            userService.loginUser(user);

            return ResponseEntity.ok().build();
        }
        throw new RuntimeException();
    }
}
