package Gui;
   import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
 
   public class Screen
   {
      ArrayList<Obstacle> obst = new ArrayList<Obstacle>();
      ArrayList<Rectangle> cords = new ArrayList<Rectangle>(); 
      ArrayList<RoleGui> guis = new ArrayList<RoleGui>();
      
      String loc = "";
      int xCord, yCord;
      int temp;
      
      public Screen()
      {
    	 temp = 0;
    	 generate();
      }
       public Screen(int t)
      {
    	 temp = t;
    	 generate();
      }

       public ArrayList<Obstacle> getObs()
      {
         return obst;
      }
       public void generate()
      {
         obst.clear();
         
         if(temp==1){//Main city pane
        	 obst.add(new Obstacle(200, 100, 50, 50));
        	 obst.add(new Obstacle(400, 100, 50, 50));
        	 obst.add(new Obstacle(600, 100, 50, 50));
        	 obst.add(new Obstacle(400, 700, 50, 50));
         }
         else if(temp==2){//Restaurant
        	 obst.add(new Obstacle(25,50,20,20));
        	 
        	 
         }
         else if(temp==3){//Market
        	 obst.add(new Obstacle(25,50,20,20));
         }
         else if(temp==4){//Bank
        	 obst.add(new Obstacle(25,50,20,20));
         }
         else if(temp==5){//Home
        	 obst.add(new Obstacle(25,50,20,20));
         }

      }
       
       
       
       
       public void addGui(RoleGui g1){
    	   guis.add(g1);
       }
      
       public void updateAgents(){
    	   for (RoleGui gui : guis) {
    		   gui.updatePosition();
    	   }
       }
       
       public void paintAgents(Graphics g){
		   for (RoleGui gui : guis) {
			gui.draw(g);
		}
    	   
       }
       
       public  void paintBackground(Graphics g)
      {
         g.setColor(Color.white);
         g.fillRect(0,0,800, 800);
         g.setColor(Color.black);
         if(temp==1){//Main city pane
        	 g.drawString("Main City", 400, 50);
         }
         else if(temp==2){//Restaurant
        	 g.drawString("Restaurant", 400, 50);
        	 
         }
         else if(temp==3){//Market
        	 g.drawString("Market", 400, 50);
         }
         else if(temp==4){//Bank
        	 g.drawString("Bank", 400, 50);
         }
         else if(temp==5){//Home
        	 g.drawString("Home", 400, 50); 
         }
      }
      
       public  void paintObstacles(Graphics g2)
      {
         for (int i = 0; i < obst.size(); i++) {
            g2.setColor(Color.black);
            Obstacle o = obst.get(i);
            g2.fillRect(o.getX(),o.getY(),o.getWidth(),o.getHeight());
         }
      }
       
       
	public String checkSwap(int x, int y) {
		if(temp == 1){
			if((x>200)&&(x<250)&&(y>100)&&(y<150)){
				return "Restaurant";
			}
			if((x>400)&&(x<450)&&(y>100)&&(y<150)){
				return "Market";
			}
			if((x>600)&&(x<650)&&(y>100)&&(y<150)){
				return "Bank";
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
   }