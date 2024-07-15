package com.example.budgetfriendly.repository;

import com.example.budgetfriendly.entity.BudgetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetRepository extends JpaRepository<BudgetEntity, Long> {
    BudgetEntity findByGeminiResponse(String gemini);
}
