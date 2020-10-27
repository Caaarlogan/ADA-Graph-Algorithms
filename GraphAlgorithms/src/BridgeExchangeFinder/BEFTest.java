package BridgeExchangeFinder;

import java.util.HashMap;
import java.util.Scanner;

/**
 * @author Carlo Carbonilla
 */
public class BEFTest {
    public static void main(String[] args) {
        double[][] rates1 = {
            //AUD EUR MXN NZD USD
            {1, 0.61, 0, 1.08, 0.72}, //AUD
            {1.64, 1, 0, 1.77, 1.18}, //EUR
            {0, 0, 1, 0, 0.047}, //MXN
            {0.92, 0.56, 0, 1, 0.67}, //NZD
            {1.39, 0.85, 21.19, 1.5, 1} //USD
        };
        
        String[] currencies1 = {"AUD", "EUR", "MXN", "NZD", "USD"};
        
        double[][] rates2 = {
                //USD EUR JPY NZD GBP AUD CAD CNY MYR KRW
                {1, 0.85, 105.4, 1.51, 0, 1.41, 1.32, 6.68, 0, 0},//USD 
                {1.18, 1, 124.18, 1.78, 0.91, 1.66, 0, 0, 0, 0},//EUR
                {0.0095, 0.0081, 1, 0.014, 0, 0, 0, 0.063, 0.039, 10.81},//JPY
                {0.66, 0.56, 69.81, 1, 0, 0.94, 0, 0, 0, 0},//NZD
                {0, 1.1, 0, 0, 1, 0, 0, 0, 0, 0},//GBP
                {0.71, 0.6, 0, 1.07, 0, 1, 0, 0, 0, 0},//AUD
                {0.76, 0, 0, 0, 0, 0, 1, 0, 0, 0},//CAD
                {0.15, 0, 0, 15.78, 0, 0, 0, 1, 0.62, 170.47},//CNY
                {0, 0, 0, 25.48, 0, 0, 0, 1.61, 1, 0},//MYR
                {0, 0, 0, 0.093, 0, 0, 0, 0.0059, 0, 1} //KRW
            };
        String[] currencies2 = {"USD", "EUR", "JPY", "NZD", "GBP", "AUD", "CAD", "CNY", "MYR", "KRW"};
        
        Scanner scan = new Scanner(System.in);
        
        int example = 0;
        
        do
        {
            System.out.println("Example 1 or 2?");

            while (!scan.hasNextInt())
            {
                System.out.println("Enter integer between 1 and 2");
                scan.next();
            }

            example = scan.nextInt();

        } while (example < 1 || example > 2);
        
        BridgeExchangeFinder<String> bef;
        
        if(example==1) {
            bef = new BridgeExchangeFinder<String>(currencies1, rates1);
        }
        else {
            bef = new BridgeExchangeFinder<String>(currencies2, rates2);
        }
        
        System.out.println("Example Graph:\n" + bef.getGraph());
        
        String start = bef.getVertexOrder().get(0).getUserObject();
        System.out.println("Performing depth-first search from " + start);
        bef.search(bef.getVertexOrder().get(0));
        
        System.out.println("Traversed Edges");
        
        for(Edge<String> edge : bef.getEdgeOrder()) {
            System.out.println(edge);
        }
        
        System.out.println("\nD Values");
        
        HashMap<Vertex<String>, Integer> dVals = bef.getD();
        
        for(Vertex<String> v : dVals.keySet()) {
            String key = v.getUserObject();
            int dVal = dVals.get(v);
            System.out.println(key + ", " + dVal);
        }
        
        System.out.println("\nParents");
        
        for(Vertex<String> v : bef.getParents().keySet()) {
            Vertex<String> parent = bef.getParents().get(v);
            System.out.println("Parent of " + v + " is: " + parent);
        }
        
        System.out.println("\nM Values");
        
        for(Vertex<String> v : bef.getM().keySet()) {
            int mVal = bef.getM().get(v);
            System.out.println(v + ", " + mVal);
        }
        
        System.out.println("\nFinding bridges in graph");
        bef.findBridges();
        
        System.out.println("Bridges");
        
        for(Edge<String> edge : bef.getBridges()) {
            System.out.println(edge);
        }
    }
}
