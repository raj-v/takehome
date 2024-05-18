package microsoft;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SentenceSplitter {

    public List<String> splitIntoSentences(String input) {
        List<String> sentences = new ArrayList<>();
        BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.US);
        iterator.setText(input);
        int start = iterator.first();

        for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator.next()) {
            sentences.add(input.substring(start, end));
        }

        return sentences;
    }

    public List<String> optimizeSentenceSplitToFitChunkSize(List<String> sentences, int chunkSize) {
        List<String> optimized = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sentences.size(); i ++) {
            if (sb.length() + sentences.get(i).length() < chunkSize) {
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

    public List<String> splitSentecesAndGroupForChunkSize(String input, int chunkSize) {
        List<String> splitSentences = splitIntoSentences(input);
        List<String> groupedToFitChunkSize = optimizeSentenceSplitToFitChunkSize(splitSentences, chunkSize);
        return groupedToFitChunkSize;
    }

}
