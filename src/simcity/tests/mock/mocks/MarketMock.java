package simcity.tests.mock.mocks;

import java.util.HashMap;
import java.util.Map;

import simcity.tests.mock.*;

public class MarketMock extends Mock {
    public EventLog log = new EventLog();

    public MarketMock(String name) {
    	super(name);
    }
    
    public Map<String,Integer> giveMeGroceries() {
    	log.add(new LoggedEvent("Person needs groceries. Will give list of groceries back."));
    	Map<String, Integer> map = new HashMap<String, Integer>();
    	map.put("Pizza", 10);
    	map.put("Steak", 5);
    	return map;
    }
}