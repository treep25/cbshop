package com.cbshop.demo.utils;

import com.cbshop.demo.exceptions.controlleradvice.InvalidDataException;
import com.cbshop.demo.user.User;
import org.apache.commons.lang3.StringUtils;

public class DataValidation {
    private static boolean isStringValuesCorrect(String s) {
        return s == null || StringUtils.isBlank(s) || StringUtils.isNumeric(s);
    }

    public static void isUserValidForRegistration(User user) {
        if (user != null) {
            if (user.getEmail() != null && isStringValuesCorrect(user.getEmail())) {
                throw new InvalidDataException("Invalid input name: " + user.getEmail());
            }
            if (user.getFirstName() != null && isStringValuesCorrect(user.getFirstName())) {
                throw new InvalidDataException("Invalid input description: " + user.getFirstName());
            }
            if (user.getLastName() != null && isStringValuesCorrect(user.getLastName())) {
                throw new InvalidDataException("Price should be > 0. Your value " + user.getLastName());
            }
            if (user.getPassword() != null && isStringValuesCorrect(user.getPassword())) {
                throw new InvalidDataException("Duration should be > 0. Your value " + user.getPassword());
            }
        }
        throw new InvalidDataException("Invalid data input");
    }

    public static boolean validateLoginUser(User user) {
        return !StringUtils.isEmpty(user.getEmail())
               && !StringUtils.isEmpty(user.getPassword());
    }
}
