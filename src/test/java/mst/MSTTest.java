package mst;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class MSTTest {

    private Graph createConnectedGraph() {
        List<String> vertices = List.of("A", "B", "C", "D", "E");
        List<Edge> edges = List.of(
                new Edge("A", "B", 4),
                new Edge("A", "C", 3),
                new Edge("B", "C", 2),
                new Edge("B", "D", 5),
                new Edge("C", "D", 7),
                new Edge("C", "E", 8),
                new Edge("D", "E", 6)
        );
        // 👇 добавляем "test" как тип
        return new Graph(1, "test", vertices, edges);
    }

    private Graph createDisconnectedGraph() {
        List<String> vertices = List.of("A", "B", "C");
        List<Edge> edges = List.of(
                new Edge("A", "B", 1)
        );
        return new Graph(2, "test", vertices, edges);
    }

    private boolean isAcyclic(List<Edge> edges, List<String> vertices) {
        Map<String, String> parent = new HashMap<>();
        for (String v : vertices) parent.put(v, v);

        for (Edge e : edges) {
            String aRoot = find(e.getFrom(), parent);
            String bRoot = find(e.getTo(), parent);
            if (aRoot.equals(bRoot)) return false;
            parent.put(aRoot, bRoot);
        }
        return true;
    }

    private boolean isConnected(List<Edge> edges, List<String> vertices) {
        if (vertices.isEmpty()) return true;
        Map<String, List<String>> adj = new HashMap<>();
        for (String v : vertices) adj.put(v, new ArrayList<>());
        for (Edge e : edges) {
            adj.get(e.getFrom()).add(e.getTo());
            adj.get(e.getTo()).add(e.getFrom());
        }

        Set<String> visited = new HashSet<>();
        dfs(vertices.get(0), adj, visited);
        return visited.size() == vertices.size();
    }

    private void dfs(String v, Map<String, List<String>> adj, Set<String> visited) {
        visited.add(v);
        for (String n : adj.get(v)) {
            if (!visited.contains(n)) dfs(n, adj, visited);
        }
    }

    private String find(String x, Map<String, String> parent) {
        if (parent.get(x).equals(x)) return x;
        parent.put(x, find(parent.get(x), parent));
        return parent.get(x);
    }

    @Test
    public void testTotalCostSameForPrimAndKruskal() {
        Graph g = createConnectedGraph();
        MSTResult prim = PrimAlgorithm.run(g);
        MSTResult kruskal = KruskalAlgorithm.run(g);
        assertEquals(prim.totalCost, kruskal.totalCost,
                "Total MST cost should be identical for both algorithms");
    }

    @Test
    public void testEdgesCountEqualsVMinusOne() {
        Graph g = createConnectedGraph();
        MSTResult prim = PrimAlgorithm.run(g);
        assertEquals(g.getVertexCount() - 1, prim.edges.size(),
                "MST must contain V - 1 edges");
    }

    @Test
    public void testMSTAcyclic() {
        Graph g = createConnectedGraph();
        MSTResult prim = PrimAlgorithm.run(g);
        assertTrue(isAcyclic(prim.edges, g.getVertices()),
                "MST must be acyclic (no cycles)");
    }

    @Test
    public void testMSTConnected() {
        Graph g = createConnectedGraph();
        MSTResult prim = PrimAlgorithm.run(g);
        assertTrue(isConnected(prim.edges, g.getVertices()),
                "MST must connect all vertices");
    }

    @Test
    public void testHandlesDisconnectedGraph() {
        Graph g = createDisconnectedGraph();
        MSTResult prim = PrimAlgorithm.run(g);
        assertTrue(prim.edges.size() <= g.getVertexCount() - 1,
                "Algorithm should handle disconnected graphs gracefully");
    }

    @Test
    public void testExecutionTimeIsNonNegative() {
        Graph g = createConnectedGraph();
        MSTResult prim = PrimAlgorithm.run(g);
        MSTResult kruskal = KruskalAlgorithm.run(g);
        assertTrue(prim.timeMs >= 0 && kruskal.timeMs >= 0,
                "Execution time must be non-negative");
    }

    @Test
    public void testOperationCountIsNonNegative() {
        Graph g = createConnectedGraph();
        MSTResult prim = PrimAlgorithm.run(g);
        MSTResult kruskal = KruskalAlgorithm.run(g);
        assertTrue(prim.operations >= 0 && kruskal.operations >= 0,
                "Operation count must be non-negative");
    }

    @Test
    public void testResultsReproducible() {
        Graph g = createConnectedGraph();
        MSTResult firstRun = PrimAlgorithm.run(g);
        MSTResult secondRun = PrimAlgorithm.run(g);
        assertEquals(firstRun.totalCost, secondRun.totalCost,
                "Results must be reproducible for the same dataset");
    }
}
