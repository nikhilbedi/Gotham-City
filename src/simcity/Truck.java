package simcity;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import agent.Role;
import simcity.Market.MarketWorkerRole;
import simcity.Market.interfaces.MarketWorker;
import simcity.restaurants.Restaurant;
import simcity.restaurants.restaurant1.Restaurant1;
import simcity.restaurants.restaurant2.Restaurant2;
import simcity.restaurants.restaurant3.Restaurant3;
import simcity.restaurants.restaurant4.Restaurant4;
import simcity.restaurants.restaurant5.Restaurant5;
import Gui.RoleGui;
import Gui.ScreenFactory;


public class Truck extends RoleGui{
	ImageIcon currentImage;
	ImageIcon truckLeft = new ImageIcon(this.getClass().getResource("/resources/mika/truckLeft.jpg"));
	ImageIcon truckRight = new ImageIcon(this.getClass().getResource("/resources/mika/truckRight.jpg"));
	ImageIcon truckUp = new ImageIcon(this.getClass().getResource("/resources/mika/truckUp.jpg"));
	ImageIcon truckDown = new ImageIcon(this.getClass().getResource("/resources/mika/truckDown.jpg"));
	Restaurant1 r1 = (Restaurant1) TheCity.getBuildingFromString("Restaurant 1");
	Restaurant2 r2 = (Restaurant2) TheCity.getBuildingFromString("Restaurant 2");
	Restaurant3 r3  = (Restaurant3) TheCity.getBuildingFromString("Restaurant 3");
	Restaurant4 r4  = (Restaurant4) TheCity.getBuildingFromString("Restaurant 4");
	Restaurant5 r5  = (Restaurant5) TheCity.getBuildingFromString("Restaurant 5");
	Restaurant restaurant;
	Map<String, Integer> delivery = new HashMap<String, Integer>();
	int returnX, returnY;
	private MarketWorkerRole worker;
	private Role role;
	public String command = "";
	
	public Truck(int x, int y, int destX, int destY){
		xPos = x;
		yPos = y;
		xDestination = destX;
		yDestination = destY;
		currentImage = truckDown;
	}
	
	
	public void setData(MarketWorker w, Restaurant r, Map<String, Integer> m){
		worker = (MarketWorkerRole) w;
		restaurant = r;
		//this.role = role;
		delivery = m;
	}
	
	public void setReturnCoordinates(int x, int y){
		returnX = x;
		returnY = y;
	}
	
	public boolean checkIfOpen(){
		boolean open = true; // change it to false later !!!!!!!!!!!!!!!!!!!!!!!!!!!!
		if (restaurant== r1){
			//open =  r1.isOpen();
		}
		else if (restaurant == r2){
			//open = r2.isOpen();
		}
		else if (restaurant == r3){
			//open = r3.isOpen();
		}
		else if (restaurant == r4){
			//open = r4.isOpen();
		}
		else if (restaurant == r5){
		//	open = r5.isOpen()
		}
		return open;
	}
	
	
	@Override
	public void updatePosition(){
		if (xPos < xDestination){
			xPos+=speed;
			currentImage = truckRight;
		}
		else if (xPos > xDestination){
			xPos-=speed;
			currentImage = truckLeft;
		}
		if (yPos < yDestination){
			yPos+=speed;
			currentImage = truckDown;
		}

		else if (yPos > yDestination){
			yPos-=speed;
			currentImage = truckUp;
		}
		
		if (xPos == xDestination && yPos == yDestination && command == "delivering"){ // && and restaurant is open
			command = "";
			
			if (checkIfOpen()){
				if (restaurant== r1){
					//r1.getCook().HereIsYourFood(delivery, worker);
				
				}
				else if (restaurant == r2){
					r2.getCook().HereIsYourFood(delivery, worker);
	
				}
				else if (restaurant == r3){
					 r3.getCook().HereIsYourFood(delivery, worker);
	
				}
				else if (restaurant == r4){
					r4.getCook().HereIsYourFood(delivery, worker);

				}
				else if (restaurant == r5){
					r5.getCook().HereIsYourFood(delivery, worker);

				}
			}
			else{
				worker.RestaurantIsClosed(restaurant);
			}
			goBackToMarket();
		}
		if (xPos == xDestination  && yPos == yDestination && command == "GoingBack"){
			command = "";
			ScreenFactory.main.removeGui(this);
		}
	}
	
	@Override
	public void draw(Graphics g){
		g.drawImage(currentImage.getImage(), xPos, yPos, null);
	}
	
	public void goBackToMarket(){
		xDestination = returnX;
		yDestination = returnY;
		command = "GoingBack";
	}
	
}

