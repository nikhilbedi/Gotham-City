   import java.awt.event.*;
   import javax.swing.*;
   import java.awt.*;
   import java.awt.Color;
   import java.awt.event.*;
   import java.awt.image.*;
   import java.util.*;
   public class LegendPanel extends JPanel implements KeyListener
   {
      Player player;
      Map m;
      boolean start;
      boolean always = true;
      ArrayList<Obstacle> obst = new ArrayList<Obstacle>();
      ArrayList<Door> door;
   	
      public LegendPanel(){
         m = new Map(0, 0, 0);
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
            m.generate(m.getY(), m.getX(), m.getZ());
            player.x = 400;
         }
         else if(player.getX() >400)
         {
            m.xCord++;
            m.generate(m.getY(), m.getX(), m.getZ());
            player.x = 0;
         }
         else if(player.getY() <0)
         {
            m.yCord--;
            m.generate(m.getY(), m.getX(), m.getZ());
            player.y = 400;
         }
         else if(player.getY() > 400)
         {
            m.yCord++;
            m.generate(m.getY(), m.getX(), m.getZ());
            player.y = 0;
         }
         else if(player.getDoor())
         {
            //m.generate(temp.xCord, temp.yCord, temp.zCord);
         
         }
      }   
      public void paintComponent(Graphics g){
         m.paintBackground(g);
         m.paintObstacles(g);
      	
         /*g.setColor(Color.red);
         g.fillRect(player.getX(), player.getY(), player.length, player.height);
         g.setColor(Color.blue);
         g.drawRect(player.getX(), player.getY(), player.length, player.height);//created in player*/
         
         player.paintSprite(g, player.getSprite());
         
      	
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