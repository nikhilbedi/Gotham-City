package simcity.tests.mock.mocks;

import java.util.HashMap;
import java.util.Map;

import simcity.PersonAgent;
import simcity.PersonAgent.MarketState;
import simcity.tests.mock.*;

public class MarketMock extends Mock {
    public EventLog log = new EventLog();

    public MarketMock(String name) {
    	super(name);
    }
    
    public Map<String,Integer> giveMeGroceries(PersonAgent person) {
    	log.add(new LoggedEvent("Person needs groceries. Will give list of groceries back."));
    	person.marketState = MarketState.TakeGroceriesHome;
    	person.checkPersonScheduler = true;
    	Map<String, Integer> map = new HashMap<String, Integer>();
    	map.put("Pizza", 10);
    	map.put("Steak", 5);
    	person.groceryBag = map;
    	person.groceryList.clear();
    	return map;
    }
}