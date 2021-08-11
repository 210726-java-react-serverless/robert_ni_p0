package com.revature.utils;

import com.revature.datasource.repositories.CourseRepository;
import com.revature.datasource.repositories.ScheduleRepository;
import com.revature.datasource.repositories.UserRepository;
import com.revature.screens.*;
import com.revature.services.CourseService;
import com.revature.services.ScheduleService;
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
        CourseRepository courseRepository = new CourseRepository();
        ScheduleRepository scheduleRepository = new ScheduleRepository();

        UserSession session = new UserSession();
        UserService userService = new UserService(userRepository, session);
        CourseService courseService = new CourseService(courseRepository, scheduleRepository);
        ScheduleService scheduleService = new ScheduleService(scheduleRepository, courseRepository);

        router.addScreen(new WelcomeScreen(consoleReader, router))
                .addScreen(new LoginScreen(consoleReader, router, logger, userService))
                .addScreen(new RegisterScreen(consoleReader, router, logger, userService))
                .addScreen(new StudentScreen(consoleReader, router, logger, userService, courseService, scheduleService))
                .addScreen(new FacultyScreen(consoleReader, router, logger, userService, courseService, scheduleService));
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

    public void stop() {
        running = false;
    }
}
