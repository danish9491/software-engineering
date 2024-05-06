package com.yash;
import edu.princeton.cs.algs4.*;
import java.util.Arrays;
import java.util.Scanner;

public class PrimMST_1 {
    private static final double FLOATING_POINT_EPSILON = 1.0E-12;

    private Edge[] edgeTo;
    private double[] distTo;
    private boolean[] marked;
    private IndexMinPQ<Double> pq;


    public PrimMST_1(EdgeWeightedGraph G) {
        edgeTo = new Edge[G.V()];
        distTo = new double[G.V()];
        marked = new boolean[G.V()];
        pq = new IndexMinPQ<Double>(G.V());
        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;

        for (int v = 0; v < G.V(); v++)
            if (!marked[v]) prim(G, v);

        assert check(G);
    }

    private void prim(EdgeWeightedGraph G, int s) {
        distTo[s] = 0.0;
        pq.insert(s, distTo[s]);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            scan(G, v);
        }
    }

    private void scan(EdgeWeightedGraph G, int v) {
        marked[v] = true;
        for (Edge e : G.adj(v)) {
            int w = e.other(v);
            if (marked[w]) continue;
            if (e.weight() < distTo[w]) {
                distTo[w] = e.weight();
                edgeTo[w] = e;
                if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
                else                pq.insert(w, distTo[w]);
            }
        }
    }

    public Iterable<Edge> edges() {
        Queue<Edge> mst = new Queue<Edge>();
        for (int v = 0; v < edgeTo.length; v++) {
            Edge e = edgeTo[v];
            if (e != null) {
                mst.enqueue(e);
            }
        }
        return mst;
    }

    public double weight() {
        double weight = 0.0;
        for (Edge e : edges())
            weight += e.weight();
        return weight;
    }


    private boolean check(EdgeWeightedGraph G) {

        double totalWeight = 0.0;
        for (Edge e : edges()) {
            totalWeight += e.weight();
        }
        if (Math.abs(totalWeight - weight()) > FLOATING_POINT_EPSILON) {
            System.err.printf("Weight of edges does not equal weight(): %f vs. %f\n", totalWeight, weight());
            return false;
        }

        UF uf = new UF(G.V());
        for (Edge e : edges()) {
            int v = e.either(), w = e.other(v);
            if (uf.find(v) == uf.find(w)) {
                System.err.println("Not a forest");
                return false;
            }
            uf.union(v, w);
        }

        for (Edge e : G.edges()) {
            int v = e.either(), w = e.other(v);
            if (uf.find(v) != uf.find(w)) {
                System.err.println("Not a spanning forest");
                return false;
            }
        }

        for (Edge e : edges()) {

            uf = new UF(G.V());
            for (Edge f : edges()) {
                int x = f.either(), y = f.other(x);
                if (f != e) uf.union(x, y);
            }

            // check that e is min weight edge in crossing cut
            for (Edge f : G.edges()) {
                int x = f.either(), y = f.other(x);
                if (uf.find(x) != uf.find(y)) {
                    if (f.weight() < e.weight()) {
                        System.err.println("Edge " + f + " violates cut optimality conditions");
                        return false;
                    }
                }
            }

        }

        return true;
    }


    public static void main(String[] args) {
        int i;
        Scanner sc = new Scanner(System.in);
        int l  = sc.nextInt();
        float []a = new float [3];
        String input = null;
        if (l==0) {
            for (i = 0; i < 3; i++) {
                if (i == 0) {
                    input = "./facebook.txt";
                } else if (i == 1) {
                    input = "./deezereurope.txt";
                } else if (i == 2) {
                    input = "./resistance.txt";
                }

                In in = new In(input);

                EdgeWeightedGraph G = new EdgeWeightedGraph(in);
                PrimMST_1 mst = new PrimMST_1(G);
                for (Edge e : mst.edges()) {
                    StdOut.println(e);
                }
                StdOut.printf("%.5f\n", mst.weight());
                a[i] = (float) mst.weight();
            }
        }
        if(l==1)
        {
            for (i = 0; i < 3; i++) {
                if (i == 0) {
                    input = "./facebook.txt";
                } else if (i == 1) {
                    input = "./deezereurope.txt";
                } else if (i == 2) {
                    input = "./resistance.txt";
                }

                In in = new In(input);

                EdgeWeightedGraph G = new EdgeWeightedGraph(in);
                PrimMST_1 mst = new PrimMST_1(G);
                for (Edge e : mst.edges()) {
                    StdOut.println(e);
                }
                StdOut.printf("%.5f\n", mst.weight());
                a[i] = (float) mst.weight();
            }
        }
        Arrays.sort(a);
        System.out.print("Mean = ");
        System.out.println((a[0]+a[1]+a[2])/3);
        System.out.print("Median = ");
        System.out.println(a[1]);
        System.out.print("Minimum = ");
        System.out.println(a[0]);
        System.out.print("Maximum = ");
        System.out.println(a[2]);
    }
}
