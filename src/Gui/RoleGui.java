package Gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.ImageIcon;

import agent.Role;

public class RoleGui {

	protected int xPos, yPos;
	protected int xDestination, yDestination;

	protected static int speed = 1;


	private int repeat, repeatBuffer = 10;
	boolean active;


	protected enum Orientation{North, South, East, West};
	protected Orientation o;

	ImageIcon current;

	ImageIcon south1 = new ImageIcon(this.getClass().getResource("/resources/Sprites/batman_South_2.png"));
	ImageIcon south2 = new ImageIcon(this.getClass().getResource("/resources/Sprites/batman_South_3.png"));
	ImageIcon west1 = new ImageIcon(this.getClass().getResource("/resources/Sprites/batman_West_1.png"));
	ImageIcon west2 = new ImageIcon(this.getClass().getResource("/resources/Sprites/batman_West_3.png"));
	ImageIcon east1 = new ImageIcon(this.getClass().getResource("/resources/Sprites/batman_East_1.png"));
	ImageIcon east2 = new ImageIcon(this.getClass().getResource("/resources/Sprites/batman_East_3.png"));
	ImageIcon north1 = new ImageIcon(this.getClass().getResource("/resources/Sprites/batman_North_2.png"));
	ImageIcon north2 = new ImageIcon(this.getClass().getResource("/resources/Sprites/batman_North_3.png"));

	public static final int guiSize = 20;

	protected Color myColor;

	protected Screen homeScreen;


	public RoleGui(){
		//agent = c;
		//System.err.println("Here we are 1");    
		xPos = 10;
		yPos = 10;
		xDestination = 10;
		yDestination = 10;
		myColor = Color.gray;
		current = south1;
		active = true;
		//this.gui = gui;
	}

	public RoleGui(Role r, Screen meScreen) {
		// TODO Auto-generated constructor stub
		current = south1;
		homeScreen = meScreen;
	}


	public void updatePosition(){
		
		Orientation temp = o;
		if (xPos < xDestination){
			xPos+=speed;
			o = Orientation.East;
		}
		else if (xPos > xDestination){
			xPos-=speed;
			o = Orientation.West;
		}
		if (yPos < yDestination){
			yPos+=speed;
			o = Orientation.South;
		}

		else if (yPos > yDestination){
			yPos-=speed;
			o = Orientation.North;
		}
		if(!(temp == o)){
			repeat = 0;
		}
		updateImage();
	}

	public void updateImage(){	      
		if(o == Orientation.North)
		{
			if(repeat > repeatBuffer){
				if(current == north1){
					current = north2;
				}
				else{
					current = north1;
				}
				repeat = 0;
			}
			else{
				repeat++;
			}
		}
		else  if(o == Orientation.South)
		{
			if(repeat > repeatBuffer){
				if(current == south1){
					current = south2;
				}
				else{
					current = south1;
				}
				repeat = 0;
			}
			else{
				repeat++;
			}
		}
		else  if(o == Orientation.East)
		{
			if(repeat > repeatBuffer){
				if(current == east1){
					current = east2;
				}
				else{
					current = east1;
				}
				repeat = 0;
			}
			else{
				repeat++;
			}
		}
		else  if(o == Orientation.West)
		{
			if(repeat > repeatBuffer){
				if(current == west1){
					current = west2;
				}
				else{
					current = west1;
				}
				repeat = 0;
			}
			else{
				repeat++;
			}
		}
		else
		{
			//System.out.println("Problemo");
		}

	}

	public void draw(Graphics g){
		g.setColor(myColor);
		g.fillRect(xPos, yPos, guiSize, guiSize);
		g.drawImage(current.getImage(), xPos, yPos, null);
	}

	public void doLeaveBuilding(){
	}
	public void setColor(Color c){
		myColor = c;
	}
	public Screen getHomeScreen() {
		//System.out.println("Here is home Screen: " + homeScreen);
		if(homeScreen == null){
			System.out.println("Sup bro");
		}
		return homeScreen;
	}
	public void setHomeScreen(Screen s){
		homeScreen = s;
	}
}
