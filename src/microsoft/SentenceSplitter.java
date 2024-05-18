package microsoft;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SentenceSplitter {

    private static final int CHUNK_SIZE = 50;


    public static void main(String[] args) {
        String input = "Hello, world! This is a sample text. It has multiple sentences.";
        List<String> sentences = splitIntoSentences(input);
        List<String> groupedSentences = optimizeSentenceSplitToFitChunkSize(sentences);
        for (String sentence : groupedSentences) {
            System.out.println(sentence);
        }
    }

    public static List<String> splitIntoSentences(String input) {
        List<String> sentences = new ArrayList<>();
        BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.US);
        iterator.setText(input);
        int start = iterator.first();

        for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator.next()) {
            sentences.add(input.substring(start, end));
        }

        return sentences;
    }

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
