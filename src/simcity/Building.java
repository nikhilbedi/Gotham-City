package simcity;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.ImageIcon;

import agent.Role;

/**
 * The base class that every building will extend. 
 * The gui and instantiation system will function more smoothly with this class 
 * @author nikhil
 *
 */
public class Building {
	private String name;
	private String imagePath = "";
	private Location entranceLocation;
	private Location guiLocation;
	private Location parkingLocation;
	protected int weekdayOpen;
	protected int weekdayClose;
	protected int weekendOpen;
	protected int weekendClose;
	public ImageIcon icon;
	protected Map<String, Role> jobRoles = Collections.synchronizedMap(new HashMap<String, Role>());
	
	public ImageIcon getIcon() {
		return icon;
	}

	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}

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
		
		//This is a test for the GUI remove if you want.
	/*	jobRoles.put("Test1", new Role());
		jobRoles.put("Test2", new Role());
		jobRoles.put("Test3", new Role());*/
		
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
	public void setParkingLocation(int x, int y){
		parkingLocation = new Location(x, y);
	}
	
	
	public Location getEntranceLocation() {
		return entranceLocation;
	}
	
	public Location getGuiLocation() {
		return guiLocation;
	}
	
	public Location getParkingLocation(){
		return parkingLocation;
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
	
	public int getWeekdayOpen() {
		return weekdayOpen;
	}

	public int getWeekdayClose() {
		return weekdayClose;
	}

	public int getWeekendOpen() {
		return weekendOpen;
	}

	public int getWeekendClose() {
		return weekendClose;
	}
	
	public void setWeekdayHours(int open, int close) {
		weekdayOpen = open;
		weekdayClose = close;
	}

	public void setWeekendHours(int open, int close) {
		weekendOpen = open;
		weekendClose = close;
	}

	public Map<String, Role> getJobRoles() {
		return jobRoles;
	}

	public void setJobRoles(Map<String, Role> jobRoles) {
		this.jobRoles = jobRoles;
	}
	
	public Vector getJobCollec(){
		
		Vector<String> jobStringList;
		//synchronize this list
		jobStringList = new Vector<String>();
		synchronized(jobRoles){
			for(String s : jobRoles.keySet()) {
				jobStringList.add(s);
			}
		}
		
		return jobStringList;
	}

	public Role getRoleFromString(String roleString) {
		Role temp = new Role();
		boolean jobExists = false;
		synchronized(jobRoles){
			for(String s : jobRoles.keySet()) {
				if(roleString.equalsIgnoreCase(s)){
					temp = jobRoles.get(s);
					jobExists = true;
				}
			}
		}
		if(jobExists) {
			return temp;
		}
		else {
			System.out.println("Role not found in this building.");
			return null;
		}
	}
	
	public Vector<Item> getStockItems(){
		return null;//menu.returnList();
	}
	
	public Vector<String> getBuildingInfo(){
		Vector<String> info = new Vector<String>();
		info.add("this is super class info");
		info.add("this is some more super class info");
		info.add("this is even more super class info");
		return info;
	}

	public void updateItem(String s, int hashCode) {
		// TODO Auto-generated method stub
		//THIS MUST BE UPDATED BY YOUR BUILDING
	}
	
}