package com.project.trackerapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;

import com.project.trackerapp.model.Category;
import com.project.trackerapp.model.Transaction;
import com.project.trackerapp.model.User;
import com.project.trackerapp.repository.TransactionRepository;
import com.project.trackerapp.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveValidTransaction() {
        // Arrange: create a valid Transaction and a current User
        Transaction transaction = new Transaction();
        transaction.setTitle("Valid Transaction");
        transaction.setAmount(100.0f); // positive amount
        transaction.setDate(LocalDate.now());
        transaction.setDescription("Test valid transaction");
        transaction.setCategory(new Category());
        // Assume category and other fields are properly set if needed

        User currentUser = new User();
        currentUser.setFullName("Test User");
        // Optionally, set an ID or other properties if needed

        // Act: call the saveTransaction method
        transactionService.saveTransaction(transaction, currentUser);

        assertEquals(currentUser, transaction.getUser(), "The transaction should have the current user set.");
         verify(transactionRepository, times(1)).save(transaction);
    }
}
