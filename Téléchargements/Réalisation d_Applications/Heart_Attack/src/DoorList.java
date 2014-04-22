import java.util.ArrayList;


public class DoorList {
	private ArrayList<Door> doors = new ArrayList<Door>();

	
	public DoorList() {
		
	}
	
	/**
	 * Adds a door to the list. 
	 * @param i Item to be added.
	 */
	public void addDoor(String d, Room n) {
		doors.add(new Door(d,n));
	}
	
	/**
	 * Adds a door to the list. 
	 * @param i Item to be added.
	 */
	public void addDoor(String d, Room n, String k) {
		doors.add(new Door(d,n,k));
	}
	
	
	/**
	 * Removes a door from the list.
	 * @param i Item to be removed.
	 */
	public void removeDoor(Door d) {
		doors.remove(d);
	}
	
	/**
	 * List size getter.
	 * @return The size of item list.
	 */
	public int getDoorListSize() {
		return doors.size();
	}
	
    /**
    * @return D
    */
    public String getDoorsString() {
    	String returnString = "";
    	if(doors.size() == 0) return null;
    	for(Door d : doors)
    		returnString += " "+d.getDirection();
    	return returnString;
    }
    
    public Room getNeighbor(String direction) {
    	for(Door d : doors)
    		if(direction.equals(d.getDirection()))
    			return d.getNeighbor();
    	return null;
    }
	
    public Door getDoor(String direction) {
    	for(Door d : doors) 
    		if(d.getDirection().equals(direction))
    			return d;
    	return null;
    }
}
