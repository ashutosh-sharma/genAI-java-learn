package com.epam.training.gen.ai.controller;

import com.epam.training.gen.ai.service.OpenAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private OpenAIService openAIService;

    @GetMapping
    public ResponseEntity<Map<String, String>> handlePrompt(
            @RequestParam("input") String prompt) {
        System.out.println("Prompt: " + prompt);

        String response = openAIService.getChatCompletions(prompt);

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("input", prompt);
        responseBody.put("response", response);

        return ResponseEntity.ok(responseBody);
    }
}
