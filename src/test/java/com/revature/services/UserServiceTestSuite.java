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

