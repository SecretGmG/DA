import com.sun.source.tree.Tree;

import java.awt.Component;
import java.awt.*;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Collections;

/**
 * Implements the operations on a KD-Tree and
 * functions to visualize the KD-Tree
 */
public class KDTreeVisualization extends Component {

    LinkedList<Point> points;  // List of points
    TreeNode kdRoot;           // The kd-Tree
    Image img;                 // Image to display points
    int w, h;                  // Width an height of image
    Graphics2D gi;             // Graphics object to draw points
    int n;                     // Number of points

    /**
     * Initializes the points.
     *
     * @param w width of window.
     * @param h height of window.
     * @param n number of points.
     */
    public KDTreeVisualization(int w, int h, int n) {

        this.w = w;
        this.h = h;
        this.n = n;

        this.kdRoot = null;
    }

    /**
     * Initializes the image
     */
    public void init() {
        img = createImage(w, h);
        gi = (Graphics2D) img.getGraphics();
        gi.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    /**
     * create and show a set of randomly generated points
     */
    public void initPoints() {
        points = this.createPoints(n);
        this.visualizePoints();
    }

    /**
     * Initialize points by distributing them randomly.
     */
    public LinkedList<Point> createPoints(int size) {
        LinkedList<Point> p = new LinkedList<Point>();
        for (int i = 0; i < size; i++) {
            p.add(new Point(Math.round((float) Math.random() * w) - 1, Math.round((float) Math.random() * h) - 1));
        }
        return p;
    }

    /**
     * Searches the nearest neighbor for x points
     *
     * @param x    number of points to search
     * @param mode data structure to use (0: list, 1: kd-tree)
     */
    public void searchNN(int x, int mode) {

        LinkedList<Point> searchPoints = createPoints(x);
        Timer t = new Timer();
        t.reset();
        Iterator<Point> it = searchPoints.iterator();
        while (it.hasNext()) {
            Point p = it.next();
            Point q;

            switch (mode) {
                case 0:
                    q = this.listSearchNN(p);
                    break;
                case 1:
                    q = this.treeSearchNN(p);
            }
        }
        System.out.printf("Mode: %s, Number of points searched: %d, Number of points %d, Time: %dms\n",mode == 0 ? "list" : "tree", x, points.size(), t.timeElapsed());
    }

    public void testSearch(int x) {
        LinkedList<Point> searchPoints = createPoints(x);
        Iterator<Point> it = searchPoints.iterator();
        while (it.hasNext()) {
            Point p = it.next();
            Point q1 = this.listSearchNN(p);
            Point q2 = this.treeSearchNN(p);
            if (p.distanceSq(q1) != p.distanceSq(q2)) {
                System.out.printf("\nSomething went wrong linear search found %s, while tree search found %s \nthe goal was %s\n", q1.toString(), q2.toString(), p.toString());
                return;
            }
        }
        System.out.printf("\nLinear search and tree search always came up with a point with the same distance!");
    }

    public void time(int x, int[] sizes, int mode){
        int prevN = n;
        for(int size : sizes){
            n = size;
            initPoints();
            createKDTree();
            searchNN(x, mode);
        }
        n = prevN;
        initPoints();
    }

    /**
     * starts creation of the kd-Tree
     */
    public void createKDTree() {
        this.kdRoot = createKDTree(0, (LinkedList<Point>) points.clone());
    }

    public TreeNode createKDTree(int axis, LinkedList<Point> points) {

        if (points.size() == 0) {
            return null;
        }

        points.sort(new PointComparator(axis % 2));

        int m = points.size() / 2;

        LinkedList<Point> L = new LinkedList<Point>();
        LinkedList<Point> R;
        //move all the point to the left of l to l
        for (int i = 0; i < m; i++) {
            L.push(points.pollFirst());
        }
        TreeNode node = new TreeNode(points.pollFirst());
        R = points; //whatever that's left goes in R

        node.left = createKDTree(axis + 1, L);
        node.right = createKDTree(axis + 1, R);
        return node;
    }

    /**
     * searches the nearest neighbor of a point in a
     * list of points
     *
     * @param p the point for which to search
     * @return the nearest neighbor of p
     */
    public Point listSearchNN(Point p) {

        double minDistSq = Double.POSITIVE_INFINITY;
        Point minP = null;
        for (Point q : points) {
            double distSquared = p.distanceSq(q);
            if (distSquared < minDistSq) {
                minDistSq = distSquared;
                minP = q;
            }
        }

        return minP;
    }

    /**
     * searches the nearest neighbor of a point in a kd-tree
     *
     * @param p the point for which to search
     * @return the nearest neighbor of p
     */
    public Point treeSearchNN(Point p) {
        return treeSearchNN(p, kdRoot, 0, Double.POSITIVE_INFINITY);
    }

    /**
     * searches the nearest neighbor of a point "below" the given node
     * ignores all points farther with dist Squared > boundSq
     */
    private Point treeSearchNN(Point p, TreeNode node, int axis, double boundSq) {
        Comparator<Point> comp = new PointComparator(axis % 2);
        //get a subTree on the same side (along axis) of node as p and one on the other side of p
        TreeNode closerSubTree;
        TreeNode otherSubTree;

        if (comp.compare(p, node.position) < 0) {
            closerSubTree = node.left;
            otherSubTree = node.right;
        } else {
            closerSubTree = node.right;
            otherSubTree = node.left;
        }

        //set node.position as the currently closest point
        Point closest = node.position;

        //calculate the current closest dist Squared
        double closestDistSq = p.distanceSq(closest);

        //update the bound if possible
        if (closestDistSq < boundSq) {
            boundSq = closestDistSq;
        }

        //search the closerSubTree
        if(closerSubTree != null){
            //get the closest point in this subtree (recursively)
            Point closerSubTreeClosest = treeSearchNN(p, closerSubTree, axis + 1, boundSq);
            double closerSubTreeDistSq = p.distanceSq(closerSubTreeClosest);

            //update the closest point
            if (closerSubTreeDistSq < closestDistSq) {
                closest = closerSubTreeClosest;
                closestDistSq = closerSubTreeDistSq;
            }
            //update the bound if possible
            if (closerSubTreeDistSq < boundSq){
                boundSq = closestDistSq;
            }
        }

        //search the otherSubTree but only if there might be any node with distSq < boundSq
        //if the distance squared of node along the axis is > than boundSq then every node
        //in otherSubTree is farther away than boundSq therefor no node in otherSubTree can be closer than boundSq^0.5
        //this should give a huge performance boost because now most of the points can be ignored
        if (otherSubTree != null && getAxisDistSq(node.position, p, axis) < boundSq){
            Point otherSubTreeClosest = treeSearchNN(p, otherSubTree, axis + 1, boundSq);
            double otherSubTreeDistSq = p.distanceSq(otherSubTreeClosest);

            //update closest point
            if (otherSubTreeDistSq < closestDistSq) {
                closest = otherSubTreeClosest;
            }
        }
        return closest;
    }

    /**
     * gets the distance squared along a given axis
     * @param p
     * @param q
     * @param axis tha axis on which the distance should be measured
     * @return (p.axis - q.axis)^2
     */
    private static double getAxisDistSq(Point p, Point q, int axis){
        double distAxis = getAxis(p, axis) - getAxis(q, axis);
        return distAxis*distAxis;
    }

    /**
     *  gets the component of the point p along an axis
     */
    private static int getAxis(Point p, int axis) {
        switch (axis % 2) {
            case 0 -> {
                return p.x;
            }
            case 1 -> {
                return p.y;
            }
            default -> {
                return 0; //Unreachable}
            }
        }
    }

    /**
     * Visualizes the points in the list
     */
    public void visualizePoints() {
        gi.clearRect(0, 0, w, h);

        Iterator<Point> it = points.iterator();
        while (it.hasNext()) {
            Point p = it.next();
            gi.fillOval(p.x - 2, p.y - 2, 5, 5);
        }
        this.repaint();
    }

    /**
     * Visualizes the order of the points in the list
     */
    public void visualizeList() {
        gi.clearRect(0, 0, w, h);

        Point old = new Point(0, 0);
        Iterator<Point> it = points.iterator();
        if (it.hasNext()) {
            old = it.next();
            gi.setColor(Color.RED);
            gi.fillOval(old.x - 2, old.y - 2, 5, 5);
        }
        while (it.hasNext()) {
            Point p = it.next();
            gi.setColor(Color.BLACK);
            gi.fillOval(p.x - 2, p.y - 2, 5, 5);
            gi.setColor(Color.BLUE);
            gi.drawLine(old.x, old.y, p.x, p.y);
            old = p;
        }
        this.repaint();
    }

    /**
     * starter for the kd-tree visualization
     */
    public void visualizeTree() {
        gi.clearRect(0, 0, w, h);
        visualize(this.kdRoot, 0, 0, w, 0, h);
        this.repaint();
    }

    /**
     * Visualizes the kd-tree
     *
     * @param n      TreeNode
     * @param depth  depth in the tree
     * @param left   left border of the sub-image
     * @param right  right border of the sub-image
     * @param top    top border of the sub-image
     * @param bottom bottom border of the sub-image
     */
    private void visualize(TreeNode n, int depth, int left, int right, int top, int bottom) {
        if (n != null) {
            int axis = depth % 2;
            if (axis == 0) {
                gi.fillOval(n.position.x - 2, n.position.y - 2, 5, 5);
                gi.drawLine(n.position.x, top, n.position.x, bottom);
                visualize(n.left, depth + 1, left, n.position.x, top, bottom);
                visualize(n.right, depth + 1, n.position.x, right, top, bottom);
            } else {
                gi.fillOval(n.position.x - 2, n.position.y - 2, 5, 5);
                gi.drawLine(left, n.position.y, right, n.position.y);
                visualize(n.left, depth + 1, left, right, top, n.position.y);
                visualize(n.right, depth + 1, left, right, n.position.y, bottom);
            }
        }
    }


    /**
     * Paint the image
     */
    public void paint(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }

    public Dimension getPreferredSize() {
        return new Dimension(w, h);
    }

    /**
     * Node in the kd-Tree
     */
    private class TreeNode {
        private TreeNode left, right;    // Pointers to left and right child
        private Point position;          // Position of the Point

        TreeNode(Point point) {
            this.position = point;
        }
    }
}
