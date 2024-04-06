package ADA.Lab5.RodCutting;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Rod cutting problem: we have a rod of a given length.
 * We can cut pieces of any size in the range 1..length.
 * Every piece length has a price,  prices[i-1] is the profit
 * for a piece of size i.
 * The length of array prices ie equal with the length of the rod.
 * Which is the maximum profit that can be obtained by cutting the rod?
 */
public class RodCuttingProblem {

    public static int rodDP(int[]prices,int length) {

        if(length ==0 )
            return 0;

        int maxProfit = Integer.MIN_VALUE;

        for( int i=1; i<=length; i++ ) {

            maxProfit = Math.max( maxProfit, prices[i-1] + rodCuttingDP(prices,length-i) );
        }

        return maxProfit;
    }


    public static int rodMemo(int[] prices,int length) {

        int[] res = new int[length+1];

        for(int i=0; i<=length; i++)
            res[i] = -1;

        return rodCutting(prices,length,res);
    }
    public static int rodCuttingDP(int[] prices, int length) {
        if (length==0) {
            return 0;
        }

        int maxProfit=Integer.MIN_VALUE;
        for (int i=1; i<=length; i++) {
            // max profit is obtained with or without cutting in this step a piece of size i?
            maxProfit = Math.max(maxProfit, prices[i-1] + rodCuttingDP(prices, length - i));
        }

        return maxProfit;
    }

    public static int rodCutting(int[] prices, int length,int[] res) {

        if( res[length] != -1  )
            return res[length];

        if (length==0) {
            res[length] = 0;
        }
        else {

           int maxProfit = Integer.MIN_VALUE;

            for (int i = 1; i <= length; i++) {
                // max profit is obtained with or without cutting in this step a piece of size i?
                maxProfit = Math.max( maxProfit, prices[i - 1] + rodCutting(prices, length - i,res) );
                res[length] = maxProfit;
            }
        }

        return res[length];
    }

    private static int[] readValues(String filename) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filename));
        int n = scanner.nextInt();

        int[] all = new int[n];
        for (int i = 0; i < n; i++) {
            all[i] = scanner.nextInt();
        }
        return all;
    }

    public static void executeTest(String filename) throws FileNotFoundException {

        int[] prices =readValues(filename);
        // prices[i-1] holds the price of a piece of size i

        // length of rod equals number of prices
        int n=prices.length;

        System.out.println("length of rod n="+n);

        long start =System.currentTimeMillis();

        int maxProfit = rodMemo(prices,n);

        long end = System.currentTimeMillis();
        System.out.println("Computation time =" + (end - start) + " ms");

        System.out.println("Result [MEMO] : The maximum profit is: " + maxProfit);


        start =System.currentTimeMillis();

        maxProfit = rodDP(prices,n);

        end = System.currentTimeMillis();
        System.out.println("Computation time =" + (end - start) + " ms");

        System.out.println("Result [DP] : The maximum profit is: " + maxProfit);
    }

    public static void main(String[] args) throws FileNotFoundException {
        executeTest("src/rodCut_10.txt");
       // executeTest("rodCut_20.txt");
       // executeTest("rodCut_30.txt");

        // next ones take very long time!
        // implement the dynamic programming version to make them work!
       // executeTest("rodCut_40.txt");
       // executeTest("rodCut_50.txt");
       // executeTest("rodCut_100.txt");
    }
}