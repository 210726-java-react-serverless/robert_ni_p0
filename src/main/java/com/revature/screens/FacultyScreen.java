package com.revature.screens;

import com.revature.services.UserService;
import com.revature.utils.ScreenRouter;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;

public class FacultyScreen extends Screen {

    private final Logger logger;
    private final UserService userService;

    public FacultyScreen(BufferedReader consoleReader, ScreenRouter router, Logger logger, UserService userService) {
        super("FacultyScreen", "/faculty", consoleReader, router);
        this.logger = logger;
        this.userService = userService;
    }

    @Override
    public void render() throws Exception {
        System.out.println("This screen is under construction coming!");
        router.navigate("/welcome");
    }
}
