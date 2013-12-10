package simcity.Home.test;

import java.util.ArrayList;
import java.util.List;

import Gui.ScreenFactory;
import junit.framework.TestCase;
import simcity.PersonAgent;
import simcity.PersonAgent.RentBill;
import simcity.Home.Home;
import simcity.Home.LandlordRole;
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
		PersonAgent myPerson = new PersonAgent("person");
		//public ResidentRole resident = new ResidentRole(myPerson);
		public MockResident mockResident = new MockResident("mockResident");
		public LandlordRole landlord = new LandlordRole(myPerson);
		Home h = new Home("Home", 0, 0, 0, 0);
		private List<RentBill> rentBills = new ArrayList<RentBill>();
		private List<Home> homesOwned = new ArrayList<Home>();
        //resident.getPersonAgent().setBank(b);
		
	
   @Test
    public void testOneNormalResidentComesToHomeWithNoIntention() throws InterruptedException{
    	
   }
}
