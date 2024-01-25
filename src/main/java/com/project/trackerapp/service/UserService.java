package com.project.trackerapp.service;

import com.project.trackerapp.model.User;
import com.project.trackerapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

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
}
