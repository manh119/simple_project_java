package lab6;

public class testString {
    public static void main(String[] args) {
        String line = "		<ul class=\"vector-menu-content-list\"><li id=\"n-mainpage-description\" class=\"mw-list-item\"><a href=\"/wiki/Main_Page\" title=\"Visit the main page [z]\" accesskey=\"z\"><span>Main page</span></a></li><li id=\"n-contents\" class=\"mw-list-item\"><a href=\"/wiki/Wikipedia:Contents\" title=\"Guides to browsing Wikipedia\"><span>Contents</span></a></li><li id=\"n-currentevents\" class=\"mw-list-item\"><a href=\"/wiki/Portal:Current_events\" title=\"Articles related to current events\"><span>Current events</span></a></li><li id=\"n-randompage\" class=\"mw-list-item\"><a href=\"/wiki/Special:Random\" title=\"Visit a randomly selected article [x]\" accesskey=\"x\"><span>Random article</span></a></li><li id=\"n-aboutsite\" class=\"mw-list-item\"><a href=\"/wiki/Wikipedia:About\" title=\"Learn about Wikipedia and how it works\"><span>About Wikipedia</span></a></li><li id=\"n-contactpage\" class=\"mw-list-item\"><a href=\"//en.wikipedia.org/wiki/Wikipedia:Contact_us\" title=\"How to contact Wikipedia\"><span>Contact us</span></a></li><li id=\"n-sitesupport\" class=\"mw-list-item\"><a href=\"https://donate.wikimedia.org/wiki/Special:FundraiserRedirector?utm_source=donate&amp;utm_medium=sidebar&amp;utm_campaign=C13_en.wikipedia.org&amp;uselang=en\" title=\"Support us by donating to the Wikimedia Foundation\"><span>Donate</span></a></li></ul>";
        int startIndex = line.indexOf("<a href=\"https://") + 9;
        int endIndex = line.indexOf("\"", startIndex);
        String newURL = line.substring(startIndex, endIndex);
        System.out.println(newURL);
    }
}

