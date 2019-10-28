import java.io.*; 
import java.util.*; 

/**

 * Write a description of class PenguinIsland here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class PenguinIsland
{
    // instance variables - replace the example below with your own
    private HashMap<String, Room> rooms = new HashMap<>();
    private HashMap<String, Character> characters = new HashMap<>();
    private HashMap<String, Item> items = new HashMap<>();
    private Game game;
    private Room currentRoom;
    private Room mcRoom;
    private int maxWeight;
    private Item reward;
    private Character movingCharacter;

    /**
     * Constructor for objects of class PenguinIsland
     */
    public PenguinIsland()
    {
        // initialise instance variables
        createRooms();
        createCharacters();
        createItems();
        maxWeight = 10;
    }

    
    /**
     * Create all the rooms, link their exits together 
     */
    public void createRooms()
    {
        Room plaza, theatre, cafe, puffleShop, backyard,
        darkRoom, costumeStore, magicRoom, trapdoor, avoidedTrap;
      
        // create the rooms
        rooms.put("plaza", plaza = new Room("in the plaza"));
        rooms.put("theatre", theatre = new Room("in the theatre"));
        rooms.put("cafe", cafe = new Room("in a cafe"));
        rooms.put("puffle shop", puffleShop = new Room("in a puffle shop"));
        rooms.put("dark room", darkRoom = new 
        Room("in a dark room. It's pitch black"));
        rooms.put("costume store", costumeStore = new 
        Room("in the costume store of the theatre"));
        rooms.put("magic room", magicRoom = new Room("in a magic room"));
        rooms.put("trap door", trapdoor = new Room("in a trap door"));
        rooms.put("avoided trap", avoidedTrap = new Room("next to a trap"
        + " door. Good thing you can see it so you've avoided it"));
        
        // set locks
        darkRoom.toggleLock();
        
        // initialise room exits
        plaza.setExit("east", theatre);
        plaza.setExit("west", cafe);
        plaza.setExit("north", puffleShop);

        theatre.setExit("west", plaza);
        theatre.setExit("east", darkRoom);
        theatre.setExit("south", magicRoom);

        cafe.setExit("east", plaza);
        
        darkRoom.setExit("west", theatre);
        darkRoom.setExit("east", trapdoor);

        costumeStore.setExit("west", theatre);
        costumeStore.setExit("east", avoidedTrap);
        
        avoidedTrap.setExit("west", costumeStore);
        
        puffleShop.setExit("south", plaza);
        
        // start game at plaza
        currentRoom = plaza;
        mcRoom = puffleShop;
    }

    /**
     *  Create all the items.
     */
    public void createItems()
    {
        // create the items
        Item lamp, beard, key, goldenPuffle;
        
        items.put("lamp", lamp = new Item("lamp", 7, "a candle lamp"));
        items.put("beard", beard = new Item("beard", 5, "a beard"));
        items.put("key", key = new Item("key", 1, "a key"));
        items.put("golden puffle", goldenPuffle = new Item("golden puffle", 10, "a golden puffle"));
        
        // place items in rooms
        (rooms.get("theatre")).addItem("lamp", lamp);
        (rooms.get("costume store")).addItem("beard", beard);
        
        // place items in characters' inventories
        (characters.get("aunt arctic")).addToInventory("key", key);
        
        characters.get("pufflekeeper").setRequiredItem(beard);
        
        reward = goldenPuffle;
    }
    
    /**
     *  Create all the characters.
     */
    public void createCharacters()
    {
        // create the characters
        Character auntArctic, averageJoe, pufflekeeper;
        
        characters.put("aunt arctic", auntArctic = new Character("aunt arctic", "Aunt Arctic"));
        characters.put("pufflekeeper", pufflekeeper = new Character("pufflekeeper", "pufflekeeper"));
        characters.put("average joe", averageJoe = new Character("average joe", "Average Joe"));
        
        movingCharacter = averageJoe;
        
        // place characters in rooms
        (rooms.get("cafe")).addCharacter("aunt arctic", auntArctic);
        (rooms.get("puffle shop")).addCharacter("pufflekeeper", pufflekeeper);
    }
    
    /**
     * Return the HashMap of items in the room.
     */
    public HashMap<String, Room> getRoomHashMap()
    {
        return rooms;
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
    
    public Room getCurrentRoom() 
    {
        return currentRoom;
    }
    
    public Room getMcRoom() 
    {
        return mcRoom;
    }
    
    public int getMaxWeight()
    {
        return maxWeight;
    }
    
    public Item getReward()
    {
        return reward;
    }
    
    public Character getMovingCharacter()
    {
        return movingCharacter;
    }
    
    public void characterTalk(String character)
    {
        if(character.equals("aunt arctic")) {
            System.out.println("Aunt Arctic says: Let's see if you can");
            System.out.println("answer this joke! What is a dentist's");
            System.out.println("favourite time of the day?");
        }
        else if(character.equals("average joe")) {
            System.out.println("Average Joe says: Stuck at Aunt Arctic's");
            System.out.println("question? The answer is Tooth Thirty.");
        }
    }
}
