package ADA.Lab6.Main;


import java.util.*;

/**
 * Implements DepthFirstSearch on simple undirected graph
 */
public class DepthFirstSearch {

    enum Color {WHITE, GREY, BLACK}

    private ISimpleGraph G;

    private Color[] color;      // color[v] = status of node v

    private int[] parent;      // parent[v] = previous node on DFS search

    private Set<SimpleEdge> dfsTree;

    boolean hasCycle = false;
    List<Integer> cycle;


    /**
     * Performs the DFS search starting from the source vertex {@code s}
     * in the undirected graph {@code G}.
     * Only vertices in the same connected component with s will be reached.
     * Vertices reached by the DFS will have color BLACK.
     *
     * @param G the graph
     * @param s the source vertex
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     */
    public DepthFirstSearch(ISimpleGraph G, int s) {

        this.G = G;
        int V = G.getNrVertices();

        if (!G.hasVertex(s))
            throw new IllegalArgumentException("vertex " + s + " is not between 0 and " + (V - 1));

        dfsTree = new LinkedHashSet<SimpleEdge>();

        color = new Color[V];
        parent = new int[V];

        for (int v = 0; v < G.getNrVertices(); v++) {
            color[v] = Color.WHITE;
            parent[v] = -1;
        }

        cycle = new ArrayList<>();
        dfs(G, s);

        if( hasCycle ){

            for( int i=0; i<cycle.size(); i++) {
                System.out.print(cycle.get(i) + " - " );
            }
        }
        else
            System.out.println("No cycle detected!");
    }

    // depth-first search from a single source
    private void dfs(ISimpleGraph G, int source) {
        color[source] = Color.GREY;   // start exploring source
        //System.out.print(source + " ");
        for (int v : G.nodesAdjacentTo(source)) {

            if( hasCycle ) return;
            if (color[v] == Color.WHITE) {  // first time encounter v
                parent[v] = source;
                dfsTree.add(new SimpleEdge(source, v));
                dfs(G, v);// start exploring v
            }
        }
        color[source] = Color.BLACK;     // finished exploring source
    }

    private void dfsTestCycle(ISimpleGraph G, int source) {
        color[source] = Color.GREY;   // start exploring source
        //System.out.print(source + " ");
        for (int v : G.nodesAdjacentTo(source)) {

            if( hasCycle ) return;
            if (color[v] == Color.WHITE) {  // first time encounter v
                parent[v] = source;
                dfsTree.add(new SimpleEdge(source, v));
                dfs(G, v);// start exploring v
            }
            else if( v != parent[source]) {
                hasCycle = true;
                for( int x=source; x!=v && x!=-1; x=parent[x] ) {
                    cycle.add(x);
                }
                cycle.add(v);
                cycle.add(source);
                return;
            }
        }
        color[source] = Color.BLACK;     // finished exploring source
    }


    /**
     * Is there a path between the source vertex s, that has been
     * set in constructor, and vertex v?
     *
     * @param v the vertex
     * @return true if there is a path, false otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public boolean isConnectedTo(int v) {
        int V = G.getNrVertices();

        if (!G.hasVertex(v))
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));

        return (color[v] == Color.BLACK);
    }

    public void printDFStree(int s) {
        System.out.println("DFS Tree from source " + s + ": ");
        for( SimpleEdge e : dfsTree ) {
            System.out.println(e.either() + " - " + e.other(e.either()));
        }
        System.out.println();
    }

    public void printDFSforest(int s) {

        System.out.println("DFS Forest from source: " + s );
        int countForests = 1;
        System.out.println(countForests + " forest: ");
        printDFStree(s);

        for(int v = 0; v < G.getNrVertices(); v++) {

            dfsTree = new LinkedHashSet<>();
            if( color[v] == Color.WHITE ) {

                countForests++;
                System.out.println(countForests + " forest: ");
                dfs(G, v);
                printDFStree(v);
            }

        }
    }

}