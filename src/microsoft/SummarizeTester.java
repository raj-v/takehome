package microsoft;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SummarizeTester {

    private static final String FEEDBACK_INTEGRATED_OUTPUT_FILE_NAME = "feedbackIntegratedProcessWrapper.out";
    private static final String PLAIN_SENTENCE_WRAPPER_OUTPUT_FILE_NAME = "plainSentenceBasedProcessWrapper.out";

    public static void writeStringToFile(String fileName, String content) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        try {
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            System.err.println("Failed to write to file");
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        String inputText = """
        In a small village by the sea lived a fisherman named Tom. Every morning, Tom would sail out in his old boat to catch fish.
        One day, the sea was calm, the sky clear. Tom cast his net, waiting patiently. Suddenly, he felt a strong tug. He pulled and pulled, his heart racing. 
        Finally, he saw a glimmer in the water. To his surprise, it was a golden fish. The fish spoke, asking to be let go and promising to grant a wish. 
        Tom was astonished but quickly thought of his family. He wished for enough food and money to live comfortably. The fish nodded and disappeared into the depths. 
        Tom sailed back, doubting what had happened. When he reached home, he found his tiny cottage transformed into a beautiful house. 
        His wife and children were overjoyed. They had fresh food and new clothes. Tom never saw the golden fish again but he never forgot its kindness. 
        He became the most generous man in the village, always helping others. His family lived happily and Tom's story was told for generations. 
        The villagers learned that kindness and generosity could bring magical changes, just like the golden fish had brought to Tom.
        """;


        PlainSentenceBasedProcessWrapper plainSentenceBasedProcessWrapper = new PlainSentenceBasedProcessWrapper();
        String summary = plainSentenceBasedProcessWrapper.processWrapper(inputText);
        writeStringToFile(PLAIN_SENTENCE_WRAPPER_OUTPUT_FILE_NAME, summary);

        FeedbackIntegratedProcessWrapper feedbackIntegratedProcessWrapper = new FeedbackIntegratedProcessWrapper();
        summary = feedbackIntegratedProcessWrapper.processWrapper(inputText);
        writeStringToFile(FEEDBACK_INTEGRATED_OUTPUT_FILE_NAME, summary);

    }

}
