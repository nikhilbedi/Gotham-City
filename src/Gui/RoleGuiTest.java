package Gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class RoleGuiTest extends RoleGui{

        ///private CustomerAgent agent = null;

        private int xPos, yPos;
        private int xDestination, yDestination;
        
        
        private enum Command {noCommand, commandA, commandB, commandC};
        
        private Command command = Command.noCommand;
        
        public static final int customerSize = 20;
        
        
        Color myColor;
        
        //Here are a few coordinate locations that this Gui "knows" about.
        public static final int xCash = 400;
        public static final int yCash = 0;
        public static int waitX = 50;
        public static int waitY = 16;
        
        public RoleGuiTest(){
                //agent = c;
                xPos = 10;
                yPos = 10;
                xDestination = 10;
                yDestination = 10;
                command = Command.commandA;
                myColor = Color.gray;
                //this.gui = gui;
        }
        
        public RoleGuiTest(String s){
                xPos = 10;
                yPos = 10;
                xDestination = 10;
                yDestination = 10;
                command = Command.commandA;
                if(s.equalsIgnoreCase("red")){
                        myColor = Color.red;
                }
                else{
                        myColor = Color.gray;
                }
        }
        
        
        public void updatePosition(){
                super.updatePosition();
                if (xPos == xDestination && yPos == yDestination) {
                        if(command == Command.noCommand){
                        return;
                        }
                        if(command == Command.commandA){
                                goToB();
                                return;
                        }
                        if(command == Command.commandB){
                                goToA();
                                return;
                        }
                        if (command==Command.commandC) {//this is just an example of how special operations can be done via a gui finishing movement
                                //isHungry = false;
                                //gui.setCustomerEnabled(agent);
                        }
                        //this would usually be called here but is not for testing purposes
                        //agent.doneMoving  This releases the semaphore in the person class
                        //command = Command.noCommand;
                }
    }
        
        public void goToA(){
                xDestination = 100;
                yDestination = 100;
                command = Command.commandA;
        }
        public void goToB(){
                xDestination = 200;
                yDestination = 500;
                command = Command.commandB;
        }
}

