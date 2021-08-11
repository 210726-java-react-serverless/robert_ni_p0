package com.revature.services;

import com.revature.datasource.models.AppUser;
import com.revature.datasource.repositories.UserRepository;
import com.revature.utils.UserSession;
import com.revature.utils.exceptions.AuthenticationException;
import com.revature.utils.exceptions.InvalidRequestException;

public class UserService {

    private final UserRepository userRepository;
    private final UserSession session;

    public UserService(UserRepository userRepository, UserSession session) {
        this.userRepository = userRepository;
        this.session = session;
    }

    public UserSession getSession() {
        return session;
    }

    public AppUser register(AppUser newUser) {
        if (!isUserValid(newUser)) {
            throw new InvalidRequestException("Invalid user data provided");
        }
        return userRepository.save(newUser);
    }

    public AppUser login(String username, String password) {
        if (username == null || username.trim().equals("") || password == null || password.trim().equals("")) {
            throw new InvalidRequestException("Invalid user credentials provided");
        }

        AppUser authUser = userRepository.findUserByCredentials(username, password);

        if (authUser == null) {
            throw new AuthenticationException("Invalid credentials provided");
        }

        session.setCurrentUser(authUser);

        return authUser;
    }

    private boolean isUserValid(AppUser user) {
        if (user == null) return false;
        if (user.getFirstname() == null || user.getFirstname().trim().equals("")) return false;
        if (user.getLastname() == null || user.getLastname().trim().equals("")) return false;
        if (user.getEmail() == null || user.getEmail().trim().equals("")) return false;
        if (user.getUsername() == null || user.getUsername().trim().equals("")) return false;
        return user.getPassword() != null && !user.getPassword().trim().equals("");
    }
}
