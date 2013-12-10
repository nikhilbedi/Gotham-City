package simcity.Home.test;

import java.util.ArrayList;
import java.util.List;

import Gui.ScreenFactory;
import junit.framework.TestCase;
import simcity.PersonAgent;
import simcity.PersonAgent.RentBill;
import simcity.Home.Apartment;
import simcity.Home.Home;
import simcity.Home.LandlordRole;
import simcity.Home.LandlordRole.HomeOwnerState;
import simcity.Home.ResidentRole;
import simcity.Home.ResidentRole.HomeEvent;
import simcity.Home.ResidentRole.HomeState;
import simcity.Home.gui.ResidentGui;
import simcity.Home.interfaces.Landlord;
import simcity.Home.test.mock.MockLandlord;
import simcity.Home.test.mock.MockResident;

import org.junit.* ;

import static org.junit.Assert.* ;

public class LandlordTest  {
		//PersonAgent myPerson = new PersonAgent("landlord");
		//public ResidentRole resident = new ResidentRole(myPerson);
		public MockResident mockResident = new MockResident("mockResident");
		public MockLandlord mockLandlord = new MockLandlord("mockLandlord");
		public LandlordRole landlord = new LandlordRole(new PersonAgent("landlord"));
		Home h = new Home("Home", 0, 0, 0, 0);
		public List<RentBill> rentBills = new ArrayList<RentBill>();
		public List<Home> homesOwned = new ArrayList<Home>();
		public List<Apartment> apartmentsOwned = new ArrayList<Apartment>();
		int time;
        //resident.getPersonAgent().setBank(b);
		int currentTime;
		int timeToSendBills;
		
	
   @Test
    public void testOneNormal() throws InterruptedException{
    	rentBills.clear();
    	homesOwned.clear();
    	time = 0;
    	//currentTime = 0;
    	timeToSendBills = 6;
    	
    	//preconditions
    	assertTrue("time from the city should be 0. It isn't. ", time == 0);
    	
    	//assertEquals("The current time should be equal to the time", landlord.currentTime == time);
    	
    	assertTrue("The rent Bills list should be empty upon instantiation. It isn't.",
    			landlord.getRentBills().isEmpty());
    
    	
    	assertTrue("This landlord does not own any property so his homesOwned list is empty. It isn't.",
    			landlord.getHomesOwned().isEmpty());
    	
    	assertEquals("Landlord should have an empty event log before the first message is called."
    			+ "Instead, the Landlord's event log reads: " + mockLandlord.log.toString(), 0, mockLandlord.log.size());
    	
    	assertTrue("landlord scheduler should return false. it doesn't", !landlord.pickAndExecuteAnAction());
    	
    	assertTrue("home owner state should be none. It isn't. ", landlord.hOwnerState == HomeOwnerState.none );
    	
    	
    	//Step 1
    	//update current time for the landlord ("looking at watch")
    	
    	landlord.updateCurrentTime(time);
    	
    	//pre/postconditions
    	
    	assertTrue("time from the city should be 0. It isn't. ", time == 0);
    	
    	assertTrue("The current time should be equal to the time", landlord.currentTime == 0);
    	
    	assertTrue("The rent Bills list should be empty upon instantiation. It isn't.",
    			landlord.getRentBills().isEmpty());
    
    	
    	assertTrue("This landlord does not own any property so his homesOwned list is empty. It isn't.",
    			landlord.getHomesOwned().isEmpty());
    	
    	assertEquals("Landlord should have an empty event log before the first message is called."
    			+ "Instead, the Landlord's event log reads: " + mockLandlord.log.toString(), 0, mockLandlord.log.size());
    	
    	assertTrue("landlord scheduler should return false. it doesn't", !landlord.pickAndExecuteAnAction());
    	
    	assertTrue("home owner state should be none. It isn't. ", landlord.hOwnerState == HomeOwnerState.none );
    	
    	
    	//Step 2
    	//increment time so it is time to send rent bills
    	
    	time += 6;
    	landlord.updateCurrentTime(time);
    	
    	//pre/postconditions
    	
    	assertTrue("time from the city should be 6. It isn't. ", time == 6);
    	
    	assertTrue("The current time should be 6. It isn't ", landlord.currentTime == 6);
    	
    	assertTrue("The time to send send bills should be 6. It isn't ", timeToSendBills == 6);
    	
    	assertTrue("The rent Bills list should be empty upon instantiation. It isn't.",
    			landlord.getRentBills().isEmpty());
    
    	assertTrue("This landlord does not own any property so his homesOwned list is empty. It isn't.",
    			landlord.getHomesOwned().isEmpty());
    	
    	assertEquals("Landlord should have an empty event log before the first message is called."
    			+ "Instead, the Landlord's event log reads: " + mockLandlord.log.toString(), 0, mockLandlord.log.size());
    	
    	assertTrue("landlord scheduler should return false. it doesn't", !landlord.pickAndExecuteAnAction());
    	
    	assertTrue("home owner state should be none. It isn't. ", landlord.hOwnerState == HomeOwnerState.none );
    	
    	//Step 3
    	//message from the World Clock Timer to send rent bills
   
    	landlord.msgSendRentBills();
    	
    	//pre/postconditions
    	
    	assertTrue("Home owner state should be sendBills. It is not. ",
    			landlord.hOwnerState == HomeOwnerState.sendBills);
    	
    	assertTrue("Scheduler should return true", landlord.pickAndExecuteAnAction());
    	
    	assertTrue("Home owner state should be sendingBills. It is not. ",
    			landlord.hOwnerState == HomeOwnerState.sendingBills);
    	
    	assertTrue("This landlord still does not own any property so his homesOwned list is empty. It isn't.",
    			landlord.getHomesOwned().isEmpty());
    	
    	assertTrue("the rentbills should be empty. It is not. ",
    			landlord.getRentBills().isEmpty());
    	
    	//assertTrue("Home owner state should be sendingBills. It is not. ",
    		//	landlord.hOwnerState == HomeOwnerState.billsSent);
   }
   
   
   
   
    	//Test two: 1 property owned
   @Test
    	public void testTwo() throws InterruptedException{
        	rentBills.clear();
        	homesOwned.clear();
        	time = 0;
        	//currentTime = 0;
        	timeToSendBills = 6;
        	
        	//preconditions
        	assertTrue("time from the city should be 0. It isn't. ", time == 0);
        	
        	//assertEquals("The current time should be equal to the time", landlord.currentTime == time);
        	
        	assertTrue("The rent Bills list should be empty upon instantiation. It isn't.",
        			landlord.getRentBills().isEmpty());
        
        	
        	assertTrue("This landlord does not own any property so his homesOwned list is empty. It isn't.",
        			landlord.getHomesOwned().isEmpty());
        	
        	assertEquals("Landlord should have an empty event log before the first message is called."
        			+ "Instead, the Landlord's event log reads: " + mockLandlord.log.toString(), 0, mockLandlord.log.size());
        	
        	assertTrue("landlord scheduler should return false. it doesn't", !landlord.pickAndExecuteAnAction());
        	
        	assertTrue("home owner state should be none. It isn't. ", landlord.hOwnerState == HomeOwnerState.none );
        	
        	
        	//Step 1
        	//update current time for the landlord ("looking at watch")
        	
        	landlord.updateCurrentTime(time);
        	
        	//pre/postconditions
        	
        	assertTrue("time from the city should be 0. It isn't. ", time == 0);
        	
        	assertTrue("The current time should be equal to the time", landlord.currentTime == 0);
        	
        	assertTrue("The rent Bills list should be empty upon instantiation. It isn't.",
        			landlord.getRentBills().isEmpty());
        
        	
        	assertTrue("This landlord does not own any property so his homesOwned list is empty. It isn't.",
        			landlord.getHomesOwned().isEmpty());
        	
        	assertEquals("Landlord should have an empty event log before the first message is called."
        			+ "Instead, the Landlord's event log reads: " + mockLandlord.log.toString(), 0, mockLandlord.log.size());
        	
        	assertTrue("landlord scheduler should return false. it doesn't", !landlord.pickAndExecuteAnAction());
        	
        	assertTrue("home owner state should be none. It isn't. ", landlord.hOwnerState == HomeOwnerState.none );
        	
        	
        	//Step 2
        	//increment time so it is time to send rent bills
        	
        	time += 6;
        	landlord.updateCurrentTime(time);
        	
        	//pre/postconditions
        	
        	assertTrue("time from the city should be 6. It isn't. ", time == 6);
        	
        	assertTrue("The current time should be 6. It isn't ", landlord.currentTime == 6);
        	
        	assertTrue("The time to send send bills should be 6. It isn't ", timeToSendBills == 6);
        	
        	assertTrue("The rent Bills list should be empty upon instantiation. It isn't.",
        			landlord.getRentBills().isEmpty());
        
        	assertTrue("This landlord does not own any property so his homesOwned list is empty. It isn't.",
        			landlord.getHomesOwned().isEmpty());
        	
        	assertEquals("Landlord should have an empty event log before the first message is called."
        			+ "Instead, the Landlord's event log reads: " + mockLandlord.log.toString(), 0, mockLandlord.log.size());
        	
        	assertTrue("landlord scheduler should return false. it doesn't", !landlord.pickAndExecuteAnAction());
        	
        	assertTrue("home owner state should be none. It isn't. ", landlord.hOwnerState == HomeOwnerState.none );
        	
        	//Step 3
        	//creating a new Home so the landlord can own it
        	Home h = new Home("house 1", 0, 0, 0, 0);
        	landlord.homesOwned.add(h);
        	
        	//pre/postconditions
        	
        	assertTrue("time from the city should be 6. It isn't. ", time == 6);
        	
        	assertTrue("The current time should be 6. It isn't ", landlord.currentTime == 6);
        	
        	assertTrue("The time to send send bills should be 6. It isn't ", timeToSendBills == 6);
        	
        	assertTrue("The rent Bills list should be empty upon instantiation. It isn't.",
        			landlord.getRentBills().isEmpty());
        
        	assertTrue("This landlord owns a property so his homesOwned list is not empty. It is.",
        			!landlord.getHomesOwned().isEmpty());
        	
        	assertTrue("This landlord owns 1 property so his homesOwned list should equal 1. It doesn't.",
        			landlord.getHomesOwned().size() == 1);
        	
        	assertEquals("Landlord should have an empty event log before the first message is called."
        			+ "Instead, the Landlord's event log reads: " + mockLandlord.log.toString(), 0, mockLandlord.log.size());
        	
        	assertTrue("landlord scheduler should return false. it doesn't", !landlord.pickAndExecuteAnAction());
        	
        	assertTrue("home owner state should be none. It isn't. ", landlord.hOwnerState == HomeOwnerState.none );
        	
        	
        	//Step 4
        	//message from the World Clock Timer to send rent bills
       
        	landlord.msgSendRentBills();
        	
        	//pre/postconditions
        	
        	assertTrue("Home owner state should be sendBills. It is not. ",
        			landlord.hOwnerState == HomeOwnerState.sendBills);
        	
        	assertTrue("Scheduler should return true", landlord.pickAndExecuteAnAction());
        	
        	assertTrue("Home owner state should be sendingBills. It is not. ",
        			landlord.hOwnerState == HomeOwnerState.sendingBills);
        	
        	assertTrue("This landlord still owns a property so his homesOwned list is not empty. It is.",
        			!landlord.getHomesOwned().isEmpty());
        	
        	assertTrue("This landlord owns 1 property so his homesOwned list should equal 1. It doesn't.",
        			landlord.getHomesOwned().size() == 1);
        	
        	assertTrue("the landlord owns a property so he should create a rent Bill. RentBills list should"
        			+ "not be empty. It is. ",
        			!landlord.getRentBills().isEmpty());
        	
        	assertTrue("the landlord needs to send out 1 rent bill because he owns 1 property. His rent bills"
        			+ "list should equal 1. It doesn't. ",
        			landlord.getRentBills().size() == 1);
        	
        	//assertTrue("Home owner state should be sendingBills. It is not. ",
        		//	landlord.hOwnerState == HomeOwnerState.billsSent);
   }
   
   
   
 //Test three: 1 apartment owned. 1 home owned
   @Test
    	public void testThree() throws InterruptedException{
        	rentBills.clear();
        	homesOwned.clear();
        	apartmentsOwned.clear();
        	time = 0;
        	//currentTime = 0;
        	timeToSendBills = 6;
        	
        	//preconditions
        	assertTrue("time from the city should be 0. It isn't. ", time == 0);
        	
        	//assertEquals("The current time should be equal to the time", landlord.currentTime == time);
        	
        	assertTrue("The rent Bills list should be empty upon instantiation. It isn't.",
        			landlord.getRentBills().isEmpty());
        
        	
        	assertTrue("This landlord does not own any property so his homesOwned list is empty. It isn't.",
        			landlord.getHomesOwned().isEmpty());
        	
        	assertTrue("This landlord does not own any property so his homesOwned list is empty. It isn't.",
        			landlord.apartmentsOwned.isEmpty());
        	
        	assertEquals("Landlord should have an empty event log before the first message is called."
        			+ "Instead, the Landlord's event log reads: " + mockLandlord.log.toString(), 0, mockLandlord.log.size());
        	
        	assertTrue("landlord scheduler should return false. it doesn't", !landlord.pickAndExecuteAnAction());
        	
        	assertTrue("home owner state should be none. It isn't. ", landlord.hOwnerState == HomeOwnerState.none );
        	
        	
        	//Step 1
        	//update current time for the landlord ("looking at watch")
        	
        	landlord.updateCurrentTime(time);
        	
        	//pre/postconditions
        	
        	assertTrue("time from the city should be 0. It isn't. ", time == 0);
        	
        	assertTrue("The current time should be equal to the time", landlord.currentTime == 0);
        	
        	assertTrue("The rent Bills list should be empty upon instantiation. It isn't.",
        			landlord.getRentBills().isEmpty());
        
        	assertTrue("This landlord does not own any property so his homesOwned list is empty. It isn't.",
        			landlord.getHomesOwned().isEmpty());
        	
        	assertTrue("This landlord does not own any property so his apartmentsOwned list is empty. It isn't.",
        			landlord.apartmentsOwned.isEmpty());
        	
        	assertEquals("Landlord should have an empty event log before the first message is called."
        			+ "Instead, the Landlord's event log reads: " + mockLandlord.log.toString(), 0, mockLandlord.log.size());
        	
        	assertTrue("landlord scheduler should return false. it doesn't", !landlord.pickAndExecuteAnAction());
        	
        	assertTrue("home owner state should be none. It isn't. ", landlord.hOwnerState == HomeOwnerState.none );
        	
        	
        	//Step 2
        	//increment time so it is time to send rent bills
        	
        	time += 6;
        	landlord.updateCurrentTime(time);
        	
        	//pre/postconditions
        	
        	assertTrue("time from the city should be 6. It isn't. ", time == 6);
        	
        	assertTrue("The current time should be 6. It isn't ", landlord.currentTime == 6);
        	
        	assertTrue("The time to send send bills should be 6. It isn't ", timeToSendBills == 6);
        	
        	assertTrue("The rent Bills list should be empty upon instantiation. It isn't.",
        			landlord.getRentBills().isEmpty());
        
        	assertTrue("This landlord does not own any property so his homesOwned list is empty. It isn't.",
        			landlord.getHomesOwned().isEmpty());
        	
        	assertTrue("This landlord does not own any property so his apartmentsOwned list is empty. It isn't.",
        			landlord.apartmentsOwned.isEmpty());
        	
        	assertEquals("Landlord should have an empty event log before the first message is called."
        			+ "Instead, the Landlord's event log reads: " + mockLandlord.log.toString(), 0, mockLandlord.log.size());
        	
        	assertTrue("landlord scheduler should return false. it doesn't", !landlord.pickAndExecuteAnAction());
        	
        	assertTrue("home owner state should be none. It isn't. ", landlord.hOwnerState == HomeOwnerState.none );
        	
        	//Step 3
        	//creating a new Home so the landlord can own it
        	Home h = new Home("house 1", 0, 0, 0, 0);
        	landlord.homesOwned.add(h);
        	
        	Apartment a = new Apartment("apartment 1",0, 0, 0, 0);
        	landlord.apartmentsOwned.add(a);
        	
        	//pre/postconditions
        	
        	assertTrue("time from the city should be 6. It isn't. ", time == 6);
        	
        	assertTrue("The current time should be 6. It isn't ", landlord.currentTime == 6);
        	
        	assertTrue("The time to send send bills should be 6. It isn't ", timeToSendBills == 6);
        	
        	assertTrue("The rent Bills list should be empty upon instantiation. It isn't.",
        			landlord.getRentBills().isEmpty());
        
        	assertTrue("This landlord owns a property so his homesOwned list is not empty. It is.",
        			!landlord.getHomesOwned().isEmpty());
        	
        	assertTrue("This landlord owns 1 property so his homesOwned list should equal 1. It doesn't.",
        			landlord.getHomesOwned().size() == 1);
        	
        	assertTrue("This landlord owns a property so his apartmentsOwned list is not empty. It is.",
        			!landlord.apartmentsOwned.isEmpty());
        	
        	assertTrue("This landlord owns 1 property so his apartmentsOwned list should equal 1. It doesn't.",
        			landlord.apartmentsOwned.size() == 1);
        	
        	assertEquals("Landlord should have an empty event log before the first message is called."
        			+ "Instead, the Landlord's event log reads: " + mockLandlord.log.toString(), 0, mockLandlord.log.size());
        	
        	assertTrue("landlord scheduler should return false. it doesn't", !landlord.pickAndExecuteAnAction());
        	
        	assertTrue("home owner state should be none. It isn't. ", landlord.hOwnerState == HomeOwnerState.none );
        	
        	
        	//Step 4
        	//message from the World Clock Timer to send rent bills
       
        	landlord.msgSendRentBills();
        	
        	//pre/postconditions
        	
        	assertTrue("Home owner state should be sendBills. It is not. ",
        			landlord.hOwnerState == HomeOwnerState.sendBills);
        	
        	assertTrue("Scheduler should return true", landlord.pickAndExecuteAnAction());
        	
        	assertTrue("Home owner state should be sendingBills. It is not. ",
        			landlord.hOwnerState == HomeOwnerState.sendingBills);
        	
        	assertTrue("This landlord still owns a property so his homesOwned list is not empty. It is.",
        			!landlord.getHomesOwned().isEmpty());
        	
        	assertTrue("This landlord owns 1 home so his homesOwned list should equal 1. It doesn't.",
        			landlord.getHomesOwned().size() == 1);
        	
        	assertTrue("This landlord owns 1 apartment so his apartmentsOwned list should equal 1. It doesn't.",
        			landlord.apartmentsOwned.size() == 1);
        	
        	assertTrue("the landlord owns a property so he should create a rent Bill. RentBills list should"
        			+ "not be empty. It is. ",
        			!landlord.getRentBills().isEmpty());
        	
        	assertTrue("the landlord needs to send out 2 rent bill because he owns 2 property. His rent bills"
        			+ "list should equal 2. It doesn't. ",
        			landlord.getRentBills().size() == 2);
        	
        	//assertTrue("Home owner state should be sendingBills. It is not. ",
        		//	landlord.hOwnerState == HomeOwnerState.billsSent);
   
   
   
   
   
   
   
   
   
   
   
   
   
   }
}
