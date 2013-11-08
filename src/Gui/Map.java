
   import java.awt.event.*;
   import javax.swing.*;
   import java.awt.*;
   import java.awt.Color;
   import java.awt.event.*;
   import java.awt.image.*;
   import java.util.*;
    public class Map
   {
      ArrayList<Obstacle> obst = new ArrayList<Obstacle>();
      ArrayList<Rectangle> cords = new ArrayList<Rectangle>();
      ArrayList<Door> door = new ArrayList<Door>();
      String loc = " ";
      private int pos[][];
      int xCord, yCord, zCord;
       public Map(int y, int x, int z)
      {
         generate(y, x, z);
         zCord = z;
         xCord = x;
         yCord = y;
      }
       public int getX()
      { 
         return xCord; 
      }
       public int getY()
      { 
         return yCord;
      }
       public int getZ()
      {
         return zCord;
      }
       public void changeZ(int next)
      {
         zCord = next;
      }
       public ArrayList<Obstacle> getObs()
      {
         return obst;
      }
       public ArrayList<Door> getDoors()
      {
         return door;
      }
       public void generate(int y, int x, int z)
      {
         obst.clear();
         door.clear();
         if( z == 0)
         {
            if(y==0)
            {
               if(x==0)
               {
                  obst.add(new Obstacle(0,0, 400, 20));
                  obst.add(new Obstacle(0,0, 20, 400));
                  obst.add(new Obstacle(180, 180, 10, 50));
                  obst.add(new Obstacle(180, 180, 50, 10));
                  door.add(new Door(20,20, 1));
               }
               if(x==1)
               {
                  obst.add(new Obstacle(0,0, 400, 20));
               
               }
               if(x==2)
               {
                  obst.add(new Obstacle(0,0, 400, 20));
               }
               if(x==3)
               {
                  obst.add(new Obstacle(0,0, 400, 20));
                  obst.add(new Obstacle(375,0,10, 400));
               }	
            }
            if(y ==1)
            {
               if(x==0)
               {
                  obst.add(new Obstacle(0,0, 20, 400));
               }
               if(x==1)
               {
                  obst.add(new Obstacle(190, 0, 20, 185));
                  obst.add(new Obstacle(190, 215, 20, 185));
               }
               if(x==2)
               {
                  obst.add(new Obstacle(190, 0, 20, 185));
                  obst.add(new Obstacle(190, 215, 20, 185));
               }
               if(x==3)
               {
                  obst.add(new Obstacle(375,0,10, 400));
               }
            }
            if(y==2)
            {
               if(x==0)
               {
                  obst.add(new Obstacle(0,0, 20, 400));
                  obst.add(new Obstacle(0, 390, 400, 10));
               }
               if(x==1)
               {
                  obst.add(new Obstacle(0, 390, 400, 10));
               }
               if(x==2)
               {
                  obst.add(new Obstacle(0, 390, 400, 10));
               }
               if(x==3)
               {
                  obst.add(new Obstacle(375,0,10, 400));
                  obst.add(new Obstacle(0, 390, 400, 10));
               }
            }
         }
         if(z==1)
         {
            if(y==0)
            {
               if(x==0)
               {
                  obst.add(new Obstacle(0,0, 400, 10));
                  obst.add(new Obstacle(0,0, 10, 400));
                  obst.add(new Obstacle(380, 0, 20, 400));
                  obst.add(new Obstacle(0, 380, 400, 20));
               }
            }
         }
      }
       public  void paintBackground(Graphics g)
      {
         g.setColor(Color.black);
         g.fillRect(0,0,400,450);
         g.setColor(Color.white);
         g.fillRect(0,0,400, 400);
         g.setColor(Color.black);
      }
      
       public  void paintObstacles(Graphics g2)
      {
         for (int i = 0; i < obst.size(); i++) {
            g2.setColor(Color.black);
            Obstacle o = obst.get(i);
            g2.fillRect(o.getX(),o.getY(),o.getWidth(),o.getHeight());
         }
         for(int a = 0; a< door.size(); a++) {
            g2.setColor(Color.red);
            Door d = door.get(a);
            g2.fillRect(d.getX(), d.getY(), d.getWidth(), d.getHeight());
         }
      }
   }