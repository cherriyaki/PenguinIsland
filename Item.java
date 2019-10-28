
/**
 * Class Item - an item in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * An "Item" represents an item in a room of the game.
 * 
 * @author  Cherry Lim
 */
public class Item
{
    // instance variables - replace the example below with your own
    private String name;
    private int weight;
    private String description;

    /**
     * Constructor for objects of class Item
     */
    public Item(String name,  int weight, String description)
    {
        // initialise instance variables
        this.name = name;
        this.weight = weight;
        this.description = description;
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public int getWeight()
    {
        // put your code here
        return weight;
    }
    
    public String getName()
    {
        return name;
    }
}
