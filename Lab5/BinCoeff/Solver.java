package ADA.Lab5.BinCoeff;

/*
        computes C(n,k) exemplifying recursivity -> memoization -> dynamic programming
        additional memory efficient solution may exist
 */

public class Solver {

    public static void executeTest( IBinCoeffSol sol, int n, int k ) {

        System.out.println( sol.C(n,k) );
    }

    public static void main(String[] args) {

        IBinCoeffSol res = new BinCoeffRecursive();
        executeTest(res,10,2);
        res = new  BinCoeffMemo();
        executeTest(res,10,2);
        res = new BinCoeffDP();
        executeTest(res,10,2);
    }
}
