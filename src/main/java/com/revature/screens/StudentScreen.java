package com.revature.screens;

import com.revature.services.UserService;
import com.revature.utils.ScreenRouter;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;

public class StudentScreen extends Screen {

    private final Logger logger;
    private final UserService userService;

    public StudentScreen(BufferedReader consoleReader, ScreenRouter router, Logger logger, UserService userService) {
        super("StudentScreen", "/student", consoleReader, router);
        this.logger = logger;
        this.userService = userService;
    }

    @Override
    public void render() throws Exception {

        String menu = "\nWhat would you like to do?\n" +
                "1) View profile\n" +
                "2) Logout\n" +
                "> ";
        System.out.print(menu);

        String userSelection = consoleReader.readLine();

        switch (userSelection) {
            case "2":

                break;
            default:
                System.out.println("This is still a work in progress");
                System.out.println("Returning to welcome page");
        }

        router.navigate("/welcome");
    }
}
