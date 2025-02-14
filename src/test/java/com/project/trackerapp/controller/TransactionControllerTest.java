package com.project.trackerapp.controller;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import com.project.trackerapp.model.User;
import com.project.trackerapp.repository.CategoryRepository;
import com.project.trackerapp.repository.TransactionRepository;
import com.project.trackerapp.service.TransactionService;
import org.springframework.test.web.servlet.MockMvc;
import com.project.trackerapp.service.UserService;
import org.springframework.http.MediaType;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.jupiter.api.Test;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @MockBean
    private UserService userService;

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private TransactionRepository transactionRepository;

    @Test
    @WithMockUser(username = "user@example.com", roles = {"USER"})



    public void testSaveTransactionValidationErrors() throws Exception {
         User mockUser = new User();
        mockUser.setFullName("Test User");
        Mockito.when(userService.getCurrentUser(Mockito.any(Principal.class))).thenReturn(mockUser);

        Mockito.when(categoryRepository.findByCategoryType("Expense"))
                .thenReturn(Collections.emptyList());

        Mockito.when(categoryRepository.findByCategoryType("Income"))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(post("/transaction/save")
                        .with(csrf())
                        .param("title", "Test Transaction")
                        .param("amount", "-10.0")
                        .param("date", "2025-02-14")
                        .param("description", "Test description")
                        .param("category.id", "1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("add_transaction"))
                .andExpect(model().attributeHasFieldErrors("transaction", "amount"));
    }
}
