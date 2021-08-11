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

    /**
     * Takes a non-null Course object, validates its fields, and attempts to persist it to the datasource.
     *
     * @param newCourse
     * @return
     */
    public Course addCourse(Course newCourse) {
        if (!isCourseValid(newCourse)) {
            throw new InvalidRequestException("Invalid course data provided");
        }
        newCourse = convertRegisterOpen(newCourse);
        return courseRepository.save(newCourse);
    }

    /**
     * Helps expand registerOpen fields from Y to Yes and N to No
     *
     * @param course
     * @return
     */
    private Course convertRegisterOpen(Course course) {
        if (course.getRegisterOpen().equals("Y")) {
            course.setRegisterOpen("Yes");
        } else {
            course.setRegisterOpen("No");
        }
        return course;
    }

    /**
     * Takes a non-null String, checks if its a valid id, and attempts to remove it from the datasource
     *
     * @param courseId
     * @return
     */
    public boolean deleteCourse(String courseId) {
        if (!isCourseIdValid(courseId)) {
            throw new InvalidRequestException("Invalid course id provided");
        }
        return courseRepository.delete(courseId);
    }

    /**
     * Takes in multiple non-null Strings, validates its fields, and attempts to update it in
     * the datasource
     *
     * @param courseId
     * @param context
     * @param newInfo
     * @return
     */
    public boolean updateCourse(String courseId, String context, String newInfo) {
        if (!isCourseIdValid(courseId)) {
            throw new InvalidRequestException("Invalid course id provided");
        }
        return courseRepository.updateCourse(courseId, context, newInfo);
    }

    /**
     * Takes in non-null Strings, validates its fields, and attempts to update it in the datasource
     *
     * @param courseId
     * @param context
     * @return
     */
    public boolean updateCourse(String courseId, String context) {
        if (!isCourseIdValid(courseId)) {
            throw new InvalidRequestException("Invalid course id provided");
        }
        return courseRepository.updateCourse(courseId, context);
    }

    /**
     * Takes in a non-null Course object and validates its fields by checking
     * for empty Strings or whitespaces
     *
     * @param course
     * @return
     */
    private boolean isCourseValid(Course course) {
        if (course == null) return false;
        if (course.getCourseId() == null || course.getCourseId().trim().equals("")) return false;
        if (course.getCourseName() == null || course.getCourseName().trim().equals("")) return false;
        if (course.getRegisterOpen() == null || course.getRegisterOpen().trim().equals("")) return false;
        return course.getRegisterOpen().trim().equals("Y") || course.getRegisterOpen().trim().equals("N");
    }

    private boolean isCourseIdValid(String courseId) {
        return courseId != null && !courseId.trim().equals("");
    }
}
