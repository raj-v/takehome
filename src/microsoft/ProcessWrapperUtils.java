package microsoft;

public class ProcessWrapperUtils {
    public static final int CHUNK_SIZE = 600;
    public static final int OVERHEAD = 100;
    public static final int ADJUSTED_CHUNK_SIZE = CHUNK_SIZE - OVERHEAD;

    public static void slowDownToAvoidRateLimiting() {
        try {
            // sleep 30 secs
            Thread.sleep(30 * 1000);
        }catch (InterruptedException e) {
            System.err.println("Interrupted while slowing down");
        }
    }
}
