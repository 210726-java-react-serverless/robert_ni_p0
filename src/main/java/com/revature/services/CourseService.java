package com.revature.services;

import com.revature.datasource.models.Course;
import com.revature.datasource.repositories.CourseRepository;
import com.revature.utils.exceptions.DataSourceException;
import com.revature.utils.exceptions.InvalidRequestException;

import java.util.List;

public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course addCourse(Course newCourse) {
        if (!isCourseValid(newCourse)) {
            throw new InvalidRequestException("Invalid course data provided");
        }
        newCourse = convertRegisterOpen(newCourse);
        return courseRepository.save(newCourse);
    }

    public Course convertRegisterOpen(Course course) {
        if (course.getRegisterOpen().equals("Y")) {
            course.setRegisterOpen("Yes");
        } else {
            course.setRegisterOpen("No");
        }
        return course;
    }

    public boolean deleteCourse(String courseId) {
        if (!isCourseIdValid(courseId)) {
            throw new InvalidRequestException("Invalid course id provided");
        }
        return courseRepository.delete(courseId);
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
                    System.out.print("\n------------------------------------------------------------\n");
                }
            }
        } catch (Exception e) {
            throw new DataSourceException("An unexpected error occurred", e);
        }
    }

    public void findClosedCourses() {
        List<Course> courses;
        try {
            courses = courseRepository.findAllCourses();
            for (Course course : courses) {
                if (course.getRegisterOpen().equals("No")) {
                    displayCourse(course);
                    System.out.print("\n------------------------------------------------------------\n");
                }
            }
        } catch (Exception e) {
            throw new DataSourceException("An unexpected error occurred", e);
        }
    }

    public void displayCourse(Course course) {
        System.out.println("Course ID: " + course.getCourseId() +
                "\nCourse Name: " + course.getCourseName() +
                "\nCourse Description: " + course.getCourseDesc()
        );
    }

    public boolean isCourseValid(Course course) {
        if (course == null) return false;
        if (course.getCourseId() == null || course.getCourseId().trim().equals("")) return false;
        if (course.getCourseName() == null || course.getCourseName().trim().equals("")) return false;
        if (course.getRegisterOpen() == null || course.getRegisterOpen().trim().equals("")) return false;
        return course.getRegisterOpen().trim().equals("Y") || course.getRegisterOpen().trim().equals("N");
    }

    public boolean isCourseIdValid(String courseId) {
        return courseId != null && !courseId.trim().equals("");
    }
}
