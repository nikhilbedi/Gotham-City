package simcity.Home.gui;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.text.StyledEditorKit.ForegroundAction;

import Gui.RoleGui;
import simcity.Home.interfaces.Resident;


public class ResidentGui extends RoleGui{
        /*private int xPos;
        private int yPos;
        private int xDestination;
        private int yDestination;*/
        private Resident resident;
        private Command command;
        private enum Command {none, atRefridgerator, gettingItems, left};
        
        public ResidentGui(Resident r){
                myColor = Color.red;
                //System.err.println("Here we are 2");
        		resident = r;
                xPos = 110;
                yPos = 350;
                xDestination = 110;
                yDestination = 300;
        }
        

        //@Override
        public void updatePosition() {
              super.updatePosition();
                if (xPos == 110 && yPos == 183 && command == Command.atRefridgerator){
                        command = Command.none;
                        //resident.AtCashier();
                }
               if (xPos == 290 && yPos == 183 && command == Command.gettingItems){
                        command = Command.none;
                        //resident.ArrivedToGetItem();
               }
        }

        
        
        /*public void draw(Graphics g) {
                g.setColor(Color.BLUE);
        g.fillRect(xPos, yPos, 20, 20);
                
        }*/

        
        
        
        public void DoMoveToCashier(){
                yDestination = 183;
                command = Command.atRefridgerator;
        }
        
        public void DoGetItems(){
                xDestination = 290;
                command = Command.gettingItems;
        }
        
        public void DoLeave(){
                yDestination = 400;
                command = Command.left;
        }

		public void DoClearFood() {
			// TODO Auto-generated method stub
			
		}


		public void DoGoToPlatingArea() {
			// TODO Auto-generated method stub
			
		}


		public void DoGoToStove() {
			// TODO Auto-generated method stub
			xDestination = 800;
			yDestination = 600;
		}


		public void DoGoToBed() {
			// TODO Auto-generated method stub
			xDestination = 700;
			yDestination = 600;
			
		}


		public void DoGoToFridge() {
			// TODO Auto-generated method stub
			xDestination = 800;
			yDestination = 800;
			
		}


		public void DoExitHome() {
			// TODO Auto-generated method stub
			
		}
}
