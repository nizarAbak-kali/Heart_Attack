import java.util.ArrayList;
import java.util.Stack;

/**
 * Class Player - Represents a single player.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A player is one caracter playing the game.
 * He has his own information. The game can manage many players.
 * 
 * @author  Nouira Chafik
 * @version 2014.04.14
 */
public class Player {
	private String name;
	private Integer maximumWeight = 200;
	private Integer carriedItemsWeight = 0;
	private Room currentRoom;
	private Room beamer = null; //Question 44
	private Stack<Room> previousRooms = new Stack<Room>();
	private ItemList items = new ItemList();
	
	/**
	 * Player Class Constructor.
	 * @param n Name of the player.
	 * @param mw Maximum weight that the player can carry around.
	 * @param r Current room of the player.
	 */
	public Player(String n, Integer mw, Room r) {
		this.name = n;
		this.maximumWeight = mw;
		this.currentRoom = r;
	}
	
	/**
	 * Name getter.
	 * @return The name of the player.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Maximum weight getter.
	 * @return The maximum wight that the player can carry around.
	 */
	public Integer getMaximumWeight() {
		return this.maximumWeight;
	}
	
	/**
	 * Carried items weight getter.
	 * @return The total weight of the carried items.
	 */
	public Integer getCarriedItemsWeight() {
		return this.carriedItemsWeight;
	}

	/**
	 * Current room getter.
	 * @return The current room of the player.
	 */
	public Room getCurrentRoom() {
		return this.currentRoom;
	}
	
	/**
	 * Name setter.
	 * @param n Name(Or new name) of the player.
	 */
	public void setName(String n) {
		this.name = n;
	}
	
	/**
	 * Maximum weight setter.
	 * @param mw Maximum weight the player can carry around.
	 */
	public void setMaximumWeight(Integer mw) {
		this.maximumWeight = mw;
	}
	
	/**
	 * Current room setter.
	 * @param r Current room of the player.
	 */
	public void setCurrentRoom(Room r) {
		this.currentRoom = r;
	}
	
    /**
    * Creates an instance of item with given description and weight
    * Question 7.22
    */
    public void addItem(String name, String description, Integer weight) {
    	items.addItem(new Item(name, description, weight));
    }
	
	/**
	 * Adds an item to the list of items carried by the player.
	 * @param item New item to add to the list.
	 */
	public void addItem(Item item) {
		//Tha player can't exceed the maximum weight he can carry around.
		if((carriedItemsWeight + item.getWeight()) <= maximumWeight) {
			items.addItem(item);
			this.carriedItemsWeight += item.getWeight();
		}
	}
	
	/**
	 * Removes an item from the list of items carried by the player.
	 * @param item Item to remove from the list.
	 */
	public void removeItem(Item item) {
		items.removeItem(item);
		this.carriedItemsWeight -= item.getWeight(); 
	}
	
	/**
	 * Adds a previous room to the previous rooms list.
	 * @param room Previous room to add to the list.
	 */
	public void addPreviousRoom(Room room) {
		this.previousRooms.push(room);
	}
	
	/**
	 * Tells if the previous rooms list is empty or not.
	 * @return Boolean value : True if the list is empty, false if it's not.
	 */
	public boolean emptyPreviousRooms() {
		return this.previousRooms.empty();
	}
	
	/**
	 * Returns the last room visited by the player.
	 * @return Last room visited.
	 */
	public Room getLastRoom() {
		return this.previousRooms.peek();
	}
	
	/**
	 * Removes the last room visited by the player of the previousRooms list.
	 */
	public void removePreviousRoom() {
		this.previousRooms.pop();
	}
	
	/**
	 * Produces the description and the total weight of all the items carried by the player. 
	 * @return List of carried items with descriptions.
	 */
	public String getCarriedItemsList() {
		if(items.getItemListSize() == 0) return "No carried items.";
		return "Carried Items : " + items.getItemString();
	}
	
    /**
     * Searches an item by name in the list of carried items.
     * @param name Name of the item we're looking for.
     * @return The reference of the item if it exists, null if it doesn't.
     */
    public Item findItem(String name) {
    	return items.findItem(name);
    }
    
    /**
     * Beamer room getter.
     * @return The room where the beamer was charged.
     */
    public Room getBeamer() {
    	return this.beamer;
    }
    
    /**
     * Beamer room setter.
     * @param r New room where the beamer was charged.
     */
    public void setBeamer(Room r) {
    	this.beamer = r;
    }
}
