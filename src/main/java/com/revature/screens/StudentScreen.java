package com.revature.screens;

import com.revature.services.CourseService;
import com.revature.services.ScheduleService;
import com.revature.services.UserService;
import com.revature.utils.ScreenRouter;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;

public class StudentScreen extends Screen {

    private final Logger logger;
    private final UserService userService;
    private final CourseService courseService;
    private final ScheduleService scheduleService;

    public StudentScreen(BufferedReader consoleReader, ScreenRouter router, Logger logger, UserService userService, CourseService courseService, ScheduleService scheduleService) {
        super("StudentScreen", "/student", consoleReader, router);
        this.logger = logger;
        this.userService = userService;
        this.courseService = courseService;
        this.scheduleService = scheduleService;
    }

    @Override
    public void render() throws Exception {
        String menu = "\nSTUDENT DASHBOARD\n" +
                "1) View all available courses\n" +
                "2) View my courses\n" +
                "3) Register for a course\n" +
                "4) Un-enroll from a course\n" +
                "5) Logout\n" +
                "> ";

        System.out.print(menu);

        String userSelection = consoleReader.readLine();

        switch (userSelection) {
            case "1":
                scheduleService.findOpenCourses();
                break;
            case "2":
                System.out.println("\nSCHEDULE\n");
                scheduleService.getSchedule(userService.getSession().getCurrentUser().getUsername());
                break;
            case "3":
                System.out.print("Enter the id of the course to register: ");
                scheduleService.register(userService.getSession().getCurrentUser().getUsername(), consoleReader.readLine());
                break;
            case "4":
                System.out.print("Enter the id of the course to unregister: ");
                scheduleService.deleteSchedule(userService.getSession().getCurrentUser().getUsername(), consoleReader.readLine());
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
