public class PercolationStats {
        
    private double mean;
    private double stddev;    
    private double[] precolationRate;
    
        
    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats(int N, int T) {        
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("out of Bound");
        }
        precolationRate = new double[T];
        for (int i = 0; i < T; i++) {            
            precolationRate[i] = runPercolation(N);            
        }        
        mean = mean();
        stddev = stddev();        
    }
    
    
    private double runPercolation(int N) {        
        int openCount = 0;
        Percolation obj = new Percolation(N);
        
        while (!obj.percolates()) {
            //random fill obj
            int x = (int) (Math.random()*N + 1), 
                y = (int) (Math.random()*N + 1);
            if (!obj.isOpen(x, y)) {
                openCount++;
                obj.open(x, y);
            }            
        }        
        return (double) openCount/(N*N);
    }

    // sample mean of percolation threshold
    public double mean() {        
        return StdStats.mean(precolationRate);
    }
    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(precolationRate);
    }
    // returns lower bound of the 95% confidence interval
    public double confidenceLo() {
        return (mean - (1.96 * stddev) / Math.sqrt(precolationRate.length));
    }
    // returns upper bound of the 95% confidence interval
    public double confidenceHi() {
        return (mean + (1.96 * stddev) / Math.sqrt(precolationRate.length));
    }
    // test client, described below
    public static void main(String[] args) {        
        if (args.length != 2) return;        
        try {
            int N = Integer.parseInt(args[0]);
            int T = Integer.parseInt(args[1]);

            PercolationStats percolationStats = new PercolationStats(N, T);
            StdOut.println("mean                    = "
                    + percolationStats.mean());
            StdOut.println("stddev                  = "
                    + percolationStats.stddev());
            StdOut.println("95% confidence interval = "
                    + percolationStats.confidenceLo() + ", "
                    + percolationStats.confidenceHi());
        } catch (NumberFormatException e) {
            StdOut.println("Argument must be an integer");
            return;
        }
    }
}