package microsoft;

import java.util.List;

public class FeedbackIntegratedProcessWrapper implements ProcessWrapper {

    private SentenceSplitter sentenceSplitter;
    private Processor processor;

    public FeedbackIntegratedProcessWrapper() {
        sentenceSplitter = new SentenceSplitter();
        processor = new Anthropic50WordSummarizer100TokenLimit();
    }

    public String processWrapper(String input) {
        List<String> splitSentences = sentenceSplitter.splitSentecesAndGroupForChunkSize(input,
                ProcessWrapperUtils.ADJUSTED_CHUNK_SIZE);
        String summarySoFar = null;
        for (String s: splitSentences) {
            if (summarySoFar == null || summarySoFar.isEmpty()) {
                summarySoFar = processor.process(s);
            } else {
                summarySoFar = processor.process(summarySoFar + s);
            }
            ProcessWrapperUtils.slowDownToAvoidRateLimiting();
        }
        return summarySoFar;
    }

}
