package com.project.trackerapp.service;


import com.project.trackerapp.exception.UserAlreadyExistsException;
import com.project.trackerapp.model.User;
import com.project.trackerapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterUserAlreadyExists() {
        User user = new User();
        user.setUsername("yankadomenikova");
        user.setEmail("yankadomenikova@gmail.com");
        user.setFullName("Yanka Domenikova");
        user.setPassword("111111");

        when(userRepository.findByUsername("yankadomenikova")).thenReturn(new User());

        Exception exception = assertThrows(UserAlreadyExistsException.class, () -> {
            userService.registerUser(user);
        });

        assertEquals("This user already exists.", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testRegisterUserSuccess()  {
        User user = new User();
        user.setUsername("newUser");
        user.setPassword("password123");
        user.setEmail("usernail@mail.com");
        user.setFullName("New User");

        when(userRepository.findByUsername("newUser")).thenReturn(null);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");

        userService.registerUser(user);

        assertEquals("encodedPassword", user.getPassword());
        verify(userRepository, times(1)).save(user);
    }
}
