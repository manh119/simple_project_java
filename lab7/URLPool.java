package lab7;

import java.util.*;

// List of URLDepthPair
class URLPool {
    private LinkedList<URLDepthPair> pendingURLs;
    private LinkedList<String> visitedURLs;
    private int nWaitingThreads;
    private int maxDepth;

    public URLPool(int maxDepth) {
        pendingURLs = new LinkedList<>();
        visitedURLs = new LinkedList<>();
        nWaitingThreads = 0;
        this.maxDepth = maxDepth;
    }
    
    /**
     * only add to pendingURLS if this URL haven't been in List
     * if this URL have depth >= maxDepth, it should add to visitedURL 
     * @param urlDepthPair
     */
    public synchronized void addURLPair(URLDepthPair urlDepthPair) {
    	String url = urlDepthPair.getURL();
        if (!visitedURLs.contains(url) && !pendingURLs.contains(urlDepthPair)) 
        {
        	if (urlDepthPair.getDepth() < maxDepth) {
        		pendingURLs.addLast(urlDepthPair);
                notify();
        	}            
            else 
            	visitedURLs.add(url);            	
        }
    }

    public synchronized URLDepthPair getURLPair() {
    	// wait for case URLpool is being empty
        while (pendingURLs.isEmpty()) {
            try {
                nWaitingThreads++;
                wait();			
                nWaitingThreads--;
            } catch (InterruptedException e) {
                // Handle interrupted exception if required
            	e.printStackTrace();
            }
        }

        URLDepthPair urlDepthPair = pendingURLs.removeFirst();
        visitedURLs.add(urlDepthPair.getURL());
        return urlDepthPair;
    }

    public synchronized int getWaitingThreads() {
        return nWaitingThreads;
    }
}
