package com.revature.datasource.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Schedule {

    private String id;
    private String username;
    private String courseId;
    private String courseName;
    private String courseDesc;

    public Schedule() {
        super();
    }

    public Schedule(String username, String courseId, String courseName, String courseDesc) {
        this.username = username;
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseDesc = courseDesc;
    }

    public Schedule(String id, String username, String courseId, String courseName, String courseDesc) {
        this(username, courseId, courseName, courseDesc);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return Objects.equals(id, schedule.id) && Objects.equals(username, schedule.username) && Objects.equals(courseId, schedule.courseId) && Objects.equals(courseName, schedule.courseName) && Objects.equals(courseDesc, schedule.courseDesc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, courseId, courseName, courseDesc);
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", courseId='" + courseId + '\'' +
                ", courseName='" + courseName + '\'' +
                ", courseDesc='" + courseDesc + '\'' +
                '}';
    }
}
