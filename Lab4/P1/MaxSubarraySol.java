package ADA.Lab4.P1;

public class MaxSubarraySol {

    static class MaxSubArrayResult {
            int start;
            int end;
            int sum;

        public MaxSubArrayResult(int start, int end, int sum) {
            this.start = start;
            this.end = end;
            this.sum = sum;
        }

        @Override
        public String toString() {
            return "MaxSubArrayResult{" +
                    "start=" + start +
                    ", end=" + end +
                    ", sum=" + sum +
                    '}';
        }

        public int cmp(MaxSubArrayResult r) {
            return this.sum - r.sum;
        }

    }

    public static MaxSubArrayResult bruteForce(int[] A) {

        int maxSum = A[0];
        int maxSt = -1;
        int maxEn = -1;

        for (int i = 0; i < A.length; i++) {
            for (int j = i; j < A.length; j++) {
                int sum = 0;

                for (int k = i; k <= j; k++)
                    sum += A[k];

                if (sum > maxSum) {
                    maxSum = sum;
                    maxSt = i;
                    maxEn = j;
                }
            }
        }
        return new MaxSubArrayResult(maxSt, maxEn, maxSum);
    }

        public static MaxSubArrayResult DeI ( int[] A ){
            return Divide(A, 0, A.length - 1);
        }

        private static MaxSubArrayResult Divide ( int[] Arr, int left, int right){

            if (left == right) return new MaxSubArrayResult(left, right, Arr[left]);
            else {

                int mid = (left + right) / 2;

                MaxSubArrayResult maxLeft = Divide(Arr, left, mid);
                MaxSubArrayResult maxRight = Divide(Arr, mid+1 , right);
                MaxSubArrayResult maxCross = CrossingSum(Arr,left, right, mid);

                if( maxLeft.cmp(maxRight) > 0 && maxLeft.cmp(maxCross) > 0 )
                    return maxLeft;
                else if ( maxCross.cmp(maxRight) < 0 && maxLeft.cmp(maxRight) < 0 )
                    return maxRight;
                else
                    return maxCross;
            }
        }

        private static MaxSubArrayResult CrossingSum(int[] a,int l,int r,int m) {

            int i;
            int sumL = Integer.MIN_VALUE;
            int sumR = Integer.MIN_VALUE;

            int ML=0,MR=0;

            int sum=0;
            for( i=m; i>=l; i-- ) {
                sum+= a[i];
                if( sum > sumL ) {
                    sumL = sum;
                    ML = i;
                }
            }

            sum=0;
            for( i=m+1; i<=r; i++ ) {
                 sum += a[i];
                 if( sum > sumR ) {
                     sumR = sum;
                     MR = i;
                 }
            }
            return new MaxSubArrayResult(ML,MR, sumL + sumR );
        }
    }
