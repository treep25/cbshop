package com.cbshop.demo.utils;

import com.cbshop.demo.enums.Category;
import com.cbshop.demo.exceptions.controlleradvice.InvalidDataException;
import com.cbshop.demo.product.model.ProductDTO;
import com.cbshop.demo.user.auth.model.LoginRequest;
import com.cbshop.demo.user.auth.model.RegistrationRequest;
import com.cbshop.demo.user.model.UserDTO;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;

public class DataValidation {
    private static boolean isStringValuesNotCorrect(String s) {
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
            if (isStringValuesNotCorrect(registrationRequest.getEmail())) {
                throw new InvalidDataException("Invalid input email: " + registrationRequest.getEmail());
            }
            if (isStringValuesNotCorrect(registrationRequest.getFirstName())) {
                throw new InvalidDataException("Invalid input first name: " + registrationRequest.getFirstName());
            }
            if (isStringValuesNotCorrect(registrationRequest.getLastName())) {
                throw new InvalidDataException("Invalid input last name: " + registrationRequest.getLastName());
            }
            if (isStringValuesNotCorrect(registrationRequest.getPassword())) {
                throw new InvalidDataException("invalid password format. Your value " + registrationRequest.getPassword());
            }
            if (isStringValuesNotCorrect(registrationRequest.getPasswordRepeat())) {
                throw new InvalidDataException("Invalid input repeating password: " + registrationRequest.getPasswordRepeat());
            }
        } else {
            throw new InvalidDataException("Invalid data input");
        }
    }

    public static void validateLoginUser(LoginRequest loginRequest) {
        if (isObjectNotNull(loginRequest)) {
            if (loginRequest.getEmail() != null && isStringValuesNotCorrect(loginRequest.getEmail())) {
                throw new InvalidDataException("Invalid input email: " + loginRequest.getEmail());
            }
            if (loginRequest.getPassword() != null && isStringValuesNotCorrect(loginRequest.getPassword())) {
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

    public static void validateUserForUpdating(UserDTO user) {
        if (user.getFirstName() != null) {
            if (isStringValuesNotCorrect(user.getFirstName())) {
                throw new InvalidDataException("Invalid input first name");
            }
        }
        if (user.getLastName() != null) {
            if (isStringValuesNotCorrect(user.getLastName())) {
                throw new InvalidDataException("Invalid input last name");
            }
        }
    }

    public static void isProductValidForCreate(ProductDTO productDTO) {
        if (productDTO != null) {
            if (isStringValuesNotCorrect(productDTO.getName())) {
                throw new InvalidDataException("Invalid input name");
            }
            if (isStringValuesNotCorrect(productDTO.getDescription())) {
                throw new InvalidDataException("Invalid input description");
            }
            if (!EnumUtils.isValidEnum(Category.class, productDTO.getCategory().toString())) {
                throw new InvalidDataException("Invalid input category");
            }
            if (isPriceValid(productDTO.getPrice())) {
                throw new InvalidDataException("Invalid input price");
            }
        }
        throw new InvalidDataException("Invalid input");
    }

    public static void isProductValidForUpdate(ProductDTO productDTO) {
        if (productDTO != null) {
            if (productDTO.getName() != null && isStringValuesNotCorrect(productDTO.getName())) {
                throw new InvalidDataException("Invalid input name");
            }
            if (productDTO.getDescription() != null &&isStringValuesNotCorrect(productDTO.getDescription())) {
                throw new InvalidDataException("Invalid input description");
            }
            if (productDTO.getCategory() != null && !EnumUtils.isValidEnum(Category.class, productDTO.getCategory().toString())) {
                throw new InvalidDataException("Invalid input category");
            }
            if (productDTO.getPrice() != null && productDTO.getPrice() < 1) {
                throw new InvalidDataException("Invalid input price");
            }
        }
        throw new InvalidDataException("Invalid input");
    }

    private static boolean isPriceValid(Integer price) {
        return price == null || price < 1;
    }
}
