package com.revature.screens;

import com.revature.utils.ScreenRouter;

import java.io.BufferedReader;

public class WelcomeScreen extends Screen {

    public WelcomeScreen(BufferedReader consoleReader, ScreenRouter router) {
        super("WelcomeScreen", "/welcome", consoleReader, router);
        System.out.println("Welcome to the Student Management App");
    }

    @Override
    public void render() throws Exception {
        String menu = "\nWhat would you like to do?\n" +
                "1) Login\n" +
                "2) Register\n" +
                "3) Exit Application\n" +
                "> ";

        System.out.print(menu);

        String userSelection = consoleReader.readLine();
        userSelection = userSelection.toLowerCase();

        switch (userSelection) {
            case "1":
            case "login":
                router.navigate("/login");
                break;
            case "2":
            case "register":
                System.out.println("Register selected");
                break;
            case "3":
            case "exit":
                System.out.println("Exit selected");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid entry selected");
        }
    }
}
