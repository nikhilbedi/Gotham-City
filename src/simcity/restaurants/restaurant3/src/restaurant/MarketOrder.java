package simcity.restaurants.restaurant3.src.restaurant;

public class MarketOrder {

	public String foodType = "";
	int amountNeeded = 0;
	int amountProvided = 0;
	double bill = 0;
	
	public MarketOrder(String foodType, int amountNeeded) {
		this.foodType = foodType;
		this.amountNeeded = amountNeeded;
	}


public int getAmountNeeded() {
	return amountNeeded;
}
public int getAmountProvided() {
	return amountProvided;
}

}