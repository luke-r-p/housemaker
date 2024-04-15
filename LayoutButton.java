import javax.swing.JMenuItem;
import javax.swing.BorderFactory;
import javax.swing.JMenu;

/**
 * Class for the button that users press to get a menu of layout options (Empty, Wall, etc) and turn on layout mode
 */
public class LayoutButton extends JMenu {
  /**
   * Constructor for the layout button, sets up all listeners ("LISTEN [number of tiletype]")
   * @param mf the MainFrame the button is being made in, needed to add listeners
   */
  public LayoutButton(MainFrame mf) {
    setToolTipText("Layout Mode");
    setBorder(BorderFactory.createEmptyBorder());

    int numberOfItems = 4;

    JMenuItem[] items = new JMenuItem[numberOfItems];

    items[0] = new JMenuItem("Empty");
    items[1] = new JMenuItem("Wall");
    items[2] = new JMenuItem("Door");
    items[3] = new JMenuItem("Window");

    for (int i = 0; i < numberOfItems; i++) {
      items[i].setActionCommand("LAYOUT " + i);
      items[i].addActionListener(mf);
      this.add(items[i]);
    }
  }
}
