import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Class to represent an item in the house
 */
public class Item {
  private boolean table;
  private boolean can_exist_on_table;
  private boolean can_exist_off_table;

  private int rotation = 0;
  private int width;
  private int height;
  private int x;
  private int y;

  private String imageFile;
  private BufferedImage image;

  /**
   * Constructor for the item
   * @param imageFile pathname of the image
   * @param width width of the item (in tiles)
   * @param height height of the item (in tiles)
   * @param x x position
   * @param y y position
   * @param table is the item a table?
   * @param can_exist_on_table can the item be on a table?
   * @param can_exist_off_table can an item be on the floor?
   */
  public Item(String imageFile, int width, int height, int x, int y, boolean table, boolean can_exist_on_table, boolean can_exist_off_table) {
    this.imageFile = imageFile;
    this.width = width;
    this.height = height;
    this.x = x;
    this.y = y;
    this.table = table;
    this.can_exist_on_table = can_exist_on_table;
    this.can_exist_off_table = can_exist_off_table;

    try {
      image = ImageIO.read(new File(imageFile));
    } catch (IOException e) { System.out.println(e); }
  }

  /**
   * Rotates item by 90 degrees
   */
  public void rotate() {
    rotation += 1;
    if (rotation > 3) {
      rotation = 0;
    }
  }

  /**
   * Gets the image of the item
   * @return the image
   */
  public BufferedImage getImage() {
    return image;
  }

  /**
   * Sets the position of the item
   * @param x x position
   * @param y y position
   */
  public void setPosition(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Gets the item's position as an array
   * @return array [x, y]
   */
  public int[] getPosition() {
    int[] position = {x, y};
    return position;
  }

  /**
   * Gets the item's size as an array
   * @return array [width, height]
   */
  public int[] getSize() {
    int[] size = {width, height};
    return size;
  }

  /**
   * Gets the pathname of the item's image
   * @return pathname
   */
  public String getImageFile() {
    return imageFile;
  }

  /**
   * Is the item a table?
   * @return true/false value
   */
  public boolean get_table() {
    return table;
  }

  /**
   * Can the item be on a table?
   * @return true/false value
   */
  public boolean get_can_exist_on_table() {
    return can_exist_on_table;
  }

  /**
   * Can an item be on the floor?
   * @return true/false value
   */
  public boolean get_can_exist_off_table() {
    return can_exist_off_table;
  }
}