import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import java.awt.Cursor;
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
    setSize((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()), (int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()));
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setTitle("House Maker");
    setExtendedState(MAXIMIZED_BOTH);

    width = 100;
    height = 80;
    scale = 10;
    board = new Board(width, height);

    // splitpane to hold display and menu bar
    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    this.add(splitPane);
    splitPane.setDividerLocation((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.04)); // sets to proportion of screen width
    splitPane.setEnabled(false);

    // menu bar to hold buttons
    JMenuBar menu = new JMenuBar();
    splitPane.setLeftComponent(menu);

    // creates the view mode button
    JButton view = new JButton();
    view.setToolTipText("View Mode");
    view.setBorder(BorderFactory.createEmptyBorder());
    view.setActionCommand("VIEW");
    view.addActionListener(this);
    menu.add(view);

    // creates the layout mode button
    LayoutButton layout = new LayoutButton(this);
    menu.add(layout);

    // creates the add mode button
    JButton add = new JButton();
    add.setToolTipText("Add Mode");
    add.setBorder(BorderFactory.createEmptyBorder());
    add.setActionCommand("ADD");
    add.addActionListener(this);
    menu.add(add);

    // creates the delete mode button
    JButton delete = new JButton();
    delete.setToolTipText("Delete Mode");
    delete.setBorder(BorderFactory.createEmptyBorder());
    delete.setActionCommand("DELETE");
    delete.addActionListener(this);
    menu.add(delete);

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

      // add button
      Image addIcon = ImageIO.read(new File("icons/add.png"));
      Image scaledAddIcon = addIcon.getScaledInstance(splitPane.getDividerLocation(), splitPane.getDividerLocation(), Image.SCALE_SMOOTH);
      add.setIcon(new ImageIcon(scaledAddIcon));

      // delete button
      Image deleteIcon = ImageIO.read(new File("icons/delete.png"));
      Image scaledDeleteIcon = deleteIcon.getScaledInstance(splitPane.getDividerLocation(), splitPane.getDividerLocation(), Image.SCALE_SMOOTH);
      delete.setIcon(new ImageIcon(scaledDeleteIcon));
    } catch (IOException e) { System.out.println(e); }

    // display
    display = new Display(board, scale);
    splitPane.setRightComponent(display);

    setVisible(true);

    menu.setLayout(new GridLayout(splitPane.getHeight() / splitPane.getDividerLocation() - 1, 1));
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String command = e.getActionCommand();
    // splits the command into parts
    String[] parts = command.split(" ");

    // does action based on button command
    if (parts[0].equals("VIEW")) {
      display.viewMode();
    }
    else if (parts[0].equals("LAYOUT")) {
      display.layoutMode(Integer.parseInt(parts[1]));
    }
    else if (parts[0].equals("ADD")) {
      // creates panel to select item
      JPanel popupPanel = new JPanel();
      popupPanel.add(new JLabel("Select item to add:"));
      DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
      for (String name : ItemGuide.itemNames) {
        model.addElement(name);
      }
      JComboBox<String> comboBox = new JComboBox<String>(model);
      popupPanel.add(comboBox);

      // gets user input from popup box
      int selected = JOptionPane.showConfirmDialog(null, popupPanel, "Item selection", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

      // activates add mode with the selected item
      if (selected == JOptionPane.OK_OPTION) {
        display.addMode(ItemGuide.makeItem(comboBox.getSelectedIndex()));
      }
    }
    else if (parts[0].equals("DELETE")) {
      display.deleteMode();
    }
  }
}
