package com.example.budgetfriendly.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BudgetRequestDto {
    private String budget;
    private String plans;
    private String geminiResponse;
    private boolean weeklyOrMonthly;
}
