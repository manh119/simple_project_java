package lab6;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularExpressiontest {

	    public static void main(String[] args) {
	        // Dòng văn bản của bạn
	        String line = "line : <link rel=\"canonical\" href=\"https://en.wikipedia.org/wiki/GSM\">\n"
	        		+ "line : <link rel=\"license\" href=\"https://creativecommons.org/licenses/by-sa/4.0/deed.en\">\n"
	        		+ "line : <link rel=\"alternate\" type=\"application/atom+xml\" title=\"Wikipedia Atom feed\" href=\"/w/index.php?title=Special:RecentChanges&amp;feed=atom\">\n"
	        		+ "line : <link rel=\"dns-prefetch\" href=\"//meta.wikimedia.org\" />\n"
	        		+ "line : <link rel=\"dns-prefetch\" href=\"//login.wikimedia.org\">\n"
	        		+ "line : </head>";
	        // Sử dụng regular expression để tìm các link HTTP
	        String pattern = "https?://[^\\\"]+(?=\\\")";
	        List<String> links = findLinks(line, pattern);

	        // In kết quả
	        for (String link : links) {
	            System.out.println(link);
	        }
	    }

	    public static List<String> findLinks(String text, String pattern) {
	        List<String> links = new ArrayList<>();
	        Pattern regex = Pattern.compile(pattern);
	        Matcher matcher = regex.matcher(text);
	        int i = 0;
	        while (matcher.find()) {
	        	
	            links.add(matcher.group());
	            System.out.println("matcher " + i + " " + matcher.group());
	            i++;
	        }
	        return links;
	    }
	}

