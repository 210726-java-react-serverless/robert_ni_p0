package com.revature.services;

import com.revature.datasource.models.AppUser;
import com.revature.datasource.repositories.UserRepository;
import com.revature.utils.exceptions.AuthenticationException;
import com.revature.utils.exceptions.InvalidRequestException;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    /**
     * Takes a non-null AppUser, validates its fields, and attempts to persist it to the datasource.
     *
     * @param newUser
     * @return
     */
    public AppUser register(AppUser newUser) {
        if (!isUserValid(newUser)) {
            throw new InvalidRequestException("Invalid user data provided");
        }
        return userRepository.save(newUser);
    }

    /**
     * Takes non-null Strings, validates its fields, and attempts to find an AppUser associated with the credentials
     * from the datasource
     *
     * @param username
     * @param password
     * @return
     */
    public AppUser login(String username, String password) {
        if (username == null || username.trim().equals("") || password == null || password.trim().equals("")) {
            throw new InvalidRequestException("Invalid user credentials provided");
        }

        AppUser authUser = userRepository.findUserByCredentials(username, password);

        if (authUser == null) {
            throw new AuthenticationException("Invalid credentials provided");
        }

        return authUser;
    }

    /**
     * Takes a non-null AppUser and validates its fields by checking for
     * empty Strings and whitespaces
     *
     * @param user
     * @return
     */
    private boolean isUserValid(AppUser user) {
        if (user == null) return false;
        if (user.getFirstname() == null || user.getFirstname().trim().equals("")) return false;
        if (user.getLastname() == null || user.getLastname().trim().equals("")) return false;
        if (user.getUsername() == null || user.getUsername().trim().equals("")) return false;
        return user.getPassword() != null && !user.getPassword().trim().equals("");
    }
}
