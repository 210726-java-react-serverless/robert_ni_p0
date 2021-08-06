package com.revature.utils;

import com.revature.repositories.UserRepository;
import com.revature.screens.LoginScreen;
import com.revature.screens.RegisterScreen;
import com.revature.screens.WelcomeScreen;
import com.revature.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class AppState {

    private static AppState app = new AppState();
    private boolean running;
    private final ScreenRouter router;
    private final Logger logger = LogManager.getLogger(this.getClass());

    private AppState() {
        running = true;
        router = new ScreenRouter();

        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        UserRepository userRepository = new UserRepository();
        UserService userService = new UserService(userRepository);

        router.addScreen(new WelcomeScreen(consoleReader, router))
                .addScreen(new LoginScreen(consoleReader, router, logger, userService))
                .addScreen(new RegisterScreen(consoleReader, router, logger, userService));
    }

    public static AppState getAppState() {
        return app;
    }

    public void start() {
        router.navigate("/welcome");
        while (running) {
            try {
                router.getCurrentScreen().render();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }
}
