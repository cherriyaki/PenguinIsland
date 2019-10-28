# PenguinIsland

## Penguin Island
By Cherry Lim, k1894317

My game is called Penguin Island, a town where penguins live.
The player plays as a penguin who navigates around the town. The goal of the game is to
obtain the golden puffle, a creature in the game. In order to obtain the puffle, the player has
to get a beard and give that beard to the pufflekeeper at the puffle shop.
The player is able to travel from room to room. There are other characters in the game who
can speak dialogue, and the player can reply to the dialogue. The player is able to give and
receive items, which are stored in the inventory. There is a locked door which the player has
to unlock, a dark room that the player has to light up, and a trap door that if the player falls
through, the game restarts.
### Base tasks
1. The game has numerous rooms
Seeing that the createRooms() method in the Game class is already substantially long, I
created a separate class called PenguinIsland that stores the methods for creating rooms,
items and characters. The PenguinIsland class can be interpreted as a type of object that is a
world called Penguin Island, with its own set of rooms (locations), items and characters.
The PenguinIsland class has a field that is a HashMap of the Room class called rooms. This
will store all the rooms of Penguin Island. In the PenguinIsland() constructor of the
PenguinIsland class, the createRooms() method is called.

2. The player can go from room to room
When the ‘go’ command is entered, the goRoom method in the Game class is called. The
player should enter “go” followed by the direction, such as “east”. The second word of the
command is passed to a String called direction. A Room variable called nextRoom stores the
Room in that direction using the getExit method in the Room class. If the next room is not
null, is not locked, and is not a trap door, the player can go to that room. The currentRoom
is updated by passing the nextRoom to it. Then print the description of the currentRoom to
show that the player is now in that room.

3. There are items in some of the rooms. There is no limit on the number of items a
room can hold. Not all items can be kept by the player.
First I created the class Item. It has a String field called name, an int field called weight, and
a String field called description. In the Room class I gave it a HashMap field that stores the
Item class as the value, and the String name as the key. This hashmap stores all items in the
particular room. The addItem method is used to put the item in the item hashmap. In the
PenguinIsland class, there is a HashMap field that stores all items in Penguin Island. There is
a createItems method where the items are created and put in the HashMap using the put
method. 

4. The player can keep some items in his inventory
The player can input “keep” and the item name to keep the item in his inventory. If the
item, when added to the inventory will not cause the weight to exceed the maximum, and if
the item specified by the player is in the current room, then the item will be added to the
player’s inventory via the addToInventory method in the Character class.
The player will have to drop items to pick up other items, as the total weight of all collected
will exceed the maximum weight. The Character class has a int maxWeight field that stores
the maximum weight he can carry. The getTotalWeight method calculates the total weight
of all items in the inventory using a for loop. If the inventory is empty then 0 is returned.

5. The player can win the game and there is a message showing that.
The player wins by obtaining the golden puffle. In the Game class, in the give method, the
player uses the “give” command to give the beard to the pufflekeeper. If successful, the
pufflekeeper receives the beard in his inventory. An if statement checks whether the
pufflekeeper has the required item, and if so, the golden puffle is added to the player’s
inventory and a message is printed for congratulations.
The Game class has a boolean field called hasWon. When the player wins, the hasWon is
updated to true. The else if statement in the processCommand method will return hasWon,
which will update the finished variable, and will end the game.

6. The command “back” is implemented that takes the player back to the previous
room.
The Character class has a Stack<Room> field called roomStack. In the Game class in goRoom
method, before the player moves to the next room, the current room is added to the room
stack of the player.
The “back” command is added. The back method in Game class first checks if the player’s
room stack is empty. If so, a message is printed saying there are no more rooms to go back,
and then a return statement. If not, the nextRoom variable will have the top room in the
stack passed to it via popRoom method of the Character class. If the current room has no
exits, a message will say there is no door. Else, the player will go to the next room which is
the previous room (the top room of the stack).
  
7. At least four new commands are added.
The additional commands are:
• Look: prints a description of the room
• Back: takes player back to previous room
• Keep: keeps an item
• Drop: drops an item from inventory in the current room
• Items: prints all items in inventory
• Commands: prints all command words for use
• Talk: gets characters to talk
• Reply: replies to characters
• Unlock: unlocks a door
• Light: lights up a room
• Give: gives an item to a character

### Challenge tasks
1. Characters are added. One can move around by itself.
There is a Character class. In PenguinIsland class, the createCharacters method adds
characters to its character hashmap, and also places characters in the respective room’s
hashmaps. When the room description is printed, the items and characters of the room are
also listed.
The talk command is implemented by the talk method in Game class. The player inputs talk
and the character name, and the character, if it is a talking one, will say something via a
printed message.
The private mcMove method in Game allows the moving character to move to a random
room each time the player moves rooms. The mcMove method is called every time the go
and back commands are entered.

2. Three-word commands are recognised.
The getCommand method in the Parser class now takes three words as input. I based the
code for the third word based on those for the 1st and 2nd words. The Command class now
has a thirdWord String field. I added getThirdWord and hasThirdWord methods.
In Game, I added if statements in methods that take in commands. Those if statements
check if there is a third word. If so, a string variable will store the 2nd and 3rd word separated
by a space. I used the toLowerCase method to convert both words to lower case for easier
reference in hashmaps.

3. There is a locked room. It can be unlocked if the player has a key.
The Room class has an isLocked boolean field. The toggleLock method can lock a room. The
unlock command will call the unlock method. If the player has a key, the toggleLock method
will be called and a printed message will say that the room has been unlocked. If the player
has no key, a printed message will say you have no key.

4. There is a trap door. There are no exits. Entering it causes the game to restart and the
player returns to the starting room.
The trap door is preceeded by a dark room. When the player first enters the dark room and
continues east, he will enter the trap door. The goRoom method in Game has an else if that
checks if there next room is a trap door. If so, a printed message will say you’ve fallen
through a trap door. The room stack is then cleared, and the currentRoom will be set to
plaza. Description of plaza is printed. 

### Code quality
Coupling
I made it such that if a class wants to access the fields of another class, then it does so by
calling an accessor method from that class. For instance, all methods starting with ‘get’, like
‘getInventory’ in the Character class, are accessor methods that return the respective field.
Any method I wrote starting with ‘add’ or ‘remove’, such as ‘addItem’ in the Room class, are
methods that add or remove elements from a hashmap field of that class. This allows the
interface of the class to allow changes made to the fields, without accessing the fields
directly.

Cohesion
I tried as much as possible to allocate methods to the class that it is most relevant to.
Game only has methods that implement user commands and methods that are specific to
the game. However, I’ve made 2 mistakes in Game – getCurrentRoom should have been
deleted, and exceedWeight should be in the Character class ideally. PenguinIsland has
methods for creating the specific rooms, items and characters to the Penguin Island game,
and methods that return data that are only specific to this game. For example, if this was a
different game, such as a dungeon game, the current room would be different and so on.
In Room, Item, Character, Command, CommandWords and Parser, all methods are relevant
to their respective class. These classes can be used to create a totally different game, as the
methods don’t need change, only the data does.

Responsibility-driven design
I tried as much as possible to ensure that the relevant class handles the relevant data. I’ve
discussed this in cohesion.
Ideally, PenguinIsland should contain all dialogue required in the game, as the dialogue is
only specific to the Penguin Island game. For example, characterTalk method in
PenguinIsland contains the dialogue that is printed when the talk command is passed.
Ideally, I would move all welcome and congratulatory messages from Game to
PenguinIsland, because if this was a dungeon game for example, those messages would say
different things.

Maintainability
As I tried to ensure the other three code qualities, especially cohesion and responsibilitydriven design, this allows errors to be easily found as you would only need to look in the
class that is relevant.

### Game walk-through
The game starts at the plaza. If you type ‘help’, the goal of the game and command words
are revealed. If at any point in the game you need to know the command words or items in
your inventory, just type ‘commands’ and ‘items’.
You can walk around the rooms to explore, but the only helpful place would be the café
where Aunt Arctic is. When you get her to talk, she asks a riddle which you need to answer
with ‘reply’. If you don’t know the answer and you go exploring, the moving character,
Average Joe, might run into you and upon talking, will reveal the answer to you.
Once you answer Aunt Arctic correctly, she gives you a key. You use the key to open the
locked door in the theatre. Upon entering, the room is pitch black, and if you go east from
there you fall into a trap door which restarts the game. But if you pick up a lamp from the
theatre and light up the black room, the room is revealed to be the costume store, where
you can pick up the beard. (If you go east from the lit up costume store, you won’t fall into
the trap door but will be standing next to it instead. Phew!) You will have to drop the lamp
in order to pick up the beard, else the total weight would exceed your maximum inventory
weight.
Now you can go to the puffle shop and give the beard to the pufflekeeper, upon which the
golden puffle is added to your inventory and you win.

### Known bugs
In my most recent test of the game, when I go east from the lit up costume store, and then
type ‘back’ an exception occurs. The error is in the mcMove method of Game, in the
mcRoomExits.get method. This could be because the moving character has moved into the
theatre and then into the dark room then trap door, after which it couldn’t exit as the trap
door has no exits. I should have added if and else if statements in the mcMove method to
ensure the moving character only goes to rooms that are not locked and not trap doors.
Also, I wanted to implement the magic transporter room task but didn’t manage to do so,
however I did not delete the magic room exit from the theatre under createRooms in
PenguinIsland, so that could have also caused the bug. 
