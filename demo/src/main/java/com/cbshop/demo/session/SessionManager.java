package com.cbshop.demo.session;

import com.cbshop.demo.user.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

@Component
public class SessionManager {

    public void addSessionsParam(HttpSession session, User user) {
        session.setAttribute("Id", user.getId());
        session.setAttribute("Email", user.getEmail());
        session.setAttribute("Role", user.getRole());
    }
}
