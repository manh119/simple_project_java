package lab7;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Crawler {

    public static void main(String[] args) {

        String url = "https://www.babla.vn/tieng-anh-tieng-viet/hi";
        int maxDepth = 3;
        int numThreads = 100;

        URLPool urlPool = new URLPool(maxDepth);
        urlPool.addURLPair(new URLDepthPair(url, 0));

        for (int i = 0; i < numThreads; i++) {
            Thread crawlerThread = new Thread(new CrawlerTask(urlPool));
            crawlerThread.start();
        }

        while (urlPool.getWaitingThreads() < numThreads) {
            try {
            	System.out.println("numWaitingThreads : " + urlPool.getWaitingThreads());
                Thread.sleep(100); // Sleep for a short time before checking again
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }   
        System.exit(1);
    }
}

