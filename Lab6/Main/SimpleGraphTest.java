package ADA.Lab6.Main;

import ADA.Lab6.P1.ShortestPathFinder;

import java.io.IOException;
import java.util.Scanner;

public class SimpleGraphTest {
    public static void main(String[] args) throws IOException {

        ISimpleGraph G = null;

        String filename = "Lab6/Main/demo1.txt"; //file containing input data for simple graph

        System.out.println("Choose graph implementation: 1=Adj matrix, 2=Adj lists");
        System.out.println("Enter your choice:");
        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();

        if (choice == 1)
            G = new SimpleGraphMatrix(filename);
        else if (choice == 2)
            G = new SimpleGraphAdjList(filename);
        else {
            System.out.println("Wrong choice!");
            System.exit(1);
        }

        System.out.println(" The graph from file " + filename);
        G.printGraph();

        int source = 2; // set a vertex as source for DFS and BFS

        System.out.println("Depth First Search starting from " + source);

        DepthFirstSearch dfs = new DepthFirstSearch(G, 0);
        System.out.println();
        dfs.printDFStree(source);

        dfs.printDFSforest(source);


        /*
        for (int v = 0; v < G.getNrVertices(); v++) {
            if (v != source) {
                if (search.isConnectedTo(v))
                    System.out.println(source + " and " + v + "  are connected");
                else System.out.println(source + " and " + v + "  are not connected");
            }
        }
        */


        System.out.println();

        System.out.println("Breadth First Search starting from " + source);

        BreadthFirstSearch bfs = new BreadthFirstSearch(G, source);

        // bfs.printBFStree(source);

        //bfs.printBFSforest(source);


        /*
        for (int v = 0; v < G.getNrVertices(); v++) {
            if (bfs.hasPathTo(v)) {
                System.out.print(" path from " + source + " to " + v + " distance=" + bfs.distTo(v) + "  Nodes: ");
                for (int x : bfs.pathTo(v)) {
                    System.out.print(x + " ");
                }
                System.out.println();
            } else {
                System.out.println("Nodes " + source + "-" + v + " are not connected through any path");
            }

        }
       */

        ShortestPathFinder pathFinder = new ShortestPathFinder(G,3,1);

    }
}
