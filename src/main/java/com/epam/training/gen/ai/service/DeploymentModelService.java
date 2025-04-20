package com.epam.training.gen.ai.service;

import com.epam.training.gen.ai.dto.Model;
import com.epam.training.gen.ai.dto.ModelList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
public class DeploymentModelService {
    private static final String DEPLOYMENTS_API_URL = "https://ai-proxy.lab.epam.com/openai/deployments";

    @Value("${client-openai-key}")
    private String clientKey;

    @Value("${client-openai-endpoint}")
    private String clientEndpoint;

    @Value("${model}")
    private String model;
    private final RestTemplate restTemplate;

    public DeploymentModelService() {
        this.restTemplate = new RestTemplate();
    }

    public List<String> getAvailableDeployments() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Api-Key", clientKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<ModelList> response = restTemplate.exchange(
                DEPLOYMENTS_API_URL,
                HttpMethod.GET,
                entity,
                ModelList.class
        );

        return response.getBody().getData().stream()
                .map(Model::getModel)
                .toList();
    }
}
