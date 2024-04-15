import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * Class to display the board and facilitate user interaction with the board
 */
public class Display extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {
  public static final int VIEW_MODE = 0;
  public static final int LAYOUT_MODE = 1;
  public static final int ADD_MODE = 2;
  public static final int MOVE_MODE = 3;
  public static final int ROTATE_MODE = 4;
  public static final int DELETE_MODE = 5;
  private int mode = 0; // the mode that the display is in

  private Board board;
  private int width;
  private int height;
  private int scale;
  private int[] corner = {0, 0}; // top left corner of the board in the display
  private Color[][] colorArray; // array of colors to display

  private int itemCount; // number of items on the board
  private BufferedImage[] itemImages; // list of images for the items on the board
  private int[][] itemPositions; // list of positions for the items on the board
  private int[][] itemSizes; // list of sizes for the items on the board

  private int[] mouseStart = new int[2]; // previous position of mouse for moving

  private int tileType = Tile.EMPTY;
  private Item item;

  /**
   * Constructor for the display
   * @param width width of the board in tiles
   * @param height height of the board in tiles
   * @param scale initial scale of the board (e.g. 8 means 8 pixels per tile)
   * @param colorArray initial array of colors to display
   */
  public Display(Board board, int scale) {
    this.board = board;
    this.width = board.getWidth();
    this.height = board.getHeight();
    this.scale = scale;
    this.colorArray = board.getColorArray();

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
   * Updates the information on all items from the board
   */
  public void updateItems() {
    itemCount = board.getItemCount();
    itemImages = board.getItemImages();
    itemPositions = board.getItemPositions();
    itemSizes = board.getItemSizes();
    repaint();
  }

  /**
   * Changes the interaction mode to the view mode
   */
  public void viewMode() {
    mode = VIEW_MODE;
    this.getParent().getParent().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
  }

  /**
   * Changes the interaction mode to layout mode with the specified tile type
   * @param tileType Tile.***** integer to represent the type of tile to paint
   */
  public void layoutMode(int tileType) {
    mode = LAYOUT_MODE;
    this.tileType = tileType;
  }

  /**
   * Changes the interaction mode to add mode with the specified item
   * @param item the item to add to the board
   */
  public void addMode(Item item) {
    this.item = item;
    mode = ADD_MODE;
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

    // paints the items onto the panel
    for (int i = 0; i < itemCount; i++) {
      Image scaledImage = itemImages[i].getScaledInstance(itemSizes[i][0] * scale, itemSizes[i][1] * scale, BufferedImage.SCALE_FAST);
      g2.drawImage(scaledImage, corner[0] + itemPositions[i][0] * scale, corner[1] + itemPositions[i][1] * scale, null);
    }
  }

  /**
   * Gets the tile index based on the location on the display specified
   * @param x the x-position on the screen
   * @param y the y-position on the screen
   * @return the position [xPos, yPos] of the tile in the array
   */
  public int[] getTilePosition(int x, int y) {
    int[] position = {0, 0};

    position[0] = (x - corner[0]) / scale;
    position[1] = (y - corner[1]) / scale;

    if (position[0] < 0 || position[0] >= width || position[1] < 0 || position[1] >= height) {
      position[0] = -1;
      position[1] = -1;
    }

    return position;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
  }

  @Override
  public void mousePressed(MouseEvent e) {
    int[] position;

    switch (mode) {
      case VIEW_MODE:
        mouseStart[0] = e.getX();
        mouseStart[1] = e.getY();
        break;
      case LAYOUT_MODE:
        position = getTilePosition(e.getX(), e.getY());
        if (position[0] > -1) {
          updateColors(board.updateTile(position[0], position[1], tileType));
        }
        break;
      case ADD_MODE:
        position = getTilePosition(e.getX(), e.getY());
        if (position[0] > -1) {
          item.setPosition(position[0], position[1]);
          board.addItem(item);
          updateItems();
          this.viewMode();
        }
    }
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
    switch (mode) {
      case VIEW_MODE:
        // moves the top-left corner of the displayed board to follow the dragged mouse

        int xDifference = e.getX() - mouseStart[0];
        int yDifference = e.getY() - mouseStart[1];

        corner[0] = corner[0] + xDifference;
        corner[1] = corner[1] + yDifference;

        mouseStart[0] = e.getX();
        mouseStart[1] = e.getY();

        repaint();
        break;
      case LAYOUT_MODE:
        int[] position = getTilePosition(e.getX(), e.getY());
        if (position[0] > -1) {
          updateColors(board.updateTile(position[0], position[1], tileType));
        }
        break;
    }
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
