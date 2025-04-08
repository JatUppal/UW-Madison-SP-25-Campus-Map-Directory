// === CS400 File Header Information ===
// Name: <Jay Uppal>
// Email: <juppal@wisc.edu>
// Group and Team: <your group name: two letters, and team color>
// Group TA: <name of your group's ta>
// Lecturer: <Gary Dahl>
// Notes to Grader: <optional extra notes>

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.*;


/**
 * This class extends the BaseGraph data structure with additional methods for
 * computing the total cost and list of node data along the shortest path
 * connecting a provided starting to ending nodes. This class makes use of
 * Dijkstra's shortest path algorithm.
 */
public class DijkstraGraph<NodeType, EdgeType extends Number>
        extends BaseGraph<NodeType, EdgeType>
        implements GraphADT<NodeType, EdgeType> {

    /**
     * While searching for the shortest path between two nodes, a SearchNode
     * contains data about one specific path between the start node and another
     * node in the graph. The final node in this path is stored in its node
     * field. The total cost of this path is stored in its cost field. And the
     * predecessor SearchNode within this path is referened by the predecessor
     * field (this field is null within the SearchNode containing the starting
     * node in its node field).
     *
     * SearchNodes are Comparable and are sorted by cost so that the lowest cost
     * SearchNode has the highest priority within a java.util.PriorityQueue.
     */
    protected class SearchNode implements Comparable<SearchNode> {
        public Node node;
        public double cost;
        public SearchNode predecessor;

        public SearchNode(Node node, double cost, SearchNode predecessor) {
            this.node = node;
            this.cost = cost;
            this.predecessor = predecessor;
        }

        public int compareTo(SearchNode other) {
            if (cost > other.cost)
                return +1;
            if (cost < other.cost)
                return -1;
            return 0;
        }
    }

    /**
     * Constructor that sets the map that the graph uses.
     */
    public DijkstraGraph() {
        super(new PlaceholderMap<>());
    }

    /**
     * This helper method creates a network of SearchNodes while computing the
     * shortest path between the provided start and end locations. The
     * SearchNode that is returned by this method is represents the end of the
     * shortest path that is found: it's cost is the cost of that shortest path,
     * and the nodes linked together through predecessor references represent
     * all of the nodes along that shortest path (ordered from end to start).
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return SearchNode for the final end node within the shortest path
     * @throws NoSuchElementException when no path from start to end is found
     *                                or when either start or end data do not
     *                                correspond to a graph node
     */
    protected SearchNode computeShortestPath(NodeType start, NodeType end) {
        if (!nodes.containsKey(start) || !nodes.containsKey(end)) {
            throw new NoSuchElementException("Start or end node not in graph");
        }

        // create a priority queue of SearchNodes
        PriorityQueue<SearchNode> pq = new PriorityQueue<>();
        // create a map of SearchNodes for each node in the graph
        MapADT<NodeType, Boolean> visited = new PlaceholderMap<>();

        while (!pq.isEmpty()) {
            // remove the SearchNode with the lowest cost from the queue
            SearchNode current = pq.poll();
            if (visited.containsKey(current.node.data)) continue;
            // mark the current node as visited
            visited.put(current.node.data, true);

            // if the current node is the end node then we have found the shortest path
            if (current.node.data.equals(end)) return current;

            // add all of the edges leaving this node to the queue
            for (Edge edge : current.node.edgesLeaving) {
                if (!visited.containsKey(edge.successor.data)) {
                    pq.add(new SearchNode(edge.successor, current.cost + edge.data.doubleValue(), current));
                }
            }
        }
        // if we reach here then there is no path from start to end
        throw new NoSuchElementException("No path from " + start.toString() + " to " + end.toString());
    }

    /**
     * Returns the list of data values from nodes along the shortest path
     * from the node with the provided start value through the node with the
     * provided end value. This list of data values starts with the start
     * value, ends with the end value, and contains intermediary values in the
     * order they are encountered while traversing this shorteset path. This
     * method uses Dijkstra's shortest path algorithm to find this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return list of data item from node along this shortest path
     */
    public List<NodeType> shortestPathData(NodeType start, NodeType end) {
        // compute the shortest path from start to end
        SearchNode endNode = computeShortestPath(start, end);
        // create a list to store the data values along the path
        List<NodeType> path = new java.util.ArrayList<>();
        // traverse the path from end to start, adding each node's data to the list
        SearchNode current = endNode;
        while (current != null) {
            // add the data value of the current node to the path
            // repeatedly adding at index 0 to push old nodes to the end
            path.add(0, current.node.data);
            current = current.predecessor;
        }
        // return the list of data values along the path
        return path;
	}

    /**
     * Returns the cost of the path (sum over edge weights) of the shortest
     * path freom the node containing the start data to the node containing the
     * end data. This method uses Dijkstra's shortest path algorithm to find
     * this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return the cost of the shortest path between these nodes
     */
    public double shortestPathCost(NodeType start, NodeType end) {
        // compute the shortest path from start to end
        SearchNode endNode = computeShortestPath(start, end);
        Double cost = endNode.cost;;

        // return the cost of the path
        return cost;
    }

    // TODO: implement 3+ tests in step 4.1
    /**
        * This test creates a graph with 10 nodes and 15 edges. It then checks
        * that the shortest path from node D to node L is correct, and that the
        * cost of that path is correct. The expected path is D -> G -> L, and
        * the expected cost is 9.0.
     */
    @Test
    public void test1() {
        BaseGraph<String, Double> graph = new DijkstraGraph<>();
        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("M");
        graph.insertNode("D");
        graph.insertNode("E");
        graph.insertNode("F");
        graph.insertNode("I");
        graph.insertNode("G");
        graph.insertNode("H");
        graph.insertNode("L");


        graph.insertEdge("A", "H", 7.0);
        graph.insertEdge("D", "A", 7.0);
        graph.insertEdge("G", "A", 4.0);
        graph.insertEdge("A", "B", 1.0);
        graph.insertEdge("A", "M", 5.0);
        graph.insertEdge("B", "M", 3.0);
        graph.insertEdge("H", "B", 6.0);
        graph.insertEdge("M", "I", 4.0);
        graph.insertEdge("M", "E", 3.0);
        graph.insertEdge("M", "F", 4.0);
        graph.insertEdge("I", "H", 2.0);
        graph.insertEdge("I", "D", 1.0);
        graph.insertEdge("H", "I", 2.0);
        graph.insertEdge("D", "F", 4.0);
        graph.insertEdge("D", "G", 2.0);
        graph.insertEdge("F", "G", 9.0);
        graph.insertEdge("G", "H", 9.0);
        graph.insertEdge("G", "L", 7.0);
        graph.insertEdge("H", "L", 2.0);

        assertEquals(Arrays.asList("D", "G", "L"), ((DijkstraGraph<String, Double>) graph).shortestPathData("D", "L"));
        assertEquals(9.0, ((DijkstraGraph<String, Double>) graph).shortestPathCost("D", "L"));
    }

    /**
        * This test creates a graph with 10 nodes and 15 edges. It then checks
        * that the shortest path from node A to node D is correct, and that the
        * cost of that path is correct. The expected path is A -> B -> M -> I ->
        * D, and the expected cost is 9.0.
     */
    @Test
    public void test2() {
        BaseGraph<String, Double> graph = new DijkstraGraph<>();
        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("M");
        graph.insertNode("D");
        graph.insertNode("E");
        graph.insertNode("F");
        graph.insertNode("I");
        graph.insertNode("G");
        graph.insertNode("H");
        graph.insertNode("L");


        graph.insertEdge("A", "H", 7.0);
        graph.insertEdge("D", "A", 7.0);
        graph.insertEdge("G", "A", 4.0);
        graph.insertEdge("A", "B", 1.0);
        graph.insertEdge("A", "M", 5.0);
        graph.insertEdge("B", "M", 3.0);
        graph.insertEdge("H", "B", 6.0);
        graph.insertEdge("M", "I", 4.0);
        graph.insertEdge("M", "E", 3.0);
        graph.insertEdge("M", "F", 4.0);
        graph.insertEdge("I", "H", 2.0);
        graph.insertEdge("I", "D", 1.0);
        graph.insertEdge("H", "I", 2.0);
        graph.insertEdge("D", "F", 4.0);
        graph.insertEdge("D", "G", 2.0);
        graph.insertEdge("F", "G", 9.0);
        graph.insertEdge("G", "H", 9.0);
        graph.insertEdge("G", "L", 7.0);
        graph.insertEdge("H", "L", 2.0);

        assertEquals(Arrays.asList("A", "B", "M", "I", "D"),
                ((DijkstraGraph<String, Double>) graph).shortestPathData("A",
                "D"));
        assertEquals(9.0, ((DijkstraGraph<String, Double>) graph).shortestPathCost("A", "D"));
    }

    /**
     * This test checks the behavior of the DijkstraGraph implementation when the
     * start and end nodes both exist in the graph, but there is no sequence of
     * directed edges that connects them. In this case, we add a node "Z" that is
     * disconnected from all other nodes. We attempt to compute the shortest path
     * from a connected node ("A") to the disconnected node ("Z").
     *
     * The test asserts that calling shortestPathData() or shortestPathCost()
     * between these nodes will throw a NoSuchElementException, as no valid path exists.
     */
    @Test
    public void test3() {
        BaseGraph<String, Double> graph = new DijkstraGraph<>();
        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("M");
        graph.insertNode("D");
        graph.insertNode("E");
        graph.insertNode("F");
        graph.insertNode("I");
        graph.insertNode("G");
        graph.insertNode("H");
        graph.insertNode("L");
        graph.insertNode("Z"); // Disconnected node

        graph.insertEdge("A", "H", 7.0);
        graph.insertEdge("D", "A", 7.0);
        graph.insertEdge("G", "A", 4.0);
        graph.insertEdge("A", "B", 1.0);
        graph.insertEdge("A", "M", 5.0);
        graph.insertEdge("B", "M", 3.0);
        graph.insertEdge("H", "B", 6.0);
        graph.insertEdge("M", "I", 4.0);
        graph.insertEdge("M", "E", 3.0);
        graph.insertEdge("M", "F", 4.0);
        graph.insertEdge("I", "H", 2.0);
        graph.insertEdge("I", "D", 1.0);
        graph.insertEdge("H", "I", 2.0);
        graph.insertEdge("D", "F", 4.0);
        graph.insertEdge("D", "G", 2.0);
        graph.insertEdge("F", "G", 9.0);
        graph.insertEdge("G", "H", 9.0);
        graph.insertEdge("G", "L", 7.0);
        graph.insertEdge("H", "L", 2.0);

        assertThrows(NoSuchElementException.class, () ->
                ((DijkstraGraph<String, Double>) graph).shortestPathData("A", "Z"));

        assertThrows(NoSuchElementException.class, () ->
                ((DijkstraGraph<String, Double>) graph).shortestPathCost("A", "Z"));
    }

}

