package Gui;
   import javax.swing.*;

import simcity.Home.gui.HomePanel;

    public class SimCityRun
   {
      JFrame window;
      SimCityPanel cityPanel;
      HomePanel homePanel;
   
       public SimCityRun()
      {
    	 //This sets up the frame of the animation window
         window = new JFrame("Team 31 Sim City");
         window.setSize(800, 800);
         window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         homePanel = new HomePanel();
         
         
         //This adds the animation of the main area to the frame
         cityPanel = new SimCityPanel();
         window.add(cityPanel);
         
         
         window.setVisible(true);
         cityPanel.go();//starts the animation in the panel
      }
       public static void main(String[] args)
      {
         SimCityRun runCity = new SimCityRun();
      }
   }