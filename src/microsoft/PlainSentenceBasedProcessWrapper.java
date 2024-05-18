package microsoft;

import java.util.List;

public class PlainSentenceBasedProcessWrapper implements ProcessWrapper {

    private SentenceSplitter sentenceSplitter;
    private Processor processor;

    public PlainSentenceBasedProcessWrapper() {
        sentenceSplitter = new SentenceSplitter();
        processor = new Anthropic50WordSummarizer100TokenLimit();
    }

    public String processWrapper(String input) {
        List<String> splitSentences = sentenceSplitter.splitSentencesAndGroupByChunkSize(input,
                ProcessWrapperUtils.ADJUSTED_CHUNK_SIZE);
        StringBuilder sb = new StringBuilder();

        for (String s: splitSentences) {
            sb.append(processor.process(s));
            ProcessWrapperUtils.slowDownToAvoidRateLimiting();
        }
        return sb.toString();
    }

}
