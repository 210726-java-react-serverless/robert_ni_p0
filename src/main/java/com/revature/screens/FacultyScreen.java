package com.revature.screens;

import com.revature.datasource.models.Course;
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
                "1) View courses\n" +
                "2) Add a course\n" +
                "3) Update a course\n" +
                "4) Remove a course\n" +
                "5) Logout\n" +
                "> ";

        System.out.print(menu);

        String userSelection = consoleReader.readLine();

        String courseId;
        String courseName;
        String courseDesc;
        String registerOpen;

        switch (userSelection) {
            case "1":
                courseService.getAllCourses();
                break;
            case "2":
                System.out.print("Enter a course id > ");
                courseId = consoleReader.readLine();

                System.out.print("Enter a course name > ");
                courseName = consoleReader.readLine();

                System.out.print("Enter a description > ");
                courseDesc = consoleReader.readLine();

                System.out.print("Can students register for this class? (Y/N) > ");
                registerOpen = consoleReader.readLine();

                courseService.addCourse(new Course(courseId, courseName, courseDesc, registerOpen));
                break;
            case "3":
                System.out.println("Update a course");
                break;
            case "4":
                System.out.print("Enter the course id of the course you wish to delete > ");
                courseId = consoleReader.readLine();
                courseService.deleteCourse(courseId);
                break;
            case "5":
                userService.getSession().closeSession();
                router.navigate("/welcome");
                break;
            default:
                System.out.println("This is still a work in progress");
        }
    }
}
