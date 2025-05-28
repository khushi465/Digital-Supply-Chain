package com.supplytracker.Controller;

import com.supplytracker.Entity.User;
import com.supplytracker.Enums.Role;
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
            throw new RuntimeException("Not logged in ");
        }
        Role currentRole = currentUser.getRole();
        for (Role role1 : roles) {
            if (currentRole == role1) {
                return;
//                returns control back to the calling function
            }
        }
        throw new RuntimeException("Access denied to role " + currentRole);
    }
}
