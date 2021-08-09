package com.revature.services;

import com.revature.models.Course;
import com.revature.repositories.CourseRepository;
import com.revature.utils.exceptions.InvalidRequestException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CourseServiceTestSuite {

    CourseService sut;

    private CourseRepository mockCourseRepository;

    @Before
    public void beforeEachTest() {
        mockCourseRepository = mock(CourseRepository.class);
        sut = new CourseService(mockCourseRepository);
    }

    @After
    public void afterEachTest() {
        sut = null;
    }

    @Test
    public void isCourseValid_returnsTrue_givenValidCourse() {
        Course validCourse = new Course("valid");
        boolean testResult = sut.isCourseValid(validCourse);
        Assert.assertTrue("Expected course to be considered valid", testResult);
    }

    @Test
    public void isCourseValid_returnsFalse_givenInvalidCourse() {
        Course invalidCourse1 = new Course(null);
        Course invalidCourse2 = new Course("");
        Course invalidCourse3 = new Course("                     ");

        boolean testResult1 = sut.isCourseValid(invalidCourse1);
        boolean testResult2 = sut.isCourseValid(invalidCourse2);
        boolean testResult3 = sut.isCourseValid(invalidCourse3);

        Assert.assertFalse("Course name can not be null", testResult1);
        Assert.assertFalse("Course name can not be empty string", testResult2);
        Assert.assertFalse("Course name can not be whitespaces", testResult3);
    }

    @Test
    public void addCourse_returnsTrue_givenValidCourse() {
        Course expectedCourse = new Course("valid");
        Course validCourse = new Course("valid");

        when(mockCourseRepository.save(any())).thenReturn(expectedCourse);

        Course testResult = sut.addCourse(validCourse);

        Assert.assertEquals(expectedCourse, testResult);
        verify(mockCourseRepository, times(1)).save(any());
    }

    @Test(expected = InvalidRequestException.class)
    public void addCourse_throwsException_whenGivenInvalidCourse() {
        Course invalidCourse = new Course(null);

        try {
            sut.addCourse(invalidCourse);
        } finally {
            verify(mockCourseRepository, times(0)).save(any());
        }
    }
}
