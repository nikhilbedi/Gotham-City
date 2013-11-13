package Gui;
   import java.awt.event.*;
   import javax.swing.*;
   import java.awt.*;
   import java.awt.Color;
   import java.awt.event.*;
   import java.awt.image.*;
   import java.util.*;
    public class Door extends Object
   {	 int xCord, yCord, width, height;
       public Door(int x, int y)
      {
         super(x, y);
         width = 16;
         height = 16;
      }
       public Rectangle getBounds()
      {
         Rectangle r1 = new Rectangle(xCord, yCord, width, height);
         return r1;
      }
   }