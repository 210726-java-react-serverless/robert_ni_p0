package com.revature.services;

import com.revature.models.Course;
import com.revature.repositories.CourseRepository;
import com.revature.utils.exceptions.InvalidRequestException;

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

    public boolean isCourseValid(Course course) {
        if (course == null) return false;
        return course.getCourseName() != null && !course.getCourseName().trim().equals("");
    }
}
