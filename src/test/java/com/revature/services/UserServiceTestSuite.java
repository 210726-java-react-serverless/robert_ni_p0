package com.revature.services;

import com.revature.datasource.models.AppUser;
import com.revature.datasource.repositories.UserRepository;
import com.revature.utils.UserSession;
import com.revature.utils.exceptions.InvalidRequestException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class UserServiceTestSuite {

    UserService sut;

    private UserSession mockUserSession;
    private UserRepository mockUserRepository;

    @Before
    public void beforeEachTest() {
        mockUserSession = mock(UserSession.class);
        mockUserRepository = mock(UserRepository.class);
        sut = new UserService(mockUserRepository, mockUserSession);
    }

    @After
    public void afterEachTest() {
        sut = null;
    }

    @Test
    public void isUserValid_returnsTrue_givenValidUser() {
        AppUser validUser = new AppUser("valid", "valid", "valid", "valid", "valid");
        boolean testResult = sut.isUserValid(validUser);
        Assert.assertTrue("Expected user to be considered valid", testResult);
    }

    @Test
    public void isUserValid_returnsFalse_givenInvalidUser() {
        AppUser invalidUser1 = new AppUser(null, "valid", "valid", "valid", "valid");
        AppUser invalidUser2 = new AppUser("", "valid", "valid", "valid", "valid");
        AppUser invalidUser3 = new AppUser("       ", "valid", "valid", "valid", "valid");

        boolean testResult1 = sut.isUserValid(invalidUser1);
        boolean testResult2 = sut.isUserValid(invalidUser2);
        boolean testResult3 = sut.isUserValid(invalidUser3);

        Assert.assertFalse("User first name can not be null", testResult1);
        Assert.assertFalse("User first name can be not empty string", testResult2);
        Assert.assertFalse("User first name can not be whitespaces", testResult3);
    }

    @Test
    public void register_returnsTrue_whenGivenValidUser() {
        AppUser expectedUser = new AppUser("valid", "valid", "valid", "valid", "valid");
        AppUser validUser = new AppUser("valid", "valid", "valid", "valid", "valid");

        when(mockUserRepository.save(any())).thenReturn(expectedUser);

        AppUser testResult = sut.register(validUser);

        Assert.assertEquals(expectedUser, testResult);
        verify(mockUserRepository, times(1)).save(any());
    }

    @Test(expected = InvalidRequestException.class)
    public void register_throwsException_whenGivenInvalidUser() {
        AppUser invalidUser = new AppUser(null, "valid", "valid", "valid", "valid");

        try {
            sut.register(invalidUser);
        } finally {
            verify(mockUserRepository, times(0)).save(any());
        }
    }
}

