package com.revature.screens;

import com.revature.models.Course;
import com.revature.services.CourseService;
import com.revature.services.UserService;
import com.revature.utils.ScreenRouter;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;

public class FacultyScreen extends Screen {

    private final Logger logger;
    private final UserService userService;
    private final CourseService courseService;

    public FacultyScreen(BufferedReader consoleReader, ScreenRouter router, Logger logger, UserService userService, CourseService courseService) {
        super("FacultyScreen", "/faculty", consoleReader, router);
        this.logger = logger;
        this.userService = userService;
        this.courseService = courseService;
    }

    @Override
    public void render() throws Exception {
        String menu = "\nFaculty Dashboard\n" +
                "1) Add new course\n" +
                "2) View courses\n" +
                "3) Logout\n" +
                "> ";

        System.out.print(menu);
        String userSelection = consoleReader.readLine();

        switch (userSelection) {
            case "1":
                System.out.print("Course ID: ");
                String courseId = consoleReader.readLine();

                System.out.print("Course Name: ");
                String courseName = consoleReader.readLine();

                System.out.print("Course description: ");
                String courseDesc = consoleReader.readLine();

                System.out.print("Is this course open for registration? (Y/N): ");
                String regOpen = consoleReader.readLine();

                Course newCourse = new Course(courseId, courseName, courseDesc, regOpen);

                try {
                    courseService.addCourse(newCourse);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case "3":
                userService.getSession().closeSession();
                router.navigate("/welcome");
                break;
            default:
                System.out.println("This is still a work in progress!");
        }
    }
}
