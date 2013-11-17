package Gui;
   import java.awt.event.*;
   import javax.swing.*;
   import java.awt.*;
   import java.awt.Color;
   import java.awt.event.*;
   import java.awt.image.*;
   import java.util.*;
 
   public class Screen
   {
      ArrayList<Obstacle> obst = new ArrayList<Obstacle>();
      ArrayList<Rectangle> cords = new ArrayList<Rectangle>();
      
      ArrayList<RoleGuiTest> guis = new ArrayList<RoleGuiTest>();
      
      //Map<String, Screen> convert;
      
      String loc = "";
      int xCord, yCord;
      int temp;
      
      public Screen()
      {
    	 temp = 0;
    	 RoleGuiTest gui1, gui2;
    	 gui1 = new RoleGuiTest("Red");
    	 gui2 = new RoleGuiTest("notRed");
    	 guis.add(gui1);
    	 //guis.add(gui2);
    	// gui1.startThread();
    	// gui2.startThread();
         generate();
      }
       public Screen(int t)
      {
    	 temp = t;
    	 RoleGuiTest gui1, gui2;
    	 gui1 = new RoleGuiTest("red");
    	 gui2 = new RoleGuiTest("notred");
    	 guis.add(gui1);
    	 //guis.add(gui2);
         generate();
      }

       public ArrayList<Obstacle> getObs()
      {
         return obst;
      }
       public void generate()
      {
         obst.clear();
         
         if(temp==1){
        	 obst.add(new Obstacle(25,25,20,20));
        	 obst.add(new Obstacle(400,150, 50, 300));//draws a 1
         }
         else if(temp==2){
        	 obst.add(new Obstacle(25,25,20,20));
        	 obst.add(new Obstacle(200, 100, 100, 20));//draws a 2
             obst.add(new Obstacle(300,120, 20, 100));
             obst.add(new Obstacle(200, 220, 100, 20));
             obst.add(new Obstacle(200, 240, 20, 100));
             obst.add(new Obstacle(200, 340, 100, 20));
        	 
         }
         else if(temp==3){
        	 obst.add(new Obstacle(0,0, 400, 20));
             obst.add(new Obstacle(0,0, 20, 400));
             obst.add(new Obstacle(180, 180, 10, 50));
             obst.add(new Obstacle(180, 180, 50, 10));
         }
         else if(temp==4){
        	 obst.add(new Obstacle(0,0, 400, 20));
             obst.add(new Obstacle(0,0, 20, 400));
             obst.add(new Obstacle(180, 180, 10, 50));
             obst.add(new Obstacle(180, 180, 50, 10));
         }
         else if(temp==5){
        	 
         }

         }
       
       public void paintAgents(Graphics g){
    	   //for Agent a : agents
    	   //gui.drawSelf()

		   for (RoleGuiTest gui : guis) {
			gui.updatePosition();
			gui.draw(g);
		}
    	   
       }
       
       public  void paintBackground(Graphics g)
      {
         g.setColor(Color.black);
         //g.fillRect(0,0,400,450);
         g.setColor(Color.white);
         g.fillRect(0,0,800, 800);
         g.setColor(Color.black);
      }
      
       public  void paintObstacles(Graphics g2)
      {
         for (int i = 0; i < obst.size(); i++) {
            g2.setColor(Color.black);
            Obstacle o = obst.get(i);
            g2.fillRect(o.getX(),o.getY(),o.getWidth(),o.getHeight());
         }
      }
   }