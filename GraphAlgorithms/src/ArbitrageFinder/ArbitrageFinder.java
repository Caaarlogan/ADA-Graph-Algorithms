package ArbitrageFinder;

import static java.lang.Math.exp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Carlo Carbonilla
 */
public class ArbitrageFinder {

    private AllPairsFloydWarshall fw;
    private AdjacencyListGraph<String> graph;
    private List<Vertex<String>> vertexOrder; //order vertices are added
    public Map<Edge<String>, Double> weights;
    private double[][] weightTable;
    private static final double INFINITY = Double.MAX_VALUE;
    public boolean hasArbitrage;

    public ArbitrageFinder(String[] currencies, double[][] rates) {

        vertexOrder = new ArrayList();
        weights = new HashMap();
        weightTable = new double[rates.length][rates.length];
        hasArbitrage = false;

        GraphADT<String> graph = createGraph(currencies, rates);

        fw = new AllPairsFloydWarshall(weightTable);
    }
    
    //Creates graph from n x n table
    public GraphADT<String> createGraph(String[] currencies, double[][] rates) {
        graph = new AdjacencyListGraph(GraphADT.GraphType.DIRECTED);
        
        //Adds vertices
        for (int i = 0; i < rates.length; i++) {
            Vertex<String> vertex = graph.addVertex(currencies[i]);
            vertexOrder.add(vertex);
        }
        
        //if the rates from vertex i to vertex j isn't 0
        //add edge between them with weight = rate
        for (int i = 0; i < rates.length; i++) {
            Vertex<String> row = vertexOrder.get(i);
            for (int j = 0; j < rates.length; j++) {
                if (rates[i][j] != 0 && i != j) {
                    double weight = rates[i][j];
                    weight = Math.log((1 / weight));
                    Vertex<String> column = vertexOrder.get(j);
                    Edge<String> edge = graph.addEdge(row, column);
                    weights.put(edge, weight);
                    weightTable[i][j] = weight;
                //if from vertex i to i
                //weight is one
                } else if (i == j) {
                    weightTable[i][j] = 1;
                //if rate from vertix i to j is 0,
                //weight is infinity
                } else {
                    weightTable[i][j] = INFINITY;
                }
            }
        }

        return graph;
    }

    public String findArbitrage() {
        int NO_VERTEX = -1;
        int n = fw.getN();
        double[][][] d = fw.getD();
        int[][][] p = fw.getP();

        String output = "Arbitrage\n";

        //goes through the final iteration of the floyd-warshall 3d array
        //checks if the loop back to itself has value < 0 (meaning >1 after conversion)
        for (int i = 0; i < n; i++) {
            if (d[n][i][i] < 0) {
                boolean returns = false;
                String tempout = "";
                tempout += vertexOrder.get(i) + "->";
                int pre = p[n][i][i];
                //while loop to go back through the predecessors
                while (pre != NO_VERTEX) {
                    tempout += vertexOrder.get(pre) + "->";
                    int temp = p[n][i][pre];
                    returns = (i == pre);
                    if (pre == p[n][i][temp]) {
                        if (!returns){
                            returns = (i == temp);
                        }
                        break;
                    }
                    pre = temp;
                }
                //only print if i makes it back to itself via predecessors.
                if (returns) {
                    output += tempout;
                    output += vertexOrder.get(i) + "\n";
                }
            }
        }
        return output;
    }
    
    //Print edges of graph and weight of edges
    public String toString() {
        String output = "Graph:\n";
        for (Vertex<String> vertex : vertexOrder) {

            output += "" + vertex + " has edges:";

            for (Edge edge : graph.adjacencyLists.get(vertex)) {
                double weight = weights.get(edge);
                output += edge + "" + weight + ", ";
            }

            output += "\n";
        }
        return output;
    }

    public AdjacencyListGraph<String> getGraph() {
        return graph;
    }

    public AllPairsFloydWarshall getFw() {
        return fw;
    }
}
