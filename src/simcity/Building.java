package simcity;

public class Building {
	private String name;
	private String imagePath = "";
	private Location location; 
	
	/**
	 * A constructor that sets the location and name for a building
	 * @param type The name for this building
	 * @param x The x-position 
	 * @param y The y-position
	 */
	public Building(String type, int x, int y) {
		location = new Location (x, y);
		name = type;
	}
	
	/**
	 * A constructor that sets the location, address, and name for a building
	 * @param @param type The name for this building
	 * @param x The x-position 
	 * @param y The y-position
	 * @param address A realistic way to identify a particular building in Simcity
	 */
	public Building(String type, int x, int y, String address) {
		location = new Location (x, y, address);
		name = type;
	}
	
	
	/**
	 * PedestrianRole's GUI will always need to know which location he/she is headed to.
	 * @return the location, containing x- and y-coordinates, for this building
	 */
	public Location getLocation() {
		return location;
	}
	
	public String getName() {
		return name;
	}
	
	public String getImagePath() {
		return imagePath;
	}
	
	public void setImagePath(String i) {
		imagePath = i;
	}
}