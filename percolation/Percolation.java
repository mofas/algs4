
public class Percolation {
    
     private WeightedQuickUnionUF uf;
     private WeightedQuickUnionUF fullUf;
     private int dim;  //dimension
     private boolean[] isOpenArray;     
     


       
    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        dim = N;
        uf = new WeightedQuickUnionUF(dim*dim + 2);
        fullUf = new WeightedQuickUnionUF(dim*dim + 2);
        isOpenArray = new boolean[dim*dim + 2];        
        
        for (int i = 0, n = isOpenArray.length; i < n; i++) {
            isOpenArray[i] = false;
        }        
        for (int i = 1; i <= dim; i++) {
            //connect start point            
            union(0, xyTo1D(1, i));
            uf.union(xyTo1D(dim, i), dim*dim+1);
        }        
    }            
    private void union(int i, int j) {
        uf.union(i, j);
        fullUf.union(i, j);
    }
     private int xyTo1D(int i, int j) { 
         return (i - 1) * dim + j;
     }
     private void checkBound(int i, int j) {
         if (i < 1 || i > dim || j < 1 || j > dim)
             throw new IndexOutOfBoundsException("out of bounds");
     }
    
    private void connect(int i, int j) {        
        //Connect Left
        if (j > 1 && isOpen(i, j-1)) {            
            union(xyTo1D(i, j), xyTo1D(i, j-1));
        }
        //Connect Right
        if (j < dim && isOpen(i, j+1)) {
            union(xyTo1D(i, j), xyTo1D(i, j+1));
        }
        //Connect Top
        if (i > 1 && isOpen(i-1, j)) {
            union(xyTo1D(i, j), xyTo1D(i-1, j));
        }
        //Connect Down
        if (i < dim && isOpen(i+1, j)) {
            union(xyTo1D(i, j), xyTo1D(i+1, j));
        }        
    }
    
    // open site (row i, column j) if it is not already
    public void open(int i, int j) {
        checkBound(i, j);
        if (isOpenArray[xyTo1D(i, j)]) return;        
        isOpenArray[xyTo1D(i, j)] = true;
        connect(i, j);        
    }
    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        checkBound(i, j);        
        return isOpenArray[xyTo1D(i, j)];
    }    
    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {        
        return isOpen(i, j) && fullUf.connected(0, xyTo1D(i, j));
    }
    // does the system percolate?
    public boolean percolates() {            
        if (dim == 1) return isOpen(1, 1);        
        return uf.connected(0, dim*dim+1);
    }
}