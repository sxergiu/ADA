package ADA.Lab5.BinCoeff;

public class BinCoeffDP implements IBinCoeffSol{

    @Override
    public long C(int N, int K) {

        long [][] result = new long[N+1][K+1];

        for(int i=0; i<=N; i++)
            result[i][0] = 1;
        for(int j=0; j<=K; j++)
            result[0][j] = 1;

        for(int i=1; i<=N; i++)
            for(int j=1; j<=K; j++)
            {
                result[i][j] = result[i-1][j-1] + result[i-1][j];
            }

        return result[N][K];
    }
}
