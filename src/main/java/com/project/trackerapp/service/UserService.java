package com.project.trackerapp.service;

import com.project.trackerapp.exception.UserAlreadyExistsException;
import com.project.trackerapp.model.User;
import com.project.trackerapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.ui.Model;

import java.security.Principal;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String getCurrentUsername(Principal principal) {
        if (principal == null) {
            return null;
        }

        return principal.getName();
    }

    public User getCurrentUser(Principal principal) {
        if (principal == null) {
            return null;
        }

        return userRepository.findByUsername(principal.getName());
    }

    public void registerUser(User user) throws UserAlreadyExistsException {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new UserAlreadyExistsException("This user already exists.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}
