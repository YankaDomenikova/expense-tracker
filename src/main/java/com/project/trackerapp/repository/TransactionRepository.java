package com.project.trackerapp.repository;

import com.project.trackerapp.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserId(long id);

    @Query("SELECT t FROM Transaction t WHERE t.title LIKE %:criteria% OR t.category.categoryName LIKE %:criteria%")
    List<Transaction> searchTransaction(@Param("criteria") String criteria);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.user.id = :userId AND t.category.categoryType = :categoryType")
    Float sumTransactionAmountByUserAndCategoryType(@Param("userId") long userId,
                                                    @Param("categoryType") String categoryType);


    @Query("SELECT c.categoryName, c.chartColor, SUM(t.amount) " +
            "FROM Transaction t " +
            "JOIN t.category c " +
            "WHERE t.user.id = :userId " +
            "GROUP BY c.id, c.categoryName, c.chartColor")
    List<Object[]> sumTransactionAmountByCategoryAndUser(@Param("userId") Long userId);
}


