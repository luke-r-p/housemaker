import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

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
    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    this.add(splitPane);
    splitPane.setDividerLocation((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.04)); // sets to proportion of screen width
    splitPane.setEnabled(false);

    // menu bar to hold buttons
    JMenuBar menuBar = new JMenuBar();
    splitPane.setLeftComponent(menuBar);

    // creates the view mode button
    JButton view = new JButton();
    view.setSize(splitPane.getDividerLocation(), splitPane.getDividerLocation());
    view.setToolTipText("View Mode");
    view.setBorder(BorderFactory.createEmptyBorder());
    menuBar.add(view);

    // makes the scaled icons for the buttons
    try {
      Image viewIcon = ImageIO.read(new File("icons/view.png"));
      Image scaledViewIcon = viewIcon.getScaledInstance(splitPane.getDividerLocation(), splitPane.getDividerLocation(), Image.SCALE_SMOOTH);
      view.setIcon(new ImageIcon(scaledViewIcon));
    } catch (IOException e) {}

    // display
    display = new Display(width, height, scale, board.getColorArray());
    splitPane.setRightComponent(display);

    setVisible(true);
  }
}
