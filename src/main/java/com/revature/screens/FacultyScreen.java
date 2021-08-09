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
                "1) Add new course\n" +
                "2) Delete course by id\n" +
                "3) Logout\n" +
                "> ";

        System.out.print(menu);
        String userSelection = consoleReader.readLine();

        String courseId;
        String courseName;
        String courseDesc;
        String regOpen;
        switch (userSelection) {
            case "1":
                System.out.print("Course ID: ");
                courseId = consoleReader.readLine();

                System.out.print("Course Name: ");
                courseName = consoleReader.readLine();

                System.out.print("Course description: ");
                courseDesc = consoleReader.readLine();

                System.out.print("Is this course open for registration? (Y/N): ");
                regOpen = consoleReader.readLine();

                Course newCourse = new Course(courseId, courseName, courseDesc, regOpen);

                try {
                    newCourse = courseService.addCourse(newCourse);

                    if (newCourse == null) {
                        System.out.println("The course was not added");
                    } else {
                        System.out.println("\"" + newCourse.getCourseName() + "\" successfully added!");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "2":
                System.out.print("What is the id of the course you'd like to delete?\n> ");
                courseId = consoleReader.readLine();

                try {
                    boolean result = courseService.deleteCourse(courseId);
                    if (result) {
                        System.out.println("The course has been successfully removed");
                    } else {
                        System.out.println("There was no course with that id found");
                    }
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
