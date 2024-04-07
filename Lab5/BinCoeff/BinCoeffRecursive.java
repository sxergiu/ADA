package ADA.Lab5.BinCoeff;

public class BinCoeffRecursive implements IBinCoeffSol {

    @Override
    public long C(int N, int K) {

        if( K==0 || N==0 ) return 1;
        else
            return C(N-1,K-1) + C(N-1,K);
    }
}
