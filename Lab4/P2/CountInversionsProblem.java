package ADA.Lab4.P2;

import org.jsoup.select.CombiningEvaluator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
    Inversions are pairs of numbers (A[i], A[j]), in an array, where i<j and A[i] > A[j].
        Find out the number of the inversions in a given input.
        Example: In the following array: [1, 3, 5, 2, 4, 6], there are 3 inversions: (3,2), (5,2), and (5,4).
        The program reads input data from text files with the following structure:
        The first line contains the value of n, the number of values
        For the next n lines, each line contains one integer, reprsesenting the values of the array.
 */
public class CountInversionsProblem {

    private static long countInvBF(int[] A) {

        long cnt = 0;

        for(int i=0; i<A.length; i++) {
            for(int j=i+1; j<A.length; j++) {

                if( A[j] < A[i] )
                    cnt++;
            }
        }
        return cnt;
    }

    public static long Divide(int[] A) {
        return AndConquer(A,0,A.length-1);
    }

    private static long AndConquer(int[] arr,int l,int r) {

        if( l!=r )
        {

            int m = (l+r)/2;

            long invLeft = AndConquer(arr,l,m);
            long invRight = AndConquer(arr,m+1,r);
            long invCross = Cross(arr,l,r,m);

            return invLeft + invRight + invCross;
        }
        return 0;
    }

    private static long Cross(int[] A, int st,int dr, int mid ){

        int i=st;
        int j=mid+1;
        int k=0;
        int[] temp= new int[dr-st+1];
        long count = 0;

        while( i<=mid && j<=dr ) {

            if( A[i] > A[j] ) {
                count += mid - i + 1;
                temp[k++] = A[j++];
            }
            else {
                temp[k++] = A[i++];
            }
        }

        while( i<=mid )
            temp[k++] = A[i++];
        while( j<=dr )
            temp[k++] = A[j++];

        for( i = st,k=0; i<=dr; i++,k++ ) {
            A[i] = temp[k];
        }

        return count;
    }

    private static int[] readVals(String filename) throws FileNotFoundException {

        Scanner s = new Scanner(new File(filename));
        
        int n = s.nextInt();
        int[] a = new int[n];

        for( int i=0; i<n; i++) {
            a[i] = s.nextInt();
        }
        return a;
    }

    public static void main(String[] args) throws FileNotFoundException {

        try {
            int[] A = readVals("ADA/Lab4/P2/testFiles/inv1000.txt");

            System.out.println("Opened file! Counting inversions for array: ");
            for(int i=0; i< A.length; i++ )
                System.out.print(" " + A[i]);
            System.out.println();

            System.out.println("Brute force count: " + countInvBF(A));
            System.out.println("Divide et impera count: " + Divide(A) );

            for(int i=0; i< A.length; i++ )
                System.out.print(" " + A[i]);
            System.out.println();

        }catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

}
