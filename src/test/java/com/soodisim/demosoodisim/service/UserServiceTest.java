package com.soodisim.demosoodisim.service;

import com.soodisim.demosoodisim.model.User;
import com.soodisim.demosoodisim.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserById() {
        User user = new User();
        user.setUserId(1L);
        user.setName("Dea");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals("Dea", result.getName());
        assertEquals(1L, result.getUserId());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateUser() {
        User user = new User();
        user.setName("Dea");
        user.setEmail("dea@example.com");
        user.setPhoneNumber("123456");

        when(userRepository.save(user)).thenReturn(user);

        User result = userService.createUser(user);

        assertNotNull(result);
        assertEquals("Dea", result.getName());
        assertEquals("dea@example.com", result.getEmail());
        verify(userRepository, times(1)).save(user);
    }
}
