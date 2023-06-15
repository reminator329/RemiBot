package reminator.RemiBot.Services;

import fr.feavy.simpleopenai.ChatMessage;
import fr.feavy.simpleopenai.CompletionEngine;
import fr.feavy.simpleopenai.Conversation;
import fr.feavy.simpleopenai.OpenAIClient;

import java.util.concurrent.CompletableFuture;

public class OpenAIService {
    private static OpenAIService INSTANCE = new OpenAIService();

    public static OpenAIService get() {
        return INSTANCE;
    }

    public static OpenAIService openAI() {
        return INSTANCE;
    }

    private final OpenAIClient client = new OpenAIClient(System.getenv("OPENAI_API_KEY"));

    public CompletableFuture<String> generate(String prompt) {
        Conversation conversation = new Conversation();
        conversation.addMessage(new ChatMessage(ChatMessage.Role.USER, prompt));
        return client.complete(conversation, CompletionEngine.GPT4);
    }
}
