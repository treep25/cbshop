package com.cbshop.demo.auth.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangeUserPasswordRequest {
    private String password;
    private String repeatPassword;
    private String forgotPasswordCode;
}
