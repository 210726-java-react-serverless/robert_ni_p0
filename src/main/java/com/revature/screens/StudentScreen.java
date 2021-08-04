package com.revature.screens;

import com.revature.utils.ScreenRouter;

import java.io.BufferedReader;

public class StudentScreen extends Screen {

    public StudentScreen(BufferedReader consoleReader, ScreenRouter router) {
        super("StudentScreen", "/student", consoleReader, router);
    }

    @Override
    public void render() throws Exception {
        System.out.println("This screen is under construction coming!");
        router.navigate("/welcome");
    }
}
