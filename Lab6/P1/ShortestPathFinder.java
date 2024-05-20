package ADA.Lab6.P1;

import ADA.Lab6.Main.ISimpleGraph;

import java.util.LinkedList;
import java.util.Queue;

public class ShortestPathFinder {

    ISimpleGraph G;
    int[] parent;
    int[] viz;

    public ShortestPathFinder(ISimpleGraph G,int start,int end) {

        this.G = G;
        parent = new int[G.getNrVertices()];
        viz = new int[G.getNrVertices()];

        for( int i=0; i< G.getNrVertices(); i++ ) {
            parent[i] = -1;
            viz[i] = -1;
        }

        if( bfs(start,end) ) {
            constructPath(end);
        }
        else System.out.println("No path found between: "+start+" - "+end);
    }

    private boolean bfs(int start,int end) {

        Queue<Integer> q = new LinkedList<Integer>();
        q.add(start);
        viz[start] = 0;
        //parent[start] = start;

        while(!q.isEmpty()) {
            int v = q.poll();

            for( int  w : G.nodesAdjacentTo(v) ) {

                if( viz[w] == -1 ) {
                    viz[w] = 0;
                    parent[w] = v;
                    q.add(w);
                    if( w == end )
                        return true;
                }
            }
        }
        return false;
    }

    private void constructPath(int end) {

        System.out.println("Shortest Path: ");
        LinkedList<Integer> path = new LinkedList<>();
        for( int v = end; v != -1; v = parent[v]) {
            path.addFirst(v);
        }
        System.out.println(path);
    }
}
