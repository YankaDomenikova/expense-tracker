package com.project.trackerapp.repository;

import com.project.trackerapp.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByCategoryType(String type);
}



