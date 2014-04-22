import java.util.ArrayList;

/**
 * Class ItemList - Represents and manage a list of items.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * An item list is a collection of items that we can find in a room or that a player carries around.
 * 
 * @author  Nouira Chafik
 * @version 2014.04.14
 */

public class ItemList {
	private ArrayList<Item> items = new ArrayList<Item>();
	
	/**
	 * Class Constructor.
	 */
	public ItemList() {
	}	
	
	/**
	 * Adds an item to the list. 
	 * @param i Item to be added.
	 */
	public void addItem(Item i) {
		items.add(i);
	}
	
	/**
	 * Removes an item from the list.
	 * @param i Item to be removed.
	 */
	public void removeItem(Item i) {
		items.remove(i);
	}
	
	/**
	 * List size getter.
	 * @return The size of item list.
	 */
	public int getItemListSize() {
		return items.size();
	}
	
	/**
	 * Searches an Item in the list.
	 * @param name Name of the item we are looking for.
	 * @return The Item if it is found, null if it doesn't exist in the list.
	 */
	public Item findItem(String name) {
		for(Item i : items)
			if(i.getName().equals(name)) return i;
		return null;
	}
	
    /**
    * Return a description of the room's items,
    * for example "Items: Magic cookie, 50". Question 7.22
    * @return A description of the available items.
    */
    public String getItemString() {
    	String returnString = "";
    	if(items.size() == 0) return null;
    	for(Item i : items)
    		returnString += "\n" + i.getLongDescription();
    	return returnString;
    }
}