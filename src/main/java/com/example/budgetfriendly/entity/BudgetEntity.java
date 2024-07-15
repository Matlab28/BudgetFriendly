package com.example.budgetfriendly.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "budget_info")
public class BudgetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String budget;

    @Size(max = 255)
    private String plans;

    @Column(name = "gemini_response")
    private String geminiResponse;

    @Column(name = "weekly_or_monthly")
    private boolean weeklyOrMonthly;
}
