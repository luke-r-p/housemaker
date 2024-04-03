import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * The frame in which the program is shown
 */
public class MainFrame extends JFrame implements ActionListener {
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
    JPanel menu = new JPanel();
    menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
    splitPane.setLeftComponent(menu);

    // creates the view mode button
    JButton view = new JButton();
    view.setSize(splitPane.getDividerLocation(), splitPane.getDividerLocation());
    view.setToolTipText("View Mode");
    view.setBorder(BorderFactory.createEmptyBorder());
    view.setActionCommand("VIEW");
    view.addActionListener(this);
    menu.add(view);

    // creates the layout mode button
    JButton layout = new JButton();
    layout.setSize(splitPane.getDividerLocation(), splitPane.getDividerLocation());
    layout.setToolTipText("Layout Mode");
    layout.setBorder(BorderFactory.createEmptyBorder());
    layout.setActionCommand("LAYOUT");
    layout.addActionListener(this);
    menu.add(layout);

    // makes the scaled icons for the buttons
    try {
      // view button
      Image viewIcon = ImageIO.read(new File("icons/view.png"));
      Image scaledViewIcon = viewIcon.getScaledInstance(splitPane.getDividerLocation(), splitPane.getDividerLocation(), Image.SCALE_SMOOTH);
      view.setIcon(new ImageIcon(scaledViewIcon));

      // layout button
      Image layoutIcon = ImageIO.read(new File("icons/layout.png"));
      Image scaledLayoutIcon = layoutIcon.getScaledInstance(splitPane.getDividerLocation(), splitPane.getDividerLocation(), Image.SCALE_SMOOTH);
      layout.setIcon(new ImageIcon(scaledLayoutIcon));
    } catch (IOException e) {}

    // display
    display = new Display(board, scale);
    splitPane.setRightComponent(display);

    setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String command = e.getActionCommand();

    // does action based on button command
    if (command.equals("VIEW")) {
      display.viewMode();
    }
    if (command.equals("LAYOUT")) {
      display.layoutMode(Tile.WALL);
    }
  }
}
