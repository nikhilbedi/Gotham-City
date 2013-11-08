   import java.awt.event.*;
   import javax.swing.*;
   import java.awt.*;
   import java.awt.Color;
   import java.awt.event.*;
   import java.awt.image.*;
   import java.util.*;  
	
    public class NPC extends Object
   {
      boolean talkable;
      String name;
      String message;
       public NPC(String s, int x, int y)
      {
         super(x,y);
         name = s;
         message = "oh hello there tester";
      }
   }