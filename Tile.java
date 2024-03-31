
import java.awt.Color;

/**
 * Individual tile on a board
 */
public class Tile {
  // int types
  public static final int EMPTY = 0;
  
  // array of colors relating to tile types
  public static final Color[] colors = {Color.lightGray};

  // type of this tile
  private int type;

  /**
   * Constructor of an empty tile
   */
  public Tile() {
    type = EMPTY;
  }

  /**
   * Gets the integer type of the tile
   * @return type of tile
   */
  public int getType() {
    return type;
  }

  /**
   * Gets the color of the tile
   * @return color of tile
   */
  public Color getColor() {
    return colors[type];
  }

  /**
   * Sets the tile's type to the given type (use a Tile value)
   * @param newType the type to set the tile to
   */
  public void setType(int newType) {
    type = newType;
  }
}
