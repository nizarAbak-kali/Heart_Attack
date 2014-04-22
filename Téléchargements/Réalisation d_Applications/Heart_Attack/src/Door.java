/**
 *  This class is part of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.
 * 
 *  This class creates all rooms, creates the parser and starts
 *  the game.  It also evaluates and executes the commands that 
 *  the parser returns.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0 (Jan 2003)
 */
public class Door {
	private String direction;
	private Room neighbor;
	private boolean key = false;
	private String keyString = null;
	
	
	public Door(String d, Room r) {
		this.direction = d;
		this.neighbor = r;
	}
	
	public Door(String d, Room r, String kString) {
		this.direction = d;
		this.neighbor = r;
		this.key = true;
		this.keyString = kString;
	}
	
	public String getDirection() {
		return this.direction;
	}
	
	public void setDirection(String d) {
		this.direction = d;
	}
	
	public String getKeyString() {
		return this.keyString;
	}
	
	public boolean getKey() {
		return this.key;
	}
	
	public void setKeyString(String k) {
		this.keyString = k;
		this.key = true;
	}
	
	public boolean validKeyString(String ks) {
		return ks.equals(this.keyString);
	}
	
	public void openDoor() {
		this.key = false;
		this.keyString = null;
	}
	
	public Room getNeighbor() {
		return this.neighbor;
	}
	
}
