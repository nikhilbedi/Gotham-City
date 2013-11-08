   import java.awt.event.*;
   import javax.swing.*;
   import java.awt.*;
   import java.awt.Color;
   import java.awt.event.*;
   import java.awt.image.*;
   import java.util.*;
    public class Door extends Object
   {	 int xCord, yCord, wCord, hCord,zCord;
       public Door(int x, int y, int z)
      {
         super(x, y);
         wCord = 16;
         hCord = 16;
         zCord = z;
      }
       public Rectangle getBounds()
      {
         Rectangle r1 = new Rectangle(xCord, yCord, wCord, hCord);
         return r1;
      }
   }