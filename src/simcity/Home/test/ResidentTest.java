package simcity.Home.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Gui.ScreenFactory;
import junit.framework.TestCase;
import simcity.PersonAgent;
import simcity.PersonAgent.JobState;
import simcity.PersonAgent.RentBill;
import simcity.Home.Food;
import simcity.Home.Home;
import simcity.Home.ResidentRole;
import simcity.Home.ResidentRole.HomeEvent;
import simcity.Home.ResidentRole.HomeState;
import simcity.Home.gui.ResidentGui;
import simcity.Home.test.mock.MockLandlord;
import simcity.Home.test.mock.MockResident;

import org.junit.* ;

import static org.junit.Assert.* ;

public class ResidentTest  {
		public PersonAgent person = new PersonAgent("person");
		public ResidentRole resident = new ResidentRole(new PersonAgent("resident"));
		ResidentGui rGui = new ResidentGui(resident);
		public MockLandlord landlord = new MockLandlord("landord");
		public MockResident mockResident = new MockResident("mockResident");
		Home h = new Home("Home", 0, 0, 0, 0);
		public List<RentBill> rentBills = new ArrayList<RentBill>();
		//RentBill rb = new RentBill(person, 10);
		public Map<String, Food> fridgeFoods = new HashMap<String, Food>();
		public Map<String, Food> foods = new HashMap<String, Food>();
		public Map<String, Integer> groceryList = new HashMap<String, Integer>();
		public Map<String, Integer> groceryBag = new HashMap<String, Integer>();
        //resident.getPersonAgent().setBank(b);
		int rent;
		boolean hungry = true;
	
   @Test
    public void testOne() throws InterruptedException{
    	
            //Check initial conditions
	   		
            assertTrue("The groceryBag should be empty at the resident's creation. It is not.", 
                            resident.groceryBag.isEmpty());
            
            assertTrue("The resident's rentBills should be empty at the resident's creation. It is not.",
            		resident.rentBills.isEmpty());
            
            assertTrue("There is no food in the fridge. The size of fridgeFoods should be 0."
            		+ "It is not. ", fridgeFoods.isEmpty());
            
            assertTrue("There is nothing on the groceryList. GroceryList shoule be empty."
            		+ " It is not. ", groceryList.isEmpty());
       
            assertTrue("Resident state to begin with should be DoingNothing. It's currently not.", 
                             resident.state == HomeState.DoingNothing);
            
            assertEquals("Resident should have an empty event log before the Resident's messages are called. Instead, the Resident's event log reads: "
    				+ mockResident.log.toString(), 0, mockResident.log.size());
            
            resident.msgCheckMailbox();
            
            assertTrue("The groceryBag should be empty at the resident's creation. It is not.", 
                    resident.groceryBag.isEmpty());
    
            assertTrue("The resident's rentBills should be empty at the resident's creation. It is not.",
            		resident.rentBills.isEmpty());
    
            assertTrue("There is no food in the fridge. The size of fridgeFoods should be 0."
            		+ "It is not. ", fridgeFoods.isEmpty());
    
            assertTrue("There is nothing on the groceryList. GroceryList shoule be empty."
            		+ " It is not. ", groceryList.isEmpty());

            assertTrue("Resident state to begin with should be DoingNothing. It's currently not.", 
                     resident.state == HomeState.DoingNothing);
    
            assertEquals("Resident should have an empty event log before the Resident's messages are called. Instead, the Resident's event log reads: "
            		+ mockResident.log.toString(), 0, mockResident.log.size());
    
            
           //resident.person.myJob.state = JobState.GoToWorkSoon;
            
           // assertTrue("The resident scheduler should return true. it doesn't. ", resident.pickAndExecuteAnAction());
            
           // rent = 10;
            
            //RentBill rb =  resident.person.new RentBill(resident.person, rent);
            
			//resident.rentBills.add(rb);
           
//            assertFalse("The resident's rentBills should NOT be empty at the resident's creation. It is .",
//            		resident.rentBills.isEmpty());
            
            //assertTrue("Scheduler should return true", resident.pickAndExecuteAnAction());
//            assertTrue("The resident event should be payRent. It's currently not.",
//            		resident.event == HomeEvent.payRent);
//            
            
            
            
    }



	
} 

            /*
           
            
            assertTrue("Name should be testCustomer. It is instead " + bankCustomer.getName(), 
                            bankCustomer.getName().equals("testCustomer"));
            
            
            //set up
            
            bankCustomer.setTransactions();
            assertTrue("Transaction list should be increased after setTransactions. It is not. Instead the size is: " + 
                            bankCustomer.transactionList.size(), bankCustomer.transactionList.size() > 0);
            bankCustomer.setGreeter(mockGreeter);
            assertTrue("Greeter should be set to mockGreeter and not null.", bankCustomer.getBankGreeter().equals(mockGreeter));
            bankCustomer.setBankTeller(mockTeller);
            assertTrue("Teller should be set to mockTeller and not null.", bankCustomer.getBankTeller().equals(mockTeller));
            assertTrue("", bankCustomer.getBankTeller() == mockTeller);
            
            
            //step 1
            
            assertFalse("No messages were given but the customer should be waiting and pickAndExecute should be "
                            + "false. It is not.", bankCustomer.pickAndExecuteAnAction());
            bankCustomer.msgEnteredBank();
            assertTrue("Bank Customer state should be updated, and the customer should msg the greeter concerning getting"
                            + "a teller. The state was not changed.", bankCustomer.pickAndExecuteAnAction());
            assertTrue("The state should be waiting after speaking to the greeter. It is instead something else.", bankCustomer.state == CustomerState.waiting);
            
            
            //step 2
            
            bankCustomer.msgWaitHere(0);
            assertTrue("The customer's waiting number should be 0. It is something else", bankCustomer.waitingNumber == 0);
            assertTrue("Customer state should be updated to goingToLine after scheduler was run. It was not updated.", bankCustomer.state == CustomerState.goingToLine);
            
            
            //step 3
            
            bankCustomer.msgGoToTeller(mockTeller);
            assertTrue("The customer's state should be changed to inLine before the scheduler runs. This didn't occur.", bankCustomer.state == CustomerState.inLine);
            assertTrue("Scheduler should run DoGoToTeller() and return true. This didn't occur", bankCustomer.pickAndExecuteAnAction());
            assertTrue("The customer's state should be changed to atTeller after the scheduler runs. This didn't occur.", bankCustomer.state == CustomerState.goingToTeller);
            
            
            //step 4
            
            bankCustomer.msgAtTeller();
            assertTrue("Customer state should be atTeller. It is not.", bankCustomer.state == CustomerState.atTeller);
            assertTrue("Should ask teller for a transaction through scheduler. Something was missed.", bankCustomer.pickAndExecuteAnAction());
            assertTrue("Transaction List should be empty once the teller is messaged for a transaction. It does not occur", bankCustomer.transactionList.size() == 0);
            
            
            //step 5
            
            BankReceipt receipt = new BankReceipt(50, 50, "openingAccount");
            bankCustomer.HereIsReceiptAndAccountInfo(receipt, 101);
            assertEquals("The person's money amount should have been decremented by 50. It didn't.",bankCustomer.getPersonAgent().checkMoney(),-50.0);
            assertTrue("The customer's state should be receivedReceipt. It is not.", bankCustomer.state == CustomerState.receivedReceipt);
            assertTrue("The person's account number should be set to 101. It is not.", bankCustomer.getPersonAgent().getAccountNumber() == 101);
            assertTrue("Should begin to leave bank after finished through scheduler. Something was missed.", bankCustomer.pickAndExecuteAnAction());
            assertTrue("Customer state should be done after transaction is finished. It is not.", bankCustomer.state == CustomerState.done);
            
    }//end one normal customer opening Account scenario
    
	}
	*/
