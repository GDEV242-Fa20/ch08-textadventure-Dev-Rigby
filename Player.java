
/**
 * A player with an inventory
 *
 * @author Brian McMahon
 * @version 10/26/2020
 */
public class Player
{
    private Item[] inventory;
    public Player(int inventorySpace)
    {
        inventory = new Item[inventorySpace];
    }
    public Item[] getInventory()
    {
        return inventory;
    }
    public void setItem(int index, Item item)
    {
        inventory[index] = item;
    }
}