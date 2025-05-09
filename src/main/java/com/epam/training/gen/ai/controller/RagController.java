package com.epam.training.gen.ai.controller;

import com.epam.training.gen.ai.service.RagService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/rag")
public class RagController {

    private final RagService ragService;

    @PostMapping("/source/upload/file")
    public ResponseEntity<?> uploadKnowledgeSourceFromFile(@RequestParam("file") MultipartFile file) {
        //upload txt, pdf files
        try {
            ragService.storeKnowledgeSource(file);
            return ResponseEntity.ok("Knowledge successfully uploaded from file.");
        } catch (IllegalArgumentException e) {
            log.error("There is something wrong with file, error: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Validation error while processing file: " + e.getMessage());
        } catch (Exception e) {
            log.error("Error occurred while uploading the knowledge source: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    @PostMapping("/source/upload/url")
    public ResponseEntity<?> uploadKnowledgeSourceFromUrl(@RequestParam("url") String url) {
        try {
            ragService.storeKnowledgeSource(url);
            return ResponseEntity.ok("Knowledge base successfully uploaded from given URL.");
        } catch (IllegalArgumentException e) {
            log.error("Validation error occurred: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Validation error occurred: " + e.getMessage());
        } catch (Exception e) {
            log.error("Error occurred while uploading the knowledge source:: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/prompt")
    public ResponseEntity<String> handleChatPrompt(@RequestBody String prompt) {
        log.info("Start :: handleChatPrompt.");
        try {
            String response = ragService.getPromptResponse(prompt);
            log.info("Successfully retrieved a response.");
            log.info("End :: handleChatPrompt.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing...try again later");
        }
    }
}