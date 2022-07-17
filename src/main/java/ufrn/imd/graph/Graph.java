package ufrn.imd.graph;

import lombok.Data;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Data
public class Graph {
    private static final AtomicInteger count = new AtomicInteger(0);
    private final AtomicInteger countNodes = new AtomicInteger(0);

    private final int id;
    private String name;
    private List<Node> nodes = new ArrayList<>();
    private ArrayList<ArrayList<Double>> adjacencyMatrix = new ArrayList<>(100);


    public Graph() {
        this.id = count.incrementAndGet();
        this.name = "Graph_" + id;
        initializeAdjacencyMatrix();
    }

    public Graph(String name) {
        this.id = count.incrementAndGet();
        this.name = name;
        initializeAdjacencyMatrix();
    }

    public Graph(int initialMatrixCapacity) {
        this.id = count.incrementAndGet();
        this.name = "Graph_" + id;
        initializeAdjacencyMatrix(initialMatrixCapacity * 2);
    }

    public Graph(Graph graph) {
        this.id = count.incrementAndGet();
        this.name = "Graph_" + id + "_copy";
        this.nodes = new ArrayList<>(graph.getNodes());
        this.adjacencyMatrix = new ArrayList<>(graph.getAdjacencyMatrix());
    }

    private void initializeAdjacencyMatrix() {
        initializeAdjacencyMatrix(100);
    }

    private void initializeAdjacencyMatrix(int initialMatrixCapacity) {
        for (int i = 0; i < initialMatrixCapacity; i++) {
            adjacencyMatrix.add(new ArrayList<>(initialMatrixCapacity));
            for (int j = 0; j < initialMatrixCapacity; j++) {
                adjacencyMatrix.get(i).add(null);
            }
        }
    }

    public boolean addNode(Coordinates coordinates) {
        return nodes.add(new Node(countNodes.incrementAndGet(), coordinates));
    }

    public boolean addNode(Node node) {
        return nodes.add(new Node(countNodes.incrementAndGet(), node.getCoordinates()));
    }

    public boolean removeNode(Node node) {
        return nodes.remove(node);
    }

    public double getDistance(Node node1, Node node2) {
        return adjacencyMatrix.get(node1.getId()).get(node2.getId());
    }

    public void clearMatrix() {
        adjacencyMatrix.clear();
    }

    public void clear() {
        nodes.clear();
        adjacencyMatrix.clear();
    }

    public void generateCompleteGraph() {
        clearMatrix();

        for (Node node1 : nodes) {
            for (Node node2 : nodes) {
                if (node1 != node2) addEdge(node1, node2);
            }
        }
    }

    public Graph shortestSpanningSubtree(boolean completeGraphIsGenerated) {
        if(!completeGraphIsGenerated) generateCompleteGraph();
        int nodesSize = nodes.size();

        Graph shortestSpanningSubtree = new Graph(nodesSize);
        Map.Entry<Node, Node> minimalEdge;

        Set<Node> notIncludedNodes = new HashSet<>(nodes);
        Set<Node> includedNodes = new HashSet<>();

        Node randomNode = nodes.get(0);
        shortestSpanningSubtree.addNode(randomNode);

        includedNodes.add(randomNode);
        notIncludedNodes.remove(randomNode);

        while(includedNodes.size() < nodesSize) {
            minimalEdge = findMinimalEdge(notIncludedNodes, includedNodes);

            shortestSpanningSubtree.addNode(minimalEdge.getValue());
            shortestSpanningSubtree.addEdge(minimalEdge.getKey(), minimalEdge.getValue());

            notIncludedNodes.remove(minimalEdge.getValue());
            includedNodes.add(minimalEdge.getValue());
        }

        return shortestSpanningSubtree;
    }

    private Map.Entry<Node, Node> findMinimalEdge(Set<Node> notIncludedNodes, Set<Node> includedNodes) {
        Map.Entry<Node, Node> minimalEdge = null;
        double distance;
        double minDistance = Double.MAX_VALUE;
        for(Node node1 : includedNodes) {
            for(Node node2 : notIncludedNodes) {
                if(node1.getId() != node2.getId()) {
                    distance = getDistance(node1, node2);
                    if(distance < minDistance) {
                        minimalEdge = new AbstractMap.SimpleEntry<>(node1, node2);
                        minDistance = distance;
                    }
                }
            }
        }
        return minimalEdge;
    }

    private void addEdge(Node node1, Node node2) {
        double distance = node1.calculeDistance(node2);

        verifyAdjacencyMatrix();

        adjacencyMatrix.get(node1.getId()).set(node2.getId(), distance);
        adjacencyMatrix.get(node2.getId()).set(node1.getId(), distance);
    }

    private void removeEdge(Node node1, Node node2) {
        int node1Index = nodes.indexOf(node1);
        int node2Index = nodes.indexOf(node2);
        adjacencyMatrix.get(node1Index).set(node2Index, 0d);
        adjacencyMatrix.get(node2Index).set(node1Index, 0d);
    }

    private void verifyAdjacencyMatrix() {
        int nodesSize = nodes.size();
        int maxNodeId = nodes.get(nodesSize - 1).getId();
        int adjacencyMatrixSize = adjacencyMatrix.size();

        if (adjacencyMatrixSize < maxNodeId + 1) {
            adjacencyMatrix.ensureCapacity(maxNodeId + 1);
            for (int i = adjacencyMatrixSize; i < maxNodeId + 1; i++) {
                adjacencyMatrix.add(new ArrayList<>(maxNodeId + 1));
                for (int j = 0; j < maxNodeId + 1; j++) {
                    adjacencyMatrix.get(i).add(0d);
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
//        sb.append("Nodes:\n");
//        for (Node node : nodes) {
//            sb.append(node.toString()).append("\n");
//        }
//        sb.append("Adjacency Matrix:\n");
//        for (Node node : nodes) {
//            sb.append(adjacencyMatrix.get(node.getId()).toString()).append("\n");
//        }
        return sb.toString();
    }
}
