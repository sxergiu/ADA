package ADA.Lab5.BinCoeff;

public class BinCoeffMemo implements IBinCoeffSol{

    private class ResultEntry {
        boolean done = false;
        long val;
    }
    private ResultEntry[][] res;
    @Override
    public long C(int N, int K) {

        res = new ResultEntry[N+1][K+1];
        for(int i=0; i<=N; i++)
            for(int j=0; j<=K; j++)
                res[i][j] = new ResultEntry();

        return Cmemo(N,K);
    }

    private long Cmemo(int n,int k) {

        if( res[n][k].done )
            return res[n][k].val;
        else {

            if (k == 0 || n == 0){
                res[n][k].val = 1;
                res[n][k].done = true;
            }
            else{
                res[n][k].val = Cmemo(n-1,k-1) + Cmemo(n-1,k);
                res[n][k].done = true;
            }
        }
        return res[n][k].val;
    }
}
