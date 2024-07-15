//package com.example.budgetfriendly.working;
//
//public class WorkingService {
//    //package com.example.budgetfriendly.service;
////
////import com.example.budgetfriendly.client.GeminiApiClient;
////import com.example.budgetfriendly.dto.gemini.Candidate;
////import com.example.budgetfriendly.dto.gemini.Root;
////import com.example.budgetfriendly.dto.request.BudgetRequestDto;
////import com.example.budgetfriendly.dto.response.BudgetResponseDto;
////import com.example.budgetfriendly.entity.BudgetEntity;
////import com.example.budgetfriendly.repository.BudgetRepository;
////import com.google.gson.JsonArray;
////import com.google.gson.JsonObject;
////import com.google.gson.JsonPrimitive;
////import lombok.Getter;
////import lombok.Setter;
////import lombok.extern.slf4j.Slf4j;
////import org.modelmapper.ModelMapper;
////import org.springframework.stereotype.Service;
////
////import java.util.List;
////import java.util.stream.Collectors;
////
////@Service
////@Setter
////@Getter
////@Slf4j
////public class BudgetService implements GeminiService {
////    private final ModelMapper modelMapper;
////    private final BudgetRepository repository;
////    private final String key = "AIzaSyB3GRRnXnOsnRJOhPpLQtWvKf4Bs1Hx-nU";
////    private final GeminiApiClient client;
////    private Root latestResponse;
////
////    public BudgetService(ModelMapper modelMapper,
////                         BudgetRepository repository,
////                         GeminiApiClient client) {
////        this.modelMapper = modelMapper;
////        this.repository = repository;
////        this.client = client;
////    }
////
////    @Override
////    public Root processUserRequest(BudgetRequestDto dto) {
////        BudgetEntity entity = modelMapper.map(dto, BudgetEntity.class);
////
////        if (!dto.getBudget().matches(".*\\d+.*")) {
////            log.error("Invalid budget info.");
////            throw new RuntimeException("Please enter only numbers...");
////        }
////
////        if (dto.isWeeklyOrMonthly()) {
////            log.info("It is for one week plans");
////        } else {
////            log.info("It is for one month plans");
////        }
////
////        Root updates = getUpdates(dto.getBudget(), dto.getPlans(), dto.isWeeklyOrMonthly());
////        String extractedText = extractTextFromGeminiResponse(updates);
////        dto.setGeminiResponse(extractedText);
////
////        repository.save(entity);
////        log.info("Budget info saved.");
////
////        latestResponse = updates;
////        return latestResponse;
////    }
////
////    @Override
////    public Root getLatestResponse() {
////        return latestResponse;
////    }
////
////    private Root getUpdates(String budget, String plans, boolean isWeekly) {
////        try {
////            JsonObject json = new JsonObject();
////            JsonArray contentsArray = new JsonArray();
////            JsonObject contentsObject = new JsonObject();
////            JsonArray partsArray = new JsonArray();
////            JsonObject partsObject = new JsonObject();
////
////            String timeFrame = isWeekly ? "weekly" : "monthly";
////            String messageText = String.format(
////                    "I have a budget of %s. These are my plans: %s. Please help me split this budget %s.",
////                    budget, plans, timeFrame
////            );
////
////            partsObject.add("text", new JsonPrimitive(messageText));
////            partsArray.add(partsObject);
////            contentsObject.add("parts", partsArray);
////            contentsArray.add(contentsObject);
////            json.add("contents", contentsArray);
////
////            String content = json.toString();
////            return client.getData(key, content);
////        } catch (Exception e) {
////            log.error("Error while getting updates from Gemini API: ", e);
////            throw e;
////        }
////    }
////
////    private String extractTextFromGeminiResponse(Root updates) {
////        StringBuilder textBuilder = new StringBuilder();
////
////        if (updates.getCandidates() != null) {
////            for (Candidate candidate : updates.getCandidates()) {
////                String text = candidate.getContent().getParts().get(0).getText();
////                text = text.replace("*", ""); // Remove asterisks
////                textBuilder.append(text).append("\n\n");
////            }
////        }
////
////        return textBuilder.toString().trim();
////    }
////
////    public List<BudgetResponseDto> readAll() {
////        log.info("All budget info responded");
////        return repository
////                .findAll()
////                .stream()
////                .map(m -> modelMapper.map(m, BudgetResponseDto.class))
////                .collect(Collectors.toList());
////    }
////}
//
////======================================================================
//
//
////package com.example.budgetfriendly.service;
////
////import com.example.budgetfriendly.client.GeminiApiClient;
////import com.example.budgetfriendly.dto.gemini.Candidate;
////import com.example.budgetfriendly.dto.gemini.Root;
////import com.example.budgetfriendly.dto.request.BudgetRequestDto;
////import com.example.budgetfriendly.dto.response.BudgetResponseDto;
////import com.example.budgetfriendly.entity.BudgetEntity;
////import com.example.budgetfriendly.repository.BudgetRepository;
////import com.google.gson.JsonArray;
////import com.google.gson.JsonObject;
////import com.google.gson.JsonPrimitive;
////import lombok.Getter;
////import lombok.Setter;
////import lombok.extern.slf4j.Slf4j;
////import org.modelmapper.ModelMapper;
////import org.springframework.stereotype.Service;
////
////import java.util.List;
////import java.util.stream.Collectors;
////
////@Service
////@Setter
////@Getter
////@Slf4j
////public class BudgetService implements GeminiService {
////    private final ModelMapper modelMapper;
////    private final BudgetRepository repository;
////    private final String key = "AIzaSyB3GRRnXnOsnRJOhPpLQtWvKf4Bs1Hx-nU";
////    private final GeminiApiClient client;
////    private Root latestResponse;
////
////    public BudgetService(ModelMapper modelMapper,
////                         BudgetRepository repository,
////                         GeminiApiClient client) {
////        this.modelMapper = modelMapper;
////        this.repository = repository;
////        this.client = client;
////    }
////
////    @Override
////    public Root processUserRequest(BudgetRequestDto dto) {
////        BudgetEntity entity = modelMapper.map(dto, BudgetEntity.class);
////
////        if (!dto.getBudget().matches(".*\\d+.*")) {
////            log.error("Invalid budget info.");
////            throw new RuntimeException("Please enter only numbers...");
////        }
////
////        if (dto.isWeeklyOrMonthly()) {
////            log.info("It is for one week plans");
////        } else {
////            log.info("It is for one month plans");
////        }
////
////        Root updates = getUpdates(dto.getPlans());
////        String extractedText = extractTextFromGeminiResponse(updates);
////        dto.setGeminiResponse(extractedText);
////
////        repository.save(entity);
////        log.info("Budget info saved.");
////
////        String messageText = dto.getPlans();
////        latestResponse = getUpdates(messageText);
////        return latestResponse;
////    }
////
////    @Override
////    public Root getLatestResponse() {
////        return latestResponse;
////    }
////
////    private Root getUpdates(String messageText) {
////        try {
////            JsonObject json = new JsonObject();
////            JsonArray contentsArray = new JsonArray();
////            JsonObject contentsObject = new JsonObject();
////            JsonArray partsArray = new JsonArray();
////            JsonObject partsObject = new JsonObject();
////
////            partsObject.add("text", new JsonPrimitive(messageText));
////            partsArray.add(partsObject);
////            contentsObject.add("parts", partsArray);
////            contentsArray.add(contentsObject);
////            json.add("contents", contentsArray);
////
////            String content = json.toString();
////            return client.getData(key, content);
////        } catch (Exception e) {
////            log.error("Error while getting updates from Gemini API: ", e);
////            throw e;
////        }
////    }
////
////    private String extractTextFromGeminiResponse(Root updates) {
////        StringBuilder textBuilder = new StringBuilder();
////
////        if (updates.getCandidates() != null) {
////            for (Candidate candidate : updates.getCandidates()) {
////                String text = candidate.getContent().getParts().get(0).getText();
////                textBuilder.append(text).append("\n\n");
////            }
////        }
////
////        return textBuilder.toString().trim();
////    }
////
////    private String extractTextFromGeminiResponse(Root updates) {
////        StringBuilder textBuilder = new StringBuilder();
////
////        if (updates.getCandidates() != null) {
////            for (Candidate candidate : updates.getCandidates()) {
////                String text = candidate.getContent().getParts().get(0).getText();
////                text = text.replace("*", ""); // Remove asterisks
////                textBuilder.append(text).append("\n\n");
////            }
////        }
////
////        return textBuilder.toString().trim();
////    }
////
////    public List<BudgetResponseDto> readAll() {
////        log.info("All budget info responded");
////        return repository
////                .findAll()
////                .stream()
////                .map(m -> modelMapper.map(m, BudgetResponseDto.class))
////                .collect(Collectors.toList());
////    }
////}
//}
