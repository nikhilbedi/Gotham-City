package simcity;

public class Building {
	private String name;
	private String imagePath = "";
	private Location entranceLocation;
	private Location guiLocation;
	
	
	/**
	 * A constructor that sets the location and name for a building
	 * @param type The name for this building
	 * @param x The x-position 
	 * @param y The y-position
	 */
	public Building(String name, int entranceX, int entranceY, int guiX, int guiY) {
		entranceLocation = new Location (entranceX, entranceY);
		guiLocation = new Location(guiX, guiY);
		this.name = name;
	}
	
	/**
	 * A constructor that sets the location, address, and name for a building
	 * @param @param type The name for this building
	 * @param x The x-position 
	 * @param y The y-position
	 * @param address A realistic way to identify a particular building in Simcity
	 */
	public Building(String name, int entranceX, int entranceY, int guiX, int guiY, String address) {
		entranceLocation = new Location (entranceX, entranceY, address);
		guiLocation = new Location(guiX, guiY, address);
		this.name = name;
	}
	
	/**
	 * A constructor that defaults a location (the birth place for people)
	 * @param name The name of this spawnpoint
	 */
	public Building(String name) {
		this.name = name;
		//currently set to the center of the window?
		entranceLocation = new Location (500, 500);
		guiLocation = new Location(500, 500);
	}
	
	/**
	 * PedestrianRole's GUI will always need to know which location he/she is headed to.
	 * @return the location, containing x- and y-coordinates, for this building
	 */
	public Location getEntranceLocation() {
		return entranceLocation;
	}
	
	public Location getGuiLocation() {
		return guiLocation;
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