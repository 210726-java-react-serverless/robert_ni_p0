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

    public Schedule register(String username, String id) {
        if (username == null || username.trim().equals("") || id == null || id.trim().equals("")) {
            throw new InvalidRequestException("Invalid course id provided");
        }

        Course course = courseRepository.findById(id);
        Schedule schedule = scheduleRepository.findByUser(username, id);

        if (schedule != null) {
            return null;
        }

        return scheduleRepository.save(new Schedule(username, course.getCourseId(), course.getCourseName(), course.getCourseDesc()));
    }

    public boolean deleteSchedule(String username, String id) {
        return scheduleRepository.deleteSchedule(username, id);
    }

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

    public void getAllCourses() {
        List<Course> courses;
        try {
            courses = courseRepository.findAllCourses();
            for (Course course : courses) {
                displayCourse(course);
                System.out.print("Registration available? " + course.getRegisterOpen() +
                        "\n------------------------------------------------------------\n"
                );
            }
        } catch (Exception e) {
            throw new DataSourceException("An unexpected error occurred", e);
        }
    }

    public void findOpenCourses() {
        List<Course> courses;
        try {
            courses = courseRepository.findAllCourses();
            for (Course course : courses) {
                if (course.getRegisterOpen().equals("Yes")) {
                    displayCourse(course);
                    System.out.print("------------------------------------------------------------\n");
                }
            }
        } catch (Exception e) {
            throw new DataSourceException("An unexpected error occurred", e);
        }
    }

    private void displayCourse(Course course) {
        System.out.println("Course ID: " + course.getCourseId() +
                "\nCourse Name: " + course.getCourseName() +
                "\nCourse Description: " + course.getCourseDesc()
        );
    }
}
