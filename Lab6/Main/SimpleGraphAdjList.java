package ADA.Lab6.Main;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Implementation of the simple undirected unweighted graph
 * with an Adjacency Structure with Lists.
 */
public class SimpleGraphAdjList implements ISimpleGraph {

    protected int V; // number of nodes
    protected int E; // number of edges

    /**
     * The graph is represented as an adjacency structure.
     * At g[i] there is the set of nodes j
     * that are adjacent to i.
     * Because the graph is undirected,
     * if j is in the adjacency list of i, then
     * also i is in the adjacency list of j.
     */
    protected Set<Integer>[] g = null;

    /**
     * Constructs a graph having V nodes and no edges.
     *
     * @param V = number of nodes
     */
    public SimpleGraphAdjList(int V) {
        this.V = V;
        E = 0;
        g = (Set<Integer>[]) new Set[V];
        for (int v = 0; v < V; v++) {
            g[v] = new HashSet<Integer>() {
            };
        }
    }

    /**
     * Constructs a graph with V nodes and E edges, reading from file.
     *
     * The expected format of the file is:
     * On the first line, the number of nodes.
     * On the next lines, the edges given as node1 node2
     *
     * @param file - name of file
     * @throws IOException
     */
    public SimpleGraphAdjList(String file) throws IOException {
        initFromFile(file);
    }

    /**
     * @return - number of vertices(nodes)
     */
    public int getNrVertices() {
        return V;
    }

    /**
     * @return - number of edges
     */
    public int getNrEdges() {
        return E;
    }

    /**
     * @param node an integer number
     * @return - true if node represents a vertex (value between 0 and V-1)
     */
    public boolean hasVertex(int node) {
        if ((node >= 0) && (node < V))
            return true;
        else
            return false;
    }

    /**
     * @param either - an integer number representing a node
     * @param other  - another integer number representing another node
     * @return - true if there is an edge between the two nodes
     */
    public boolean hasEdge(int either, int other) {
        if (hasVertex(either) && hasVertex(other))
            if (g[either].contains(other))
                return true;
        return false;
    }

    /**
     * Adds an edge in an undirected graph by adding two directed edges.
     */
    public void addUndirectedEdge(int either, int other) {
        if (hasVertex(either) && hasVertex(other)) {
            g[either].add(other);
            g[other].add(either); // in undirected graphs add 2
            // entries in the adjacency structure
            E++; // increase edge counter
        }
    }

    /**
     * Returns all edges of an undirected graph. An edge i->j is
     * reported only once, when i<j.
     */
    public Iterable<SimpleEdge> allEdges() {
        Set<SimpleEdge> edgeSet = new HashSet<SimpleEdge>();
        for (int i = 0; i < V; i++) // for all nodes i
            for (Integer j : g[i]) { //iterate adjacents j
                if (j > i) { // in undirected graphs make sure to
                    // list every edge only once
                    SimpleEdge ed = new SimpleEdge(i, j);
                    edgeSet.add(ed);
                }
            }
        return edgeSet;
    }

    /**
     * Read graph from file
     *
     * @param file - name of file
     * @throws IOException
     */
    public void initFromFile(String file) throws IOException {
        File input = new File(file);
        Scanner is = new Scanner(input);

        V = is.nextInt();
        E = 0;
        g = (Set<Integer>[]) new Set[V];
        for (int v = 0; v < V; v++) {
            g[v] = new HashSet<Integer>();
        }

        System.out.println("Reading graph with " + V + " nodes from file "
                + file + " ...");

        int either, other;
        while (is.hasNext()) {
            either = is.nextInt();
            other = is.nextInt();
            addUndirectedEdge(either, other);
        }
    }

    /**
     * Displays undirected unweighted graph.
     * Undirected edges i-j are shown only when i<j
     */
    public void printGraph() {
        System.out.println("Vertices (nodes): 0 .. " + (V - 1));
        System.out.println("Edges: E=" + E);
        for (int i = 0; i < V; i++) {
            for (Integer j : g[i]) {
                if (j > i) //print undirected edges only once
                    System.out.println(i + "-" + j);
            }
        }
    }


    public Iterable<Integer> nodesAdjacentTo(int node) {
        if ((node >= 0) && (node < V))
            return g[node];
        return null;
    }

    public Iterable<SimpleEdge> edgesAdjacentTo(int node) {
        Set<SimpleEdge> edgeSet = new HashSet<SimpleEdge>();
        for (Integer e : g[node]) {
            SimpleEdge ed = new SimpleEdge(node, e);
            edgeSet.add(ed);
        }
        return edgeSet;
    }

}
