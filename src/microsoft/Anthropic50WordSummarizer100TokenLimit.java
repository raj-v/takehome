package microsoft;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONObject;

public class Anthropic50WordSummarizer100TokenLimit implements Processor {
    private static final String API_KEY = "";
    private static final String API_URL = "https://api.anthropic.com/v1/complete";

    private static final String CONTENT_TYPE_HEADER_KEY = "Content-Type";
    private static final String CONTENT_TYPE_HEADER_VALUE = "application/json";
    private static final String X_API_KEY_HEADER = "x-api-key";
    private static final String ANTHROPIC_VERSION_HEADER_KEY = "anthropic-version";
    private static final String ANTHROPIC_VERSION_HEADER_VALUE = "2023-06-01";

    public String summarize(String text) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .headers(CONTENT_TYPE_HEADER_KEY, CONTENT_TYPE_HEADER_VALUE,
                        X_API_KEY_HEADER, API_KEY,
                        ANTHROPIC_VERSION_HEADER_KEY, ANTHROPIC_VERSION_HEADER_VALUE)
                .POST(HttpRequest.BodyPublishers.ofString(buildRequestPayload(text)))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                JSONObject json = new JSONObject(response.body());
                return json.getString("completion");
            } else {
                // For now simply print the error cases
                System.out.println("Request failed with status code: " + response.statusCode());
                System.out.println(response.body());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public String process(String text) {
        String summary = summarize(text);
        String[] splitPartsOfProcessedText = summary.split("\n");
        StringBuilder sb = new StringBuilder();
        for (String s: splitPartsOfProcessedText) {
            String trimmed = s.trim();
            // clean up a bit
            // Ignore empty string and the text that starts with "Here is a summary of text"
            if (!trimmed.isEmpty() && trimmed.indexOf("Here is a ") != 0) {
                sb.append(trimmed);
            }
        }

        return sb.toString();
    }

    private String buildRequestPayload(String text) {
        JSONObject payload = new JSONObject();
        payload.put("prompt", "\n\nHuman:Summarize this text in 50 words:\n\n" + text + "\n\nAssistant:");
        payload.put("max_tokens_to_sample", 100); // Adjust as needed
        payload.put("model","claude-2.0");
        return payload.toString();
    }
}
