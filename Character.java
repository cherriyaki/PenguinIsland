import java.util.HashMap; 
import java.io.*; 
import java.util.*; 

/**
 * Class Character - a character in an adventure game.
 *
 * @author Cherry Lim
 */
public class Character
{
    // instance variables - replace the example below with your own
    private Stack<Room> roomStack = new Stack<Room>();
    private int maxWeight;
    private int totalWeight; // total weight of items character is carrying
    private HashMap<String, Item> inventory = new HashMap<>();
    private String name;
    private String description;
    private Item requiredItem;

    /**
     * Constructor for objects of class Player
     */
    public Character(String name, String description)
    {
        // initialise instance variables
        this.name = name;
        this.description = description;
        totalWeight = 0;
    }

    /**
     * Push current room onto stack of rooms player has gone through.
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public void pushRoom(Room currentRoom)
    {
        // put your code here
        roomStack.push(currentRoom);
    }
    
    /**
     * Pop current room from stack of rooms player has gone through.
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public Room popRoom()
    {
        // put your code here
        return roomStack.pop();
    }
    
    public Stack<Room> getRoomStack()
    {
        return roomStack;
    }
    
    /**
     * Push current room onto stack of rooms player has gone through.
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public void addToInventory(String name, Item item)
    {
        // put your code here
        inventory.put(name, item);
    }
    
    /** 
     * Remove item from inventory.
     */
    public void removeInventory(String name)
    {
        inventory.remove(name);
    }
    
    /**
     * Return HashMap inventory.
     */
    public HashMap<String, Item> getInventory()
    {
        return inventory;
    }
    
    /** 
     * Return maximum weight character can carry.
     */
    public int getMaxWeight()
    {
        return maxWeight;
    }
    
    /**
     * Return total weight of items in inventory.
     */
    public int getTotalWeight()
    {
        totalWeight = 0;
        if(!inventory.isEmpty()) {
            Collection<Item> items = inventory.values();
            for(Item item : items) {
            totalWeight += item.getWeight();
            }
            return totalWeight;
        }
        else {
            return 0;
        }
    }
    
    public void setMaxWeight(int weight)
    {
        maxWeight = weight;
    }
    
    public void setRequiredItem(Item item)
    {
        requiredItem = item;
    }
    
    public boolean hasItem()
    {
        if (inventory.containsKey(requiredItem.getName())) {
            return true;
        }
        else {
            return false;
        }
    }
    
    public String getName()
    {
        return name;
    }
    
}
