package com.revature.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Course {

    private String id;
    private String courseId;
    private String courseName;
    private String courseDesc;
    private String regOpen;

    public Course() {
        super();
    }

    public Course(String courseId, String courseName, String courseDesc, String regOpen) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseDesc = courseDesc;
        this.regOpen = regOpen;
    }

    public Course(String id, String courseId, String courseName, String courseDesc, String regOpen) {
        this(courseId, courseName, courseDesc, regOpen);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDesc() {
        return courseDesc;
    }

    public void setCourseDesc(String courseDesc) {
        this.courseDesc = courseDesc;
    }

    public String getRegOpen() {
        return regOpen;
    }

    public void setRegOpen(String regOpen) {
        this.regOpen = regOpen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(id, course.id) && Objects.equals(courseId, course.courseId)
                && Objects.equals(courseName, course.courseName)
                && Objects.equals(courseDesc, course.courseDesc)
                && Objects.equals(regOpen, course.regOpen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, courseId, courseName, courseDesc, regOpen);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id='" + id + '\'' +
                ", courseId='" + courseId + '\'' +
                ", courseName='" + courseName + '\'' +
                ", courseDesc='" + courseDesc + '\'' +
                ", regOpen='" + regOpen + '\'' +
                '}';
    }
}
