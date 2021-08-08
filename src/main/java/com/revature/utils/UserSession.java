package com.revature.utils;

import com.revature.models.AppUser;

public class UserSession {

    private AppUser currentUser;

    public AppUser getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(AppUser currentUser) {
        this.currentUser = currentUser;
    }

    public boolean isActive() {
        return currentUser != null;
    }

    public boolean superPowers() {
        return (currentUser.getUserType().equals("faculty"));
    }

    public void closeSession() {
        setCurrentUser(null);
    }
}
