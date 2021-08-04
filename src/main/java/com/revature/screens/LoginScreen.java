package com.revature.screens;

import com.revature.utils.ScreenRouter;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;

public class LoginScreen extends Screen {

    public LoginScreen(BufferedReader consoleReader, ScreenRouter router, Logger logger) {
        super("LoginScreen", "/login", consoleReader, router, logger);
    }

    @Override
    public void render() throws Exception {
        System.out.print("Username: ");
        String username = consoleReader.readLine();

        System.out.print("Password: ");
        String password = consoleReader.readLine();

        System.out.println("Your username is: " + username);
        System.out.println("Your password is: " + password);

        router.navigate("/welcome");
    }
}
