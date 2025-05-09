package com.epam.training.gen.ai.service;

import com.azure.ai.openai.OpenAIAsyncClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.credential.KeyCredential;
import com.epam.training.gen.ai.plugin.AgeCalculatorPlugin;
import com.epam.training.gen.ai.plugin.CurrentDateTimeCalculatorPlugin;
import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.aiservices.openai.chatcompletion.OpenAIChatCompletion;
import com.microsoft.semantickernel.orchestration.InvocationContext;
import com.microsoft.semantickernel.orchestration.InvocationReturnMode;
import com.microsoft.semantickernel.orchestration.PromptExecutionSettings;
import com.microsoft.semantickernel.orchestration.ToolCallBehavior;
import com.microsoft.semantickernel.plugin.KernelPlugin;
import com.microsoft.semantickernel.plugin.KernelPluginFactory;
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

    private ChatHistory history = new ChatHistory();

    public OpenAIService() {
        // can configure system prompt for better user support
    }

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
        printChatHistory(history);
        return response.get(0).getContent();
    }

    public void printChatHistory(ChatHistory history) {
        System.out.println("Printing Chat History ---->>> ");
        history.forEach(chatMessageContent -> {
            String role = chatMessageContent.getAuthorRole().toString().toLowerCase();
            String content = chatMessageContent.getContent();
            System.out.printf("%s: %s%n", capitalize(role), content);
        });
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
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

    private ChatCompletionService createChatCompletionService(OpenAIAsyncClient client, String dynamicModel) {
        logger.info("Creating chat Completion service");

        if (dynamicModel!= null && !model.equals(dynamicModel)) {
            model = dynamicModel;
        }
        logger.info("Creating chat service with model {}.", model);
        return OpenAIChatCompletion.builder()
                .withOpenAIAsyncClient(client)
                .withModelId(model)
                .build();
    }

    private Kernel createKernel(ChatCompletionService chatCompletionService) {
        logger.info("Creating Kernal....");

        logger.info("Adding plugins....");
        KernelPlugin timePlugin = KernelPluginFactory.createFromObject(new CurrentDateTimeCalculatorPlugin(),"TimePlugin");
        KernelPlugin agePlugin = KernelPluginFactory.createFromObject(new AgeCalculatorPlugin(),"AgePlugin");

        return Kernel.builder()
                .withAIService(ChatCompletionService.class, chatCompletionService)
                .withPlugin(timePlugin)
                .withPlugin(agePlugin)
                .build();
    }

    public String getChatCompletions(String prompt, String dynamicModelName) {
        OpenAIAsyncClient client = createOpenAIAsyncClient();

        ChatCompletionService chatCompletionService = createChatCompletionService(client, dynamicModelName);
        Kernel kernel = createKernel(chatCompletionService);

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

        InvocationContext optionalInvocationContext = InvocationContext.builder()
                .withPromptExecutionSettings(PromptExecutionSettings.builder()
                        .withTemperature(0.9)
                        .build())
                .withReturnMode(InvocationReturnMode.LAST_MESSAGE_ONLY)
                .withToolCallBehavior(ToolCallBehavior.allowAllKernelFunctions(true))
                .build();
        return chatCompletionService.getChatMessageContentsAsync(history, kernel,
                optionalInvocationContext).block();
    }


}