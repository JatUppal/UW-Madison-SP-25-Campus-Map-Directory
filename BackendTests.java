//////////////////////////////////////////////////
//
// Title: BackendTests.java
// Course: CS 400
//
// Author: Saketh Adusumilli
// Email: adusumilli4@wisc.edu
// Lecturer: Gary Dahl
//
//////////////////////////////////////////////////

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Tests the methods in Backend.java
 */
public class BackendTests {

  /**
   * Tests the loadGraphData() method
   */
  @Test
  public void backendTest1() {

    // Creates a graph and a Backend to call the method
    Graph_Placeholder graph = new Graph_Placeholder();
    Backend backend = new Backend(graph);

    try {

      // Calls loadGraphData() on a valid file
      backend.loadGraphData("campus.dot");
    } catch (IOException e) {
      assertTrue(false, "Threw an IOException when not suppose to.");
    } catch (Exception e) {
      assertTrue(false, "Threw an exception when not suppose to.");
    }

    // Checks if loadGraphData() correctly loaded the data into the graph
    assertTrue(graph.containsNode("Memorial Union"), "loadGraphData() didn't correctly load the data.");

    try {

      // Calls loadGraphData() on a valid file in order to delete the current contents in the graph
      backend.loadGraphData("campus.dot");
    } catch (IOException e) {
      assertTrue(false, "Threw an IOException when not suppose to.");
    } catch (Exception e) {
      assertTrue(false, "Threw an exception when not suppose to.");
    }

    // Checks if loadGraphData() correctly deleted the contents in the graph before adding in the new data
    assertFalse(graph.containsNode("Union South"), "loadGraphData() didn't correctly delete previous data");
    assertTrue(graph.containsNode("Memorial Union"), "loadGraphData() didn't correctly load the data.");
    assertTrue(graph.containsNode("Science Hall"), "loadGraphData() didn't correctly load the data.");

    try {

      // Calls loadGraphData() on an invalid file to see if an IOException is thrown
      backend.loadGraphData("file");
      assertTrue(false, "loadGraphData() didn't correctly throw an IOException");
    } catch (IOException e) {
    }
  }

  /**
   * Tests the getListOfAllLocations() method
   */
  @Test
  public void backendTest2() {

    // Creates a graph and a Backend to call the method
    Graph_Placeholder graph = new Graph_Placeholder();
    Backend backend = new Backend(graph);

    // Calls the loadGraphData() method to add the data into the graph
    try {
      backend.loadGraphData("campus.dot");
    } catch (Exception e) {
    }

    // Checks if the getListOfAllLocations() correctly returns the list of all locations
    List<String> returnList = backend.getListOfAllLocations();
    List<String> expectedList = new ArrayList<String>();
    expectedList.add("Union South");
    expectedList.add("Computer Sciences and Statistics");
    expectedList.add("Weeks Hall for Geological Sciences");
    expectedList.add("Memorial Union");
    assertTrue(returnList.equals(expectedList), "getListOfAllLocations() didn't correctly a list of all locations.");
  }

  /**
   * Tests the findLocationsOnShortestPath() method
   */
  @Test
  public void backendTest3() {

    // Creates a graph and a Backend to call the method
    Graph_Placeholder graph = new Graph_Placeholder();
    Backend backend = new Backend(graph);

    // Calls the loadGraphData() method to add the data into the graph
    try {
      backend.loadGraphData("campus.dot");
    } catch (Exception e) {
    }

    // Checks if the findLocationsOnShortestPath() correctly returns the shortest path when the last node is skipped
    List<String> returnList1 = backend.findLocationsOnShortestPath("Union South", "Weeks Hall for Geological Sciences");
    List<String> expectedList1 = new ArrayList<String>();
    expectedList1.add("Union South");
    expectedList1.add("Computer Sciences and Statistics");
    expectedList1.add("Weeks Hall for Geological Sciences");
    assertTrue(returnList1.equals(expectedList1), "findLocationsOnShortestPath() didn't correctly return the shortest path.");

    // Checks if the findLocationsOnShortestPath() correctly returns the shortest path when the first node is skipped
    List<String> returnList2 = backend.findLocationsOnShortestPath("Computer Sciences and Statistics", "Memorial Union");
    List<String> expectedList2 = new ArrayList<String>();
    expectedList2.add("Computer Sciences and Statistics");
    expectedList2.add("Weeks Hall for Geological Sciences");
    expectedList2.add("Memorial Union"); // Change null
    assertTrue(returnList2.equals(expectedList2), "findLocationsOnShortestPath() didn't correctly return the shortest path.");

    // Checks if the findLocationsOnShortestPath() correctly returns an empty list if no path exists
    List<String> returnList3 = backend.findLocationsOnShortestPath("Psychology Building", null);
    assertTrue(returnList3.size() == 0, "findLocationsOnShortestPath() didn't correctly return an empty list when a path doesn't exist.");
  }

  /**
   * Tests the findTimesOnShortestPath() method
   */
  @Test
  public void backendTest4() {

    // Creates a graph and a Backend to call the method
    Graph_Placeholder graph = new Graph_Placeholder();
    Backend backend = new Backend(graph);

    // Calls the loadGraphData() method to add the data into the graph
    try {
      backend.loadGraphData("campus.dot");
    } catch (Exception e) {
    }

    // Checks if findTimesOnShortestPath() correctly returns the times on the shortest path when the last node is skipped
    List<Double> returnList1 = backend.findTimesOnShortestPath("Union South", "Weeks Hall for Geological Sciences");
    List<Double> expectedList1 = new ArrayList<Double>();
    expectedList1.add(1.0);
    expectedList1.add(2.0);
    assertTrue(returnList1.equals(expectedList1), "findTimesOnShortestPath() didn't correctly return the times on the shortest path.");

    // Checks if findTimesOnShortestPath() correctly returns the times on the shortest path when the first node is skipped
    List<Double> returnList2 = backend.findTimesOnShortestPath("Computer Sciences and Statistics", "Memorial Union");
    List<Double> expectedList2 = new ArrayList<Double>();
    expectedList2.add(2.0);
    expectedList2.add(3.0);
    assertTrue(returnList2.equals(expectedList2), "findTimesOnShortestPath() didn't correctly return the times on the shortest path.");

    // Checks if the findTimesOnShortestPath() correctly returns an empty list if no path exists
    List<Double> returnList3 = backend.findTimesOnShortestPath("Psychology Building", null);
    assertTrue(returnList3.size() == 0, "findTimesOnShortestPath() didn't correctly return an empty list when a path doesn't exist.");
  }

  /**
   * Tests the getFurthestDestinationFrom() method
   */
  @Test
  public void backendTest5() {

    // Creates a graph and a Backend to call the method
    Graph_Placeholder graph = new Graph_Placeholder();
    Backend backend = new Backend(graph);

    // Calls the loadGraphData() method to add the data into the graph
    try {
      backend.loadGraphData("campus.dot");
    } catch (Exception e) {
    }

    // Checks if getFurthestDestinationFrom() correctly finds the most distant location
    String resultValue = backend.getFurthestDestinationFrom("Union South");
    String expectedValue = "Memorial Union";
    assertTrue(resultValue.equals(expectedValue), "getFurthestDestinationFrom() didn't correctly find the most distant location.");

    // Checks if getFurthestDestinationFrom() correctly throws a NoSuchElementException when the startNode doesn't exist
    try {
      backend.getFurthestDestinationFrom("Psychology Building");
      assertTrue(false, "getFurthestDestinationFrom() did not throw a NoSuchElementException when it was suppose to");
    } catch (NoSuchElementException e) {
    } catch (Exception e) {
      assertTrue(false, "getFurthestDestinationFrom() threw an exception.");
    }
  }
}
