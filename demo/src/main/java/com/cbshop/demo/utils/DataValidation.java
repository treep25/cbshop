package com.cbshop.demo.utils;

import com.cbshop.demo.exceptions.controlleradvice.InvalidDataException;
import com.cbshop.demo.user.auth.model.LoginRequest;
import com.cbshop.demo.user.auth.model.RegistrationRequest;
import com.cbshop.demo.user.model.User;
import org.apache.commons.lang3.StringUtils;

public class DataValidation {
    private static boolean isStringValuesCorrect(String s) {
        return s == null || StringUtils.isBlank(s) || StringUtils.isNumeric(s);
    }

    private static <T> boolean isObjectNotNull(T value) {
        return value != null;
    }

    public static void isIdValid(long id) {
        if (id <= 0) {
            throw new InvalidDataException("Invalid input id " + id);
        }
    }

    public static void isUserValidForRegistration(RegistrationRequest registrationRequest) {
        if (isObjectNotNull(registrationRequest)) {
            if (registrationRequest.getEmail() != null && isStringValuesCorrect(registrationRequest.getEmail())) {
                throw new InvalidDataException("Invalid input email: " + registrationRequest.getEmail());
            }
            if (registrationRequest.getFirstName() != null && isStringValuesCorrect(registrationRequest.getFirstName())) {
                throw new InvalidDataException("Invalid input first name: " + registrationRequest.getFirstName());
            }
            if (registrationRequest.getLastName() != null && isStringValuesCorrect(registrationRequest.getLastName())) {
                throw new InvalidDataException("Invalid input last name: " + registrationRequest.getLastName());
            }
            if (registrationRequest.getPassword() != null && isStringValuesCorrect(registrationRequest.getPassword())) {
                throw new InvalidDataException("Duration should be > 0. Your value " + registrationRequest.getPassword());
            }
            if (registrationRequest.getPasswordRepeat() != null && isStringValuesCorrect(registrationRequest.getPasswordRepeat())) {
                throw new InvalidDataException("Invalid input repeating password: " + registrationRequest.getPasswordRepeat());
            }
        } else {
            throw new InvalidDataException("Invalid data input");
        }
    }

    public static void validateLoginUser(LoginRequest loginRequest) {
        if (isObjectNotNull(loginRequest)) {
            if (loginRequest.getEmail() != null && isStringValuesCorrect(loginRequest.getEmail())) {
                throw new InvalidDataException("Invalid input email: " + loginRequest.getEmail());
            }
            if (loginRequest.getPassword() != null && isStringValuesCorrect(loginRequest.getPassword())) {
                throw new InvalidDataException("Invalid input password " + loginRequest.getPassword());
            }
        } else {
            throw new InvalidDataException("Invalid data input");
        }
    }

    public static void isVerificationTokenValid(String verificationToken) {
        if (verificationToken == null || verificationToken.isBlank()) {
            throw new InvalidDataException("Token is not valid");
        }
    }

    public static void validatePageAndSizePagination(int page, int size) {
        if (page >= 0) {
            if (size >= 1) {
                return;
            }
            throw new InvalidDataException("page size must not be less than one size = " + size);
        }
        throw new InvalidDataException("page index must not be less than zero page = " + page);
    }

    public static void validateUserForUpdating(User user){
        if(user.getFirstName() != null){
            if(isStringValuesCorrect(user.getFirstName())){
                throw new InvalidDataException("Invalid input first name");
            }
        }
        if(user.getLastName() != null){
            if(isStringValuesCorrect(user.getLastName())){
                throw new InvalidDataException("Invalid input last name");
            }
        }
    }
}
