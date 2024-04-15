import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

/**
 * Class for the board of tiles
 */
public class Board {
  int width;
  int height;

  Tile[][] tiles;

  LinkedList<Item> items = new LinkedList<Item>();

  /**
   * Constructor for the board
   * @param width number of tiles for width
   * @param height number of tiles for height
   */
  public Board(int width, int height) {
    this.width = width;
    this.height = height;

    this.tiles = new Tile[width][height];

    for (int w = 0; w < width; w++) {
      for (int h = 0; h < height; h++) {
        tiles[w][h] = new Tile();
      }
    }
  }

  /**
   * Updates the type of the specified tile
   * @param x the x index of the tile
   * @param y the y index of the tile
   * @param type the type to make the tile
   * @return the new color array of all tiles
   */
  public Color[][] updateTile(int x, int y, int type) {
    tiles[x][y].setType(type);
    
    return getColorArray();
  }

  /**
   * Gets a 2d array of all colors of the tiles
   * @return 2d array of tile colors
   */
  public Color[][] getColorArray() {
    Color[][] colorArray = new Color[width][height];

    for (int w = 0; w < width; w++) {
      for (int h = 0; h < height; h++) {
        colorArray[w][h] = tiles[w][h].getColor();
      }
    }

    return colorArray;
  }

  /**
   * Gets array of images relating to all items on the board
   * @return array of images
   */
  public BufferedImage[] getItemImages() {
    BufferedImage[] images = new BufferedImage[items.size()];

    for (int i = 0; i < items.size(); i++) {
      images[i] = items.get(i).getImage();
    }

    return images;
  }

  /**
   * Gets 2d array of positions of all items
   * @return array of positions [[x,y], [x,y], ...]
   */
  public int[][] getItemPositions() {
    int[][] positions = new int[items.size()][2];

    for (int i = 0; i < items.size(); i++) {
      positions[i] = items.get(i).getPosition();
    }

    return positions;
  }

  /**
   * Gets 2d array of sizes of all items
   * @return array of sizes [[width,height], [width,height], ...]
   */
  public int[][] getItemSizes() {
    int[][] sizes = new int[items.size()][2];

    for (int i = 0; i < items.size(); i++) {
      sizes[i] = items.get(i).getSize();
    }

    return sizes;
  }

  /**
   * Gets the number of items on the board
   * @return number of items
   */
  public int getItemCount() {
    return items.size();
  }

  /**
   * Adds the given item to the board
   * @param item item to be added
   */
  public void addItem(Item item) {
    items.add(item);
  }

  /**
   * Gets the width of the board
   * @return width of the board (in tiles)
   */
  public int getWidth() {
    return width;
  }

  /**
   * Gets the height of the board
   * @return height of the board (in tiles)
   */
  public int getHeight() {
    return height;
  }
}
