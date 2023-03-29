import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Main program that starts the KDTree visualization
 */
public class KDTreeTester extends JFrame{
  
  int w=1000;    //width of the window
  int h=1000;    //height of the window
  int n= 2500;    //number of points
  int x=1000;      //number of points to be searched (has to be smaller than n) why??
  KDTreeVisualization vis;  //the visualization
  
  public KDTreeTester(){
    
    //  Initialize the visualization and add it to the main frame.
    vis = new KDTreeVisualization(w, h, n);
    add(vis);
    
    // Creates a menubar for a JFrame
    JMenuBar menuBar = new JMenuBar();
    
    // Add the menubar to the frame
    setJMenuBar(menuBar);
    
    // Define and add two drop down menu to the menubar
    JMenu kdMenu = new JMenu("Create");
    JMenu searchMenu = new JMenu("Search");
    JMenu visualizeMenu = new JMenu("Visualize");
    JMenuItem timeMenu = new JMenu("Time");
    menuBar.add(kdMenu);
    menuBar.add(searchMenu);
    menuBar.add(visualizeMenu);
    menuBar.add(timeMenu);
    
    // Create and add the menu items
    JMenuItem createPoints = new JMenuItem("Create Point Set");
    createPoints.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent arg0) {
            vis.initPoints();
        }
    });
    JMenuItem createTree = new JMenuItem("Create KD Tree");
    createTree.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent arg0) {
            vis.createKDTree();
        }
    });
    
    JMenuItem listSearch = new JMenuItem("Search List for NN");
    listSearch.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
          vis.searchNN(x, 0);
      }
    });
    JMenuItem treeSearch = new JMenuItem("Search Tree for NN");
    treeSearch.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
          vis.searchNN(x, 1);
      }
    });
    JMenuItem testSearch = new JMenuItem("Test Search");
    testSearch.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) { vis.testSearch(x); }
    });
    JMenuItem timeList = new JMenuItem("Time List");
    timeList.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) { vis.time(x, new int[]
              {5000,10000,15000,20000,25000,30000}, 0
      );}
    });
    JMenuItem timeTree = new JMenuItem("Time Tree");
    timeTree.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) { vis.time(x, new int[]
              {5000000,10000000,15000000,20000000,25000000,30000000}, 1
      );}
    });

    JMenuItem points = new JMenuItem("Points in the list");
    points.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
          vis.visualizePoints();
      }
    });
    JMenuItem pointList = new JMenuItem("Order of points in the list");
    pointList.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
          vis.visualizeList();
      }
    });
    JMenuItem tree = new JMenuItem("KD Tree");
    tree.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
          vis.visualizeTree();
      }
    });
    
    kdMenu.add(createPoints);
    kdMenu.add(createTree);
    searchMenu.add(listSearch);
    searchMenu.add(treeSearch);
    //new items
    searchMenu.add(testSearch);
    timeMenu.add(timeList);
    timeMenu.add(timeTree);
    //
    visualizeMenu.add(points);
    visualizeMenu.add(pointList);
    visualizeMenu.add(tree);
    //Display the window.
    pack();
    vis.init();
    vis.initPoints();
    vis.setVisible(true);
  }
  public static void main(String[] args) {
    
    KDTreeTester me=new KDTreeTester();  
    me.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    me.setVisible(true);
  }
  
}
