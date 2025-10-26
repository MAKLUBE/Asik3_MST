package mst;

import java.util.List;

public class Graph {
    public int id;
    public String type;
    private List<String> vertices;
    private List<Edge> edges;

    public Graph(int id, String type, List<String> vertices, List<Edge> edges) {
        this.id = id;
        this.type = type;
        this.vertices = vertices;
        this.edges = edges;
    }

    public List<String> getVertices() {
        return vertices;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public int getVertexCount() {
        return vertices.size();
    }

    public int getEdgeCount() {
        return edges.size();
    }
}
