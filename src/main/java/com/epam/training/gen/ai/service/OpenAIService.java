package com.epam.training.gen.ai.service;

import com.azure.ai.openai.OpenAIAsyncClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.credential.KeyCredential;
import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.aiservices.openai.chatcompletion.OpenAIChatCompletion;
import com.microsoft.semantickernel.orchestration.InvocationContext;
import com.microsoft.semantickernel.services.chatcompletion.AuthorRole;
import com.microsoft.semantickernel.services.chatcompletion.ChatCompletionService;
import com.microsoft.semantickernel.services.chatcompletion.ChatHistory;
import com.microsoft.semantickernel.services.chatcompletion.ChatMessageContent;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class OpenAIService {

    Logger logger = LoggerFactory.getLogger(OpenAIService.class);

    @Value("${client-openai-key}")
    private String clientKey;

    @Value("${client-openai-endpoint}")
    private String clientEndpoint;

    @Value("${model}")
    private String model;

    private String processResponse(List<ChatMessageContent<?>> response, ChatHistory history) {
        if (response == null || response.isEmpty()) {
            logger.info("No response received");
            return "Response not received from the assistant.";
        }

        for (ChatMessageContent<?> result : response) {
            if (result.getAuthorRole() == AuthorRole.ASSISTANT && result.getContent() != null) {
                logger.info("Assistant: " + result);
                history.addMessage(result);
            }
        }
        return response.get(0).getContent();
    }

    private OpenAIAsyncClient createOpenAIAsyncClient() {
        logger.info("Creating OpenAI Client");
        if (clientKey != null) {
            return new OpenAIClientBuilder()
                    .credential(new AzureKeyCredential(clientKey))
                    .endpoint(clientEndpoint)
                    .buildAsyncClient();
        } else {
            return new OpenAIClientBuilder()
                    .credential(new KeyCredential(clientKey))
                    .buildAsyncClient();
        }
    }

    private ChatCompletionService createChatCompletionService(OpenAIAsyncClient client) {
        logger.info("Creating chat Completion service");
        return OpenAIChatCompletion.builder()
                .withOpenAIAsyncClient(client)
                .withModelId(model)
                .build();
    }

    private Kernel createKernel(ChatCompletionService chatCompletionService) {
        logger.info("Creating Kernal....");
        return Kernel.builder()
                .withAIService(ChatCompletionService.class, chatCompletionService)
                .build();
    }

    public String getChatCompletions(String prompt) {
        OpenAIAsyncClient client = createOpenAIAsyncClient();

        ChatCompletionService chatCompletionService = createChatCompletionService(client);
        Kernel kernel = createKernel(chatCompletionService);

        ChatHistory history = new ChatHistory();
        history.addUserMessage(prompt);

        List<ChatMessageContent<?>> response = fetchChatResponse(chatCompletionService, history,
                kernel);

        return processResponse(response, history);
    }

    private List<ChatMessageContent<?>> fetchChatResponse(
            ChatCompletionService chatCompletionService,
            ChatHistory history,
            Kernel kernel
    ) {
        InvocationContext optionalInvocationContext = null;
        return chatCompletionService.getChatMessageContentsAsync(history, kernel,
                optionalInvocationContext).block();
    }


}