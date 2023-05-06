package com.cbshop.demo.forgotpasswordcode;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ForgotPasswordCodeRepo extends JpaRepository<ForgotPasswordCode, Long> {
    Optional<ForgotPasswordCode> findByUserId(Long userId);
    Optional<ForgotPasswordCode> findByForgotPasswordCode(String forgotPasswordCode);
}