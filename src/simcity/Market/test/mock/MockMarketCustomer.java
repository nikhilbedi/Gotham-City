package simcity.Market.test.mock;

import java.util.List;

import simcity.Market.Order;
import simcity.Market.interfaces.MarketCustomer;

public class MockMarketCustomer extends Mock implements MarketCustomer{

	public MockMarketCustomer(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void HereIsChange(double d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void amountDue(double amountDue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void HereIsYourStuff(List<Order> orders) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void outOf(Order order) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void AtCashier() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void NextCustomerPlease() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ArrivedToGetItem() {
		// TODO Auto-generated method stub
		
	}

	public String getName(){
		return null;
		
		
	}

}
