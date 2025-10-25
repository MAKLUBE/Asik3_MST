package mst;

import java.util.*;

public class Graph {
    private final List<String> vertices;
    private final List<Edge> edges;
    public final int id;

    public Graph(int id, List<String> vertices, List<Edge> edges) {
        this.id = id;
        this.vertices = vertices;
        this.edges = edges;
    }

    public List<String> getVertices() { return vertices; }
    public List<Edge> getEdges() { return edges; }
    public int getVertexCount() { return vertices.size(); }
    public int getEdgeCount() { return edges.size(); }
}
