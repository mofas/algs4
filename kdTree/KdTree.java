public class KdTree {

  private int size; 
  private Node root;

  private static class Node {
    private Point2D p;      // the point
    private RectHV rect;    // the axis-aligned rectangle corresponding to this node
    private Node lb;        // the left/bottom subtree
    private Node rt;        // the right/top subtree
  }

  public KdTree() {
    size = 0;
    root = null;
  }

  // is the tree empty?
  public boolean isEmpty() {
    return size == 0;
  }

  // number of points in the tree
  public int size() {
    return size;
  } 

  // add the point p to the tree (if it is not already in the tree)  
  public void insert(Point2D p) {
    
  } 

    // does the tree contain the point p?
  public boolean contains(Point2D p) {
    return false;
  }

  // draw all of the points to standard draw
  public void draw() {
    
  }

  //recursive draw the subtree's point 
  private void draw(Point2D p) {
    p.draw();
  }

  // all points in the tree that are inside the rectangle
  public Iterable<Point2D> range(RectHV rect) {
    Queue<Point2D> q = new Queue<Point2D>();    

    return q;
  } 

  // a nearest neighbor in the tree to p; null if tree is empty
  public Point2D nearest(Point2D p) {
    return null;
  }

}