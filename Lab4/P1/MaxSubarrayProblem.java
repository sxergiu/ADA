package ADA.Lab4.P1;

/*
Given an array A of n integer(positive and negative) numbers, we want to find the nonempty,
        contiguous subarray of A whose values have
        the largest sum. We call this contiguous subarray the maximum subarray.

        The program reads input data from text files with the following structure:
        The first line contains the value of n, the number of values
        The next n lines contain the values of the array.

        The output must show:
        - one pair of indices i and j such that the subarray A[i..j] has the
        greatest sum of any nonempty contiguous subarray of A (these indices are not necessarily unique!), and
        - the sum of the values in A[i..j].
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class MaxSubarrayProblem {

    private static int[] readValues(String filename) throws FileNotFoundException {

        Scanner scanner = new Scanner(new File(filename));
        int n = scanner.nextInt();

        int[] all = new int[n];
        for( int i=0; i<n; i++) {
            all[i] = scanner.nextInt();
        }
        return all;
    }

    public static void main(String[] args) throws FileNotFoundException {

        try {
            int[] A = readValues("ADA/Lab4/P1/testFiles/maxsub_100.txt");
            System.out.println("Opened the file! Read the following array: ");
            for( int i=0; i<A.length; i++)
                System.out.print(A[i] + " ");
            System.out.println();

            System.out.println("Solution with brute force: " + MaxSubarraySol.bruteForce(A));
            System.out.println("Solution with Divide et Impera: " + MaxSubarraySol.DeI(A) );
        }
        catch(FileNotFoundException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

}
