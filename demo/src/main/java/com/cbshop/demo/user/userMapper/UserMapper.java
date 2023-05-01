package com.cbshop.demo.user.userMapper;

import com.cbshop.demo.user.model.User;
import com.cbshop.demo.user.model.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {

    public Page<UserDTO> getUserDTOListFromUserList(Page<User> users) {
        List<UserDTO> userDTOs = new ArrayList<>();
        users.forEach(user -> {
            userDTOs.add(
                    getUserDTOFromUser(user)
            );
        });
        return new PageImpl<>(userDTOs, users.getPageable(), userDTOs.size());
    }

    public UserDTO getUserDTOFromUser(User user) {
        return UserDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole())
                .email(user.getEmail())
                .isVerificated(user.isVerificated())
                .build();
    }

}
