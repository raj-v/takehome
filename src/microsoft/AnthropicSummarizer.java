package microsoft;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

public class AnthropicSummarizer {
    private static final String API_KEY = "";
    private static final String API_URL = "https://api.anthropic.com/v1/complete";

    public static String summarize(String text) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .headers("Content-Type", "application/json",
                        "x-api-key", API_KEY,
                        "anthropic-version", "2023-06-01")
                .POST(HttpRequest.BodyPublishers.ofString(buildRequestPayload(text)))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            JSONObject json = new JSONObject(response.body());
            return json.getString("completion");
        } else {
            System.out.println("Request failed with status code: " + response.statusCode());
            System.out.println(response.body());
        }
        return "";
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        String text = "In a small village by the sea lived a fisherman named Tom. Every morning, Tom would sail out in his old boat to catch fish. One day, the sea was calm, the sky clear. Tom cast his net, waiting patiently. Suddenly, he felt a strong tug. He pulled and pulled, his heart racing. Finally, he saw a glimmer in the water. To his surprise, it was a golden fish. The fish spoke, asking to be let go and promising to grant a wish. Tom was astonished but quickly thought of his family. He wished for enough food and money to live comfortably. The fish nodded and disappeared into the depths. Tom sailed back, doubting what had happened. When he reached home, he found his tiny cottage transformed into a beautiful house. His wife and children were overjoyed. They had fresh food and new clothes. Tom never saw the golden fish again but he never forgot its kindness. He became the most generous man in the village, always helping others. His family lived happily and Tom's story was told for generations. The villagers learned that kindness and generosity could bring magical changes, just like the golden fish had brought to Tom.";

        String summary = summarize(text);
        System.out.println("Summary \n" + summary);

    }

    private static String buildRequestPayload(String text) {
        JSONObject payload = new JSONObject();
        payload.put("prompt", "\n\nHuman:Summarize this text in 50 words:\n\n" + text + "\n\nAssistant:");
        payload.put("max_tokens_to_sample", 100); // Adjust as needed
        //payload.put("stop_sequences", new JSONObject()); // Empty object for no stop sequences
        payload.put("model","claude-2.0");
        return payload.toString();
    }
}
