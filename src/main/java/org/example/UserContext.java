package org.example;


import org.example.entity.User;

public class UserContext {
    private static UserContext instance;
    private User currentUser;

    private UserContext() {
        // Private constructor to prevent instantiation
    }

    public static UserContext getInstance() {
        if (instance == null) {
            instance = new UserContext();
        }
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
}