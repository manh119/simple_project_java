package lab7;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

// List of URLDepthPair
class URLPool {
    private LinkedList<URLDepthPair> pendingURLs;
    private LinkedList<String> visitedURLs;
    private int nWaitingThreads;

    public URLPool() {
        pendingURLs = new LinkedList<>();
        visitedURLs = new LinkedList<>();
        nWaitingThreads = 0;
    }
    
    /**
     * only add to pendingURLS if this URL haven't been in List
     * @param urlDepthPair
     */
    public synchronized void addToPendingURLs(URLDepthPair urlDepthPair) {    	
        if (!pendingURLs.contains(urlDepthPair)) 
        { 
        	pendingURLs.addLast(urlDepthPair);
            notify();
        }            
    }

    public synchronized void addToVisitedURLs(URLDepthPair urlDepthPair) {
    	String url = urlDepthPair.getURL();
    	if (!visitedURLs.contains(url))
    		visitedURLs.add(url);
    }
    
    /* get and remove URLPair from pending List
     * If pendingURLs is empty, must wait() until it has a new URL
     * */
    public synchronized URLDepthPair getNewURLPair() {
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
        return urlDepthPair;
    }

    public synchronized LinkedList<String> getVistedThreads() {
        return this.visitedURLs;
    }
    
    public synchronized int getWaitingThreads() {
        return nWaitingThreads;
    }
}
