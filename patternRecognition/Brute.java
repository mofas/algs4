import java.util.Arrays;
public class Brute {

  public static void main(String[] args) {

    String filename = args[0];
    In in = new In(filename);
    int N = in.readInt();
    Point[] points = new Point[N];

    for (int i = 0; i < N; i++) {
      int x = in.readInt();
      int y = in.readInt();      
      points[i] = new Point(x, y);
      points[i].draw();
    }

    Arrays.sort(points);

    for (int i = 0; i < N; i++) {
      for (int j = i+1; j < N; j++) {
        for (int k = j+1; k < N; k++) {
          for (int l = k+1; l < N; l++) {
            Point p = points[i];
            Point q = points[j];
            Point r = points[k];
            Point s = points[l];
            double slopePQ = p.slopeTo(q);
            double slopePR = p.slopeTo(r);
            double slopePS = p.slopeTo(s);
            if (slopePQ == slopePR && slopePQ == slopePS) {
              StdOut.printf("%s -> %s -> %s -> %s\n", p, q, r, s);
              p.drawTo(s);
            }
          }
        }
      }
    }

  }

}