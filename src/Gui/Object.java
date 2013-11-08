
   import java.awt.event.*;
   import javax.swing.*;
   import java.awt.*;
   import java.awt.Color;
   import java.awt.event.*;
   import java.awt.image.*;
   import java.util.*;    
    public class Object
   {	int xcord, ycord, width, height;
       public Object(int x,int y,int w,int h)
      {
         xcord = x;
         ycord = y;
         width = w;
         height = h;
      }
       public Object(int x, int y)
      {
         xcord = x;
         ycord= y;
         width = 16;
         height = 16;
      }
       public int getX()
      { 
         return xcord; 
      }
       public int getY()
      {	
         return ycord;
      }
       public int getWidth()
      {	
         return width;
      }
       public int getHeight()
      {  
         return height; 
      }
       public Rectangle getBounds()
      {
         Rectangle r1 = new Rectangle(xcord, ycord, width, height);
         return r1;
      }
   }
