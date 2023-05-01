package com.cbshop.demo.user.controller;

import com.cbshop.demo.user.model.User;
import com.cbshop.demo.user.model.UserDTO;
import com.cbshop.demo.user.service.UserService;
import com.cbshop.demo.user.userMapper.UserMapper;
import com.cbshop.demo.utils.DataValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<?> read(@RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "10") int size) {
        DataValidation.validatePageAndSizePagination(page, size);

        Page<UserDTO> users = userMapper
                .getUserDTOListFromUserList(userService
                        .read(PageRequest.of(page, size)));

        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> readById(@PathVariable long id) {
        DataValidation.isIdValid(id);

        UserDTO userDTO = userMapper
                .getUserDTOFromUser(userService.readById(id));

        return ResponseEntity.ok(userDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        DataValidation.isIdValid(id);

        userService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody User userForUpdating) {
        DataValidation.isIdValid(id);
        DataValidation.validateUserForUpdating(userForUpdating);

        UserDTO updatedUser = userMapper
                .getUserDTOFromUser(userService.update(id, userForUpdating));

        return ResponseEntity.ok(updatedUser);
    }

}
