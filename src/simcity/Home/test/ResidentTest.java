package simcity.Home.test;

import Gui.ScreenFactory;
import junit.framework.TestCase;
import simcity.PersonAgent;
import simcity.Home.Home;
import simcity.Home.ResidentRole;
import simcity.Home.ResidentRole.HomeState;
import simcity.Home.gui.ResidentGui;
import simcity.Home.test.mock.MockResident;

public class ResidentTest extends TestCase {
	
		//these are instantiated for each test separately via the setUp() method.
		ResidentRole resident;
		PersonAgent person;
		MockResident mockResident;
	
		/**
		 * This method is run before each test. You can use it to instantiate the class variables
		 * for your agent and mocks, etc.
		 */
		
		public void setUp() throws Exception{
			super.setUp();		
			resident = new ResidentRole(new PersonAgent ("testResident"));	
			Home h = new Home("Home", 0, 0, 0, 0);
			resident.getPersonAgent().setHome(h);
			resident.setGui(new ResidentGui((ResidentRole)resident, ScreenFactory.getMeScreen("Home")));
			mockResident = new MockResident("mockResident");
		}
}
	
    /*
    public void testOneNormalResidentComesToHomeWithNoIntention()
    {
            //Check initial conditions
            assertTrue("The transactionList should be empty at the customer's creation. It is not.", 
                            bankCustomer.transactionList.size() == 0);
            
            assertTrue("Resident state to begin with should be DoingNothing. It's currently not.", 
                             resident.state == HomeState.DoingNothing);
            
            
            
            
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
