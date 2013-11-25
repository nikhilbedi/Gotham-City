package Gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.ImageIcon;

import agent.Role;

public class RoleGui {

	protected int xPos, yPos;
	protected int xDestination, yDestination;
	private static int speed = 2;

	private int repeat;


	private enum Orientation{North, South, East, West};
	protected Orientation o;

	ImageIcon current;

	ImageIcon south1 = new ImageIcon(this.getClass().getResource("/resources/Sprites/bunny_0.png"));
	ImageIcon south2 = new ImageIcon(this.getClass().getResource("/resources/Sprites/bunny_1.png"));
	ImageIcon west1 = new ImageIcon(this.getClass().getResource("/resources/Sprites/bunny_8.png"));
	ImageIcon west2 = new ImageIcon(this.getClass().getResource("/resources/Sprites/bunny_9.png"));
	ImageIcon east1 = new ImageIcon(this.getClass().getResource("/resources/Sprites/bunny_6.png"));
	ImageIcon east2 = new ImageIcon(this.getClass().getResource("/resources/Sprites/bunny_7.png"));
	ImageIcon north1 = new ImageIcon(this.getClass().getResource("/resources/Sprites/bunny_4.png"));
	ImageIcon north2 = new ImageIcon(this.getClass().getResource("/resources/Sprites/bunny_5.png"));

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
		//this.gui = gui;
	}


	public RoleGui(Role r, Screen meScreen) {
		// TODO Auto-generated constructor stub
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

	private void updateImage(){	      
		if(o == Orientation.North)
		{
			if(repeat >5){
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
			if(repeat >5){
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
			if(repeat >5){
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
			if(repeat >5){
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
		System.out.println("Here is home Screen: " + homeScreen);
		return homeScreen;
	}
	public void setHomeScreen(Screen s){
		homeScreen = s;
	}
}
