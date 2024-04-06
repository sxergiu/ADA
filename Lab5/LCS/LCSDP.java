package ADA.Lab5.LCS;

public class LCSDP {

    public int LCS_length(String x, String y) {
        char[] X = x.toCharArray();
        char[] Y = y.toCharArray();
        int n = X.length;
        int m = Y.length;

        // replace big result matrix by two current rows
        int[] result1 = new int[m + 1];  // previous row
        int[] result2 = new int[m + 1];  // current row

        for (int j = 0; j <= m; j++) {
            result1[j] = 0;  // initialize first row
        }
        for (int i = 1; i <= n; i++) {
            // at every iteration i,
            // result1 holds row i-1 and result2 holds row i
            result2[0] = 0;   // init element of first column
            for (int j = 1; j <= m; j++) {
                if (X[i - 1] == Y[j - 1]) {
                    result2[j] = 1 + result1[j - 1];
                } else
                    result2[j] = Math.max(result1[j], result2[j - 1]);
            }
            // before next iteration, swap rows
            // the new previous row will be the current row
            int[] auxi = result1;
            result1 = result2;
            result2 = auxi;
        }
        return result1[m];
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
