package Gui;
   import java.awt.event.*;
   import javax.swing.*;
   import java.awt.*;
   import java.awt.Color;
   import java.awt.event.*;
   import java.awt.image.*;
   import java.util.*;
   public class SimCityPanel extends JPanel implements KeyListener
   {
      Player player;//this will soon be replaced by AgentGuis
      Map m;
      boolean start;
      boolean always = true;
      ArrayList<Obstacle> obst = new ArrayList<Obstacle>();
      ArrayList<Door> door;
   	
      public SimCityPanel(){
         m = new Map(0, 0);
         player = new Player();
         obst = m.getObs();
         door = m.getDoors();
         addKeyListener(this);
         setFocusable(true);  
      }
      public void update(){
         player.check(obst);
         player.checkDoor(door); 
         player.move();
      
         
         if(player.getX() <0)
         {
            m.xCord--;
            m.generate(m.getY(), m.getX());
            player.x = 400;
         }
         else if(player.getX() >400)
         {
            m.xCord++;
            m.generate(m.getY(), m.getX());
            player.x = 0;
         }
         else if(player.getY() <0)
         {
            m.yCord--;
            m.generate(m.getY(), m.getX());
            player.y = 400;
         }
         else if(player.getY() > 400)
         {
            m.yCord++;
            m.generate(m.getY(), m.getX());
            player.y = 0;
         }
         else if(player.getDoor())
         {
            //m.generate(temp.xCord, temp.yCord, temp.zCord);
         
         }
      }   
      public void paintComponent(Graphics g){//Here is where everything in the animation panel is generated
         m.paintBackground(g);
         m.paintObstacles(g);
      	 //m.paintAgents(g) TODO write this jones
         
         player.paintSprite(g, player.getSprite());
         
      	
         
         
         /**
          * This is all debug messages that probably will be deleted soon
          */
         g.setColor(Color.red);//checking conditions
         g.drawString("I: " + m.getY() + " A: " + m.getX(), 25, 25);
         g.setColor(Color.white);
         g.drawString("X: " + player.getX() + "  Y: " + player.getY(), 50, 410);
         g.setColor(Color.white);
         g.drawString("X: " + player.getX() + "  Y: " + player.getY(), 50, 410);
         if(!player.rChange)
         { g.drawString("right collided!", 200, 410);}
         if(!player.lChange)
         { g.drawString("left collided!", 200, 410);}
         if(!player.uChange)
         { g.drawString("up collided!", 200, 410);}
         if(!player.dChange)
         { g.drawString("down collided!", 200, 410);}
         if(player.getDoor())
            g.drawString("door", 300, 410);
         if(player.getDoor()==false)
            g.drawString(""+door.toString(), 300, 410);
      }
      public void go(){//intializes game always happens
         while(always)
         {
            try{
               update();
               repaint();
               Thread.sleep(25);
            }
               catch(Exception e) {
                  e.printStackTrace();
            }
         
         }
      }
   
      public void keyPressed(KeyEvent e)//allows movement
      {
         int key = e.getKeyCode();
         if (key == KeyEvent.VK_LEFT)//&&player.lChange)
            player.left = true;
         if (key == KeyEvent.VK_RIGHT)//&&player.rChange)
            player.right = true;
         if(key == KeyEvent.VK_UP)//&&player.uChange)
            player.up = true;
         if(key == KeyEvent.VK_DOWN)//&&player.dChange)
            player.down = true;
      }
      public void keyTyped(KeyEvent e)
      {
      }
   
      public void keyReleased(KeyEvent e)
      {
         int key = e.getKeyCode();
         if (key == KeyEvent.VK_LEFT)
            player.left = false;
         if (key == KeyEvent.VK_RIGHT)
            player.right = false;
         if(key == KeyEvent.VK_UP)
            player.up = false;
         if(key == KeyEvent.VK_DOWN)
            player.down = false;
      }
   }