import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.util.NoSuchElementException;

public class FrontendTests {
    /**
     * Test that the shortest path prompt HTML contains the correct input fields and button.
     */
    @Test
    public void roleTest1() {
        Frontend frontend = new Frontend(new Backend_Placeholder(new Graph_Placeholder()));
        String html = frontend.generateShortestPathPromptHTML();

        assertTrue(html.contains("id='start'"));
        assertTrue(html.contains("id='end'"));
        assertTrue(html.contains("Find Shortest Path"));
    }

    /**
     * Test that generateShortestPathResponseHTML returns correct HTML structure
     * and includes the full hardcoded placeholder path and travel time.
     */
    @Test
    public void roleTest2() {
        Frontend frontend = new Frontend(new Backend_Placeholder(new Graph_Placeholder()));
        String html = frontend.generateShortestPathResponseHTML("Union South", "Weeks Hall for Geological Sciences");

        assertTrue(html.contains("<p>Shortest path from Union South to Weeks Hall for Geological Sciences:</p>"));
        assertTrue(html.contains("<ol>"));
        assertTrue(html.contains("<li>Union South</li>"));
        assertTrue(html.contains("<li>Computer Sciences and Statistics</li>"));
        assertTrue(html.contains("<li>Weeks Hall for Geological Sciences</li>"));
        assertTrue(html.contains("</ol>"));
        assertTrue(html.contains("Total travel time: 6.0 seconds"));
    }

    /**
     * Test that the furthest destination prompt and response HTML behave correctly.
     */
    @Test
    public void roleTest3() {
        Frontend frontend = new Frontend(new Backend_Placeholder(new Graph_Placeholder()));

        // Test the prompt HTML for correct fields
        String promptHtml = frontend.generateFurthestDestinationFromPromptHTML();
        assertTrue(promptHtml.contains("id='from'"));
        assertTrue(promptHtml.contains("Furthest Destination From"));

        // Test the response HTML for correct location and path
        String responseHtml = frontend.generateFurthestDestinationFromResponseHTML("Union South");
        assertTrue(responseHtml.contains("Furthest destination from Union South is Weeks Hall for Geological Sciences"));
        assertTrue(responseHtml.contains("<ol>"));
        assertTrue(responseHtml.contains("<li>Union South</li>"));
        assertTrue(responseHtml.contains("<li>Computer Sciences and Statistics</li>"));
        assertTrue(responseHtml.contains("<li>Weeks Hall for Geological Sciences</li>"));
        assertTrue(responseHtml.contains("</ol>"));
    }
}

