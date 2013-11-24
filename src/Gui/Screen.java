package Gui;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;

import javax.swing.*;
<<<<<<< HEAD

=======
>>>>>>> master
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;

public class Screen
{
<<<<<<< HEAD
        List<RoleGui> guis = Collections.synchronizedList(new ArrayList<RoleGui>());
=======
        List<RoleGui> guis = Collections.synchronizedList( new ArrayList<RoleGui>() );
>>>>>>> master

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
<<<<<<< HEAD
        	synchronized(guis) {
                guis.add(g1);
=======
        	synchronized(guis){
        	guis.add(g1);
>>>>>>> master
        	}
        }
        
        public void removeGui(RoleGui g1){
<<<<<<< HEAD
        	synchronized(guis) {
                guis.remove(g1);
        	}
=======
        	synchronized(guis){
            	guis.remove(g1);
            	}
>>>>>>> master
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
                        g.drawRect(400, 700, 25, 25);
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



        public String checkSwap(int x, int y) {
                if(temp == 1){
                        if((x>200)&&(x<250)&&(y>100)&&(y<150)){
                                return "Restaurant";
                        }
                        if((x>400)&&(x<450)&&(y>100)&&(y<150)){
                                return "Bank";
                        }
                        if((x>600)&&(x<650)&&(y>100)&&(y<150)){
                                return "Market";
                        }
                        if((x>400)&&(x<450)&&(y>700)&&(y<750)){
                                return "Home";
                        }
                }
                else{
                        if((x>25)&&(x<45)&&(y>50)&&(y<70)){
                                return "City";
                        }
                }
                return "na";
        }
        
        public String printGuiList(){
        	return guis.toString();
        }
}