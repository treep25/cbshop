package com.cbshop.demo.user.permitComponents;

import com.cbshop.demo.user.model.User;
import com.cbshop.demo.user.role.Role;
import com.cbshop.demo.utils.DataValidation;
import org.springframework.stereotype.Component;

@Component
public class PermissionComponent {
    public boolean hasPermission(User user, long id) {
        DataValidation.isIdValid(id);
        return id == user.getId() || user.is(Role.ADMIN);
    }
}
