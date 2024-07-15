package com.example.budgetfriendly.controller;

import com.example.budgetfriendly.dto.request.BudgetRequestDto;
import com.example.budgetfriendly.dto.response.BudgetResponseDto;
import com.example.budgetfriendly.service.BudgetService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/budget")
@CrossOrigin(origins = "https://matlab28.github.io")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;
    private static final Logger logger = LoggerFactory.getLogger(BudgetController.class);

    @PostMapping
    public ResponseEntity<BudgetResponseDto> processBudget(@RequestBody BudgetRequestDto requestDto) {
        try {
            budgetService.processUserRequest(requestDto);
            return ResponseEntity.ok(new BudgetResponseDto(requestDto.getGeminiResponse()));
        } catch (Exception e) {
            logger.error("Error processing budget request", e);
            return ResponseEntity.status(500).body(new BudgetResponseDto("Internal server error: " + e.getMessage()));
        }
    }
}


//package com.example.budgetfriendly.controller;
//
//import com.example.budgetfriendly.dto.gemini.Root;
//import com.example.budgetfriendly.dto.request.BudgetRequestDto;
//import com.example.budgetfriendly.dto.response.BudgetResponseDto;
//import com.example.budgetfriendly.service.BudgetService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/budget")
//@RequiredArgsConstructor
//public class BudgetController {
//    private final BudgetService service;
//
//    @PostMapping
//    public Root processUserRequest(@RequestBody BudgetRequestDto dto) {
//        return service.processUserRequest(dto);
//    }
//
////    @GetMapping("/read-all")
////    public List<BudgetResponseDto> readAll() {
////        return service.readAll();
////    }
//}
