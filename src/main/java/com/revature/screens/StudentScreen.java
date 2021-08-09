package com.revature.screens;

import com.revature.services.CourseService;
import com.revature.services.UserService;
import com.revature.utils.ScreenRouter;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;

public class StudentScreen extends Screen {

    private final Logger logger;
    private final UserService userService;
    private final CourseService courseService;

    public StudentScreen(BufferedReader consoleReader, ScreenRouter router, Logger logger, UserService userService, CourseService courseService) {
        super("StudentScreen", "/student", consoleReader, router);
        this.logger = logger;
        this.userService = userService;
        this.courseService = courseService;
    }

    @Override
    public void render() throws Exception {
        String menu = "\nStudent Dashboard\n" +
                "1) View available courses\n" +
                "2) Register for a course\n" +
                "3) Cancel course registration\n" +
                "4) Logout\n" +
                "> ";

        System.out.print(menu);

        String userSelection = consoleReader.readLine();

        switch (userSelection) {
            case "1":
                courseService.findOpenCourses();
                break;
            case "2":
                System.out.println("Register for a course");
                break;
            case "3":
                System.out.println("Cancel a course registration");
                break;
            case "4":
                userService.getSession().closeSession();
                router.navigate("/welcome");
                break;
            default:
                System.out.println("This is still a work in progress");
        }
    }
}
