package com.revature.services;

import com.revature.datasource.models.Course;
import com.revature.datasource.models.Schedule;
import com.revature.datasource.repositories.CourseRepository;
import com.revature.datasource.repositories.ScheduleRepository;
import com.revature.utils.exceptions.InvalidRequestException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class ScheduleServiceTestSuite {

    ScheduleService sut;

    private ScheduleRepository mockScheduleRepository;
    private CourseRepository mockCourseRepository;

    @Before
    public void beforeEachTest() {
        mockScheduleRepository = mock(ScheduleRepository.class);
        mockCourseRepository = mock(CourseRepository.class);
        sut = new ScheduleService(mockScheduleRepository, mockCourseRepository);
    }

    @After
    public void afterEachTest() {
        sut = null;
    }

    @Test
    public void register_returnsTrue_givenValidInput() {
        Schedule expectedSchedule = new Schedule("valid", "valid", "valid", "valid", "valid");

        Course validCourse = new Course("valid", "valid", "valid", "valid", "valid");
        Schedule validSchedule = new Schedule("valid", "valid", "valid", "valid", "valid");

        when(mockCourseRepository.findById(any())).thenReturn(validCourse);
        when(mockScheduleRepository.findByUser(any(), any())).thenReturn(null);
        when(mockScheduleRepository.save(any())).thenReturn(validSchedule);

        Schedule actualResult = sut.register("valid", "valid");

        Assert.assertEquals(expectedSchedule, actualResult);
        verify(mockCourseRepository, times(1)).findById(any());
        verify(mockScheduleRepository, times(1)).findByUser(any(), any());
        verify(mockScheduleRepository, times(1)).save(any());
    }

    @Test
    public void register_returnsNull_givenDuplicateSchedule() {
        Schedule expectedSchedule = new Schedule("valid", "valid", "valid", "valid", "valid");

        Course validCourse = new Course("valid", "valid", "valid", "valid", "valid");
        Schedule validSchedule = new Schedule("valid", "valid", "valid", "valid", "valid");

        when(mockCourseRepository.findById(any())).thenReturn(validCourse);
        when(mockScheduleRepository.findByUser(any(), any())).thenReturn(validSchedule);
        when(mockScheduleRepository.save(any())).thenReturn(validSchedule);

        Schedule actualResult = sut.register("valid", "valid");

        Assert.assertEquals(null, actualResult);
        verify(mockCourseRepository, times(1)).findById(any());
        verify(mockScheduleRepository, times(1)).findByUser(any(), any());
        verify(mockScheduleRepository, times(0)).save(any());
    }

    @Test(expected = InvalidRequestException.class)
    public void register_throwsException_givenInvalidSchedule() {
        try {
            sut.register("valid", null);
            sut.register("valid", "");
            sut.register("valid", "     ");
        } finally {
            verify(mockCourseRepository, times(0)).findById(any());
            verify(mockScheduleRepository, times(0)).findByUser(any(), any());
            verify(mockScheduleRepository, times(0)).save(any());
        }
    }
}
