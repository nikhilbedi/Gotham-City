package simcity.restaurant_evan.src.restaurant;

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
