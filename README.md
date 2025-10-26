<<<<<<< HEAD
#DAA Assignment 3
=======
# DAA Assignment 3 — Minimum Spanning Tree (MST)

**Sakenov Rassul** — SE-2435  

## 🎯 Goal
Implementation and comparison of **Prim’s** and **Kruskal’s** algorithms  
for constructing a **Minimum Spanning Tree (MST)** in a weighted undirected graph.

The program:
- Reads graph data from `input.json`
- Builds MST using both algorithms
- Records results to `output.json` and `results.csv`
- Measures execution time and operation count



## Validation & Testing

All tests implemented in **`MSTTest.java`**.

### ✔ Correctness Tests
- MST total cost is identical for both algorithms
- Number of edges = **V − 1**
- MST is **acyclic** (no cycles)
- MST connects all vertices (single component)
- Handles **disconnected graphs** gracefully

### ⚙ Performance & Consistency Tests
- Execution time ≥ 0 (ms)
- Operation count ≥ 0
- Results reproducible for same dataset

### 🧾 Test Output



---

## 🧠 Conclusion

Both **Prim’s** and **Kruskal’s** algorithms successfully generate a minimum spanning tree (MST)  
with identical total cost, confirming algorithmic correctness.

- Kruskal performs better on **sparse** graphs
- Prim performs better on **dense** graphs
- Execution times and operation counts are consistent and non-negative

---

## 📂 Project Structure  


>>>>>>> 1d002dc (MST, Prim and Kruskal algorithms)
