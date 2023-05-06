package com.cbshop.demo.forgotpasswordcode;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ForgotPasswordSession {
    private final Set<ForgotPasswordCode> forgotPasswordCodeList = new HashSet<>();
    private final ForgotPasswordCodeRepo forgotPasswordCodeRepo;

    @PostConstruct
    void postConstruct() {
        forgotPasswordCodeList.addAll(forgotPasswordCodeRepo.findAll());
    }

    public Set<ForgotPasswordCode> getAllCodes() {
        return forgotPasswordCodeList;
    }

    public void store(List<ForgotPasswordCode> codeList) {
        forgotPasswordCodeList.addAll(codeList);
    }
}
