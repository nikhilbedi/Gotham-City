package Gui;
import java.awt.event.*;

import javax.swing.*;

import simcity.TheCity;

import java.awt.*;
   
   public class SimCityPanel extends JPanel implements MouseListener
   {
      Player player;//this will soon be replaced by AgentGuis
      //ScreenFactory loader;
      Screen currentScreen;
      BuildingInfoPanel buildingInfo;
   
      boolean start;
      boolean always = true;
      
      // Grid Dimensions for transportation
      final int GRIDWIDTH = 40;
      final int GRIDHEIGHT = 40;
      
      // Grid utilizing dimensions for transportation
      Character grid[][] = new Character[GRIDWIDTH][GRIDHEIGHT];
      
      public Character[][] getGrid() {
    	  return grid;
      }
      
      public SimCityPanel(){
    	 setPreferredSize(new Dimension(800, 800));
         currentScreen = ScreenFactory.getMainScreen();
         
         addMouseListener(this);
         setFocusable(true);
         
         
         //Setup Transportation Grid
         
            //Initially set grid to empty ('E') entirely
         for(int x = 0; x < GRIDWIDTH; x++)
        	 for(int y = 0; y < GRIDHEIGHT; y++) {
        		 grid[x][y] = 'E';
        	 }
         
         //Sidewalks ('S')
         for(int x = 4; x < GRIDWIDTH - 4; x++) //Middle Horizontal Sidewalks
    		grid[x][19] = 'S';
         
         for(int x = 4; x < GRIDWIDTH - 4; x++)
         	grid[x][22] = 'S';
         

        for(int y = 5; y < GRIDHEIGHT - 3; y++) //Middle Vertical Sidewalks
        	grid[18][y] = 'S';
        
        for(int y = 5; y < GRIDHEIGHT - 3; y++)
       	 	grid[21][y] = 'S';
        
         //Put in roads ('R')
         for(int x = 4; x < GRIDWIDTH - 4; x++) //Top road
        	 for(int y = 7; y < 9; y++)
        		 grid[x][y] = 'R';
         
         for(int x = 4; x < GRIDWIDTH - 4; x++) //Top Sidewalks
        	 grid[x][6] = 'S';
         
         for(int x = 4; x < GRIDWIDTH - 4; x++)
        	 grid[x][9] = 'S';
         
         for(int x = 4; x < GRIDWIDTH - 4; x++) //Bottom road
        	 for(int y = 33; y < 35; y++)
        		 grid[x][y] = 'R';
         
         for(int x = 4; x < GRIDWIDTH - 4; x++) //Bottom Sidewalks
        		 grid[x][35] = 'S';
         
         for(int x = 4; x < GRIDWIDTH - 4; x++)
    		 grid[x][32] = 'S';
         
         for(int x = 6; x < 8; x++) //Left road
        	 for(int y = 5; y < GRIDHEIGHT - 3; y++)
        		 grid[x][y] = 'R';
         
        for(int y = 5; y < GRIDHEIGHT - 3; y++) //Left Sidewalks
        	grid[5][y] = 'S';
        
        for(int y = 5; y < GRIDHEIGHT - 3; y++)
   		 	grid[8][y] = 'S';
         
         for(int x = 32; x < 34; x++) //Right road
        	 for(int y = 5; y < GRIDHEIGHT - 3; y++)
        		 grid[x][y] = 'R';
         
        for(int y = 5; y < GRIDHEIGHT - 3; y++) //Right Sidewalks
        	grid[34][y] = 'S';
        	 
        for(int y = 5; y < GRIDHEIGHT - 3; y++)
        	grid[31][y] = 'S';
         
         for(int x = 4; x < GRIDWIDTH - 4; x++) //Middle roads
        	 for(int y = 20; y < 22; y++)
        		 grid[x][y] = 'R';
         
         for(int x = 19; x < 21; x++)
        	 for(int y = 5; y < GRIDHEIGHT - 3; y++)
        		 grid[x][y] = 'R';
         
         //Setup crosswalks for intersections
         setupIntersectionFromPosition(5, 6);  //Top-Left Intersection
         setupIntersectionFromPosition(18, 6); //Top-Middle Intersection
         setupIntersectionFromPosition(31, 6); //Top-Right Intersection
         setupIntersectionFromPosition(5, 19); //Mid-Left Intersection
         setupIntersectionFromPosition(18, 19);//Middle Intersection
         setupIntersectionFromPosition(31, 19);//Mid-Right Intersection
         setupIntersectionFromPosition(5, 32); //Bot-Left Intersection
         setupIntersectionFromPosition(18, 32);//Bottom Intersection
         setupIntersectionFromPosition(31, 32);//Bot-Right Intersection
         
         for(int x = 0; x < GRIDWIDTH; x++) { //Test prints for layout
        	 for(int y = 0; y < GRIDHEIGHT; y++)
        		 System.out.print(grid[x][y]);
        	 System.out.println();
         }
         
         
         ScreenFactory.getMainScreen().setGrid(grid);
      }
      

      private void setupIntersectionFromPosition(int i, int j) {
    	  grid[i+1][j+3] = 'I';
          grid[i+2][j+3] = 'I';
          grid[i][j+1] = 'I';
          grid[i][j+2] = 'I';
          grid[i+1][j] = 'I';
          grid[i+2][j] = 'I';
          grid[i+3][j+1] = 'I';
          grid[i+3][j+2] = 'I';
		
	}

	public void paintComponent(Graphics g){//Here is where everything in the animation panel is generated
         ScreenFactory.updateScreens();
         //currentScreen.updateAgents();
    	 currentScreen.paintBackground(g);
         //currentScreen.paintObstacles(g);
         currentScreen.paintAgents(g);         
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
  		//swap = "Restaurant";
  		Screen swapScreen = ScreenFactory.getMeScreen(swap);
  		if(!(swapScreen == null)){
  			currentScreen = swapScreen;
  			selPane.refresh();
  			if(swap.equalsIgnoreCase("city")){
  				buildingInfo.setVisible(false);
  			}
  			else{
  				buildingInfo.setVisible(true);
  				buildingInfo.update(TheCity.getBuildingFromString(swap).getBuildingInfo());
  				buildingInfo.getUpdators(TheCity.getBuildingFromString(swap));
  			}
  		} 
  	}
      
      public BuildingInfoPanel getBuildingInfo() {
  		return buildingInfo;
  	}


  	public void setBuildingInfo(BuildingInfoPanel buildingInfo) {
  		this.buildingInfo = buildingInfo;
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
        
        public MainScreen getCityScreen(){
        	return ScreenFactory.getMainScreen();
        }
        
        public PersonSelectionPane getSelPane() {
    		return selPane;
    	}


    	public void setSelPane(PersonSelectionPane selPane) {
    		this.selPane = selPane;
    	}

    	PersonSelectionPane selPane;
    
    
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

