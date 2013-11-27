package simcity.restaurants.restaurant2;
import java.util.*;

public class Check {
	//List<String> foods = new LinkedList<String> (Arrays.asList("Steak", "Chicken", "Salad", "Pizza"));
	double moneyOwed = 0;
	int table;
	
	public Check() {
		//foods = Arrays.asList("Steak", "Chicken", "Salad", "Pizza");
	}
	
	public Check(double m) {
		moneyOwed = m;
	}
	
	public Check(double m, int t) {
		moneyOwed = m;
		table = t;
	}
	
	public void setTable(int num) {
		table = num;
	}
	
	public int getTable() { return table; }
	
	public void addDues(double value) {
		moneyOwed += value;
	}
	
	public void clearDues() {
		moneyOwed = 0;
	}
	
	public double getTotal() {
		return moneyOwed;
	}
}
