package Gui;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;

import javax.swing.*;

import java.awt.event.*;
import java.awt.image.*;
import java.util.*;

public class Screen
{

	List<RoleGui> guis = Collections.synchronizedList( new ArrayList<RoleGui>() );

	String loc = "";
	int xCord, yCord;
	public int temp;

	public Screen()
	{
		temp = 0;
	}
	public Screen(int t)
	{
		temp = t;
	}

	public void addGui(RoleGui g1){
		synchronized(guis){
			guis.add(g1);
		}
	}

	public void removeGui(RoleGui g1){
		synchronized(guis){
			guis.remove(g1);
		}
	}

	public void updateAgents(){
		synchronized(guis) {
			for (RoleGui gui : guis) {
				gui.updatePosition();
			}
		}
	}

	public void paintAgents(Graphics g){
		synchronized(guis) {
			for (RoleGui gui : guis) {
				gui.draw(g);
			}
		}
	}

	public void paintObstacles(Graphics g){
		System.err.println("You shouldn't use this method anymore. Call Hunter and I'll explain it to you.");
	}

	public  void paintBackground(Graphics g)
	{
		g.setColor(Color.white);
		g.fillRect(0,0,1000, 800);
		g.setColor(Color.black);
		if(temp==1){//Main city pane
			g.drawString("Main City", 400, 50);
		}
		else if(temp==2){//Restaurant
			g.drawString("Restaurant", 400, 50);
			g.drawRect(25,50,20,20);

		}
		else if(temp==3){//Market
			g.drawString("Market", 400, 50);
			g.drawRect(25,50,20,20);
		}
		else if(temp==4){//Bank
			g.drawString("Bank", 400, 50);
			g.drawRect(25,50,20,20);
		}
		else if(temp==5){//Home
			g.drawString("Home", 400, 50); 
			g.drawRect(25,50,20,20);
		}
		else{
			g.fillRect(25,50,20,20);
		}
	}



	public String checkSwap(int x, int y){
		if((x>25)&&(x<45)&&(y>50)&&(y<70)){
			return "City";
		}

		return "na";
	}

	public String printGuiList(){
		return guis.toString();
	}
}
