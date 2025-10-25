package mst;

import java.util.*;

public class KruskalAlgorithm {

    public static MSTResult run(Graph graph) {
        long start = System.nanoTime();
        int operations = 0;

        List<Edge> mstEdges = new ArrayList<>();
        List<Edge> allEdges = new ArrayList<>(graph.getEdges());
        Collections.sort(allEdges);

        DSU dsu = new DSU(graph.getVertexCount());
        Map<String, Integer> indexMap = new HashMap<>();
        for (int i = 0; i < graph.getVertices().size(); i++) {
            indexMap.put(graph.getVertices().get(i), i);
        }

        for (Edge edge : allEdges) {
            operations++;
            int u = indexMap.get(edge.getFrom());
            int v = indexMap.get(edge.getTo());

            if (dsu.find(u) != dsu.find(v)) {
                dsu.union(u, v);
                mstEdges.add(edge);
            }
        }

        int totalCost = mstEdges.stream().mapToInt(Edge::getWeight).sum();
        double timeMs = (System.nanoTime() - start) / 1e6;
        return new MSTResult("Kruskal", mstEdges, totalCost, operations, timeMs);
    }
}

class DSU {
    private int[] parent;
    private int[] rank;

    public DSU(int n) {
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;
    }

    public int find(int x) {
        if (parent[x] != x)
            parent[x] = find(parent[x]);
        return parent[x];
    }

    public void union(int x, int y) {
        int rootX = find(x), rootY = find(y);
        if (rootX == rootY) return;

        if (rank[rootX] < rank[rootY]) parent[rootX] = rootY;
        else if (rank[rootX] > rank[rootY]) parent[rootY] = rootX;
        else {
            parent[rootY] = rootX;
            rank[rootX]++;
        }
    }
}
