public class BoardChecker {

    public static void main(String[] args) {

        // for each command-line argument
        for (String filename : args) {

            // read in the board specified in the filename
            In in = new In(filename);
            int N = in.readInt();
            int[][] tiles = new int[N][N];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    tiles[i][j] = in.readInt();
                }
            }

            // solve the slider puzzle
            Board board = new Board(tiles);
            StdOut.println(board);
            StdOut.println("hamming: " + board.hamming());
            StdOut.println("manhattan: " + board.manhattan());
        }
    }
}