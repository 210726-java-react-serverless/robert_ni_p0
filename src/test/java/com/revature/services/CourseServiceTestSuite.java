package com.revature.services;

import com.revature.datasource.models.Course;
import com.revature.datasource.repositories.CourseRepository;
import com.revature.datasource.repositories.ScheduleRepository;
import com.revature.utils.exceptions.InvalidRequestException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CourseServiceTestSuite {

    CourseService sut;

    private CourseRepository mockCourseRepository;
    private ScheduleRepository mockScheduleRepository;

    @Before
    public void beforeEachTest() {
        mockCourseRepository = mock(CourseRepository.class);
        mockScheduleRepository = mock(ScheduleRepository.class);
        sut = new CourseService(mockCourseRepository, mockScheduleRepository);
    }

    @After
    public void afterEachTest() {
        sut = null;
    }

    @Test
    public void addCourse_returnsTrue_givenValidCourse() {
        Course expectedCourse = new Course("valid", "valid", "valid", "Y");
        Course validCourse = new Course("valid", "valid", "valid", "Y");

        when(mockCourseRepository.save(any())).thenReturn(expectedCourse);

        Course testResult = sut.addCourse(validCourse);

        Assert.assertEquals(expectedCourse, testResult);
        verify(mockCourseRepository, times(1)).save(any());
    }

    @Test(expected = InvalidRequestException.class)
    public void addCourse_throwsException_whenGivenInvalidCourse() {
        Course invalidCourse = new Course(null, "valid", "valid", "Y");

        try {
            sut.addCourse(invalidCourse);
        } finally {
            verify(mockCourseRepository, times(0)).save(any());
        }
    }

    @Test
    public void deleteCourse_returnsTrue_givenValidCourseId() {
        String validId = "valid";
        when(mockCourseRepository.delete(any())).thenReturn(true);
        when(mockScheduleRepository.delete(any())).thenReturn(true);

        boolean testResult = sut.deleteCourse(validId);

        Assert.assertTrue("Successfully deleted course from database", testResult);
        verify(mockCourseRepository, times(1)).delete(any());
        verify(mockScheduleRepository, times(1)).delete(any());
    }

    @Test(expected = InvalidRequestException.class)
    public void deleteCourse_throwsException_whenGivenInvalidCourseId() {
        String invalidId1 = null;
        String invalidId2 = "";
        String invalidId3 = "   ";

        try {
            sut.deleteCourse(invalidId1);
            sut.deleteCourse(invalidId2);
            sut.deleteCourse(invalidId3);
        } finally {
            verify(mockCourseRepository, times(0)).delete(any());
        }
    }
}
