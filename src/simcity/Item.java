package simcity;

public class Item {
	
	public String name;
	public int amount;
	public Item(String s, int i){
		name = s;
		amount = i;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
}
