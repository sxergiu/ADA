package ADA.Lab5.optimalBST;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class OptimalBST {

    /*
    Computes and returns the cost of the Optimal BST
    built for frequencies between index i and  j.
    Since we do not keep the tree the values of the keys
    do not matter and are ignored.
    However, we know that the array is given sorted in
    ascending order of the keys.
     */
    private static class KeyFrequency{
        int key;
        int freq;
        KeyFrequency(int key, int freq){
            this.key=key;
            this.freq=freq;
        }
    }

    public static int optCostDp(KeyFrequency[] keys,int i,int j ) {

        int[][] cost = new int[keys.length][keys.length];

        for(int ii=0; ii< keys.length; ii++)
            cost[ii][ii] = keys[ii].freq;

        for(int len = 2; len<=keys.length; len ++ ) {

                for(int k=0; k <= keys.length - len; k++ ) {

                    int t = len + k - 1;
                    cost[k][t] = Integer.MAX_VALUE;

                    int sum = SumFreq(keys,k,t);

                    for(int root = k; root <= t; root ++) {

                        int left = root != k ? cost[k][root-1] : 0;
                        int right = root != k ? cost[root+1][t] : 0;
                        int totalCost = left + right + sum;
                        cost[k][t] = Math.min(totalCost,cost[k][t]);
                    }
                }
        }
        return cost[i][j];
    }

    private static int SumFreq(KeyFrequency[] keys, int st, int end) {

        int s = 0;
        for(int i=st; i<=end; i++)
            s+= keys[i].freq;
        return s;
    }

    public static int optCost(KeyFrequency[] keys, int i, int j) {
        if (i > j) {
            return 0;
        }
        if (i == j) {
            return keys[i].freq;
        }
        int minCost = Integer.MAX_VALUE;
        for (int r = i; r <= j; r++) { // for every node r between i and j
            // try to make r the root of the optimal BST, with recursively
            // left and right subtrees optimal BSTs;
            // calculate the cost, keep minimum
            int cost = optCost( keys, i, r - 1) + optCost(keys, r + 1, j) + freqSum( keys, i, j);
            if (cost < minCost) {
                minCost = cost;
            }
        }
        return minCost;
    }

    private static int freqSum(KeyFrequency[] keys,  int i, int j) {
        int sum = 0;
        for (int k = i; k <= j; k++) {
            sum += keys[k].freq;
        }
        return sum;
    }


    private static KeyFrequency[] readValues(String filename) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filename));
        int n = scanner.nextInt();

        KeyFrequency[] all = new KeyFrequency[n];
        for (int i = 0; i < n; i++) {
            int key = scanner.nextInt();
            int freq = scanner.nextInt();
            all[i] = new KeyFrequency(key, freq);
        }
        return all;
    }

    public static void executeTest(String filename) throws FileNotFoundException {

        KeyFrequency[] keys = readValues(filename);
        System.out.println("Optimal BST with input from " + filename);
        System.out.println("n=" + keys.length);
        long ts = System.currentTimeMillis();
        int rez = optCost(keys, 0, keys.length - 1);
        long tf = System.currentTimeMillis();
        System.out.println("Done in time[ms]=" + (tf - ts));
        System.out.println("Result: Cost of optimal BST is "+rez);
        System.out.println();

        System.out.println("DP result:");
         ts = System.currentTimeMillis();
         rez = optCost(keys, 0, keys.length - 1);
         tf = System.currentTimeMillis();
        System.out.println("Done in time[ms]=" + (tf - ts));
        System.out.println("Result: Cost of optimal BST is "+rez);
        System.out.println();
    }

    public static void main(String[] args) throws FileNotFoundException {

        executeTest("ADA/Lab5/optimalBST/testFiles/optbst_10.txt");
      //  executeTest("optbst_15.txt");
        //executeTest("optbst_20.txt");

        // next ones take very long time!
        // implement the dynamic programming version to make them work!
       // executeTest("optbst_100.txt");
       // executeTest("optbst_1000.txt");
    }

}