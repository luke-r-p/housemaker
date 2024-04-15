/**
 * Class containing information for all items, used for creating a new instance of an item
 */
public class ItemGuide {
  public static final String[] itemNames = {
    "Wooden table (3x2)",
    "Wooden chair",
    "Plant"
  }; // names of the items, in order

  private static final Item[] items = {
    new Item("items/wooden_table_3x2.png", 3, 2, 40, 28, true, false, true),
    new Item("items/wooden_chair.png", 1, 1, 40, 30, false, false, true),
    new Item("items/plant_1.png", 1, 1, 10, 15, false, true, true)
  }; // Item instances, to be copied in makeItem

  /**
   * Makes a new instance of the item with the given id (position of item in itemNames)
   * @param id index of item in itemNames
   * @return the new Item
   */
  public static Item makeItem(int id) {
    Item out = new Item(
      items[id].getImageFile(),
      items[id].getSize()[0],
      items[id].getSize()[1],
      0,
      0,
      items[id].get_table(),
      items[id].get_can_exist_on_table(),
      items[id].get_can_exist_off_table()
    );

    return out;
  }
}
