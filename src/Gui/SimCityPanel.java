package Gui;
   import java.awt.event.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
   
   
   public class SimCityPanel extends JPanel implements MouseListener
   {
      Player player;//this will soon be replaced by AgentGuis
      ScreenFactory loader;
      Screen currentScreen;
      boolean start;
      boolean always = true;
      ArrayList<Obstacle> obst = new ArrayList<Obstacle>();
           
      public SimCityPanel(){
         loader = new ScreenFactory();
         currentScreen = loader.getCity();
         addMouseListener(this);
         setFocusable(true);  
      }
      

      public void paintComponent(Graphics g){//Here is where everything in the animation panel is generated
         loader.updateAllPositions();
         //currentScreen.updateAgents();
    	 currentScreen.paintBackground(g);
         currentScreen.paintObstacles(g);
         currentScreen.paintAgents(g);

               
               //update position of all screens?
         //player.paintSprite(g, player.getSprite());
         
      }
      
      
      public void go(){//intializes game always happens
         while(always)
         {
            try{
               repaint();
               Thread.sleep(10);
            }
               catch(Exception e) {
                  e.printStackTrace();
            }
         
         }
      }
      

      
    public void checkMapChange(int x, int y){

            //Have map check coords
            String swap = currentScreen.checkSwap(x,y);
            //System.out.println("swap is " + swap);
            Screen swapScreen = loader.getScreen(swap);
            if(!(swapScreen == null)){
                    currentScreen = swapScreen;
                    currentScreen.generate();
            }
    }
      
      
        @Override
        public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub
                int x = e.getX();
                int y = e.getY();
                System.out.println("Coords " + e.getX() + ", " + e.getY() );
                checkMapChange(e.getX(), e.getY());
        }

    public void mousePressed(MouseEvent e){
            
    }
    
    
        @Override        
        public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub
                
        }
        @Override
        public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub
                
        }
        @Override
        public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub
                
        }
   }