/**
 * Class Item - an item found in a room.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * An "Item" represents one item in a room of the game.
 * It can be picked up, carried and left in other rooms.
 * 
 * @author  Nouira Chafik
 * @version 2014.04.14
 */
public class Item {
	private String name;
	private String description;
	private Integer weight;
	
	/**
	 * Item Class Constructor, initialises an item information.
	 * 
	 * @param n Name of the item.
	 * @param d Description of the item.
 	 * @param w Weight of the item.
	 */
	public Item(String n, String d, Integer w) {
		this.description = d;
		this.weight = w;
		this.name = n;
	}
	
	/**
	 * Name getter.
	 * @return The name of the item.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Description getter.
	 * @return The description of the item.
	 */
	public String getDescription() {
		return this.description;
	}
	
	/**
	 * Weight getter.
	 * @return The weight of the item.
	 */
	public Integer getWeight() {
		return this.weight;
	}
	
	/**
	 * Produces a long description of the item.
	 * @return A String that contains all the information about the item.
	 */
	public String getLongDescription() {
		return this.name +": "+this.description + ", " + Integer.toString(this.weight)+".";
	}
}
