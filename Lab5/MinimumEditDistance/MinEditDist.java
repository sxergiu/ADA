package ADA.Lab5.MinimumEditDistance;

public class MinEditDist {
    /**
     * Recursive algorithm to compute Minimum Edit Distance
     * between two strings. Returns total number of operations
     * needed to transform first string into second string
     * @param A first string
     * @param B second string
     * @param m length of A
     * @param n length of B
     * @return total number of operations needed for transformation
     */
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
        int dist = editDistance(X, Y, m, n);
        long end = System.currentTimeMillis();

        System.out.println("Computation time =" + (end - start) + " ms");
        System.out.println("Result: Minimum edit distance=" + dist);
        System.out.println();
    }

    public static void main(String[] args) {
        executeTest("intention", "execution");
        executeTest("algorithmic", "altruistic");
        executeTest("to be or not", "tobias notes");
        executeTest("to be or not to be", "tobias notes");

        // next one takes very long time!
        // implement the dynamic programming version to make it work for long strings!
        executeTest("to be or not to be", "to live or not to live");
    }
}
