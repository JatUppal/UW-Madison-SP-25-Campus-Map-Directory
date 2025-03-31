import java.util.List;

public class Frontend implements FrontendInterface{

    // reference to the backend (so we can call backend methods)
    private BackendInterface backend;

    /**
     * Constructor that takes a BackendInterface object
     * @param backend is used for shortest path computations
     */
    public Frontend(BackendInterface backend) {
        this.backend = backend; // Initialize backend
    }

    /**
     * Returns an HTML fragment that can be embedded within the body of a
     * larger html page.  This HTML output should include:
     * - a text input field with the id="start", for the start location
     * - a text input field with the id="end", for the destination
     * - a button labelled "Find Shortest Path" to request this computation
     * Ensure that these text fields are clearly labelled, so that the user
     * can understand how to use them.
     *
     * @return an HTML string that contains input controls that the user can
     * make use of to request a shortest path computation
     */
    @Override
    public String generateShortestPathPromptHTML() {
        StringBuilder html = new StringBuilder();
        html.append("<input type='text' id='start' placeholder='Enter the starting location:'>");
        html.append("<input type='text' id='end' placeholder='Enter the final destination:'>");
        html.append("<button id='findShortest'>Find Shortest Path</button>");
        return html.toString();
    }

    /**
     * Returns an HTML fragment that can be embedded within the body of a
     * larger html page.  This HTML output should include:
     * - a paragraph (p) that describes the path's start and end locations
     * - an ordered list (ol) of locations along that shortest path
     * - a paragraph (p) that includes the total travel time along this path
     * Or if there is no such path, the HTML returned should instead indicate
     * the kind of problem encountered.
     *
     * @param start is the starting location to find a shortest path from
     * @param end   is the destination that this shortest path should end at
     * @return an HTML string that describes the shortest path between these
     * two locations
     */
    @Override
    public String generateShortestPathResponseHTML(String start, String end) {
        List<String> locations = backend.findLocationsOnShortestPath(start, end);
        List<Double> times = backend.findTimesOnShortestPath(start, end);

        if (locations == null || locations.isEmpty()) {
            return "<p>No path found between " + start + " and " + end + ".</p>";
        }

        StringBuilder html = new StringBuilder();
        html.append("<p>Shortest path from " + start + " to " + end + ":</p>");

        html.append("<ol>");
        for (String loc : locations) {
            html.append("<li>" + loc + "</li>");
        }
        html.append("</ol>");

        if (times == null || times.isEmpty()) {
            return "<p>There is no travel time found between " + start + " and " + end + ".</p>";
        }
        else{
            double totalTime = 0.0;
            for (Double t : times) {
                totalTime += t;
            }
            html.append("<p>Total travel time: " + totalTime + " seconds</p>");
        }

        return html.toString();

    }

    /**
     * Returns an HTML fragment that can be embedded within the body of a
     * larger html page.  This HTML output should include:
     * - a text input field with the id="from", for the start location
     * - a button labelled "Furthest Destination From" to submit this request
     * Ensure that this text field is clearly labelled, so that the user
     * can understand how to use it.
     *
     * @return an HTML string that contains input controls that the user can
     * make use of to request a furthest destination calculation
     */
    @Override
    public String generateFurthestDestinationFromPromptHTML() {
        StringBuilder html = new StringBuilder();
        html.append("<input type='text' id='from' placeholder='Enter the starting location:'>");
        html.append("<button id='findFurthest'>Furthest Destination From</button>");
        return html.toString();
    }

    /**
     * Returns an HTML fragment that can be embedded within the body of a
     * larger html page.  This HTML output should include:
     * - a paragraph (p) that describes the starting point being searched from
     * - a paragraph (p) that describes the furthest destination found
     * - an ordered list (ol) of locations on the path between these locations
     * Or if there is no such destination, the HTML returned should instead
     * indicate the kind of problem encountered.
     *
     * @param start is the starting location to find the furthest dest from
     * @return an HTML string that describes the furthest destination from the
     * specified start location
     */
    @Override
    public String generateFurthestDestinationFromResponseHTML(String start) {
        StringBuilder html = new StringBuilder();
        html.append("<p>Searching furthest destination from " + start + "...</p>");
        String furthest = backend.getFurthestDestinationFrom(start);
        if (furthest == null || furthest.isEmpty()) {
            return "<p>No furthest destination found from " + start + ".</p>";
        }
        html.append("<p>Furthest destination from " + start + " is " + furthest + ".</p>");
        html.append("<p>Locations on the path between " + start + " and " + furthest + ":</p>");
        html.append("<ol>");
        List<String> locations = backend.findLocationsOnShortestPath(start, furthest);
        for (String loc : locations) {
            html.append("<li>" + loc + "</li>");
        }
        html.append("</ol>");

        return html.toString();
    }
}

