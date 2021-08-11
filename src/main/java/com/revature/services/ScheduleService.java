package com.revature.services;

import com.revature.datasource.models.Course;
import com.revature.datasource.models.Schedule;
import com.revature.datasource.repositories.CourseRepository;
import com.revature.datasource.repositories.ScheduleRepository;
import com.revature.utils.exceptions.DataSourceException;
import com.revature.utils.exceptions.InvalidRequestException;

import java.util.List;

public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final CourseRepository courseRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, CourseRepository courseRepository) {
        this.scheduleRepository = scheduleRepository;
        this.courseRepository = courseRepository;
    }

    /**
     * Takes in non-null Strings, validates its fields, and attempts to persist it to the datasource.
     *
     * @param username
     * @param id
     * @return
     */
    public Schedule register(String username, String id) {
        if (username == null || username.trim().equals("") || id == null || id.trim().equals("")) {
            throw new InvalidRequestException("Invalid course id provided");
        }

        Course course = courseRepository.findById(id);
        if (course == null || course.getRegisterOpen().equals("No")) {
            return null;
        }

        Schedule schedule = scheduleRepository.findByUser(username, id);

        if (schedule != null) {
            return null;
        }

        return scheduleRepository.save(new Schedule(username, course.getCourseId(), course.getCourseName(), course.getCourseDesc()));
    }

    /**
     * Takes in non-null Strings and attempts to delete it from the datasource
     *
     * @param username
     * @param id
     * @return
     */
    public boolean deleteSchedule(String username, String id) {
        return scheduleRepository.deleteSchedule(username, id);
    }

    /**
     * Takes in a non-null String username and attempts to find the schedule associated
     * with that user
     *
     * @param username
     */
    public void getSchedule(String username) {
        if (username == null || username.trim().equals("")) return;

        List<Schedule> schedules;

        try {
            schedules = scheduleRepository.getAllSchedules();
            for (Schedule schedule : schedules) {
                if (schedule.getUsername().equals(username)) {
                    System.out.print("ID: " + schedule.getCourseId() +
                            " | Course: " + schedule.getCourseName() +
                            " | Description: " + schedule.getCourseDesc() +
                            "\n------------------------------------------------------------\n");
                }
            }
        } catch (Exception e) {
            throw new DataSourceException("An unexpected error occurred", e);
        }
    }

    /**
     * Finds all the courses in the datasource and displays it
     */
    public void getAllCourses() {
        List<Course> courses = courseRepository.findAllCourses();
        if (courses == null) return;
        for (Course course : courses) {
            displayCourse(course);
            System.out.print("Registration available? " + course.getRegisterOpen() +
                    "\n------------------------------------------------------------\n"
            );
        }
    }

    /**
     * Finds all courses that students can register for and displays it
     */
    public void findOpenCourses() {
        List<Course> courses = courseRepository.findAllCourses();
        if (courses == null) return;
        for (Course course : courses) {
            if (course.getRegisterOpen().equals("Yes")) {
                displayCourse(course);
                System.out.print("------------------------------------------------------------\n");
            }
        }
    }

    /**
     * Takes a non-null Course and prints its info to the screen
     *
     * @param course
     */
    private void displayCourse(Course course) {
        System.out.println("Course ID: " + course.getCourseId() +
                "\nCourse Name: " + course.getCourseName() +
                "\nCourse Description: " + course.getCourseDesc()
        );
    }
}
