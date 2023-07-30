package lab6;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import java.util.LinkedList;

public class Crawler {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("must usage: java Crawler <URL> <depth>");
            return;
        }
        
        String url = "https://en.wikipedia.org/wiki/GSM";
        int maxDepth = 5;
//        String url = args[0];
//        int maxDepth = Integer.parseInt(args[1]);

        Crawler crawler = new Crawler();
        crawler.crawl(url, maxDepth);
    }

    public void crawl(String startingURL, int maxDepth) {
        LinkedList<URLDepthPair> toVisit = new LinkedList<>();
        toVisit.add(new URLDepthPair(startingURL, 0));

        LinkedList<URLDepthPair> visited = new LinkedList<>();

        while (!toVisit.isEmpty()) {
            URLDepthPair currentPair = toVisit.removeFirst();
            int currentDepth = currentPair.getDepth();

            if (currentDepth > maxDepth) {
                continue;
            }

            if (!visited.contains(currentPair)) {
                visited.add(currentPair);
                System.out.println(currentPair);
            }
            
            // connect to currentURL
            try {            	
            	String currentURL = currentPair.getURL();
            	System.out.println("send request HTML to URL : " + currentURL);
            	HttpsURLConnection connection = (HttpsURLConnection) new URL(currentURL).openConnection();
                connection.setRequestMethod("GET");                
                
                // Đọc và hiển thị nội dung HTML
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                
                // cut next URL from HTML web page and add it to toVisit LinkList 
                System.out.println("cut next URL from HTML web page...");
                while ((line = reader.readLine()) != null) {
                	while (line.contains("<a href=\"https://")) {
                		int startIndex = line.indexOf("<a href=\"https://") + 9;
                        int endIndex = line.indexOf("\"", startIndex);

                        String newURL = line.substring(startIndex, endIndex);

                        URLDepthPair newPair = new URLDepthPair(newURL, currentDepth + 1);
                        if (!visited.contains(newPair) && !toVisit.contains(newPair)) {
                        	toVisit.add(newPair);
                        }
                        
                        // remaining of line
                        line = line.substring(endIndex + 1);
                    }
                }
	
                reader.close();
                connection.disconnect();
            } catch (MalformedURLException e) {
                System.err.println("Malformed URL: " + currentPair.getURL());
            } catch (IOException e) {
                System.err.println("Error connecting to: " + currentPair.getURL());
            }
        }
    }
}
