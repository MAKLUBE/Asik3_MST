package mst;

import java.util.*;

public class PrimAlgorithm {

    public static MSTResult run(Graph graph) {
        long start = System.nanoTime();
        int operations = 0;

        List<Edge> mstEdges = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>();

        String startVertex = graph.getVertices().get(0);
        visited.add(startVertex);

        pq.addAll(getEdgesFrom(graph, startVertex));

        while (!pq.isEmpty() && mstEdges.size() < graph.getVertexCount() - 1) {
            Edge edge = pq.poll();
            operations++;

            if (visited.contains(edge.getTo()) && visited.contains(edge.getFrom())) continue;

            mstEdges.add(edge);
            String next = visited.contains(edge.getFrom()) ? edge.getTo() : edge.getFrom();
            visited.add(next);
            pq.addAll(getEdgesFrom(graph, next));
        }

        int totalCost = mstEdges.stream().mapToInt(Edge::getWeight).sum();
        double timeMs = (System.nanoTime() - start) / 1e6;
        return new MSTResult("Prim", mstEdges, totalCost, operations, timeMs);
    }

    private static List<Edge> getEdgesFrom(Graph graph, String vertex) {
        List<Edge> res = new ArrayList<>();
        for (Edge e : graph.getEdges()) {
            if (e.getFrom().equals(vertex) || e.getTo().equals(vertex))
                res.add(e);
        }
        return res;
    }
}
