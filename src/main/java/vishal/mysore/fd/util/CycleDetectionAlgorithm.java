package vishal.mysore.fd.util;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Utility class for cycle detection algorithms.
 * Implements various graph algorithms for detecting cycles in fraud detection networks.
 */
public class CycleDetectionAlgorithm {

    /**
     * Depth-First Search (DFS) based cycle detection
     * Time Complexity: O(V + E) where V = vertices, E = edges
     */
    public static class DFSCycleDetector {
        private Map<String, List<String>> adjacencyList;
        private Set<String> visited;
        private Set<String> recursionStack;
        private List<List<String>> cycleList;

        public DFSCycleDetector(Map<String, List<String>> graph) {
            this.adjacencyList = graph;
            this.visited = new HashSet<>();
            this.recursionStack = new HashSet<>();
            this.cycleList = new ArrayList<>();
        }

        /**
         * Detect all cycles in the graph using DFS
         */
        public List<List<String>> detectAllCycles() {
            for (String node : adjacencyList.keySet()) {
                if (!visited.contains(node)) {
                    dfs(node, new ArrayList<>());
                }
            }
            return cycleList;
        }

        private void dfs(String node, List<String> path) {
            visited.add(node);
            recursionStack.add(node);
            path.add(node);

            for (String neighbor : adjacencyList.getOrDefault(node, new ArrayList<>())) {
                if (!visited.contains(neighbor)) {
                    dfs(neighbor, new ArrayList<>(path));
                } else if (recursionStack.contains(neighbor)) {
                    // Cycle found - extract the cycle
                    int cycleStartIndex = path.indexOf(neighbor);
                    if (cycleStartIndex != -1) {
                        List<String> cycle = new ArrayList<>(path.subList(cycleStartIndex, path.size()));
                        cycle.add(neighbor); // Complete the cycle
                        cycleList.add(cycle);
                    }
                }
            }

            recursionStack.remove(node);
        }

        /**
         * Check if a cycle exists
         */
        public boolean hasCycle() {
            return !detectAllCycles().isEmpty();
        }
    }

    /**
     * Floyd-Warshall based cycle detection
     * Useful for finding shortest cycles and all-pairs reachability
     */
    public static class FloydWarshallCycleDetector {
        private double[][] distanceMatrix;
        private int[][] nextNode;
        private int n;

        public FloydWarshallCycleDetector(List<String> nodes, List<Edge> edges) {
            this.n = nodes.size();
            initializeMatrices(nodes, edges);
        }

        private void initializeMatrices(List<String> nodes, List<Edge> edges) {
            distanceMatrix = new double[n][n];
            nextNode = new int[n][n];

            // Initialize with infinity
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (i == j) {
                        distanceMatrix[i][j] = 0;
                    } else {
                        distanceMatrix[i][j] = Double.POSITIVE_INFINITY;
                    }
                    nextNode[i][j] = -1;
                }
            }

            // Add edges
            for (Edge edge : edges) {
                int u = nodes.indexOf(edge.from);
                int v = nodes.indexOf(edge.to);
                distanceMatrix[u][v] = edge.weight;
                nextNode[u][v] = v;
            }
        }

        /**
         * Run Floyd-Warshall algorithm
         */
        public void computeShortestPaths() {
            for (int k = 0; k < n; k++) {
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (distanceMatrix[i][k] + distanceMatrix[k][j] < distanceMatrix[i][j]) {
                            distanceMatrix[i][j] = distanceMatrix[i][k] + distanceMatrix[k][j];
                            nextNode[i][j] = nextNode[i][k];
                        }
                    }
                }
            }
        }

        /**
         * Detect negative cycles (indicates fraud in transaction networks)
         */
        public boolean hasNegativeCycle() {
            for (int i = 0; i < n; i++) {
                if (distanceMatrix[i][i] < 0) {
                    return true;
                }
            }
            return false;
        }

        /**
         * Get nodes involved in negative cycles
         */
        public List<Integer> getNegativeCycleNodes() {
            List<Integer> nodes = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                if (distanceMatrix[i][i] < 0) {
                    nodes.add(i);
                }
            }
            return nodes;
        }
    }

    /**
     * Tarjan's Algorithm for finding Strongly Connected Components (SCCs)
     * Useful for identifying groups of accounts in circular transactions
     */
    public static class TarjanSCCDetector {
        private Map<String, List<String>> graph;
        private Map<String, Integer> index;
        private Map<String, Integer> lowLink;
        private Set<String> onStack;
        private Stack<String> stack;
        private int indexCounter;
        private List<List<String>> stronglyConnectedComponents;

        public TarjanSCCDetector(Map<String, List<String>> graph) {
            this.graph = graph;
            this.index = new HashMap<>();
            this.lowLink = new HashMap<>();
            this.onStack = new HashSet<>();
            this.stack = new Stack<>();
            this.indexCounter = 0;
            this.stronglyConnectedComponents = new ArrayList<>();
        }

        /**
         * Find all strongly connected components
         */
        public List<List<String>> findSCCs() {
            for (String node : graph.keySet()) {
                if (!index.containsKey(node)) {
                    strongConnect(node);
                }
            }
            return stronglyConnectedComponents;
        }

        private void strongConnect(String node) {
            index.put(node, indexCounter);
            lowLink.put(node, indexCounter);
            indexCounter++;
            stack.push(node);
            onStack.add(node);

            for (String neighbor : graph.getOrDefault(node, new ArrayList<>())) {
                if (!index.containsKey(neighbor)) {
                    strongConnect(neighbor);
                    lowLink.put(node, Math.min(lowLink.get(node), lowLink.get(neighbor)));
                } else if (onStack.contains(neighbor)) {
                    lowLink.put(node, Math.min(lowLink.get(node), index.get(neighbor)));
                }
            }

            if (lowLink.get(node).equals(index.get(node))) {
                List<String> component = new ArrayList<>();
                String w;
                do {
                    w = stack.pop();
                    onStack.remove(w);
                    component.add(w);
                } while (!w.equals(node));

                if (component.size() > 1) {
                    stronglyConnectedComponents.add(component);
                }
            }
        }

        /**
         * Get cycles (which are SCCs with size > 1)
         */
        public List<List<String>> getCycles() {
            return stronglyConnectedComponents.stream()
                    .filter(scc -> scc.size() > 1)
                    .collect(Collectors.toList());
        }
    }

    /**
     * Bellman-Ford Algorithm for detecting negative cycles in weighted graphs
     * Useful for detecting arbitrage or fraud in financial transactions
     */
    public static class BellmanFordCycleDetector {
        private List<String> nodes;
        private List<Edge> edges;
        private Map<String, Double> distance;

        public BellmanFordCycleDetector(List<String> nodes, List<Edge> edges) {
            this.nodes = nodes;
            this.edges = edges;
            this.distance = new HashMap<>();
            nodes.forEach(node -> distance.put(node, Double.POSITIVE_INFINITY));
        }

        /**
         * Detect negative cycles
         */
        public boolean hasNegativeCycle(String source) {
            distance.put(source, 0.0);

            // Relax edges |V| - 1 times
            for (int i = 0; i < nodes.size() - 1; i++) {
                for (Edge edge : edges) {
                    if (distance.get(edge.from) != Double.POSITIVE_INFINITY &&
                            distance.get(edge.from) + edge.weight < distance.get(edge.to)) {
                        distance.put(edge.to, distance.get(edge.from) + edge.weight);
                    }
                }
            }

            // Check for negative cycles
            for (Edge edge : edges) {
                if (distance.get(edge.from) != Double.POSITIVE_INFINITY &&
                        distance.get(edge.from) + edge.weight < distance.get(edge.to)) {
                    return true;
                }
            }

            return false;
        }

        /**
         * Get all affected nodes in negative cycle
         */
        public List<String> getNegativeCycleNodes(String source) {
            List<String> affectedNodes = new ArrayList<>();

            if (!hasNegativeCycle(source)) {
                return affectedNodes;
            }

            for (Edge edge : edges) {
                if (distance.get(edge.from) != Double.POSITIVE_INFINITY &&
                        distance.get(edge.from) + edge.weight < distance.get(edge.to)) {
                    affectedNodes.add(edge.to);
                }
            }

            return affectedNodes;
        }
    }

    /**
     * Represents an edge in the graph
     */
    public static class Edge {
        public String from;
        public String to;
        public double weight;

        public Edge(String from, String to, double weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }

    /**
     * Calculate cycle characteristics
     */
    public static class CycleAnalyzer {

        /**
         * Calculate average cycle length
         */
        public static double getAverageCycleLength(List<List<String>> cycles) {
            return cycles.stream()
                    .mapToInt(List::size)
                    .average()
                    .orElse(0.0);
        }

        /**
         * Find longest cycle
         */
        public static List<String> getLongestCycle(List<List<String>> cycles) {
            return cycles.stream()
                    .max(Comparator.comparingInt(List::size))
                    .orElse(new ArrayList<>());
        }

        /**
         * Find shortest cycle
         */
        public static List<String> getShortestCycle(List<List<String>> cycles) {
            return cycles.stream()
                    .min(Comparator.comparingInt(List::size))
                    .orElse(new ArrayList<>());
        }

        /**
         * Get frequency of nodes in cycles
         */
        public static Map<String, Integer> getNodeFrequency(List<List<String>> cycles) {
            Map<String, Integer> frequency = new HashMap<>();
            cycles.forEach(cycle -> cycle.forEach(node ->
                    frequency.put(node, frequency.getOrDefault(node, 0) + 1)
            ));
            return frequency;
        }

        /**
         * Get most frequently appearing nodes (highly suspicious)
         */
        public static List<String> getMostSuspiciousNodes(List<List<String>> cycles, int topN) {
            return getNodeFrequency(cycles).entrySet().stream()
                    .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                    .limit(topN)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
        }
    }
}

