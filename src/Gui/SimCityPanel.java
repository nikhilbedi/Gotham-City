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
      Screen m;
      boolean start;
      boolean always = true;
      ArrayList<Obstacle> obst = new ArrayList<Obstacle>();
   	
      public SimCityPanel(){
         m = new Screen(1);
         player = new Player();
         obst = m.getObs();
         addMouseListener(this);
         setFocusable(true);  
      }
      
      public void update(){
         player.check(obst);
         player.move();

      }   
      
      public void paintComponent(Graphics g){//Here is where everything in the animation panel is generated
         m.paintBackground(g);
         m.paintObstacles(g);
      	 m.paintAgents(g);
         //player.paintSprite(g, player.getSprite());
         
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
      

      
    public void checkMapChange(int x, int y){
    	
    }
      
      
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int x = e.getX();
		int y = e.getY();
		if((x>25)&&(x<45)&&(y>25)&&(y<45)){
			if(m.temp ==1){
				m.temp = 2;
				m.generate();
			}
			else{
			m.temp = 1;
			m.generate();
			}
		}
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