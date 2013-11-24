package simcity.tests.mock.mocks;

import simcity.tests.mock.*;

public class BankMock extends Mock {
    public EventLog log = new EventLog();

    public BankMock(String name) {
    	super(name);
    }
    
    public int needAccountNumber() {
    	log.add(new LoggedEvent("Person needs account. Will make account for person."));
    	return 1;
    }
}