package lab6;
import java.net.*;

class URLDepthPair {
    private final String url;
    private final int depth;

    public URLDepthPair(String url, int depth) {
        this.url = url;
        this.depth = depth;
    }

    public String getURL() {
        return url;
    }

    public int getDepth() {
        return depth;
    }

    @Override
    public String toString() {
        return "depth " + depth + ": " + url;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof URLDepthPair) {
            URLDepthPair other = (URLDepthPair) obj;
            return this.url.equals(other.url) && this.depth == other.depth;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + url.hashCode();
        result = 31 * result + depth;
        return result;
    }
    
 
}


