package com.supplytracker.Controller;

import com.supplytracker.Entity.User;
import com.supplytracker.Enums.Role;
import com.supplytracker.Exception.AccessDeniedCustomException;
import com.supplytracker.Exception.NotLoggedInException;
import org.springframework.stereotype.Component;

@Component
public class SessionManager {
    private User currentUser;

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public void login(User user) {
        this.currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void checkValidUser(Role... roles) {
        if (!isLoggedIn()) {
            throw new NotLoggedInException("User is not logged in");
        }

        Role currentRole = currentUser.getRole();
        for (Role allowedRole : roles) {
            if (allowedRole == currentRole) {
                return;
            }
        }

        throw new AccessDeniedCustomException("Access denied for role: " + currentRole);
    }
}
