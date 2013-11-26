package simcity.restaurants.restaurant4.Restaurant4Gui;




import javax.swing.*;

import simcity.restaurants.restaurant4.interfaces.Restaurant4Customer;
import simcity.restaurants.restaurant4.interfaces.Restaurant4Waiter;
import Gui.RoleGui;

import java.awt.*;

public class Restaurant4WaiterGui extends RoleGui {

    private Restaurant4Waiter agent = null;
    private Restaurant4Customer customer = null;
    public Command command;
    private int table;
    public enum Command {none, atDefult, bringingToTable, goToCustomer,  hereIsCheck, arrivedToGetOrder,pickUpCheck, arrivedToNotifyNoFood, atCook, pickUpOrder, hereIsFood, onBreak, giveMeCheck};
    private int xTable;
    private boolean deliver = false;
    public static final int yTable = 170;
    private String food;
    public Restaurant4WaiterGui(Restaurant4Waiter agent, int i) {
        this.agent = agent;
        if (i==1){
        	yPos = yPos+30;
        	yDestination = yDestination+30;
        	defY = defY+30;
        }
        if (i==2){
        	yPos = yPos+60;
        	yDestination = yDestination+60;
        	defY = defY+60;
        	
        }
        
        if (i==3){
        	yPos = yPos+90;
        	yDestination = yDestination+90;
        	defY = defY+90;
        }
    }
    private final int rect = 20;
    private  int defaultPos = -20;
    private int defX = 20;
    private int defY = 70;
    private final int firstTable = 50;
    public void updatePosition() {
    	//if(agent.getPause()==false){
    	super.updatePosition();

        if (xPos == xDestination && yPos == yDestination & (xDestination == xTable + rect) & (yDestination == yTable - rect) && command == Command.bringingToTable) {
          command = Command.none;
        	agent.msgAtTable();
        }
        if (xPos == xDestination && yPos == yDestination & (xDestination == xTable + rect) & (yDestination == yTable - rect) && command == Command.arrivedToGetOrder) {
            command = Command.none;
        	agent.arrivedAtTable(customer);
         }
        
        if (xPos == xDestination && yPos == yDestination & (xDestination == xTable + rect) & (yDestination == yTable - rect) && command == Command.arrivedToNotifyNoFood){
        	command = Command.none;
        	agent.arrivedToNotifyNoFood(customer, customer.getChoice());
        }
        if (xPos == 100 && yPos == 280 && command == Command.atCook){
        	command = Command.none;
        	agent.arrivedToCook(customer);
        }
        if (xPos == 5 && yPos == 280 && command == Command.pickUpOrder){
        	command = Command.none;
        	agent.GotFood(food);
        	DeliverFood(table);
        }
        if (xPos == defaultPos && yPos == defaultPos && command == Command.onBreak){
        	command = Command.none;
        	agent.startBreak();
        }
        if (xPos == xDestination && yPos == yDestination & (xDestination == xTable + rect) & (yDestination == yTable - rect) && command == Command.hereIsFood) {
            command = Command.none;
            deliver = false;
        	agent.HereIsYourFood(table);
         }
        if (xPos == 220 && yPos == 50 && command == Command.giveMeCheck){
        	command = Command.none;
        	agent.arrivedAtCashier(customer);
        }
        
        if(xPos == 120 && yPos == 55 && command == Command.goToCustomer){
        	command = Command.none;
        	bringToTable();
        }
        
        if (xPos == 220 && yPos == 50 && command == Command.pickUpCheck){
        	command = Command.none;
        	deliverCheck(table);
        }
        if (xPos == xDestination && yPos == yDestination & (xDestination == xTable + rect) & (yDestination == yTable - rect) && command == Command.hereIsCheck) {
            command = Command.none;
        	agent.HereIsYourCheck(table);
         }
        
        if (xPos == defX && yPos == defY && command == Command.atDefult){
        	command = Command.none;
        	agent.atDefaultPosition();
        }
    	//}
    }

    public int getXTable(int table){
    	if (table ==1 ){
    		xTable = firstTable;
    		return xTable;
    	}
    	else if(table==2){
    		xTable = firstTable + 90;
    		return xTable;
    	}
    	else if(table==3){
    		xTable = firstTable +180;
    		return xTable;
    	}
    	else if(table == 4){
    		xTable = firstTable + 270;
    		return xTable;
    	}
    	else return 0;
    }
    
    public void setFood(String f){
    	food = f;
    }
    
    public void draw(Graphics g) {
    	super.draw(g);
        /*g.setColor(Color.MAGENTA);
        g.fillRect(xPos, yPos, rect, rect);*/
        if (deliver==true){
        	drawOrder(g);
        }
    }

    public void drawOrder(Graphics g){
    	g.setColor(Color.BLACK);
    	if (food=="Steak"){
			g.drawString("st", xPos, yPos);
		}
		else if (food=="Pizza"){
			g.drawString("p", xPos, yPos);
		}
		else if (food=="Chicken"){
			g.drawString("ch", xPos, yPos);
		}
		else if (food=="Salad"){
			g.drawString("s", xPos, yPos);
		}
    }
    
    public boolean isPresent() {
        return true;
    }

    public void DoBringToTable(Restaurant4Customer customer, int table) {
    	this.customer = customer;
    	this.table = table;
    	/*xDestination = getXTable(table) + rect;
        yDestination = yTable - rect;*/
    	xDestination = 120;
    	yDestination = 55;
    	
//        customer.getCustomerGui().setTable(getXTable(table), yTable);
//        customer.getCustomerGui().DoGoToSeat();
        command = Command.goToCustomer;
    }
    
    public void bringToTable(){
    	xDestination = getXTable(table) + rect;
    	int t = getXTable(table);
        yDestination = yTable - rect;
        customer.getGui().setTable(getXTable(table), yTable);
      customer.getGui().DoGoToSeat();
      command = Command.bringingToTable;
    }

    public void DoGoToTable(Restaurant4Customer customer, int table){
    	this.table = table;
    	this.customer = customer;
    	 xDestination = getXTable(table)+rect;
    	 yDestination = yTable - rect;
    	 command = Command.arrivedToGetOrder;
    }

    public void DoLeaveCustomer() {
        xDestination = defX;
        yDestination = defY;
        
        command = Command.atDefult;
    }
    
    public void GoToCook(Restaurant4Customer customer){
    	this.customer = customer;
    	xDestination = 100;
    	yDestination = 280;
    	command = Command.atCook;
    }
    
    public void notifyNoFood(Restaurant4Customer customer, int table){
    	this.table = table;
    	this.customer = customer;
    	 xDestination = getXTable(table)+rect;
    	 yDestination = yTable - rect;
    	command = Command.arrivedToNotifyNoFood;
    }
    
    
    public void PickUpOrder(int table){
    	this.table = table;
    	xDestination = 5;
    	yDestination = 280;
    	command = Command.pickUpOrder;
    	
    }
    
    public void DeliverFood(int table){
    	deliver = true;
    	this.table = table;
    	 xDestination = getXTable(table)+rect;
    	 yDestination = yTable - rect;
    	 
    	 command = Command.hereIsFood;
    }
    
    public void DoGoToCashier(Restaurant4Customer customer){
    	this.customer = customer;
    	xDestination = 220;
    	yDestination = 50;
    	command = Command.giveMeCheck;
    }
    
    public void DoPickUpCheck(int table){
    	this.table = table;
    	xDestination = 220;
    	yDestination = 50;
    	command = Command.pickUpCheck;
    }
    
    public void deliverCheck(int table){
     this.table = table;
     xDestination = getXTable(table)+rect;
   	 yDestination = yTable - rect;
   	 
   	 command = Command.hereIsCheck;
    }
    
    public void DoGoToABreak(){
    	xDestination = defaultPos;
    	yDestination = defaultPos;
    	command = Command.onBreak;
    }
    public boolean atDefaultPos() {
        if (xPos == defaultPos && yPos== defaultPos){
        	return true;
        	}
        else {
        	return false;
        }
        }


	
  
}
