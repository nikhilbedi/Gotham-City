   import java.awt.event.*;
   import javax.swing.*;
   import java.awt.*;
   import java.awt.Color;
   import java.awt.event.*;
   import java.awt.image.*;
   import java.util.*;
    public class Player
   {
      int x,y,length,height;
      boolean left, right, up, down;
      boolean lChange, rChange, uChange, dChange;
      private int repeat = 0; 
      private boolean swap = false;
      boolean doorness;
      ImageIcon current;
      
      
      java.net.URL south1 = this.getClass().getResource("/resources/walks1.png");
      java.net.URL south2 = this.getClass().getResource("/resources/walks2.png");
      java.net.URL west1 = this.getClass().getResource("/resources/walkw1.png");
      java.net.URL west2 = this.getClass().getResource("/resources/walkw2.png");
      java.net.URL east1 = this.getClass().getResource("/resources/walke1.png");
      java.net.URL east2 = this.getClass().getResource("/resources/walke2.png");
      java.net.URL north1 = this.getClass().getResource("/resources/walkn1.png");
      java.net.URL north2 = this.getClass().getResource("/resources/walkn2.png");
      
       public Player()
      {
         x=50;
         y=50;
         length=16;
         height=16;
         left = false;
         right = false;
         up = false;
         down = false;
         lChange = true;
         rChange = true;
         uChange = true;
         dChange = true;
         doorness = false;
         current = new ImageIcon(south1);
         
      }
       public int getX()
      {
         return x;
      }
       public int getY()
      {
         return y;
      }
       public int getLength()
      {
         return length;
      }
       public int getHeight()
      {
         return height;
      }
       public Rectangle getBounds()
      {
         Rectangle r1 = new Rectangle(x, y, length, height);
         return r1;
      }
       public void checkUp(ArrayList<Obstacle> o)
      {
         Rectangle r1 = new Rectangle(x+1,y,length-2,1);
         for(int i = 0; i<o.size(); i++)
         {
            Rectangle r2 = o.get(i).getBounds();
            if(r1.intersects(r2))
            {
               uChange = false;
               return;
            }
         }
         uChange = true;
      }
       public void checkBot(ArrayList<Obstacle> o)
      {
         Rectangle r1 = new Rectangle(x+1,y+16,length-2,1);
         for(int i = 0; i<o.size(); i++)
         {
            Rectangle r2 = o.get(i).getBounds();
            if(r1.intersects(r2))
            {
               dChange = false;
               return;
            }
         }
         dChange = true;
      }
       public void checkEast(ArrayList<Obstacle> o)
      {
         Rectangle r1 = new Rectangle(x+15,y+1,1,height-2);
         for(int i = 0; i<o.size(); i++)
         {
            Rectangle r2 = o.get(i).getBounds();
            if(r1.intersects(r2))
            {
               rChange = false;
               return;
            }
         }
         rChange = true;
      }
       public void checkWest(ArrayList<Obstacle> o)
      {
         Rectangle r1 = new Rectangle(x,y+1,1,height-2);
         for(int i = 0; i<o.size(); i++)
         {
            Rectangle r2 = o.get(i).getBounds();
            if(r1.intersects(r2))
            {
               lChange = false;
               return;
            }
         }
         lChange = true;
      }
       public void checkDoor(ArrayList<Door> d)//new
      {
         Rectangle r1 = new Rectangle (this.x, this.y, this.length, this.height);
         for(int i = 0; i<d.size(); i++)
         {
            Rectangle r2 = d.get(i).getBounds();
            if(r1.intersects(r2))
            {
               doorness = true;
            }
         }      
      }
       public void check(ArrayList<Obstacle> o)
      {
         checkWest(o);
         checkEast(o);
         checkUp(o);
         checkBot(o);
      }
   
       public ImageIcon getSprite()
      {
         int u = 0;
         int d = 0;
         int l = 0;
         int r = 0;
      
         if(up)
         {
            if(repeat>5)
            {
               this.current = new ImageIcon(north2);
               repeat++;
               if(repeat>10)
                  repeat = 0;
            }
            else
            {
               this.current = new ImageIcon(north1);
               repeat++;
            }
         
         }
         
         else if(down)
         {
            if(repeat>5)
            {
               this.current = new ImageIcon(south2);
               repeat++;
               if(repeat>10)
                  repeat = 0;
            }
            else
            {
               this.current = new ImageIcon(south1);
            
               repeat++;
            }
         }
         else if(left)
         {
            if(repeat>4)
            {
               this.current = new ImageIcon(west2);
               repeat++;
               if(repeat>10)
                  repeat = 0;
            }
            else
            {
               this.current = new ImageIcon(west1);
               repeat++;
            }
         }
         else if(right)
         {
            if(repeat>4)
            {
               this.current = new ImageIcon(east2);
               repeat++;
               if(repeat>10)
                  repeat = 0;
            }
            else
            {
               this.current = new ImageIcon(east1);
               repeat++;
            }
         }
         else
         {
            //System.out.println("no sprite found");
         }
         return this.current;
      }
       public void paintSprite(Graphics g, ImageIcon i)
      {
         g.drawImage(i.getImage(), x, y, null);
      }
       public int getRepeat()
      {
         return repeat;
      }
       public boolean getDoor()
      {
         return doorness;
      }
   	
       public void move()
      {
         if(right&&rChange)
            x+=1;
         if(left&&lChange)
            x-=1;
         if(up&&uChange)
            y-=1;
         if(down&&dChange)
            y+=1;
      }
   }

