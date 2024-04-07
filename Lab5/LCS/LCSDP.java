package ADA.Lab5.LCS;

/*
    longest commmon subsequence length between 2 strings
        additional variants: recursive + memoization for simple
        complex alternative: the lcs itself is returned
 */

public class LCSDP {

    public int LCS_length(String x, String y) {
        char[] X = x.toCharArray();
        char[] Y = y.toCharArray();
        int n = X.length;
        int m = Y.length;

        //allocate memory for table containing results
        int[][] result = new int[n+1][m+1];

        // init table elements corresponding to base cases:
        // init first row and first column with zero
        for (int i = 0; i <=n; i++)
            result[i][0]=0;
        for (int j = 0; j <= m; j++)
            result[0][j] = 0;

        //iterate through table and compute elements
        for (int i = 1; i <=n; i++)
            for (int j = 1; j <=m; j++) {
                if (X[i - 1] == Y[j - 1]) {
                    result[i][j] = 1 + result[i - 1][j - 1];
                }
                else
                    result[i][j] = Math.max(result[i - 1][j], result[i][j - 1]);
            }

        return result[n][m];
    }


    public void executeTest(String x, String y) {

        System.out.println("Computing Longest Common Subsequence for:");
        System.out.println(x);
        System.out.println(y);

        long start = System.currentTimeMillis();
        int l = LCS_length(x,y);
        long end = System.currentTimeMillis();

        System.out.println("Computation time =" + (end - start) + " ms");
        System.out.println("Result: LCS=" + l);
        System.out.println();
    }

    public static void main(String[] args) {
        LCSDP solver = new LCSDP();

        solver.executeTest("snowflake", "horseback");
        solver.executeTest("intention", "execution");
        solver.executeTest("algorithmic", "altruistic");
        solver.executeTest("to be or not", "tobias notes");
        solver.executeTest("to be or not to be", "tobias notes");

        solver.executeTest("The domestic cat Felis catus or Felis silvestris catus is a small, usually furry, domesticated, and carnivorous mammal.",
                "The domestic cat is small and furry.");

        String long1 = "The domestic cat Felis catus or Felis silvestris catus is a small, usually furry, domesticated, and carnivorous mammal. They are often called a housecat when kept as an indoor pet, or simply a cat when there is no need to distinguish them from other felids and felines. Cats are often valued by humans for companionship, and their ability to hunt vermin and household pests.";
        String long2 = "The domestic cat is small and furry. Cats are social species. They are cute.";
        solver.executeTest(long1, long2);
    }
}