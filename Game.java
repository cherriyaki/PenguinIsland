import java.io.*; 
import java.util.*; 
import java.util.Scanner;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game 
{
    private Parser parser;
    private Character player;
    private Character movingCharacter;
    private Room currentRoom;
    private Room mcRoom;
    private PenguinIsland penguinIsland;
    private HashMap<String, Room> rooms;
    private HashMap<String, Character> characters;
    private HashMap<String, Item> items;
    private boolean inConversation;
    private boolean hasWon;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        hasWon = false;
        penguinIsland = new PenguinIsland();
        currentRoom = penguinIsland.getCurrentRoom();
        mcRoom = penguinIsland.getMcRoom();
        rooms = penguinIsland.getRoomHashMap();
        items = penguinIsland.getItemHashMap();
        characters = penguinIsland.getCharacterHashMap();
        parser = new Parser();
        player = new Character("player", "the player");
        player.setMaxWeight(penguinIsland.getMaxWeight());
        movingCharacter = penguinIsland.getMovingCharacter();
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
        
        boolean finished = false;
        
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to Penguin Island!");
        System.out.println("Penguin Island is a bustling town for penguins.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Returns current room player is in.
     */
    public Room getCurrentRoom()
    {
        return currentRoom;
    }
    
    /**
     * Returns true if given item plus current total weight on character will exceed
     * maximum weight character can carry.
     */
    private boolean exceedWeight(Item item, Character character)
    {
        if(item.getWeight() + character.getTotalWeight() 
        > character.getMaxWeight()) {
            return true;
        }
        else {
            return false;
        }
    }
    
    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            mcMove();
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("look")) {
            look();
        }
        else if (commandWord.equals("back")) {
            mcMove();
            back();
        }
        else if (commandWord.equals("keep")) {
            keepItem(command);
        }
        else if (commandWord.equals("drop")) {
            dropItem(command);
        }
        else if (commandWord.equals("items")) {
            printItems();
        }
        else if (commandWord.equals("commands")) {
            printCommands();
        }
        else if (commandWord.equals("talk")) {
            talk(command);
        }
        else if (commandWord.equals("reply")) {
            reply(command);
        }
        else if (commandWord.equals("unlock")) {
            unlock(command);
        }
        else if (commandWord.equals("light")) {
            light();
        }
        else if (commandWord.equals("give")) {
            give(command);
            return hasWon;
        }
        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print a description and goal and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("A dog is a man's best friend,but here");
        System.out.println("on Penguin Island,a puffle is a penguin's");
        System.out.println("best friend!");
        System.out.println("There is a special golden puffle desired ");
        System.out.println("by all, but alas, money can't buy it!");
        System.out.println("Rumour has it that if you give the pufflekeeper");
        System.out.println("a special artefact belonging to the pirate");
        System.out.println("Rockhopper, he will reward you with a golden");
        System.out.println("puffle.");
        System.out.println("Your goal is to obtain a golden puffle.");
        System.out.println("Walk around and good luck!");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
        
        inConversation = false;
    }

    /** 
     * Try to go in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = (command.getSecondWord()).toLowerCase();
        
        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else if (nextRoom.isLocked()) {
            System.out.println("Room is locked.");
        }
        else if (nextRoom.isTrapDoor()) {
            System.out.println("You've fallen through a trap door."
            + " The game has restarted.\n");
            (player.getRoomStack()).clear();
            currentRoom = rooms.get("plaza");
            System.out.println(currentRoom.getLongDescription());
        }
        else {
            // Add current room to the player's room stack before moving to the next room.
            player.pushRoom(currentRoom);
            currentRoom = nextRoom;
            
            System.out.println(currentRoom.getLongDescription());
        }
        
        inConversation = false;
    }
    
    /**
     * The moving character moves to a randomly selected exit.
     */
    public void mcMove()
    {
        Random generator = new Random();
        ArrayList<Room> mcRoomExits = new ArrayList<Room>();
        mcRoomExits.addAll(mcRoom.getExitArray());
        Room nextMcRoom = mcRoomExits.get(generator.nextInt(mcRoomExits.size()));
        mcRoom = nextMcRoom;
        mcRoom.addCharacter(movingCharacter.getName(), movingCharacter);
    }
    
    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    /**
     * "back" was entered. Go back to the previous room the player was in.
     */
    private void back()
    {
        if ((player.getRoomStack()).empty()) {
            System.out.println("No more rooms to go back to.");
            return;
        }
        
        Room nextRoom = player.popRoom();

        if(currentRoom.getExitString().equals("Exits:")) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            
            System.out.println(currentRoom.getLongDescription());
        }
        
        inConversation = false;
    }
    
    /** 
     * "keep" was entered. Keep item. 
     */
    private void keepItem(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to keep.
            System.out.println("Keep what?");
            return;
        }

        String word1, word2, item;
        word1 = (command.getSecondWord()).toLowerCase();
        
        if(command.hasThirdWord()) {
            word2 = (command.getThirdWord()).toLowerCase();
            item = word1 + " " + word2;
        }
        else {
            item = word1;
        }
        
        boolean willExceed = exceedWeight(items.get(item), player);
        
        if((currentRoom.getItemHashMap()).containsKey(item) && 
        !willExceed) { 
            player.addToInventory(item, items.get(item));
            System.out.println("You've kept the " + item + ".");
            currentRoom.removeItem(item);
        }
        else if(willExceed) {
            System.out.println("You've exceeded the maximum weight you can");
            System.out.println(" carry. Please drop one or more items.");
        } 
        else {
            System.out.println("Item you specified isn't in this room.");
        }
        
        inConversation = false;
    }
    
    /**
     * "drop" was entered. Drop an item.
     */
    private void dropItem(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to drop.
            System.out.println("Drop what?");
            return;
        }
        
        String word1, word2, item;
        word1 = (command.getSecondWord()).toLowerCase();
        
        if(command.hasThirdWord()) {
            word2 = (command.getThirdWord()).toLowerCase();
            item = word1 + " " + word2;
        }
        else {
            item = word1;
        }
        
        if((player.getInventory()).containsKey(item)) {
            currentRoom.addItem(item, (player.getInventory()).get(item));
            System.out.println("You've dropped the " + item + ".");
            player.removeInventory(item);
        }
        else {
            System.out.println("Item you specified doesn't exist in inventory.");
        }
        
        inConversation = false;
    }
    
    /** 
     * "Look" was entered. Print the room description again.
     */
    private void look() 
    {
        System.out.println(currentRoom.getLongDescription());
        inConversation = false;
    }
    
    /**
     * Print out all items currently carried and their total weight.
     */
    private void printItems()
    {
        if(!items.isEmpty()) {
            String returnString = "Items in your inventory:";
            Set<String> keys = (player.getInventory()).keySet();
            
            for(String item : keys) {
            returnString += " " + item;
            }
            
            System.out.println(returnString);
        }
        else {
            System.out.println("No items in your inventory.");
        }
        
        inConversation = false;
    }
    
    /**
     * Print out all command words.
     */
    private void printCommands()
    {
        System.out.println("Your command words are:");
        parser.showCommands();
        
        inConversation = false;
    }
    
    /**
     * "Talk" was entered. get characters to talk.
     */
    private void talk(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know who the player wants to talk to.
            System.out.println("Talk to who?");
            return;
        }
        
        String word1, word2, character;
        word1 = (command.getSecondWord()).toLowerCase();
        
        if(command.hasThirdWord()) {
            word2 = (command.getThirdWord()).toLowerCase();
            character = word1 + " " + word2;
        }
        else {
            character = word1;
        }
        
        if((currentRoom.getCharacterHashMap()).containsKey(character)) { 
            penguinIsland.characterTalk(character);
            inConversation = true;
        }
        else {
            System.out.println("Character you specified isn't in this room.");
        }
    }
    
    /**
     * "reply" was entered. Reply when a character talks to you.
     */
    private void reply(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what the player wants to say.
            System.out.println("Reply what?");
            return;
        }
        else if(!inConversation) {
            System.out.println("A question hasn't been asked.");
            return;
        }
        
        String word1, word2, answer;
        word1 = (command.getSecondWord()).toLowerCase();
        if(command.hasThirdWord()) {
            word2 = (command.getThirdWord()).toLowerCase();
            answer = word1 + " " + word2;
        }
        else {
            answer = word1;
        }
        
        if(answer.equals("tooth thirty")) {
            player.addToInventory("key", items.get("key"));
            System.out.println("Aunt Arctic says: Good job!");
            System.out.println("A key has been added to your inventory.");
        }
        else {
            System.out.println("Wrong answer. Try again.");
            return;
        }
    }
    
    
    /**
     * "unlock" was entered. unlocks a room
     */
    private void unlock(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to unlock...
            System.out.println("Unlock which direction?");
            return;
        }

        String direction = (command.getSecondWord()).toLowerCase();
        
        if ((player.getInventory()).containsKey("key")) {
            (currentRoom.getExit(direction)).toggleLock();
            if (!(currentRoom.getExit(direction)).isLocked()) {
                System.out.println("You've unlocked the " + direction + " door");
            }
        }
        else {
            System.out.println("You don't have a key.");
        }
    }
    
    /**
     * "light" was entered.
     */
    private void light()
    {
        if ((player.getInventory()).containsKey("lamp")) {
            System.out.println("You've lit up the room.");
            currentRoom = rooms.get("costume store");
            System.out.println(currentRoom.getLongDescription());
        }
        else {
            System.out.println("You don't have a lamp.");
        }
    }
    
    /**
     * "give" was entered.
     */
    private void give(Command command)
    {
        if(!command.hasSecondWord() || !command.hasThirdWord()) {
            // if there is no second or third word, we don't know what to give to whom...
            System.out.println("Format: give [item] [character]");
            return;
        }
        
        String item, character;
        item = (command.getSecondWord()).toLowerCase();
        character = (command.getThirdWord()).toLowerCase();
        
        if((player.getInventory()).containsKey(item) && (currentRoom.getCharacterHashMap()).containsKey(character)) {
            (characters.get(character)).addToInventory(item, items.get(item));
            player.removeInventory(item);
            System.out.println("You have given the " + item +
            " to " + character + ".");
            
            if ((characters.get(character)).hasItem()) {
                player.addToInventory((penguinIsland.getReward()).getName(), 
                items.get((penguinIsland.getReward()).getName()));
                System.out.println("Pufflekeeper says: Good job!");
                System.out.println("You have obtained the golden puffle. You've won!");
                hasWon = true;
            }
        }
        else if (!(player.getInventory()).containsKey(item)) {
            System.out.println("Item you specified doesn't exist in inventory.");
            System.out.println("Format: give [item] [character]");
            return;
        }
        else if (!(currentRoom.getCharacterHashMap()).containsKey(character)) {
            System.out.println("Character you specified isn't in this room.");
            System.out.println("Format: give [item] [character]");
            return;
        }
    }
}
