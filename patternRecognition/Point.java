/*************************************************************************
* Name:
* Email:
*
* Compilation:  javac Point.java
* Execution:
* Dependencies: StdDraw.java
*
* Description: An immutable data type for points in the plane.
*
*************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new BySlope();


    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
        // x == x , y == y
        if (this.compareTo(that) == 0) {
            return Double.NEGATIVE_INFINITY;
        }
        // this.x == that.x , this.y != that.y
        if (this.x == that.x) {
            return Double.POSITIVE_INFINITY;
        }
        // this.x != that.x , this.y == that.y
        if (this.y == that.y) {
            return 0;
        }

        return (double) (this.y - that.y) / (this.x - that.x);
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        if (this.y > that.y) {
            return 1;
        }
        else if ((this.y < that.y)) {
            return -1;
        }
        else {
            if (this.x > that.x) {
                return 1;
            }
            else if (this.x < that.x) {
                return -1;
            }
            return 0;
        }        
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    private class BySlope implements Comparator<Point> {
        public int compare(Point p1, Point p2) { 
            double s1 = Point.this.slopeTo(p1);
            double s2 = Point.this.slopeTo(p2);
            if (s1 > s2) return 1;
            if (s1 < s2) return -1;
            return 0;
        }
    }


    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
        Point origin = new Point(0, 0);
        Point p1p1 = new Point(1, 1);
        Point p1n1 = new Point(1, -1);
        Point n1p1 = new Point(-1, 1);        
        Point n1n1 = new Point(-1, -1);
        Point p20 = new Point(2, 0);
        Point p02 = new Point(0, 2);

        StdOut.println("======== TEST slopeTo =======");
        StdOut.print("Test Result: ");

        StdOut.print(origin.slopeTo(p1p1));
        StdOut.print(",");
        StdOut.print(origin.slopeTo(p1n1));
        StdOut.print(",");
        StdOut.print(origin.slopeTo(n1p1));
        StdOut.print(",");
        StdOut.print(origin.slopeTo(n1n1));
        StdOut.print(",");
        StdOut.print(origin.slopeTo(p20));
        StdOut.print(",");
        StdOut.print(origin.slopeTo(p02));        

        StdOut.print(",");
        StdOut.print(p1p1.slopeTo(p20)); 
        
        StdOut.println();
        StdOut.println("Should be  : 1.0,-1.0,-1.0,1.0,0.0,Infinity,-1.0");

        StdOut.println("======== TEST CompareTo =======");
        StdOut.print("Test Result: ");
        
        StdOut.print(origin.compareTo(p1p1));
        StdOut.print(",");
        StdOut.print(origin.compareTo(n1p1));
        StdOut.print(",");
        StdOut.print(origin.compareTo(p1n1));
        StdOut.print(",");
        StdOut.print(origin.compareTo(n1n1));
        
        StdOut.println();
        StdOut.println("Should be  : -1,-1,1,1");

    }

}