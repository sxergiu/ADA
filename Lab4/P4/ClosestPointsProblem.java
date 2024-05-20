package ADA.Lab4.P4;

/*
            Given a set of n points in plane, where each point has known coordinates (x,y).
            Find the two points that are closest together.
            The program reads input data from text files with the following structure:
            The first line contains the value of n, the number of points.
            The next n lines contain each line contains two integers representing the values of x[i], y[i].

            The output must show:
            - one pair of points that are at minimum distance (these points are not necessarily unique!), and
            - the value of the minimum distance.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class ClosestPointsProblem {

    private static class point{
        int x,y;

        @Override
        public String toString() {
            return this.x + "," + this.y;
        }
    }

    private static class result {

        point p1;
        point p2;
        double minDist;

        public result(point p1, point p2, double minDist) {
            this.p1 = p1;
            this.p2 = p2;
            this.minDist = minDist;
        }
    }

    private static point[] readValues(String path) throws FileNotFoundException {

            Scanner sc = new Scanner(new File(path));
            int n = sc.nextInt();

            point[] pairs = new point[n];
            for(int i=0; i<n; i++) {
                pairs[i] = new point();
                pairs[i].x = sc.nextInt();
                pairs[i].y = sc.nextInt();
            }

            return pairs;
    }

    private static double dist(point a,point b) {

        double dx = a.x-b.x;
        double dy = a.y-b.y;
        return Math.sqrt(dx*dx+dy*dy);
    }

    private static result BruteForce(point[] pairs) {

        result res = new result(null,null, Double.MAX_VALUE );

        for( int i=0; i<pairs.length; i++) {
            for(int j=i+1; j<pairs.length; j++) {
                double d = dist(pairs[i],pairs[j]);
                if( d < res.minDist ) {
                        res.minDist = d;
                        res.p1 = pairs[i];
                        res.p2 = pairs[j];
                }
            }
        }
        return res;
    }
    public static result DivideEtImpera(point[] pairs, int l, int r) {
        if (r - l + 1 <= 3 || l > r)
            return BruteForce(pairs);
        else {
            Arrays.sort(pairs, Comparator.comparingInt(p -> p.x));

            int mid = l + (r - l) / 2;
            point midPoint = pairs[mid];

            result leftMin = DivideEtImpera(pairs, l, mid);
            result rightMin = DivideEtImpera(pairs, mid + 1, r);

            double minDist = Math.min(leftMin.minDist, rightMin.minDist);
            result mergeRes = minDist == leftMin.minDist ? leftMin : rightMin;

            for (int i = l; i <= r; i++) {
                if (Math.abs(pairs[i].x - midPoint.x) < minDist) {
                    for (int j = i + 1; j <= r; j++) {
                        if (Math.abs(pairs[j].x - midPoint.x) < minDist) {
                            if (dist(pairs[i], pairs[j]) < minDist)
                                mergeRes = new result(pairs[i], pairs[j], dist(pairs[i], pairs[j]));
                        }
                    }
                }
            }
            return mergeRes;
        }
    }


    public static void main(String[] args) throws FileNotFoundException{

            point[] pairs = readValues("ADA/Lab4/P4/points.txt");
            result r = DivideEtImpera(pairs,0, pairs.length-1);
        System.out.println("Minimum distance: " + r.minDist + " between\n(" + r.p1 +")(" + r.p2+")");

    }
}
