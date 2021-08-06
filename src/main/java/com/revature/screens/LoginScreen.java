package com.revature.screens;

import com.revature.models.AppUser;
import com.revature.services.UserService;
import com.revature.utils.ScreenRouter;
import com.revature.utils.exceptions.AuthenticationException;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;

public class LoginScreen extends Screen {

    private final UserService userService;
    public LoginScreen(BufferedReader consoleReader, ScreenRouter router, Logger logger, UserService userService) {
        super("LoginScreen", "/login", consoleReader, router, logger);
        this.userService = userService;
    }

    @Override
    public void render() throws Exception {
        System.out.print("Username: ");
        String username = consoleReader.readLine();

        System.out.print("Password: ");
        String password = consoleReader.readLine();

        try {
            AppUser authUser = userService.login(username, password);
            System.out.println("Logged in as: " + authUser.getUsername());
            router.navigate("/student");

        } catch (AuthenticationException ae) {
            System.out.println("No user found with provided credentials");
            System.out.println("Navigating back to welcome screen...");
            router.navigate("/welcome");
        }
    }
}
