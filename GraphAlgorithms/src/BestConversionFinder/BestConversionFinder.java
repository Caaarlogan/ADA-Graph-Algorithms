package BestConversionFinder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Oscar
 */
public class BestConversionFinder {

    private AdjacencyListGraph<String> graph;
    private static final double INFINITY = Double.MAX_VALUE;
    private boolean hasArbitrage;

    public BestConversionFinder(AdjacencyListGraph g) {
        this.graph = g;
        hasArbitrage = false;
    }

    //ask what values want to be used
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        AdjacencyListGraph<String> graph;

        System.out.println("Oscar, Angelo, or Jake Rates? (1, 2, 3)");
        int input = scan.nextInt();
        if (input == 1) {
            graph = Values.getOscarValues();
        } else if (input == 2) {
            graph = Values.getAngeloValues();
        } else if (input == 3) {
            graph = Values.getJakeValues();
        } else {
            System.exit(-1);
            graph = null;
        }

        int source, end;
        System.out.println("Source index? (0 - " + (graph.vertices.size() - 1) + ")");
        source = scan.nextInt();
        System.out.println("End index? (0 - " + (graph.vertices.size() - 1) + ")");
        end = scan.nextInt();

        BestConversionFinder find = new BestConversionFinder(graph);
        Edge[] sourceRates = find.BellmanFord(source, false);
        Edge[] endRates = find.BellmanFord(end, false);

        if (!find.hasArbitrage) {
            Edge[] sourcePath = find.generatePath(sourceRates, source, end);
            Edge[] endPath = find.generatePath(endRates, end, source);
            System.out.println(find.pathToString(sourcePath));
            System.out.println(find.pathToString(endPath));
        }else{
            System.out.println("There is arbitrage in this graph");
        }
        //array of consecutive edges to be taken that is the shortest path, from the source node to end node.
        //same but from end node to source node.
    }

    //to == -1 to find arbitrage
    //bellman-ford implementation, modified to suit this purpose. will print if arbitrage is found
    public Edge[] BellmanFord(int source, boolean findArbitrage) {
        int n = graph.vertices.size();
        double[] distance = new double[n];
        Edge[] predecessor = new Edge[n];

        for (int i = 0; i < n; i++) {
            distance[i] = INFINITY;
            predecessor[i] = null;
        }

        distance[source] = 0;

        //edge relaxing
        for (int r = 1; r < n; r++) {
            for (Edge edge : graph.edges) {
                double weight = graph.weights.get(edge);
                int u = graph.vertices.indexOf(edge.endVertices()[0]);
                int v = graph.vertices.indexOf(edge.endVertices()[1]);
                if (distance[u] < INFINITY && distance[u] + weight < distance[v]) {
                    distance[v] = distance[u] + weight;
                    predecessor[v] = edge;
                }

            }
        }

        if (findArbitrage) {
            for (Edge edge : graph.edges) {
                double weight = graph.weights.get(edge);
                int u = graph.vertices.indexOf(edge.endVertices()[0]);
                int v = graph.vertices.indexOf(edge.endVertices()[1]);
                if (distance[u] < INFINITY && distance[u] + weight < distance[v]) {
                    //has negative weight closed path
                    //ARBITRAGE
                    hasArbitrage = true;
                }
            }
        }
        return predecessor;
    }

    //method to find the path of edges taken
    public Edge[] generatePath(Edge[] predecessor, int source, int to) {
        Edge tempEdge = predecessor[to];
        ArrayList<Edge> journey = new ArrayList<>();
        int count = 0;
        while (tempEdge != predecessor[source] && count < predecessor.length) { //predecessor length as the path should never realistically be more than the number of nodes
            journey.add(tempEdge);
            Vertex currentVertex = tempEdge.endVertices()[0];
            tempEdge = predecessor[graph.vertices.indexOf(currentVertex)];
            count++;
        }
        if (!(count < predecessor.length)) {
//            return null;
            hasArbitrage = true;
            System.out.println("Arbitrage found");
        }
        Collections.reverse(journey);

        Edge[] temp = new Edge[journey.size()];
        temp = journey.toArray(temp);

        return temp;
    }

    //string format of path of edges taken
    public String pathToString(Edge[] journey) {
        double[] weights = new double[journey.length];
        String out = "";
        double totalWeight = 1;

        for (int i = 0; i < journey.length; i++) {
            weights[i] = graph.weights.get(journey[i]);
            weights[i] = 1 / Math.exp(weights[i]);
            totalWeight *= weights[i];
            out += journey[i].endVertices()[0].toString();
            out += " > " + journey[i].endVertices()[1].toString() + " exchange rate: " + String.format("%.2f", weights[i]);
            out += "\n";
        }
        out += "total exchange rate: " + String.format("%.2f", totalWeight);

        return out;
    }
}
