package ADA.Lab6.Main;

import java.util.*;

/**
 * Implements BreadthFirstSearch on simple undirected graph
 */
public class BreadthFirstSearch {
    enum Color {WHITE, GREY, BLACK}

    private static final int INFINITY = Integer.MAX_VALUE;
    private ISimpleGraph G;
    private Color[] color;     // color[v] = status of node v
    private int[] parent;      // parent[v] = previous node on shortest path from source s to v
    private int[] distTo;      // dist[v] = number of edges that define the
    // shortest path from source s to v
    private Set<SimpleEdge> bfsTree;
    private int[] component;
    private int noComponents;

    List<Integer> partition1;
    List<Integer> partition2;
    /**
     * Performs the BFS search starting from the source vertex {@code s}
     * in the undirected graph {@code G}.
     * Only vertices in the same connected component with s will be reached.
     * Vertices reached by the BFS will have color BLACK.
     *
     * @param G the graph
     * @param s the source vertex
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     */
    public BreadthFirstSearch(ISimpleGraph G, int s) {

        this.G = G;

        int V = G.getNrVertices();

        bfsTree = new LinkedHashSet<SimpleEdge>();

        color = new Color[V];
        distTo = new int[V];
        parent = new int[V];

        partition1 = new ArrayList<>();
        partition2 = new ArrayList<>();

        for (int v = 0; v < V; v++) {
            distTo[v] = INFINITY;
            color[v] = Color.WHITE; // WHITE = vertex is new, unknown
            parent[v] = -1;
        }

        component = new int[V];
        noComponents = 0;

        if (!G.hasVertex(s))
            throw new IllegalArgumentException("vertex " + s + " is not between 0 and " + (V - 1));

        // bfsTestBipartite(G,s);
        bfs(G, s);
    }

    public void bfsTestBipartite(ISimpleGraph G, int s) {

        boolean isBip = true;

        for( int v=0; v<G.getNrVertices(); v++) {
            if( color[v] == Color.WHITE) {
                isBip = isBipartite(G,v);
                break;
            }
        }

        if (isBip) {
            System.out.println("The graph is bipartite.");
            System.out.println("Partition 1: " + partition1);
            System.out.println("Partition 2: " + partition2);
        } else {
            System.out.println("The graph is not bipartite.");
        }
    }

    private boolean isBipartite(ISimpleGraph G, int source) {

        Queue<Integer> q = new LinkedList<>();
        q.add(source);
        color[source] = Color.GREY;
        partition1.add(source);

        while( !q.isEmpty()) {

            int v = q.poll();
            for( int w : G.nodesAdjacentTo(v) ) {

                if( color[w] == Color.WHITE) {

                    if( color[v] == Color.GREY) {
                        color[w] = Color.BLACK;
                        partition2.add(w);
                    }
                    else if( color[v] == Color.BLACK){
                        color[w] = Color.GREY;
                        partition1.add(w);
                    }
                    q.add(w);
                }
                else if ( color[w] == color [v] ){
                    return false;
                }
            }
        }
        return true;
    }


    // breadth-first search from a single source
    private void bfs(ISimpleGraph G, int source) {
        int V = G.getNrVertices();

        Queue<Integer> q = new LinkedList<>();

        component = new int[G.getNrVertices()];
        int cnt = 0;
        // start BFS with source node
        color[source] = Color.GREY; // GREY = start exploring
        distTo[source] = 0;
        parent[source] = -1;
        q.add(source);

        component[cnt++] = source;

        while (!q.isEmpty()) {
            int v = q.remove(); // remove oldest vertex from queue
            for (int w : G.nodesAdjacentTo(v)) {
                if (color[w] == Color.WHITE) { // first time encounter of vertex w

                    bfsTree.add(new SimpleEdge(v, w));
                    component[cnt++] = w;

                    parent[w] = v;
                    distTo[w] = distTo[v] + 1;
                    color[w] = Color.GREY;  // start exploring w
                    q.add(w);               // put w in queue
                }
            }
            System.out.print(v + " ");
            color[v] = Color.BLACK;         // finished exploring v
        }
        System.out.println();   // finished exploring all nodes
        //  from the same connected component with source
        // At this point, all nodes from the same connected component are BLACK.
        // Nodes from other connected components are still WHITE.
        printConnectedComponent(cnt);
        for(int v = 0; v < G.getNrVertices() ; v++ ) {
            if( color[v] == Color.WHITE ) {
                bfs(G,v);
            }
        }
    }


    /**
     * Is there a path between the source vertex s, that has been
     * set in the constructor, and vertex v given here as parameter?
     *
     * @param v the vertex
     * @return true if there is a path, and false otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public boolean hasPathTo(int v) {
        int V = G.getNrVertices();
        if (!G.hasVertex(v))
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
        return (color[v] == Color.BLACK);
    }

    /**
     * Returns the number of edges in a shortest path between
     * the source vertex s, set in the constructor, and vertex v
     *
     * @param v - the vertex
     * @return the number of edges in such a shortest path
     * (or Integer.MAX_VALUE if there is no such path)
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int distTo(int v) {
        int V = G.getNrVertices();
        if (!G.hasVertex(v))
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
        return distTo[v];
    }

    /**
     * Returns a shortest path between the source vertex s, set
     * in the constructor, and vertex v, or null if no such path.
     *
     * @param v the vertex
     * @return the sequence of vertices on a shortest path, as an Iterable
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public Iterable<Integer> pathTo(int v) {
        int V = G.getNrVertices();
        if (!G.hasVertex(v))
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
        if (!hasPathTo(v)) return null;

        LinkedList<Integer> path = new LinkedList<>();
        int x;
        for (x = v; distTo[x] != 0; x = parent[x]) { // start from v, go back until source s
            path.addFirst(x);
        }
        path.addFirst(x);
        return path;
    }

    public void printBFStree(int s) {
        System.out.println("BFS Tree from source: " + s);
        for( SimpleEdge e : bfsTree ) {
            System.out.println(e.either() + " - " + e.other(e.either()));
        }
    }

    public void printBFSforest(int s) {

        System.out.println("BFS forest from source: " + s);
        System.out.println();
        int countTrees = 1;
        System.out.println("Tree " + countTrees + " :");
        printBFStree(s);
        System.out.println();

        for(int v = 0;  v<G.getNrVertices(); v++ ) {
            if( color[v] == Color.WHITE ) {

                bfsTree = new LinkedHashSet<>();
                countTrees++;
                System.out.println("Tree "+ countTrees+ ": ");
                bfs(G,v);
                printBFStree(v);
                System.out.println();

            }
        }
    }

    public void printConnectedComponent(int cnt) {

        noComponents++;
        System.out.println("Connected component " + noComponents + ":");
        System.out.print("Vertices: ");

            for(int i=0; i<cnt; i++) {
                System.out.print(component[i] + " ");
            }
        System.out.println();
    }
}