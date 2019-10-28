import java.util.Set;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Room 
{
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private HashMap<String, Item> items;        // stores items of this room.
    private HashMap<String, Character> characters;
    private boolean isLocked = false;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<>();
        items = new HashMap<>();
        characters = new HashMap<>();
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }
    
    /**
     * Create an item in this room.
     * @param name The name of the item.
     * @param item The item in this room.
     */
    public void addItem(String name, Item item) 
    {
        items.put(name, item);
    }
    
    /**
     * Remove an item from this room.
     * @param name The name of the item.
     * @param item The item in this room.
     */
    public void removeItem(String name) 
    {
        items.remove(name);
    }
    
    /**
     * Create an item in this room.
     * @param name The name of the item.
     * @param item The item in this room.
     */
    public void addCharacter(String name, Character character) 
    {
        characters.put(name, character);
    }
    
    /**
     * Remove an item from this room.
     * @param name The name of the item.
     * @param item The item in this room.
     */
    public void removeCharacter(String name) 
    {
        characters.remove(name);
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     *     Items: lamp key
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString() + 
        ".\n" + getItemString() + ".\n" + getCharacterString() + ".";
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    public String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }
    
    public ArrayList<Room> getExitArray()
    {
        ArrayList<Room> exitArray = new ArrayList<Room>();
        exitArray.addAll(exits.values());
        return exitArray;
    }
    
    
    /**
     * Return the HashMap of items in the room.
     */
    public HashMap<String, Item> getItemHashMap()
    {
        return items;
    }
    
    /**
     * Return the HashMap of items in the room.
     */
    public HashMap<String, Character> getCharacterHashMap()
    {
        return characters;
    }
    
    /**
     * Return a string describing the room's items, for example
     * "Items: lamp (weight: 7) key (weight: 1)".
     * @return Details of the room's exits.
     */
    private String getItemString()
    {
        if(items.isEmpty() != true) {
            String returnString = "Items:";
            Set<String> keys = items.keySet();
            for(String item : keys) {
            returnString += " " + item + "(weight: " + 
            (items.get(item)).getWeight() + ")";
            }
        return returnString;
        }
        else {
            return "No items here";
        }
    }
    
    /**
     * Return a string describing the room's characters, for example
     * "Characters: Aunt Arctic, Average Joe".
     * @return Details of the room's exits.
     */
    private String getCharacterString()
    {
        if(characters.isEmpty() != true) {
            String returnString = "Characters:";
            Set<String> keys = characters.keySet();
            for(String character : keys) {
            returnString += " " + character;
            }
        return returnString;
        }
        else {
            return "No penguins here";
        }
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
    
    public void toggleLock()
    {
        isLocked = !isLocked;
    }
    
    public boolean isLocked()
    {
        return isLocked;
    }
    
    public boolean isTrapDoor()
    {
        if (exits.isEmpty()) {
            return true;
        }
        else {
            return false;
        }
    }
}

