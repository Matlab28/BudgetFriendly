package com.example.budgetfriendly.service;

import com.example.budgetfriendly.client.GeminiApiClient;
import com.example.budgetfriendly.dto.gemini.Candidate;
import com.example.budgetfriendly.dto.gemini.Root;
import com.example.budgetfriendly.dto.request.BudgetRequestDto;
import com.example.budgetfriendly.dto.response.BudgetResponseDto;
import com.example.budgetfriendly.entity.BudgetEntity;
import com.example.budgetfriendly.repository.BudgetRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BudgetService implements GeminiService {

    private final ModelMapper modelMapper;
    private final BudgetRepository repository;
    private final String key = "YOUR_API_KEY";
    private final GeminiApiClient client;
    private Root latestResponse;

    public BudgetService(ModelMapper modelMapper,
                         BudgetRepository repository,
                         GeminiApiClient client) {
        this.modelMapper = modelMapper;
        this.repository = repository;
        this.client = client;
    }

    @Override
    public Root processUserRequest(BudgetRequestDto dto) {
        BudgetEntity entity = modelMapper.map(dto, BudgetEntity.class);

        if (!dto.getBudget().matches(".*\\d+.*")) {
            log.error("Invalid budget info.");
            throw new RuntimeException("Please enter only numbers...");
        }

        String instruction = constructInstruction(dto);
        Root updates = getUpdates(instruction);
        String extractedText = extractTextFromGeminiResponse(updates);
        dto.setGeminiResponse(extractedText);

        repository.save(entity);
        log.info("Budget info saved.");

        latestResponse = updates;
        return latestResponse;
    }

    @Override
    public Root getLatestResponse() {
        return latestResponse;
    }

    private String constructInstruction(BudgetRequestDto dto) {
        StringBuilder instruction = new StringBuilder();
        instruction.append("User's budget: ").append(dto.getBudget()).append("\n");
        instruction.append("User's plans: ").append(dto.getPlans()).append("\n");
        instruction.append("Time frame: ").append(dto.isWeeklyOrMonthly() ? "Weekly" : "Monthly").append("\n");
        instruction.append("Please provide a budget allocation plan based on the given information.");
        return instruction.toString();
    }

    private Root getUpdates(String instruction) {
        try {
            JsonObject json = new JsonObject();
            JsonArray contentsArray = new JsonArray();
            JsonObject contentsObject = new JsonObject();
            JsonArray partsArray = new JsonArray();
            JsonObject partsObject = new JsonObject();

            partsObject.add("text", new JsonPrimitive(instruction));
            partsArray.add(partsObject);
            contentsObject.add("parts", partsArray);
            contentsArray.add(contentsObject);
            json.add("contents", contentsArray);

            String content = json.toString();
            return client.getData(key, content);
        } catch (Exception e) {
            log.error("Error while getting updates from Gemini API: ", e);
            throw e;
        }
    }

//    private String extractTextFromGeminiResponse(Root updates) {
//        StringBuilder textBuilder = new StringBuilder();
//
//        if (updates.getCandidates() != null) {
//            for (Candidate candidate : updates.getCandidates()) {
//                String text = candidate.getContent().getParts().get(0).getText();
//                text = text.replace("*", ""); // Remove asterisks
//                textBuilder.append(text).append("\n\n");
//            }
//        }
//
//        return textBuilder.toString().trim();
//    }

    private String extractTextFromGeminiResponse(Root updates) {
        StringBuilder textBuilder = new StringBuilder();

        if (updates.getCandidates() != null) {
            for (Candidate candidate : updates.getCandidates()) {
                String text = candidate.getContent().getParts().get(0).getText();
                text = text.replace("*", "");
                textBuilder.append(text).append("\n\n");
            }
        }

        String response = textBuilder.toString().trim();

        String formattedResponse = response
                .replaceFirst("^##", "")
                .replace("##", "\n##")
                .replace("Expenses:", "\nExpenses:\n")
                .replace("Total Expenses:", "\nTotal Expenses:")
                .replace("Remaining Budget:", "\nRemaining Budget:")
                .replace("Budget Allocation Plan:", "\nBudget Allocation Plan:\n")
                .replace("Important Considerations:", "\nImportant Considerations:\n");

        return formattedResponse;
    }

    public List<BudgetResponseDto> readAll() {
        log.info("All budget info responded");
        return repository
                .findAll()
                .stream()
                .map(m -> modelMapper.map(m, BudgetResponseDto.class))
                .collect(Collectors.toList());
    }
}
