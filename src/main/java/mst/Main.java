package mst;

import com.google.gson.*;
import java.io.FileWriter;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String[] inputFiles = {
                "src/main/resources/inputSmall.json",
                "src/main/resources/inputMedium.json",
                "src/main/resources/inputLarge.json"
        };

        List<Graph> allGraphs = new ArrayList<>();
        for (String path : inputFiles) {
            allGraphs.addAll(JsonReader.loadGraphs(path));
        }

        FileWriter jsonWriter = new FileWriter("src/main/resources/output.json");
        FileWriter csvWriter = new FileWriter("src/main/resources/results.csv");

        csvWriter.write("Graph ID,Type,Vertices,Edges,Prim Cost,Prim Time (ms),Kruskal Cost,Kruskal Time (ms),Prim Ops,Kruskal Ops\n");
        jsonWriter.write("{\n  \"results\": [\n");

        for (int i = 0; i < allGraphs.size(); i++) {
            Graph g = allGraphs.get(i);
            MSTResult prim = PrimAlgorithm.run(g);
            MSTResult kruskal = KruskalAlgorithm.run(g);

            jsonWriter.write("    {\n");
            jsonWriter.write("      \"graph_id\": " + g.id + ",\n");
            jsonWriter.write("      \"type\": \"" + g.type + "\",\n");
            jsonWriter.write("      \"input_stats\": { \"vertices\": " + g.getVertexCount() + ", \"edges\": " + g.getEdgeCount() + " },\n");
            jsonWriter.write("      \"prim\": " + gson.toJson(prim) + ",\n");
            jsonWriter.write("      \"kruskal\": " + gson.toJson(kruskal) + "\n");
            jsonWriter.write("    }" + (i < allGraphs.size() - 1 ? "," : "") + "\n");

            csvWriter.write(String.format(
                    "%d,%s,%d,%d,%d,%.3f,%d,%.3f,%d,%d\n",
                    g.id, g.type,
                    g.getVertexCount(), g.getEdgeCount(),
                    prim.totalCost, prim.timeMs,
                    kruskal.totalCost, kruskal.timeMs,
                    prim.operations, kruskal.operations
            ));
        }

        jsonWriter.write("  ]\n}\n");
        jsonWriter.close();
        csvWriter.close();

        System.out.println("Results generated successfully (output.json + results.csv)");
    }
}
