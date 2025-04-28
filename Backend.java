//////////////////////////////////////////////////
//
// Title: Backend.java
// Course: CS 400
//
// Author: Saketh Adusumilli
// Email: adusumilli4@wisc.edu
// Lecturer: Gary Dahl
//
//////////////////////////////////////////////////

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.io.File;
import java.util.Scanner;

/**
 * Implements the Backend methods through the use of the BackendInterface interface
 */
public class Backend implements BackendInterface {

  // Instance Fields
  private GraphADT<String, Double> graph;

  /**
   * Constructor for the Backend class.
   * 
   * @param graph is the object to store the backend's graph data
   */
  public Backend(GraphADT<String, Double> graph) {
    this.graph = graph;
  }

  /**
   * Loads graph data from a dot file. If a graph was previously loaded, this method first deletes
   * the contents (nodes and edges) of the existing graph before loading a new one.
   * 
   * @param filename is the path to a dot file to read graph data from
   * @throws IOException if there was any problem reading from this file
   * @see the method is overridden from the BackendInterface interface
   */
  @Override
  public void loadGraphData(String filename) throws IOException {

    // Checks if the graph was previously loaded and if so deletes the contents
    if (graph.getNodeCount() != 0) {
      List<String> allNodes = graph.getAllNodes();
      for (int i = 0; i < allNodes.size(); i++) {
        graph.removeNode(allNodes.get(i));
      }
    }

    // Checks if the file exists and throws an IOException if it doesn't
    File file = new File(filename);
    if (!file.exists()) {
      throw new IOException("The file does not exist.");
    }

    // Creates a Scanner object to read the file and checks if it is not empty
    Scanner sc = new Scanner(file);
    if (!(sc.hasNextLine())) {
      sc.close();
      throw new IOException("The file is empty.");
    }

    // Reads the file and loads the graph with it's data
    while (sc.hasNextLine()) {
      String line = sc.nextLine();

      // Makes sure the parsing of the contents of the file starts after and ends before the curly brace
      if (!(line.contains("{") || line.contains("}"))) {

        // Retrieves the starting and ending indexes of the predecessor node, successor node, and the weight of the edge
        int predStart = line.indexOf("\"") + 1;
        int predEnd = line.indexOf("\" -> ");
        int succStart = line.indexOf("-> \"") + 4;
        int succEnd = line.indexOf(" [") - 1;
        int weightStart = line.indexOf("[seconds=") + 9;
        int weightEnd = line.indexOf("]");

        // Makes sure the format of the file is valid
        if (predStart == -1 || predEnd == -1 || succStart == -1 || succEnd == -1 || weightStart == -1 || weightEnd == -1) {
          sc.close();
          throw new IOException("The format of the file is invalid.");
        }

        try {

          // Retrieves the predecessor, successor, and the weight based on the indexes
          String pred = line.substring(predStart, predEnd);
          String succ = line.substring(succStart, succEnd);
          Double weight = Double.parseDouble(line.substring(weightStart, weightEnd));

          // Inserts the nodes and the edge if they don't already exist
          if (!(graph.containsNode(pred))) {
            graph.insertNode(pred);
          }
          if (!(graph.containsNode(succ))) {
            graph.insertNode(succ);
          }
          if (!(graph.containsEdge(pred, succ))) {
            graph.insertEdge(pred, succ, weight);
          }

          // Makes sure the file is in the correct format
        } catch (IndexOutOfBoundsException e) {
          sc.close();
          throw new IOException("The format of the file is invalid.");

          // Makes sure the weight is always mentioned
        } catch (NullPointerException e) {
          sc.close();
          throw new IOException("The weight of the edge is not given.");

          // Makes sure the weight is the correct data type of Double
        } catch (NumberFormatException e) {
          sc.close();
          throw new IOException("The weight of the edge is not in the correct data type.");

          // Catches any other unexpected exceptions to make sure all of the data is loaded into the graph
        } catch (Exception e) {
          sc.close();;
          throw new IOException("There was a problem with reading the file.");
        }
      }
    }
    sc.close();
  }

  /**
   * Returns a list of all locations (node data) available in the graph.
   * 
   * @return list of all location names
   * @see the method is overridden from the BackendInterface interface
   */
  @Override
  public List<String> getListOfAllLocations() {

    // Calls graph's getAllNodes() method to retrieve all locations
    List<String> locations = graph.getAllNodes();
    return locations;
  }

  /**
   * Return the sequence of locations along the shortest path from startLocation to endLocation, or
   * an empty list if no such path exists.
   * 
   * @param startLocation is the start location of the path
   * @param endLocation is the end location of the path
   * @return a list with the nodes along the shortest path from startLocation to endLocation, or an empty list if no such path exists
   * @see the method is overridden from the BackendInterface interface
   */
  @Override
  public List<String> findLocationsOnShortestPath(String startLocation, String endLocation) {
    List<String> shortestPath = new ArrayList<String>();
    try {

      // Calls graph's shortestPathData() to retrieve all locations on the shortest path
      shortestPath = graph.shortestPathData(startLocation, endLocation);

      // If graph's shortestPathData() throws NoSuchElementException, then it means no path exists
    } catch (NoSuchElementException e) {
      return shortestPath;
    }
    return shortestPath;
  }

  /**
   * Return the walking times in seconds between each two nodes on the shortest path from
   * startLocation to endLocation, or an empty list if no such path exists.
   * 
   * @param startLocation is the start location of the path
   * @param endLocation is the end location of the path
   * @return a list with the walking times in seconds between two nodes along the shortest path from startLocation to endLocation, or an empty list if no such path exists
   */
  @Override
  public List<Double> findTimesOnShortestPath(String startLocation, String endLocation) {

    List<Double> shortestPathTimes = new ArrayList<Double>();
    try {

      // Calls graph's shortestPathData() to first retrieve the shortest path
      List<String> shortestPathLocations = graph.shortestPathData(startLocation, endLocation);

      // Finds the times between two nodes in the shortest path
      for (int i = 0; i < (shortestPathLocations.size() - 1); i++) {

        // Calls graph's getEdge() method to retrieve the time between two nodes
        shortestPathTimes.add(graph.getEdge(shortestPathLocations.get(i), shortestPathLocations.get(i + 1)));
      }

      // If graph's shortestPathData() throws NoSuchElementException, then it means no path exists
    } catch (NoSuchElementException e) {
      return shortestPathTimes;
    }
    return shortestPathTimes;
  }

  /**
   * Returns the most distant location (the one that takes the longest time to reach) when comparing
   * all shortest paths that begin from the provided startLocation.
   * 
   * @param startLocation is the location to find the most distant location from
   * @return the most distant location (the one that takes the longest time to reach which following the shortest path)
   * @throws NoSuchElementException if startLocation does not exist, or if there are no other locations that can be reached from there
   * @see the method is overridden from the BackendInterface interface
   */
  @Override
  public String getFurthestDestinationFrom(String startLocation) throws NoSuchElementException {
    String currentLocation = null;
    Double currentTime = 0.0;

    // Makes sure the startLocation exists
    if (!(graph.containsNode(startLocation))) {
      throw new NoSuchElementException("The location does not exist.");
    }

    // Makes sure there is at least 1 location that can be reached
    if (graph.getNodeCount() < 2) {
      throw new NoSuchElementException("There are no other locations that can be reached from there.");
    }

    // First gets all nodes in the graph by using graph's getAllNodes() method to find the most distant location
    List<String> allLocations = graph.getAllNodes();

    // Checks the time from the startLocation to every location to find the most distant location
    for (int i = 0; i < graph.getNodeCount(); i++) {
      try {
        String possibleLocation = allLocations.get(i);

        // Calls graph's shortestPathCost() to find the time between the startLocation and the possibleLocation
        Double possibleTime = graph.shortestPathCost(startLocation, possibleLocation);

        // Makes sure the possibleLocation isn't the same as the startLocation
        if (!(possibleLocation.equals(startLocation))) {

          // Updates the currentLocation if there is no value in it already regardless of the time
          if (currentLocation == null) {
            currentLocation = possibleLocation;
            currentTime = possibleTime;
          } else {

            // Only updates the currentLocation if the possibleTime is greater than the currentTime, which means there is a more distant location
            if (currentTime < possibleTime) {
              currentLocation = possibleLocation;
              currentTime = possibleTime;
            }
          }
        }
      }

      // If shortestPathCost() throws a NoSuchElementException, then it means no path exists for the location and the next location can be checked
      catch (NoSuchElementException e) {
      }
    }

    // Makes sure that there is a location that can be reached from startLocation
    if (currentLocation == null) {
      throw new NoSuchElementException("There are no other locations that can be reached from there.");
    }
    return currentLocation;
  }
}
