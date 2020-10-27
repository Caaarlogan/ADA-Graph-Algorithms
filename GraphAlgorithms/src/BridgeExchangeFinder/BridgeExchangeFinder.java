package BridgeExchangeFinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Carlo Carbonilla
 */
public class BridgeExchangeFinder<String> extends DepthFirstSearch<String> {
    private List<Vertex<String>> vertexOrder; //order vertices are added
    private HashMap<Vertex<String>, Integer> d; //vertex key, d value
    private HashMap<Vertex<String>, Integer> m; //vertex key, m value
    private HashMap<Vertex<String>, Vertex<String>> parents; //vertex key, parent vertex value
    private List<Edge<String>> traversedEdges;
    private Set<Edge<String>> bridges;
    private int counter;

    public BridgeExchangeFinder(String[] currencies, double[][] rates) {
        super(new AdjacencyListGraph());
        
        vertexOrder = new ArrayList();
        d = new HashMap();
        m = new HashMap();
        parents = new HashMap();
        traversedEdges = new ArrayList();
        bridges = new HashSet();
        counter = 0;
        
        GraphADT<String> graph = createGraph(currencies,rates);
        
        this.graph = graph;
        Set<Vertex<String>> vertices = graph.vertexSet();
        vertexColours = new HashMap<Vertex<String>, DepthFirstSearch.Colour>(vertices.size());
        for (Vertex<String> vertex : vertices) {
            vertexColours.put(vertex, DepthFirstSearch.Colour.WHITE);
        }
    }
    
    //Create graph from n x n table
    public GraphADT<String> createGraph(String[] currencies, double[][] rates) {
        graph = new AdjacencyListGraph();
        
        //Add vertices
        for (int i = 0; i < rates.length; i++) {
            Vertex<String> vertex = graph.addVertex(currencies[i]);
            vertexOrder.add(vertex);
        }
        
        //if the rates from vertex i to vertex j isn't 0
        //add edge between them with weight = rate
        for (int i = 0; i < rates.length; i++) {
            Vertex<String> row = vertexOrder.get(i);
            for (int j = i+1; j < rates.length; j++) {
                if (rates[i][j] != 0) {
                    Vertex<String> column = vertexOrder.get(j);
                    graph.addEdge(row,column);
                }
            }
        }
        
        return graph;
    }
    
    //returns a set of edges that are bridges in the graph
    public void findBridges() {
        for (Edge<String> edge : traversedEdges) {
            Vertex<String>[] edgeVertices = edge.endVertices();
            int dParent = 0;
            int mChild = 0;

            //find d value of parent and m value of child
            if (parents.get(edgeVertices[0]) == edgeVertices[1]) {
                dParent = d.get(edgeVertices[1]);
                mChild = m.get(edgeVertices[0]);
            }
            else {
                dParent = d.get(edgeVertices[0]);
                mChild = m.get(edgeVertices[1]);
            }
            
            //if d value of parent is less than m value of child,
            //edge is a bridge
            if (dParent < mChild) {
                bridges.add(edge);
            }
        }
    }

    // hook method that is called whenever a vertex has been discovered
    protected void vertexDiscovered(Vertex<String> vertex) {
        d.put(vertex, counter);
        counter++;
    }

    // hook method that is called whenever a vertex has been finished
    protected void vertexFinished(Vertex<String> vertex) {
        //get d value of vertex
        int minVal = d.get(vertex);

        //loop through neighbours of vertex
        for (Vertex<String> adjacent : vertex.adjacentVertices()) {
            //if neighbour isn't vertex's parent
            if (adjacent != parents.get(vertex)) {
                
                //if neighbour doesn't have m value
                if (m.get(adjacent) == null) {
                    //set vertex's m value to neighbour's d value if it is less
                    //than vertex's m value
                    int adjacentDVal = d.get(adjacent);
                    if (adjacentDVal < minVal) {
                        minVal = adjacentDVal;
                    }
                }
                //if neighbour has m value
                else {
                    //set vertex's m value to neighbour's m value if it is less
                    //than vertex's m value
                    int adjacentMVal = m.get(adjacent);
                    if (adjacentMVal < minVal) {
                        minVal = adjacentMVal;
                    }
                }

            }
        }

        m.put(vertex, minVal);
    }

    // hook method that is called whenever a tree edge is traversed
    protected void edgeTraversed(Edge<String> edge) {
        traversedEdges.add(edge);

        Vertex<String>[] edgeVertices = edge.endVertices();

        //if a vertex hasn't been visited, the other vertex will be it's parent
        if (d.get(edgeVertices[0]) != null && d.get(edgeVertices[1]) == null) {
            parents.put(edgeVertices[1], edgeVertices[0]);
        }
        else if (d.get(edgeVertices[1]) != null && d.get(edgeVertices[0]) == null) {
            parents.put(edgeVertices[0], edgeVertices[1]);
        }
    }

    public HashMap<Vertex<String>, Integer> getD() {
        return d;
    }

    public HashMap<Vertex<String>, Integer> getM() {
        return m;
    }

    public HashMap<Vertex<String>, Vertex<String>> getParents() {
        return parents;
    }

    public List<Edge<String>> getEdgeOrder() {
        return traversedEdges;
    }

    public Set<Edge<String>> getBridges() {
        return bridges;
    }
    
    public GraphADT<String> getGraph() {
        return graph;
    }
    
    public List<Vertex<String>> getVertexOrder() {
        return vertexOrder;
    }
}
