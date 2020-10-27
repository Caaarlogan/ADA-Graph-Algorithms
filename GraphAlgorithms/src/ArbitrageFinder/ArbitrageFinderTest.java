package ArbitrageFinder;

import java.util.Scanner;

/**
 * @author Carlo Carbonilla
 */
public class ArbitrageFinderTest {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        AdjacencyListGraph<String> graph;

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

        int example = 0;
        
        //Choose from rate values 1 or 2
        do {
            System.out.println("Example 1 or 2?");

            while (!scan.hasNextInt()) {
                System.out.println("Enter integer between 1 and 2");
                scan.next();
            }

            example = scan.nextInt();

        }
        while (example < 1 || example > 2);

        ArbitrageFinder find;
        
        //Creates graph from specified n x n table
        if (example == 1) {
            find = new ArbitrageFinder(currencies1, rates1);
        }
        else {
            find = new ArbitrageFinder(currencies2, rates2);
        }

//        System.out.println(find);
//        System.out.println(find.getFw());

        //Print results of arbitrage
        System.out.println(find.findArbitrage());
    }
}
