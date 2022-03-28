package reminator.RemiBot.Services.openai;

import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;
import reminator.RemiBot.utils.HTTPRequest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class OpenAIClient {
    public final String token;

    public OpenAIClient(String token) {
        this.token = token;
    }

    public String complete(String input) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(URI.create("https://api.openai.com/v1/engines/text-davinci-002/completions"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .POST(HttpRequest.BodyPublishers.ofString("""
                        {
                            "prompt": "%s",
                            "echo": true,
                            "temperature": 0.9,
                            "max_tokens": 256,
                            "top_p": 1,
                            "frequency_penalty": 0,
                            "presence_penalty": 0
                        }
                        """.formatted(input))).build();

        HttpResponse<String> httpResponse = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

        String response = httpResponse.body();

        JSONArray choices = new JSONObject(response).getJSONArray("choices");
        String result = format(choices.getJSONObject(0).getString("text"));
        return result;
    }

    public static String format(String input) {
        return input.replaceAll("<[^>]+>", "").replaceAll("\\t", "\t").replaceAll("\\n", "\n");
    }
}
