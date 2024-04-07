package ADA.Lab5.MinimumEditDistance;

/*
                The problem: The edit distance between two strings is
            defined as the minimum number of letter insertions, letter
            deletions, and letter substitutions required to transform
            one string into the other. Given two strings A with m and
            B with n characters, determine their minimum edit
            distance (the minimum number of operations necessary
             to transform string A into string B).
                Simple: find no of ops  Complex: op list

                 • Example, the minimum edit distance between FOOD and
            MONEY is 4: replace F->M, replace O->N, insert E,
            replace D->Y
 */
public class MinEditDist {
    public static int editDistance(char[] A, char[] B, int m, int n) {
        if (m==0) {    // if A is empty
            return n;  // insert all n characters of B
        }
        if (n==0) {     // if B is empty
            return m;   // insert all m characters of A
        }
        if (A[m-1]==B[n-1]) {   // if last char of A == last char of B
            return editDistance(A, B, m-1, n-1);       // no change
        }
        return 1 + Math.min(editDistance(A, B, m, n-1),    // insert B[n-1]
                Math.min(editDistance(A, B, m-1, n),       // delete A[m-1]
                        editDistance(A, B, m-1, n-1))); // replace A[m-1] by B[n-1]
    }

    public static int editDistDP(char[] A, char[] B, int m, int n) {

            int[][] result = new int[m+1][n+1];

            for(int i=0; i<=m; i++)
                result[i][0] = i;

            for(int j=0; j<=n; j++)
                result[0][j] = j;

            for(int i=1; i<=m; i++)
                for(int j=1; j<=n; j++) {

                    if( A[i-1] == B[j-1] ) result[i][j] = result[i-1][j-1];

                    else result[i][j] = 1+Math.min( result[i-1][j-1], Math.min( result[i-1][j],result[i][j-1] ));
                }

            return result[m][n];
    }


    public static void executeTest(String x, String y) {
        char[] X = x.toCharArray();
        char[] Y = y.toCharArray();
        int m = X.length;
        int n = Y.length;

        System.out.println("Computing Minimum edit distance for:");
        System.out.println(x);
        System.out.println(y);
        System.out.println("m="+m+"  n="+n);

        long start = System.currentTimeMillis();
        //int dist = editDistance(X, Y, m, n);
        long end = System.currentTimeMillis();

        System.out.println("Computation time =" + (end - start) + " ms");
        //System.out.println("Result: Minimum edit distance=" + dist);
        System.out.println();

        System.out.println("DP version:");

         start = System.currentTimeMillis();
         int dist = editDistDP(X, Y, m, n);
         end = System.currentTimeMillis();

        System.out.println("Computation time =" + (end - start) + " ms");
        System.out.println("Result: Minimum edit distance=" + dist);
        System.out.println();
    }

    public static void main(String[] args) {
        executeTest("intention", "execution");
        executeTest("algorithmic", "altruistic");
        executeTest("to be or not", "tobias notes");
        executeTest("to be or not to be", "tobias notes");
        executeTest("FOOD","MONEY");

        // next one takes very long time!
        // implement the dynamic programming version to make it work for long strings!
        executeTest("to be or not to be", "to live or not to live");
    }
}
