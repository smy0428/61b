package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    private double[] fractionOpen;
    private StdRandom RANDOM;
    private int times;


    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException();
        } else {
            fractionOpen = new double[T];
            times = T;
            for (int i = 0; i < T; i += 1) {
                Percolation p = pf.make(N);
                while (!p.percolates()) {
                    int id = RANDOM.uniform(N * N);
                    while (p.isOpen(helperRow(N, id), helperCol(N, id))) {
                        id = RANDOM.uniform(N * N);
                    }
                    p.open(helperRow(N, id), helperCol(N, id));
                }
                fractionOpen[i] = p.numberOfOpenSites();
            }
        }
    }


    private int helperRow(int N, int id) {
        return id % N;
    }


    private int helperCol(int N, int id) {
        return id / N;
    }


    public double mean() {
        double m = StdStats.mean(fractionOpen);
        return m;
    }


    public double stddev() {
        double s = StdStats.stddev(fractionOpen);
        return s;
    }


    public double confidenceLow() {
        double cL = StdStats.mean(fractionOpen)
                - 1.96 * Math.sqrt(StdStats.stddev(fractionOpen) / times);
        return cL;
    }


    public double confidenceHigh() {
        double cH = StdStats.mean(fractionOpen)
                + 1.96 * Math.sqrt(StdStats.stddev(fractionOpen) / times);
        return cH;
    }


    public static void main(String[] args) {
        Stopwatch timer1 = new Stopwatch();

        PercolationFactory pf = new PercolationFactory();
        PercolationStats plS = new PercolationStats(160, 100, pf);
        System.out.println(plS.confidenceLow());
        System.out.println(plS.confidenceHigh());

        double time1 = timer1.elapsedTime();
        System.out.println("time used is " + time1);
    }
}
