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
        return courseRepository.save(newCourse);
    }

    public boolean deleteCourse(String courseId) {
        if (!isCourseIdValid(courseId)) {
            throw new InvalidRequestException("Invalid course id provided");
        }
        return courseRepository.delete(courseId);
    }

    public void findOpenCourses() {
        List<Course> courses;
        try {
            courses = courseRepository.findOpenCourses();
            for (Course course : courses) {
                if (course.getRegOpen().equals("Y")) {
                    System.out.println("Course ID: " + course.getCourseId() +
                            "\nCourse Name: " + course.getCourseName() +
                            "\nCourse Description: " + course.getCourseDesc() +
                            "\n------------------------------------------------------------"
                    );
                }
            }
        } catch (Exception e) {
            throw new DataSourceException("Could not find courses", e);
        }
    }

    public boolean isCourseValid(Course course) {
        if (course == null) return false;
        if (course.getCourseId() == null || course.getCourseId().trim().equals("")) return false;
        if (course.getCourseName() == null || course.getCourseName().trim().equals("")) return false;
        if (course.getRegOpen() == null || course.getRegOpen().trim().equals("")) return false;
        return course.getRegOpen().trim().equals("Y") || course.getRegOpen().trim().equals("N");
    }

    public boolean isCourseIdValid(String courseId) {
        return courseId != null && !courseId.trim().equals("");
    }
}
