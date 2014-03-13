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
    root = insert(root, p, new RectHV(0.0, 1.0, 0.0, 1.0), true);
  } 

  // comX means compare to x-axis, 
  // We compare x-axis for odd level and y-axis for even level
  private Node insert(Node parent, Point2D p, RectHV parentsRect, boolean comX) {
    if (parent == null){
      size++;          
      return new Node(p, parentsRect);  
    }    
    if (comX) {
      if ( p.x() < parent.p.x()) {
        parent.lb = insert(parent.lb, p, new RectHV(0.0, 1.0, 0.0, 1.0), !comX);
      }
      else {
        parent.rt = insert(parent.rt, p, new RectHV(0.0, 1.0, 0.0, 1.0), !comX);
      }
    }
    else{
      if ( p.y() < parent.p.y() ) {
        parent.lb = insert(parent.lb, p, new RectHV(0.0, 1.0, 0.0, 1.0), !comX);
      }
      else {
        parent.rt = insert(parent.rt, p, new RectHV(0.0, 1.0, 0.0, 1.0), !comX);
      }
    }        
    return parent;
  }


    // does the tree contain the point p?
  public boolean contains(Point2D p) {
    return contains(root , p, true);
  }

  private boolean contains(Node parent, Point2D p, boolean comX) {
    if (parent == null) return false;
    else if (parent.p == p) return true;

    if (comX) {
      if ( p.x() < parent.p.x()) {
        return contains(parent.lb, p, !comX);
      }
      else {
        return contains(parent.rt, p, !comX);
      }
    }
    else{
      if ( p.y() < parent.p.y()) {
        return contains(parent.lb, p, !comX);
      }
      else {
        return contains(parent.rt, p, !comX);
      }
    }  
  }

  // draw all of the points to standard draw
  public void draw() {
    draw(root);
  }

  //recursive draw the subtree's point 
  private void draw(Node node) {
    node.p.draw();
    if(node.lb != null){
      draw(node.lb);
    }
    if(node.rt != null){
      draw(node.rt);
    }
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