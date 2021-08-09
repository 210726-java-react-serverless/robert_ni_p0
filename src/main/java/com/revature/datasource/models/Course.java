package com.revature.datasource.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Course {

    private String id;
    private String courseId;
    private String courseName;
    private String courseDesc;
    private String registerOpen;

    public Course() {
        super();
    }

    public Course(String courseId, String courseName, String courseDesc, String registerOpen) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseDesc = courseDesc;
        this.registerOpen = registerOpen;
    }

    public Course(String id, String courseId, String courseName, String courseDesc, String registerOpen) {
        this(courseId, courseName, courseDesc, registerOpen);
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

    public String getRegisterOpen() {
        return registerOpen;
    }

    public void setRegisterOpen(String registerOpen) {
        this.registerOpen = registerOpen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(id, course.id) && Objects.equals(courseId, course.courseId)
                && Objects.equals(courseName, course.courseName)
                && Objects.equals(courseDesc, course.courseDesc)
                && Objects.equals(registerOpen, course.registerOpen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, courseId, courseName, courseDesc, registerOpen);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id='" + id + '\'' +
                ", courseId='" + courseId + '\'' +
                ", courseName='" + courseName + '\'' +
                ", courseDesc='" + courseDesc + '\'' +
                ", registerOpen='" + registerOpen + '\'' +
                '}';
    }
}
