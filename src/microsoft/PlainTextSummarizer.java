package microsoft;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PlainTextSummarizer {

    private static final int CHUNK_SIZE = 50;
    private static final Pattern SENTENCE_DELIMITER_PATTERN = Pattern.compile("[.!?]+");
    public static List<String> splitIntoSentences(String text) {
        List<String> sentences = new ArrayList<>();
        String[] sentenceArray = SENTENCE_DELIMITER_PATTERN.split(text);

        for (String sentence : sentenceArray) {
            String trimmedSentence = sentence.trim();
            if (!trimmedSentence.isEmpty()) {
                sentences.add(trimmedSentence);
            }
        }

        return sentences;
    }

    public static void main(String[] args) {

        String input = "Hello, world! This is a sample text. It has multiple sentences. How are you?";
        List<String> sentences = splitIntoSentences(input);
        List<String> groupedSentences = optimizeSentenceSplitToFitChunkSize(sentences);
        for (String s: groupedSentences) {
            System.out.println(s);
        }
    }

    // Pack several sentences together to fit the CHUNK_SIZE.
    private static List<String> optimizeSentenceSplitToFitChunkSize(List<String> sentences) {
        List<String> optimized = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sentences.size(); i ++) {
            if (sb.length() + sentences.get(i).length() < CHUNK_SIZE) {
                sb.append(sentences.get(i));
            } else {
                optimized.add(sb.toString());
                sb = new StringBuilder(sentences.get(i));
            }
        }
        if (!sb.isEmpty()) {
            optimized.add(sb.toString());
        }
        return optimized;
    }

}
