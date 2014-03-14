public class KdTree {

  private int size; 
  private Node root;

  private static class Node {
    private Point2D p;      // the point
    private RectHV rect;    // the axis-aligned rectangle corresponding to this node
    private Node lb;        // the left/bottom subtree
    private Node rt;        // the right/top subtree
    public Node(Point2D p, RectHV rect) {
      this.p = p;
      this.rect = rect;
    }
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
    root = insert(root, p, 0.0, 0.0, 1.0, 1.0, true);
  } 

  // comX means compare to x-axis, 
  // We compare x-axis for odd level and y-axis for even level
  private Node insert(Node parent, Point2D p, double xmin, double ymin, double xmax, double ymax, boolean comX) {
    if (parent == null) {
      size++;          
      return new Node(p, new RectHV(xmin, ymin, xmax, ymax));  
    }
    else if (parent.p.x() == p.x() && parent.p.y() == p.y()) {
      return parent;
    }

    if (comX) {
      if (p.x() < parent.p.x()) {
        parent.lb = insert(parent.lb, p, xmin, ymin, parent.p.x() , ymax, !comX);
      }
      else {
        parent.rt = insert(parent.rt, p, parent.p.x(), ymin, xmax, ymax, !comX);
      }
    }
    else {
      if (p.y() < parent.p.y()) {
        parent.lb = insert(parent.lb, p, xmin, ymin, xmax, parent.p.y(), !comX);
      }
      else {
        parent.rt = insert(parent.rt, p, xmin, parent.p.y(), xmax, ymax, !comX);
      }
    }        
    return parent;
  }


  // does the tree contain the point p?
  public boolean contains(Point2D p) {
    return contains(root , p, true);
  }

  private boolean contains(Node parent, Point2D p, boolean comX) {
    if (parent == null) {
      return false;
    }
    else if (parent.p.x() == p.x() && parent.p.y() == p.y()) {
      return true;
    } 
    else {
      if (comX) {
        if (p.x() < parent.p.x()) {
          return contains(parent.lb, p, !comX);
        }
        else {
          return contains(parent.rt, p, !comX);
        }
      }
      else {
        if (p.y() < parent.p.y()) {
          return contains(parent.lb, p, !comX);
        }
        else {
          return contains(parent.rt, p, !comX);
        }
      }  
    }
  }

  // draw all of the points to standard draw
  public void draw() {
    draw(root, true);
  }

  //recursive draw the subtree's point 
  private void draw(Node node, boolean comX) {


    StdDraw.setPenRadius();
    if (comX) {
      StdDraw.setPenColor(StdDraw.RED);
      StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
    }
    else {
      StdDraw.setPenColor(StdDraw.BLUE);  
      StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
    }    

    StdDraw.setPenColor(StdDraw.BLACK);
    StdDraw.setPenRadius(.01);
    node.p.draw();

    if (node.lb != null) {
      draw(node.lb, !comX);
    }
    if (node.rt != null) {
      draw(node.rt, !comX);
    }
  }

  // all points in the tree that are inside the rectangle
  public Iterable<Point2D> range(RectHV rect) {
    Queue<Point2D> q = new Queue<Point2D>();    
    range(rect, root, q);
    return q;
  } 

  private void range(RectHV rect, Node parent, Queue<Point2D> q) {
    if (parent == null) return;
    if (rect.contains(parent.p)) {
      q.enqueue(parent.p);
    }

    if (rect.intersects(parent.rect)) {
      range(rect, parent.lb, q);
      range(rect, parent.rt, q);
    }
  }


  // a nearest neighbor in the tree to p; null if tree is empty
  public Point2D nearest(Point2D p) {
    if (root == null) return null;
    return nearest(root, p, root.p, true);    
  }


  private Point2D nearest(Node parent, Point2D p, Point2D nearP, boolean comX) {

    Point2D nearestP = nearP;
    if (parent == null) {
      return nearestP;
    }

    if (p.distanceSquaredTo(parent.p) < p.distanceSquaredTo(nearestP)) {
      nearestP = parent.p;
    }

    if (parent.rect.distanceSquaredTo(p) < p.distanceSquaredTo(nearestP)) {
      if (comX) {
        if (p.x() < parent.p.x()) {
          nearestP = nearest(parent.lb, p, nearestP, !comX);
          nearestP = nearest(parent.rt, p, nearestP, !comX);
        }
        else {
          nearestP = nearest(parent.rt, p, nearestP, !comX); 
          nearestP = nearest(parent.lb, p, nearestP, !comX);
        }
      }
      else {
        if (p.y() < parent.p.y()) {
          nearestP = nearest(parent.lb, p, nearestP, !comX);
          nearestP = nearest(parent.rt, p, nearestP, !comX);
        }
        else {
          nearestP = nearest(parent.rt, p, nearestP, !comX); 
          nearestP = nearest(parent.lb, p, nearestP, !comX);
        }
      }      
    }

    return nearestP;
  }

  public static void main(String[] args) {
    String filename = args[0];
    In in = new In(filename);
    KdTree kdtree = new KdTree();
    while (!in.isEmpty()) {
        double x = in.readDouble();
        double y = in.readDouble();
        Point2D p = new Point2D(x, y);
        kdtree.insert(p);        
    }

    kdtree.draw();
  }



}