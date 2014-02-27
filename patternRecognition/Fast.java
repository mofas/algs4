import java.util.Arrays;
public class Fast {

  public static void main(String[] args) {
    String filename = args[0];
    In in = new In(filename);
    int N = in.readInt();
    Point[] points = new Point[N];

    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);

    for (int i = 0; i < N; i++) {
      int x = in.readInt();
      int y = in.readInt();      
      points[i] = new Point(x, y);
      points[i].draw();
    }    

    if (N < 4) return;    
    findCollinear(points , N);

  }

  private static void findCollinear(Point[] points, int N) {
    Point[] aux = Arrays.copyOf(points, points.length);
    Point base;    
    for (int i = 0; i < N; i++) {
      base = aux[i];      
      Arrays.sort(points, 0, N, base.SLOPE_ORDER);
      checkCollinear(base, points);      
    }
  }

  private static void checkCollinear(Point base, Point[] points) {

    int sequenceCount = 1;
    double prevSlope = base.slopeTo(points[0]);
    double currentSlope;
    Point p;
    boolean isDup = false;
    for (int i = 1; i < points.length; i++) {      
      p = points[i];                  

      currentSlope = base.slopeTo(p);        
      //StdOut.println(currentSlope);
      if (currentSlope == prevSlope) {        
        if (base.compareTo(p) > 0) {
          isDup = true;
        }
        sequenceCount++;
      }
      else {        
        if (!isDup && sequenceCount >= 3) {          
          showCollinearPoints(base, points, sequenceCount, i);
        }
        sequenceCount = 1;
        if (base.compareTo(p) > 0) {
          isDup = true;  
        }
        else {
          isDup = false;
        }
      }      
      prevSlope = currentSlope;      
    }

    if (!isDup && sequenceCount >= 3) {
      showCollinearPoints(base, points, sequenceCount, points.length);
    }
  }

  private static void showCollinearPoints(Point base, 
                                          Point[] points,
                                          int length,
                                          int end) {    
    StdOut.printf("%s", base);
    int from = end - length;
    Arrays.sort(points, from, end);
    for (int i = from; i < end; i++) {
        StdOut.printf(" -> %s", points[i]);
    }    
    StdOut.println();
    base.drawTo(points[end-1]);    
  }


}