package simcity;

public class Location {
	private String address;
	private int xCoordinate;
	private int yCoordinate;
	
	/**
	 * Identifies a location for our Building.java
	 * @param x The x-position
	 * @param y The y-position
	 * @param s The textual address for this x-y coordinate
	 */
	public Location(int x, int y, String s) {
		xCoordinate = x;
		yCoordinate = y;
		address = s;
	}
	
	/**
	 * Identifies a location for our Building.java, without identifying the textual address
	 * @param x The x-position
	 * @param y The y-position
	 */
	public Location(int x, int y) {
		xCoordinate = x;
		yCoordinate = y;
		address = "No Address";
	}
	
	public int getX() {
		return xCoordinate;
	}
	
	public int getY() {
		return yCoordinate;
	}
	
	public String getAddress() {
		return address;
	}
}