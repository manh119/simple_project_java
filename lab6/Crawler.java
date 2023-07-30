package lab6;
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
                final String pattern = "https?://[^\\\"]+(?=\\\")";
                System.out.println("cut next URL from HTML web page...");
                while ((line = reader.readLine()) != null) {
        	        Pattern regex = Pattern.compile(pattern);
        	        Matcher matcher = regex.matcher(line);
        	        
        	        while (matcher.find()) {
        	            String newURL = matcher.group();       	        

                        URLDepthPair newPair = new URLDepthPair(newURL, currentDepth + 1);
                        if (!visited.contains(newPair) && !toVisit.contains(newPair)) {
                        	toVisit.add(newPair);
                        }
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
