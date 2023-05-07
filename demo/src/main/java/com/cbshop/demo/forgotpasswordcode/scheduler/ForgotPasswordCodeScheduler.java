package com.cbshop.demo.forgotpasswordcode.scheduler;

import com.cbshop.demo.forgotpasswordcode.ForgotPasswordCode;
import com.cbshop.demo.forgotpasswordcode.ForgotPasswordCodeRepo;
import com.cbshop.demo.forgotpasswordcode.ForgotPasswordSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ForgotPasswordCodeScheduler {
    private final ForgotPasswordSession forgotPasswordSession;
    private final ForgotPasswordCodeRepo repo;

    @Scheduled(initialDelay = 30, fixedRate = 60, timeUnit = TimeUnit.SECONDS)
    public void deletingExpiringTokens() {
        forgotPasswordSession.getAllCodes()
                .stream()
                .filter(ForgotPasswordCode::isValid)
                .forEach(code -> repo.deleteById(code.getId()));
        List<ForgotPasswordCode> commercialRates = repo.findAll();
        forgotPasswordSession.store(commercialRates);
    }
}
