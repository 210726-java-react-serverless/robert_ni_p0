package com.revature.screens;

import com.revature.utils.ScreenRouter;

import java.io.BufferedReader;

public class FacultyScreen extends Screen {

    public FacultyScreen(BufferedReader consoleReader, ScreenRouter router) {
        super("FacultyScreen", "/faculty", consoleReader, router);
    }

    @Override
    public void render() throws Exception {
        System.out.println("This screen is under construction coming!");
        router.navigate("/welcome");
    }
}
