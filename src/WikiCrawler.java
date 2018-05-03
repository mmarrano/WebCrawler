import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * WikiCrawler class
 */
public class WikiCrawler {

    /**
     * BASE_URL variable to use
     */
    static final String BASE_URL = "https://en.wikipedia.org";

    /**
     * Given fileName in constructor
     */
    private String fileName;

    /**
     * Given max
     */
    private int max;

    /**
     * Given topics
     */
    private ArrayList<String> topics;

    /**
     * Graph for finding all vertices and edges
     */
    private Graph graph;

    /**
     * Queue for links
     */
    private Queue<String> queue;

    /**
     * HashSet for visited links
     */
    private HashSet<String> visited;


    /**
     * Constructor for WikiCrawler
     *
     * @param seedURL  Relative address of the seed URL (within Wiki domain)
     * @param max      Maximum number of pages to be crawled
     * @param topics   ArrayList of keywords that describe a particular topic
     * @param fileName Represents name of a file - The graph will be written to this file
     */
    public WikiCrawler(String seedURL, int max, ArrayList<String> topics, String fileName) {
        this.max = max;
        this.topics = topics;
        this.fileName = fileName;

        // Initialize graph
        graph = new Graph();

        // Initialize queue and visited
        queue = new LinkedList<>();
        visited = new HashSet<>();

        // Add seedURL to queue and visited
        queue.add(seedURL);
        visited.add(seedURL);
    }

    /**
     * Crawl message, this method executes the WikiCrawler.
     */
    public void crawl() {
        String currentPage;
        int requestCount = 0;

        PrintWriter pw = null;
        StringBuilder sb;

        try {
            // Create PrintWriter and StringBuilder
            pw = new PrintWriter(new FileOutputStream(fileName));
            sb = new StringBuilder();

            // While queue is not empty
            while (!queue.isEmpty()) {
                // Let currentPage be the first element of queue
                currentPage = queue.remove();
                String pageText = getPage(currentPage);

                if (pageText != null)
                    pageText = pageText.substring(pageText.indexOf("<p>"));

                // Sleep for 3 seconds every 25 requests
                requestCount++;
                if (requestCount % 25 == 0)
                    Thread.sleep(3000);

                // If the currentPage has the topic words in it
                if (topicCheck(pageText)) {
                    // Extract all links from currentPage
                    Set<String> links = new LinkedHashSet<>(getLinks(pageText));
                    links.remove(currentPage);

                    // For every link u that appears in Current Page
                    for (String link : links) {
                        boolean linkTopicCheck;
                        boolean underMax = visited.size() < max;

                        if (underMax && topics.size() != 0) {
                            String linkText = getPage(link);
                            if (linkText != null)
                                linkText = linkText.substring(linkText.indexOf("<p>"));

                            requestCount++;
                            if (requestCount % 25 == 0)
                                Thread.sleep(3000);

                            linkTopicCheck = topicCheck(linkText);
                        } else
                            linkTopicCheck = true;

                        // If the link has the topic words in it
                        if (linkTopicCheck) {
                            // Add like to queue and visited set. Craft an edge from vertex currentPage to link
                            if (underMax && !visited.contains(link)) {
                                queue.add(link);
                                visited.add(link);
                                sb.append(currentPage).append(" ").append(link).append(System.getProperty("line.separator"));
                                graph.addEdge(currentPage, link);
                            } else if (visited.contains(link)) { // If visited list contains like, append it to the StringBuilder
                                sb.append(currentPage).append(" ").append(link).append(System.getProperty("line.separator"));
                                graph.addEdge(currentPage, link);
                            }
                        }
                    }
                }
            }

            // Output results to file
            pw.println(graph.getVertices().size());
            pw.println(sb.toString());
        } catch (FileNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }

        // Close PrintWriter if it is not null
        if (pw != null)
            pw.close();

        //printToFile();
    }

    /**
     * Get links from the given page
     *
     * @param page Page we are getting the links from
     * @return ArrayList of links
     */
    private ArrayList<String> getLinks(String page) {
        ArrayList<String> links = new ArrayList<>();

        // Pattern to obtain the links
        String href = "\"/wiki/[^\"#:]+\"";
        Pattern pattern = Pattern.compile(href);
        Matcher matcher = pattern.matcher(page);

        // Find each match, and add them to the links ArrayList
        while (matcher.find()) {
            String resource = matcher.group();
            links.add(resource.substring(1, resource.length() - 1));
        }

        return links;
    }

    /**
     * Get the html text from the given source
     *
     * @param source Given source
     * @return Return the html code
     */
    private String getPage(String source) {
        StringBuilder page = new StringBuilder();

        try {
            // Send a request to server at currentPage to get currentPage's html text
            URL url = new URL(BASE_URL + source);
            InputStream is = url.openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            // Read lines from URL and add them to page
            String inputLine;
            while ((inputLine = br.readLine()) != null)
                page.append(inputLine).append("\n");

            return page.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Check to see if the given page has the topics in it
     *
     * @param page Given page
     * @return True if page contains all the topics, false otherwise
     */
    private boolean topicCheck(String page) {
        if (topics.size() != 0)
            for (String topic : topics)
                if (!page.contains(topic))
                    return false;
        return true;
    }

    /**
     * Prints graph to fileName
     */
    private void printToFile() {
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(fileName));

            // Print the number of vertices in the graph
            pw.println(graph.getVertices().size());

            // Create an iterator to iterate through the vertices
            ArrayList<Vertex> iteratorV = graph.getVertices();
            for (Vertex anIteratorV : iteratorV) {
                Hashtable<String, Edge> edges = anIteratorV.getEdges();
                Set<String> keys = edges.keySet();

                // Create an iterator to iterate through the edges
                for (String key : keys) {
                    Edge e = edges.get(key);
                    pw.println(e.from.getValue() + "    " + e.to.getValue());
                }
            }

            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get graph
     *
     * @return Graph graph
     */
    public Graph getGraph() {
        return graph;
    }
}