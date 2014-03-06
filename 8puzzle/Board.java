public class Board {



    private int[][] board;
    private int N;

    private int emptyBlockRow;
    private int emptyBlockCol;


    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)    
    public Board(int[][] blocks) {
        N = blocks.length;
        board = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                board[i][j] = blocks[i][j];
                if (blocks[i][j] == 0) {
                    emptyBlockRow = i;
                    emptyBlockCol = j;
                }
            }
        }
    }    
                                           
    // board dimension N
    public int dimension() {
        return N;
    }
    
    // number of blocks out of place
    public int hamming() {
        int currentBlock;
        int total = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {                
                currentBlock = board[i][j];
                if (currentBlock != 0) {
                    total += hammingDist(currentBlock-1, i, j);
                }                
            }
        }
        return total;
    }

    private int hammingDist(int currentNum, int i, int j) {
        int correctCol = currentNum % N;
        int correctRow = currentNum / N;
        if (correctRow == i && correctCol == j) return 0;
        return 1;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int currentBlock;
        int total = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {                
                currentBlock = board[i][j];
                if (currentBlock != 0) {
                    total += manhattanDist(currentBlock-1, i, j);
                }            
            }
        }
        return total;
    }     

    private int manhattanDist(int currentNum, int i, int j) {
        int correctCol = currentNum % N;
        int correctRow = currentNum / N;
        return Math.abs(correctRow-i) + Math.abs(correctCol-j);        
    }     

    // is this board the goal board?
    public boolean isGoal() {
        return this.hamming() == 0;
    }                

    // a board obtained by exchanging two adjacent blocks in the same row
    public Board twin() {
        Board twin = this.copy();
        if (twin.board[0][0] == 0 || twin.board[0][1] == 0) {
            twin.swap(1, 0, 1, 1);
        }
        else {
            twin.swap(0, 0, 0, 1);
        }        
        return twin;
    }

    private Board copy() {
        int[][] block = new int[N][N];
        Board copy = new Board(block);
        copy.N = this.N;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {                
                copy.board[i][j] = this.board[i][j];
                copy.emptyBlockRow = this.emptyBlockRow;
                copy.emptyBlockCol = this.emptyBlockCol;
            }
        }
        return copy;
    }

    private void swap(int x1, int y1, int x2, int y2) {
        int tmp = this.board[x1][y1];
        this.board[x1][y1] = this.board[x2][y2];
        this.board[x2][y2] = tmp;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) return false;
        if (y == this) return true;
        if (y.getClass() != this.getClass()) return false;

        Board that = (Board) y;
        if (that.N != this.N) return false;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {                
                if (that.board[i][j] != this.board[i][j]) return false;
            }
        }
        return true;
    }        

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> queue = new Queue<Board>();
        Board neighbor;
        if (emptyBlockRow > 0) {
            neighbor = this.copy();
            neighbor.swap(emptyBlockRow-1, emptyBlockCol, 
                          emptyBlockRow, emptyBlockCol);
            neighbor.emptyBlockRow = emptyBlockRow-1;
            queue.enqueue(neighbor);
        }
        if (emptyBlockRow  <  N-1) {
            neighbor = this.copy();
            neighbor.swap(emptyBlockRow+1, emptyBlockCol, 
                          emptyBlockRow, emptyBlockCol);
            neighbor.emptyBlockRow = emptyBlockRow+1;
            queue.enqueue(neighbor);
        }
        if (emptyBlockCol > 0) {
            neighbor = this.copy();
            neighbor.swap(emptyBlockRow, emptyBlockCol-1, 
                          emptyBlockRow, emptyBlockCol);
            neighbor.emptyBlockCol = emptyBlockCol-1;
            queue.enqueue(neighbor);
        }
        if (emptyBlockCol  <  N-1) {
            neighbor = this.copy();
            neighbor.swap(emptyBlockRow, emptyBlockCol+1, 
                          emptyBlockRow, emptyBlockCol);
            neighbor.emptyBlockCol = emptyBlockCol+1;
            queue.enqueue(neighbor);
        }        
        return queue;
    }     


    // string representation of the board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i  <  N; i++) {
            for (int j = 0; j  <  N; j++) {
                s.append(String.format("%2d ", board[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }       

    // unit testing
    public static void main(String[] args) {
        int N = 3;
        int[][] blocks = new int[N][N];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                blocks[i][j] = i*N+j+1;
            }
        }
        blocks[2][2] = 0;
        Board board = new Board(blocks);

        StdOut.println("======== TEST 1 =======");
        StdOut.println("Test Result: ");
        StdOut.println(board);
        StdOut.println("Should be :\n3\n 1  2  3\n 4  5  6\n 7  8  0\n");

        StdOut.println("======== TEST 2 =======");
        StdOut.println("Test Result: ");
        StdOut.print("hamming: " + board.hamming());
        StdOut.print("manhattan: " + board.manhattan());
        StdOut.println();
        StdOut.println("Should be : hamming: 0 manhattan: 0");


    }
}