package mst;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        List<Graph> graphs = JsonReader.loadGraphs("src/main/resources/input.json");

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileWriter writer = new FileWriter("src/main/resources/output.json");

        writer.write("{\n  \"results\": [\n");
        for (int i = 0; i < graphs.size(); i++) {
            Graph g = graphs.get(i);

            MSTResult prim = PrimAlgorithm.run(g);
            MSTResult kruskal = KruskalAlgorithm.run(g);

            writer.write("    {\n");
            writer.write("      \"graph_id\": " + g.id + ",\n");
            writer.write("      \"input_stats\": { \"vertices\": " + g.getVertexCount() +
                    ", \"edges\": " + g.getEdgeCount() + " },\n");
            writer.write("      \"prim\": " + gson.toJson(prim) + ",\n");
            writer.write("      \"kruskal\": " + gson.toJson(kruskal) + "\n");
            writer.write("    }" + (i < graphs.size() - 1 ? "," : "") + "\n");
        }
        writer.write("  ]\n}");
        writer.close();

        System.out.println("Results saved to output.json");
    }
}
