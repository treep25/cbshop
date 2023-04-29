package com.cbshop.demo.utils;

import com.cbshop.demo.user.User;
import org.apache.commons.lang3.StringUtils;

public class UtilsValidation {

    public static boolean validateLoginUser(User user) {
        return !StringUtils.isEmpty(user.getEmail())
               && !StringUtils.isEmpty(user.getPassword());
    }
}
