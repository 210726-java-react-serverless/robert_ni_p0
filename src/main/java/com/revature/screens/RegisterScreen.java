package com.revature.screens;

import com.revature.models.AppUser;
import com.revature.utils.ScreenRouter;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;

public class RegisterScreen extends Screen {

    public RegisterScreen(BufferedReader consoleReader, ScreenRouter router, Logger logger) {
        super("RegisterScreen", "/register", consoleReader, router, logger);
    }

    @Override
    public void render() throws Exception {
        System.out.print("First Name: ");
        String firstname = consoleReader.readLine();

        System.out.print("Last Name: ");
        String lastname = consoleReader.readLine();

        System.out.print("Email: ");
        String email = consoleReader.readLine();

        System.out.print("Username: ");
        String username = consoleReader.readLine();

        System.out.print("Password: ");
        String password = consoleReader.readLine();

        AppUser newUser = new AppUser(firstname, lastname, email, username, password);

        System.out.println(newUser);

        router.navigate("/welcome");
    }
}
