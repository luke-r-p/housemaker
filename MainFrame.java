import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JSplitPane;

/**
 * The frame in which the program is shown
 */
public class MainFrame extends JFrame {
  private Board board;
  private int width;
  private int height;
  private int scale;

  private Display display; // the interactive display showing the board

  /**
   * Constructor for the MainFrame
   */
  public MainFrame() {
    setSize(500, 400);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setTitle("House Maker");
    setExtendedState(MAXIMIZED_BOTH);

    width = 100;
    height = 80;
    scale = 8;
    board = new Board(width, height);

    // splitpane to hold display and menu bar
    JSplitPane splitPane = new JSplitPane();
    this.add(splitPane);
    splitPane.setDividerLocation(32);

    // menu bar to hold buttons
    JMenuBar menuBar = new JMenuBar();
    splitPane.setLeftComponent(menuBar);

    // display
    display = new Display(width, height, scale, board.getColorArray());
    splitPane.setRightComponent(display);

    setVisible(true);
  }
}
