package simcity.tests.mock.mocks;

import java.util.Map;

import simcity.Market.test.mock.Mock;

public class HomeMock extends Mock {

	public HomeMock(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public void addGroceriesToFridge(Map<String, Integer> map) {
		map.clear();
	}
}
