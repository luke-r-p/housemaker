import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JPanel;

/**
 * Class to display the board and facilitate user interaction with the board
 */
public class Display extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {
  public static final int VIEW_MODE = 0;
  private int mode = 0; // the mode that the display is in

  private int width;
  private int height;
  private int scale;
  private int[] corner = {0, 0}; // top left corner of the board in the display
  private Color[][] colorArray; // array of colors to display

  private int[] mouseStart = new int[2]; // previous position of mouse for moving

  /**
   * Constructor for the display
   * @param width width of the board in tiles
   * @param height height of the board in tiles
   * @param scale initial scale of the board (e.g. 8 means 8 pixels per tile)
   * @param colorArray initial array of colors to display
   */
  public Display(int width, int height, int scale, Color[][] colorArray) {
    this.width = width;
    this.height = height;
    this.scale = scale;
    this.colorArray = colorArray;

    this.addMouseListener(this);
    this.addMouseMotionListener(this);
    this.addMouseWheelListener(this);
  }

  /**
   * Updates the colors to display to represent the board
   * @param colorArray the new array of colors
   */
  public void updateColors(Color[][] colorArray) {
    this.colorArray = colorArray;
    repaint();
  }

  /**
   * Changes the interaction mode to the given mode
   * @param mode mode to change to (use Display.****_MODE)
   */
  public void setMode(int mode) {
    this.mode = mode;
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);

    Graphics2D g2 = (Graphics2D)g;

    // paints the tiles onto the panel
    for (int w = 0; w < width; w++) {
      for (int h = 0; h < height; h++) {
        g2.setColor(Color.gray);
        g2.drawRect(corner[0] + w * scale, corner[1] + h * scale, scale, scale);
        g2.setColor(colorArray[w][h]);
        g2.fillRect(corner[0] + w * scale, corner[1] + h * scale, scale, scale);
      }
    }
  }

  @Override
  public void mouseClicked(MouseEvent e) {
  }

  @Override
  public void mousePressed(MouseEvent e) {
    mouseStart[0] = e.getX();
    mouseStart[1] = e.getY();
  }

  @Override
  public void mouseReleased(MouseEvent e) {
  }

  @Override
  public void mouseEntered(MouseEvent e) {
  }

  @Override
  public void mouseExited(MouseEvent e) {
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    // moves the top-left corner of the displayed board to follow the dragged mouse

    int xDifference = e.getX() - mouseStart[0];
    int yDifference = e.getY() - mouseStart[1];

    corner[0] = corner[0] + xDifference;
    corner[1] = corner[1] + yDifference;

    mouseStart[0] = e.getX();
    mouseStart[1] = e.getY();

    repaint();
  }

  @Override
  public void mouseMoved(MouseEvent e) {
  }

  @Override
  public void mouseWheelMoved(MouseWheelEvent e) {
    // zooms in/out on the point that the mouse is over

    int change = e.getWheelRotation();

    int newScale;
    // zooming in
    if (change < 0) {
      newScale = (int)(scale * 1.2) + 1;
    }
    // zooming out
    else {
      newScale = (int)(scale / 1.2);
    }

    // not allowed to have a scale less than 4
    if (newScale >= 4) {
      double percentChange = (double)newScale / scale;

      double cornerXDiff = (e.getX() - corner[0]) * percentChange;
      double cornerYDiff = (e.getY() - corner[1]) * percentChange;

      corner[0] = (int)(e.getX() - cornerXDiff);
      corner[1] = (int)(e.getY() - cornerYDiff);

      scale = newScale;
    }

    repaint();
  }
}
