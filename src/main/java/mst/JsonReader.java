package mst;

import com.google.gson.*;
import java.io.FileReader;
import java.util.*;

public class JsonReader {

    public static List<Graph> loadGraphs(String filePath) throws Exception {
        List<Graph> graphs = new ArrayList<>();

        JsonObject root = JsonParser.parseReader(new FileReader(filePath)).getAsJsonObject();
        JsonArray graphsArray = root.getAsJsonArray("graphs");

        for (JsonElement graphElement : graphsArray) {
            JsonObject g = graphElement.getAsJsonObject();
            int id = g.get("id").getAsInt();

            List<String> vertices = new ArrayList<>();
            for (JsonElement node : g.getAsJsonArray("nodes")) {
                vertices.add(node.getAsString());
            }

            List<Edge> edges = new ArrayList<>();
            for (JsonElement edgeElement : g.getAsJsonArray("edges")) {
                JsonObject e = edgeElement.getAsJsonObject();
                edges.add(new Edge(
                        e.get("from").getAsString(),
                        e.get("to").getAsString(),
                        e.get("weight").getAsInt()
                ));
            }

            graphs.add(new Graph(id, vertices, edges));
        }
        return graphs;
    }
}
