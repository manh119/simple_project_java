package lab7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

class CrawlerTask implements Runnable {
    private URLPool urlPool;
    private int maxDepth;

    public CrawlerTask(URLPool urlPool, int maxDepth) {
        this.urlPool = urlPool;
        this.maxDepth = maxDepth;
    }
    
    /** 
     * 	pop a new URL from urlPool, and it to visitedURLs
     * 	connect to this URL by HTTP connect 
     * 	take new URL from HTML page
     * 	check maxDepth and add new URL to pedingURLs
     * 										**/
    @Override
    public void run() {
    	while (true) {
            URLDepthPair currentPair = urlPool.getNewURLPair();
            urlPool.addToVisitedURLs(currentPair);
            int currentDepth = currentPair.getDepth();
        	String currentURL = currentPair.getURL();

        	if (currentDepth == this.maxDepth)
        		continue;
        	
            try { 
            	// connect to this URL by HTTP connect 
            	System.out.println("send request HTML to URL : " + currentDepth + " " + currentURL );
            	HttpsURLConnection connection = (HttpsURLConnection) new URL(currentURL).openConnection();
                connection.setRequestMethod("GET");                
                
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                
                // take new URL from HTML page 
                final String pattern = "https?://[^\\\"]+(?=\\\"\\>)";
                System.out.println("cut next URL from HTML web page...");
                
                while ((line = reader.readLine()) != null) {
        	        Pattern regex = Pattern.compile(pattern);
        	        Matcher matcher = regex.matcher(line);
        	        
        	        // add new URL to urlPool
        	        while (matcher.find()) {
        	            String newURL = matcher.group();  	        
                        URLDepthPair newPair = new URLDepthPair(newURL, currentDepth + 1);
                        urlPool.addToPendingURLs(newPair);
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