package simcity.restaurants.restaurant3;

public class MarketFood extends Food {

	private double price;
	
	public MarketFood(String type) {
		super(type);	
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public double getPrice() {
		return this.price;
	}
}
