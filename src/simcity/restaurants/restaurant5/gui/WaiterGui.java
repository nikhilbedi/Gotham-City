package simcity.restaurants.restaurant5.gui;



import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.*;

import agent.*;
import simcity.restaurants.restaurant5.*;
import simcity.restaurants.restaurant5.interfaces.Waiter;

import Gui.RoleGui;
import Gui.Screen;

public class WaiterGui extends RoleGui implements Gui {

    private WaiterRole agent = null;
    
    private int xHome = 20, yHome = 60;
    
    public static final int cookX = 162;
    public static final int cookY = 76;
    
    public static final int standX = 162;
    public static final int standY = 50;
    
    private int defaultX = 30, defaultY = 60;
    
	public static int waitX = 70;
	public static int waitY = 36;
    
    static final int hostSize = 20;
    
    private Map<Integer, Dimension> tableMap;
    private Icon holding = null;
    public List<Icon> icons = Collections.synchronizedList(new ArrayList<Icon>());
    
    private enum Command { noCommand, SeatCustomer, GoToCusts, GoTakeOrder, GoToCook, GoToTable, WaitForOrder, GoToHome, GoToStand};
    private Command command=Command.noCommand;
	
	
    public WaiterGui(WaiterRole agent) {
        this.agent = agent;
        tableMap = new HashMap<Integer, Dimension>();
        tableMap.put(new Integer(0), new Dimension(50,250));
        tableMap.put(new Integer(1), new Dimension(150,250));
        tableMap.put(new Integer(2), new Dimension(250,250));
        
    }


    public WaiterGui(Waiter waiter1, Screen meScreen) {
		 super( (Role)waiter1, meScreen);
		 super.setColor(Color.BLUE);
		 
		 tableMap = new HashMap<Integer, Dimension>();
	     tableMap.put(new Integer(0), new Dimension(50,250));
	     tableMap.put(new Integer(1), new Dimension(150,250));
	     tableMap.put(new Integer(2), new Dimension(250,250));
	     
	     
			xPos = defaultX;
			yPos = defaultY;
	}


	public void updatePosition() {
       super.updatePosition();

        if (xPos == xDestination && yPos == yDestination){
        	if(command == Command.SeatCustomer){
        		command = Command.noCommand;
        		agent.doneMoving();
        		
        	}
        	else if(command == Command.GoToHome){
        		command = Command.noCommand;
        		agent.doneMoving();
        	}
        	else if(command == Command.GoToStand){
        		command = Command.noCommand;
        		agent.doneMoving();
        	}
        	else if(command == Command.GoToCusts){
        		command = Command.noCommand;
        		agent.doneMoving();
        	}
        	else if(command == Command.GoToCook){
        		command = Command.noCommand;
        		agent.doneMoving();
        	}
        	else if(command == Command.GoToTable){
        		command = Command.noCommand;
        		agent.doneMoving();
        	}
        	else if(command == Command.GoTakeOrder){
        		command = Command.noCommand;
        		agent.doneMoving();
        	}
        	else if(command == Command.noCommand){//defaults waiter to home if he's not doing anything
        		xDestination = xHome;
                yDestination = yHome;
        	}
         }
    }

    public void draw(Graphics g) {
        super.draw(g);
        if(holding != null){
        g.drawString(holding.choice, xPos, yPos+11);
        }
        //drawing orders
        g.setColor(Color.BLACK);
        synchronized(icons){
        for(Icon icon: icons){
        	g.drawString(icon.choice, tableMap.get(icon.table).width, tableMap.get(icon.table).height);
        }
        }
        //g.drawString(agent.cook.returnAmounts(), 100, 25);
    }

    public boolean isPresent() {
        return true;
    }
    
    //icon stuff
    public void addIcon(int table, String choice){
    icons.add(new Icon(choice, table));
    }
    
    public void readyIcon(int table, String choice){
    		
    	for(int i=0; i<icons.size(); i++)
    	{
    		if(icons.get(i).table == table){
    			icons.get(i).ready();
    		}
    	}
    }
    
    public void removeIcon(int table, String choice){
    	for(int i=0; i<icons.size(); i++)
    	{
    		if(icons.get(i).table == table){
    			icons.remove(i);
    		}
    	}	
    }
    public void updateWaiterIcon(String choice){
    	holding = new Icon(choice, -1);
    	holding.ready();
    }
    
    public void removeWaiterIcon(){
    	holding = null;
    }

    public void DoBringToTable(Restaurant5CustomerGui customer, int table) {
    	Dimension dest = tableMap.get(table);
        xDestination = dest.width + 20;
        yDestination = dest.height - 20;
        command = Command.SeatCustomer;
        customer.goToSeat(dest);
    }

    public void DoGoToCustomers() {
        xDestination = waitX;
        yDestination = waitY;
        command = Command.GoToCusts;
    }
    
    public void DoGoToHome(){
    	xDestination = xHome;
    	yDestination = yHome;
    	command = Command.GoToHome;
    	
    }
    
    public void DoGoToTable(int table){
    	Dimension dest = tableMap.get(table);
        xDestination = dest.width - 20;
        yDestination = dest.height + 20;
        command = Command.GoToTable;
	}
    
    public void DoTakeOrder(int table){
    	Dimension dest = tableMap.get(table);
        xDestination = dest.width - 20;
        yDestination = dest.height + 20;
        command = Command.GoTakeOrder;
	}
    
    public void DoGoToCook(){
    	xDestination = cookX;
    	yDestination = cookY;
    	command = Command.GoToCook;
    
	}
    
	public void DoGoToStand() {
		xDestination = standX;
    	yDestination = standY;
    	command = Command.GoToStand;
	}

    
    public void setHome(int x, int y){
    	xHome = x;
    	yHome = y;
    }
//Icon class, helps with animation
    private class Icon{
    	String choice;
    	int table;
    	public Icon(String c, int t){
    		//System.out.println(c);
    		
    		String temp = "error";
    		if(c == null){
    			System.err.println("Tried to make an icon with no name!");
    			temp = "????";
    			choice = temp;
    			table = t;
    			return;
    		}
    		
    		if(c.equalsIgnoreCase("Steak")){
    			temp = "ST?";
    		}
    		else if(c.equalsIgnoreCase("Chicken")){
    			temp = "CK?";
    		}
    		else if(c.equalsIgnoreCase("Pizza")){
    			temp = "PZ?";
    		}
    		else if(c.equalsIgnoreCase("Salad")){
    			temp = "SL?";
    		}
    		else{
    			temp = "failed";
    		}
    		choice = temp;
    		table = t;
    	}
    	public void ready(){
    		choice = choice.substring(0, choice.length()-1);
    		//System.out.println(choice);
    	}
    }

    
}
