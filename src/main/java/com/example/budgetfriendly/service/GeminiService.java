package com.example.budgetfriendly.service;


import com.example.budgetfriendly.dto.gemini.Root;
import com.example.budgetfriendly.dto.request.BudgetRequestDto;
import org.springframework.stereotype.Service;

@Service
public interface GeminiService {
    Root processUserRequest(BudgetRequestDto dto);

    Root getLatestResponse();
}
