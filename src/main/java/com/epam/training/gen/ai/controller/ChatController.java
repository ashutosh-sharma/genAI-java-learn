package com.epam.training.gen.ai.controller;

import com.epam.training.gen.ai.service.DeploymentModelService;
import com.epam.training.gen.ai.service.OpenAIService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/chat")
@Slf4j
public class ChatController {

    @Autowired
    private OpenAIService openAIService;

    @Autowired
    private DeploymentModelService deploymentModelService;

    @GetMapping(path = "/deployments")
    public ResponseEntity<List<String>> getDeployments() {
        try {
            List<String> deployments = deploymentModelService.getAvailableDeployments();
            return ResponseEntity.ok(deployments);
        } catch (Exception e) {
            log.error("Error fetching deployments: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<Map<String, String>> handlePrompt(@RequestParam(value = "model", required = false) String model,
            @RequestParam(value = "input", required = true) String prompt) {
        log.info("Model: "+ model);
        log.info("Prompt: " + prompt);

        String response = openAIService.getChatCompletions(prompt, model);

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("input", prompt);
        responseBody.put("response", response);

        return ResponseEntity.ok(responseBody);
    }
}
