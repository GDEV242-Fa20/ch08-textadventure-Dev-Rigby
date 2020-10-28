
/**
 * A class designed to hold item names and descriptions
 *
 * @author Brian McMahon
 * @version 10/26/2020
 */
public class Item
{
    private int weight;
    private String description;
    /**
     * Constructor that takes a name and a description
     */
    public Item(int weight, String description)
    {
        this.weight = weight;
        this.description = description;
    }
    public int getWeight()
    {
        return weight;
    }
    public String getDescription()
    {
        return description;
    }
}
