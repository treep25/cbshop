package com.cbshop.demo.user.model;

import com.cbshop.demo.user.role.Role;
import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class UserDTO {
    private String firstName;
    private String lastName;
    private String email;
    private boolean isVerificated;
    private Role role;
}
