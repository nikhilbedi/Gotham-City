package simcity.Home.test;

import java.util.ArrayList;
import java.util.List;

import Gui.ScreenFactory;
import junit.framework.TestCase;
import simcity.PersonAgent;
import simcity.PersonAgent.RentBill;
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
		public PersonAgent myPerson = new PersonAgent("person");
		public ResidentRole resident = new ResidentRole(myPerson);
		public MockLandlord landlord = new MockLandlord("landord");
		Home h = new Home("Home", 0, 0, 0, 0);
		private List<RentBill> rentBills = new ArrayList<RentBill>();
        //resident.getPersonAgent().setBank(b);
		
	
   @Test
    public void testOneNormalResidentComesToHomeWithNoIntention() throws InterruptedException{
    	
            //Check initial conditions
	   		
            assertTrue("The groceryBag should be empty at the resident's creation. It is not.", 
                            resident.groceryBag.size() == 0);
            assertTrue("The resident's rentBills should be empty at the resident's creation. It is not.",
            		landlord.rentBills.size() == 0);
       
            assertTrue("Resident state to begin with should be DoingNothing. It's currently not.", 
                             resident.state == HomeState.DoingNothing);
            
            //landlord.rent = 10;
            RentBill rb =  resident.person.new RentBill(resident.person, 10);
            
			resident.person.rentBills.add(rb);
           
            assertFalse("The resident's rentBills should NOT be empty at the resident's creation. It is .",
            		resident.person.rentBills.size() == 0);
            assertTrue("Scheduler should return true", resident.pickAndExecuteAnAction());
            assertTrue("The resident event should be payRent. It's currently not.",
            		resident.event == HomeEvent.payRent);
            
            
            
            
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
