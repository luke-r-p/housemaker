import java.awt.Color;

/**
 * Class for the board of tiles
 */
public class Board {
  int width;
  int height;

  Tile[][] tiles;

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

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }
}
