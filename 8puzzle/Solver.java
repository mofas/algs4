public class Solver {


  
    private MinPQ<SearchNode> originalPQ;
    private MinPQ<SearchNode> twinPQ; // To test solvable
    private boolean solvable;
    private SearchNode finalSearchNode;

    private class SearchNode implements Comparable<SearchNode> {
      private Board board;
      private SearchNode prevSearchNode;
      private int moves;
      private int priority;

      public SearchNode(Board board, int moves, SearchNode prevSearchNode) {
        this.board = board;
        this.moves = moves;
        this.prevSearchNode = prevSearchNode;
        this.priority = moves + this.board.manhattan();
      }

      public int compareTo(SearchNode that) {
        if (this.priority > that.priority) {
          return 1;
        }
        if (this.priority < that.priority) {
          return -1;
        }        
        return 0;
      }
    }


    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
      originalPQ = new MinPQ<SearchNode>();
      twinPQ = new MinPQ<SearchNode>();
      SearchNode currentSearchNode;
      SearchNode twinSearchNode;

      //insert initial search node into queue
      originalPQ.insert(new SearchNode(initial, 0, null));
      twinPQ.insert(new SearchNode(initial.twin(), 0, null));
      

      while (true) {
        //delete minimun priority node
        currentSearchNode = originalPQ.delMin();
        twinSearchNode = twinPQ.delMin();

        if (currentSearchNode.board.isGoal()) {
          finalSearchNode = currentSearchNode;
          solvable = true;
          break;
        }
        if (twinSearchNode.board.isGoal()) {          
          solvable = false;
          break;
        }
        

        //put neigh board into queue
        for (Board neighBoard : currentSearchNode.board.neighbors()) {
          //first node prev is null
          if (currentSearchNode.prevSearchNode == null || !neighBoard.equals(currentSearchNode.prevSearchNode.board)) {
            SearchNode insertSearchNode = new SearchNode(neighBoard, 
                                            currentSearchNode.moves+1, currentSearchNode);
            originalPQ.insert(insertSearchNode);
          }          
        }
        for (Board neighBoard : twinSearchNode.board.neighbors()) {
          //first node prev is null
          if (twinSearchNode.prevSearchNode == null || !neighBoard.equals(twinSearchNode.prevSearchNode.board)) {
            SearchNode insertSearchNode = new SearchNode(neighBoard, 
                                            twinSearchNode.moves+1, twinSearchNode);
            twinPQ.insert(insertSearchNode);
          }          
        }

      }      

    }

    // is the initial board solvable?
    public boolean isSolvable() {
      return solvable;
    }            

    // min number of moves to solve initial board; -1 if no solution
    public int moves() {
      if (isSolvable()) {
        return finalSearchNode.moves;
      }
      return -1;
    }                     

    // sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution() {      
      if (isSolvable()) {
        Stack<Board> stack = new Stack<Board>();
        SearchNode iterNode = finalSearchNode;
        while (iterNode != null) {          
          stack.push(iterNode.board);
          iterNode = iterNode.prevSearchNode;
        }
        return stack;
      }
      return null;
    }      

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
      // create initial board from file
      In in = new In(args[0]);
      int N = in.readInt();
      int[][] blocks = new int[N][N];
      for (int i = 0; i < N; i++)
          for (int j = 0; j < N; j++)
              blocks[i][j] = in.readInt();

      Board initial = new Board(blocks);

      // solve the puzzle
      Solver solver = new Solver(initial);

      // print solution to standard output
      if (!solver.isSolvable())
          StdOut.println("No solution possible");
      else {
          StdOut.println("Minimum number of moves = " + solver.moves());
          for (Board board : solver.solution())
              StdOut.println(board);
      }
    } 

}