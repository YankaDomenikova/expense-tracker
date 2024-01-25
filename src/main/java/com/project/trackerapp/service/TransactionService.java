package com.project.trackerapp.service;

import com.project.trackerapp.model.Transaction;
import com.project.trackerapp.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> getInDescendingOrder(long id){
        List<Transaction> allTransactions = transactionRepository.findByUserId(id);

        // Reverse the order of the list
        Collections.reverse(allTransactions);

        return allTransactions;
    }

    public Float calculateSumByType(long userId, String type){
        Float sum = transactionRepository.sumTransactionAmountByUserAndCategoryType(userId, type);
        return (sum != null) ? sum : 0.0f;
    }
}
