package simcity.Home.gui;

import java.awt.Color;
import java.awt.Graphics2D;

import Gui.RoleGui;
import simcity.Home.interfaces.Resident;


public class ResidentGui extends RoleGui implements Gui {
        private int xPos;
        private int yPos;
        private int xDestination;
        private int yDestination;
        private Resident resident;
        private Command command;
        private enum Command {none, atRefridgerator, gettingItems, left};
        
        public ResidentGui(Resident r){
                resident = r;
                xPos = 110;
                yPos = 350;
                xDestination = 110;
                yDestination = 300;
        }
        

        @Override
        public void updatePosition() {
                 if (xPos < xDestination)
                    xPos++;
                else if (xPos > xDestination)
                    xPos--;

                if (yPos < yDestination)
                    yPos++;
                else if (yPos > yDestination)
                    yPos--;
                if (xPos == 110 && yPos == 183 && command == Command.atRefridgerator){
                        command = Command.none;
                        //resident.AtCashier();
                }
                if (xPos == 290 && yPos == 183 && command == Command.gettingItems){
                        command = Command.none;
                        //resident.ArrivedToGetItem();
                }
        }

        
        
        @Override
        public void draw(Graphics2D g) {
                g.setColor(Color.BLUE);
        g.fillRect(xPos, yPos, 20, 20);
                
        }

        @Override
        public boolean isPresent() {
                return true;
        }
        
        
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
}
