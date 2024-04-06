package ADA.Lab4.P3;

/*
            Given a set of n rectangular buildings, having the bottoms on a fixed line, draw the skyline of
            these buildings, eliminating hidden lines.
            A building Bi is represented as a triplet (Li, Hi, Ri)
            A skyline of a set of n buildings is a list of x coordinates and the heights connecting them.
            The program reads input data from text files with the following structure:
            The first line contains the value of n, the number of buildings.
            For the next n lines, each line contains three integers, reprsesenting Li, Hi, Ri for each building.

            For example, if we have n=8 buildings with positions:
            (1,11,5), (2,6,7), (3,13,9), (12,7,16), (14,3,25), (19,18,22), (23,13,29), (24,4,28)
            The output is a skyline defined by following segments:
            (1, 11), (3, 13), (9, 0), (12, 7), (16, 3), (19, 18), (22, 3), (23, 13), (29, 0)
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Building {
    int left;
    int height;
    int right;

    public Building(int left, int height, int right) {
        this.left = left;
        this.height = height;
        this.right = right;
    }
}

public class SkylineProblem {

    public static List<int[]> getSkyline(List<Building> buildings) {
        if (buildings.size() == 0)
            return new ArrayList<>();

        return divideAndConquer(buildings, 0, buildings.size() - 1);
    }

    private static List<int[]> divideAndConquer(List<Building> buildings, int start, int end) {
        List<int[]> result = new ArrayList<>();

        if (start == end) {
            Building building = buildings.get(start);
            result.add(new int[]{building.left, building.height});
            result.add(new int[]{building.right, 0});
            return result;
        }

        int mid = start + (end - start) / 2;
        List<int[]> leftSkyline = divideAndConquer(buildings, start, mid);
        List<int[]> rightSkyline = divideAndConquer(buildings, mid + 1, end);

        return mergeSkyline(leftSkyline, rightSkyline);
    }

    private static List<int[]> mergeSkyline(List<int[]> leftSkyline, List<int[]> rightSkyline) {
        List<int[]> result = new ArrayList<>();
        int leftH = 0, rightH = 0;
        int i = 0, j = 0;

        while (i < leftSkyline.size() && j < rightSkyline.size()) {
            int[] leftPoint = leftSkyline.get(i);
            int[] rightPoint = rightSkyline.get(j);
            int x = 0, h = 0;

            if (leftPoint[0] < rightPoint[0]) {
                x = leftPoint[0];
                leftH = leftPoint[1];
                h = Math.max(leftH, rightH);
                i++;
            } else if (leftPoint[0] > rightPoint[0]) {
                x = rightPoint[0];
                rightH = rightPoint[1];
                h = Math.max(leftH, rightH);
                j++;
            } else {
                x = leftPoint[0];
                leftH = leftPoint[1];
                rightH = rightPoint[1];
                h = Math.max(leftH, rightH);
                i++;
                j++;
            }

            if (result.size() == 0 || h != result.get(result.size() - 1)[1]) {
                result.add(new int[]{x, h});
            }
        }

        while (i < leftSkyline.size()) {
            result.add(leftSkyline.get(i++));
        }

        while (j < rightSkyline.size()) {
            result.add(rightSkyline.get(j++));
        }

        return result;
    }

    private static List<Building> readVals(String filename) throws FileNotFoundException {

        List<Building> buildings = new ArrayList<>();
        try {
           Scanner sc = new Scanner(new File(filename));
            int n = sc.nextInt();

            for(int i=0; i<n; i++) {
                buildings.add(new Building(sc.nextInt(), sc.nextInt(), sc.nextInt()));
            }
        }
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return buildings;
    }
    public static void main(String[] args)throws FileNotFoundException {

        List<Building> buildings = readVals("ADA/Lab4/P3/testFiles/skyline_9.txt");

        List<int[]> skyline = getSkyline(buildings);
        for (int[] point : skyline) {
            System.out.println("(" + point[0] + ", " + point[1] + ")");
        }
    }
}

