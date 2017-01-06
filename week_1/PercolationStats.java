import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * @author Dima Pasieka
 */
public class PercolationStats {

    // Number of independent experiments on an n-by-n grid
    private int trials;

    // Store all threshold results
    private double[] thresholdList;

    // Perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n < 1) {
            throw new IllegalArgumentException("Grid must have at least one row and column");
        }

        if (trials < 1) {
            throw new IllegalArgumentException("You must run percolation at least once");
        }

        this.trials = trials;
        thresholdList = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);

            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);

                percolation.open(row, col);
            }

            thresholdList[i] = (double) percolation.numberOfOpenSites() / (n * n);
        }
    }

    // Sample mean of percolation threshold
    public double mean()
    {
        return StdStats.mean(thresholdList);
    }

    // Sample standard deviation of percolation threshold
    public double stddev()
    {
        return StdStats.stddev(thresholdList);
    }

    // Low  endpoint of 95% confidence interval
    public double confidenceLo()
    {
        return mean() - (1.96 * stddev() / Math.sqrt(trials));
    }

    // High endpoint of 95% confidence interval
    public double confidenceHi()
    {
        return mean() + (1.96 * stddev() / Math.sqrt(trials));
    }

    // Test client
    public static void main(String[] args)
    {
        int gridLength = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(gridLength, trials);

        StdOut.println("mean = "+ stats.mean());
        StdOut.println("stddev = "+ stats.stddev());
        StdOut.println("95% confidence interval = "+ stats.confidenceLo() + ", " + stats.confidenceHi());
    }
}