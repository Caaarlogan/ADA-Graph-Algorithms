/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BestConversionFinder;

/**
 * Thanks Angelo
 *
 * @author Angelo
 */
public class Values {

    public static AdjacencyListGraph getOscarValues() {
        double[][] values = {
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
        String[] codes = {"USD", "EUR", "JPY", "NZD", "GBP", "AUD", "CAD", "CNY", "MYR", "KRW"};
        String print = "";
        for (int i = 0; i < codes.length; i++) {
            print += i + ". " + codes[i] + " ";
        }
        System.out.println(print);
        return new AdjacencyListGraph<String>(values, codes);
    }

    public static AdjacencyListGraph getJakeValues() {
        String[] codes = {"AUD", "EUR", "GBP", "NZD", "USD"};

        String print = "";
        for (int i = 0; i < codes.length; i++) {
            print += i + ". " + codes[i] + " ";
        }
        System.out.println(print);
        double[][] values = {
            {1, 0.61, 0.0, 1.08, 0.72},
            {1.63, 1.0, 0.0, 1.77, 1.18},
            {0.0, 0.0, 1.0, 0.0, 1.29},
            {0.92, 0.56, 0.0, 1.0, 0.66},
            {1.38, 0.84, 0.77, 1.50, 1.00}
        };
        return new AdjacencyListGraph<String>(values, codes);
    }

    public static AdjacencyListGraph getAngeloValues() {
        //0 AUD
        //1 EURO
        //2 MXN
        //3 NZD
        //4 USD
        //5 CNY
        //6 BDT - Bangladesh
        //7 NPR - Nepal
        //8 PEN - Peru
        //9 RUB - Russia
        //10 EGP - Egypt

        String[] codes = {"AUD", "EUR", "MXN", "NZD", "USD", "CNY", "BDT", "NPR", "PEN", "RUB", "EGP"};
        String print = "";
        for (int i = 0; i < codes.length; i++) {
            print += i + ". " + codes[i] + " ";
        }
        System.out.println(print);

        double[][] values = new double[11][11];

        //conversions added row -> col
        values[0][0] = 1.0; //AUD - AUD
        values[0][1] = 0.6056; // AUD - EURO
        values[0][3] = 1.0733; // AUD - NZD;
        values[0][4] = 0.7097;// AUD - USD;
        values[0][5] = 4.7765;// AUD - CNY

        values[1][0] = 1.6508; //EUR - AUD;
        values[1][1] = 1.0; //EUR - EURO;
        values[1][3] = 1.7719; //EUR - NZD
        values[1][4] = 1.1718; //EUR - USD
        values[1][5] = 7.8864; //EUR - CNY
        values[1][9] = 91.4132; //EUR - RUB
        values[1][10] = 11.7174; //EUR - EGP

        values[2][2] = 1.0; //MXN - MXN
        values[2][4] = 0.0467; //MXN - USD
        values[2][8] = 0.1685; // MXN - PEN

        values[3][0] = 0.9314; //NZD - AUD
        values[3][1] = 0.5641; //NZD - EUR
        values[3][3] = 1.0; //NZD - NZD
        values[3][4] = 0.6611; // NZD - USD
        values[3][5] = 4.4496; // NZD - CNY

        values[4][0] = 1.4086; //USD - AUD
        values[4][1] = 0.8532; //USD - EUR
        values[4][2] = 21.3704; //USD - MXN
        values[4][3] = 1.5120; //USD - NZD
        values[4][4] = 1.0; //USD - USD;
        values[4][5] = 6.7297; //USD - CNY
        values[4][8] = 3.6022; //USD - PEN
        values[4][9] = 78.006; //USD - RUB

        values[5][0] = 0.2091; //CNY - AUD
        values[5][1] = 0.1266; //CNY - EUR
        values[5][3] = 0.2245; //CNY - NZD
        values[5][4] = 0.1484; //CNY - USD
        values[5][5] = 1.0; //CNY - CNY
        values[5][6] = 12.3629; //CNY - BDT
        values[5][7] = 17.2090; //CNY - NPR
        values[5][9] = 11.5818; //CNY - RUB

        values[6][5] = 0.0779; //BDT - CNY
        values[6][6] = 1.0; //BDT - BDT
        values[6][7] = 1.3425; //BDT - NPR

        values[7][5] = 0.0566; //NPR - CNY
        values[7][6] = 0.7009; //NPR - BDT
        values[7][7] = 1.0; //NPR - NPR

        values[8][2] = 5.9867; //PEN - MXN
        values[8][4] = 0.2801; // PEN - USD
        values[8][8] = 1.0;//PEN - PEN

        values[9][1] = 0.0109;//RUB - EUR
        values[9][4] = 0.0128;//RUB - USD
        values[9][5] = 0.0862;//RUB - CNY
        values[9][9] = 1.0;//RUB - RUB

        values[10][1] = 0.1383;// EGP - EUR
        
        return new AdjacencyListGraph<String>(values, codes);
    }
}
