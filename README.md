###Team 31
####Team Information

  + Team Name: Team 31
  + Team E-Mail: usc-csci201-fall2013-team31-l@mymaillists.usc.edu
  + Team Mentor: Sarah Whang <sarahwha@usc.edu>

####Resources

  + [Code Repository](https://github.com/usc-csci201-fall2013/team31)
  + [Documentation](https://github.com/usc-csci201-fall2013/team31/docs)

####Team Members
| No. | Name (First (Nickname) Last) |       USC Email          |                GitHub Username                |      Role      |
| :-: | :--------------------------- | :--------------------    | :-------------------------------------------- | :------------- |
|  1  | Evan Coutre                  | evcoutre@usc.edu         | @[evcoutre](https://github.com/evcoutre  )    |  Team Leader   |
|  2  | William Hunter McNichols     | wmcnicho@usc.edu         | @[wmcnicho](https://github.com/wmcnicho)      |                |
|  3  | Meruyert Aitbayeva           | aitbayev@usc.edu         | @[aibtbayev](https://github.com/aitbayev)     |                |
|  4  | Brice Roland                 | broland@usc.edu          | @[broland](https://github.com/broland)        |                |
|  5  | Nikhil Bedi                  | nbedi@usc.edu	        | @[nikhilbedi](https://github.com/nikhilbedi)  |                |

####Team Meetings
|       Meeting       |           Time                  |      Location      |
| :------------------ | :-----------------------        | :----------------- |
| Lab                 | Wed. 10:00am                    | SAL 109            |
| Weekly Meeting 1    | Tues. 05:00pm to 10:00pm        | GFS 112            |
| Weekly Meeting 2    | Thur. 05:00pm to 10:30pm        | SAL 125            |
| General meetings    | Tues./Thur. 05:00pm to 10:30pm  | WPH B34            |



## Team Project V1

### Welcome to Gotham City

Immediately upon entering Gotham City (and running our program using the SimCityRun file to run it on Eclipse) you will hear
the Batman soundtrack, which will immediately immerse yourself into the darkness, chaos, and unpredictability that is Gotham City.

####For V1
NOTE: For V1 we did not use any code that was given out publicly via professors or CPs and designed and built everything from scratch on our own.

As you can see in our GUI we have 4 different types of buildings: Market (top right), Bank (top middle), Restaurant (top left and sides), Homes (bottom row).

When you click on a building, the screen zooms in and gets replaced with the GUI and animation of that building. There is a small black box on the top left of each zoomed in building view that can be clicked to return to the city view.

To run a simulation of our city use the add Person button on the top right of the frame.

To simplify testing we have our buildings populated with Robots instead of PersonAgents.  Robots are functionally the same as PersonAgents but their schedulers only run through job-related commands.

Our character that moves around is batman. We underlayed fillRect behind the batman characters to distinguish the roles and customers from the robots.

When a person is added you can set the amount of money they have using a slider on the pop up menu.  The minimum amount of
money a person can start with is $250 and the maximum is $1000.

There is only 1 Home that can be occupied (it is the middle home on the GUI). If a person is added after that home is occupied he will live in the "batcave" which is in the bottom left of the screen.

#####Home:
Currently there is one Home is Gotham city.  The first person that is created in the city belongs to that home.  When the personAgent enters the home he turns into a residentRole.  The person will go home if it is hungry and doesnt have enough money to eat at a restaurant or if it has groceries from the market and needs to put them in the fridge.
######HomeResident Role:
There are three main things the resident can do once it is in the home.  When it enters the home the first thing that it does is check its mailbox.  The resident checks his mailbox for rentBills to see if he has to pay his landlord if he doesnt own his home.  If he has a rentBill then he will go pay the bill (go to bank).  The next thing the  resident will do is check if his groceryBag is not empty.  If the grocerBag is not empty then the resident will go to the fridge to put the groceries in the fridge and replenish the fridge foods.  If the resident ever gets hungry, which is determined by the city clock then the resident will check the fridge to see if has food.  If the fridge is out of food the resident will go to the market to pick up groceries and a restaurant to fulfill its hunger.  If the fridge is not empty then the resident will choose a food, cook it, plate it, take it to the table, eat it, and clear it from the table.  When the resident is not doing anything then the resident will go to the couch.

######Landlord Role:
The landlord has a list of home owned. When the cityclock timer hits 12 or noon then the clock timer will send a message to for the landlord to send the rentbills out.  The landlord will then send the rentbills to the mailbox of all of the residents of the homes the landlord owns.

There is only one Home in our city and only one new person can live there.  If more than one person in our city is created, the others are spawned in a temporary location that we call the batcave.  Obviously this will be changed for V2.

  * Everything to do with the Home and resident and landlord was designed and implemented by Evan.

#####Bank:
If the person's money is low, or high, or he needs to pay a bill he will go the the bank.  In the bank there is a greeter and a teller.
######BankGreeterRole: 
The GreeterRole greets the customers and manages the customers and tellers.  When customers come in the gretter puts them in a waiting line in fron of the bank counter.  The greeter notifies a teller if they are needed when a customer comes into the bank and sends the customer to the teller.
######BankCustomerRole: 
The customer has a list of transaction types and tells the teller which transaction they want to make.
These transaction include: deposit, withdrawal, opening account, closing account, need a loan, paying rent bill, leaving.
When the bank customer comes in to make a withdrawal and the customer does not have a bank account, the teller will open a bank account for that customer
######BankTellerRole: 
The TellerRole gets messaged if a customer needs a transaction.  The teller handles each transaction and every transaction that the bank customer needs.  The teller checks a BankDatabase to get the info for the customer they are handling. For example if the customer wants to take out a loan, the teller will check the database to see if they have enough money in their account to be eligible for a loan.

  * Everything to do with the Bank was designed and implemented by Brice.

#####Market: 
There is a cashier and worker in a market. Marker accepts two types of orders. First one customer orders, the second is restaurant orders. Customers walks to market, stay in a line, by telling market cashier that he needs food, when cashier asks him what he needs he walks to the cashier, passes his orders pays, and moves to get his items. While customer is paying worker walks to bring food to the customer. When customer gets his food he leaves the market. Restaurant cooks "call" market and give order, cashier interacts with restaurant cashier, and worker sends food to cook.

######CashierRole: 
greets customers, accepts orders from customers and retsaurant cooks, Accepts payments, gives change.
Also, cashier makes worker bring or send food depending on order.

######MarketWorkerRole: 
Brings or sends food to restaurant or customer after cashier tells to do so. For now, worker "sends" food without trucks, food magically appears in restaurants.
  
#####Market: 
There is a cashier and worker in a market. Marker accepts two types of orders. First one customer orders, the second is restaurant orders. Customers walks to market, stay in a line, by telling market cashier that he needs food, when cashier asks him what he needs he walks to the cashier, passes his orders pays, and moves to get his items. While customer is paying worker walks to bring food to the customer. When customer gets his food he leaves the market. Restaurant cooks "call" market and give order, cashier interacts with restaurant cashier, and worker sends food to cook.

######CashierRole: 
greets customers, accepts orders from customers and retsaurant cooks, Accepts payments, gives change.
Also, cashier makes worker bring or send food depending on order.

######MarketWorkerRole: 
Brings or sends food to restaurant or customer after cashier tells to do so. For now, worker "sends" food without trucks, food magically appears in restaurants.


#####Restaurants:
Every one of our team member's restaurants are implemented into the city. We made this one of our biggest goals to complete by V1. Although including all team member's restaurants into the city was a difficult task, we thought if done successfully it would set us apart from other teams. None of our restaurants include the producer-consumer revolving stand. We knew that this would not be important to the main functionality of the city and we wanted to focus on getting our city running before focusing on smaller details like this. This is something we decided early on was not vital to our V1 and could be easily implemented in V2.

  + Mika's restaurant: location=left bottom
  + Nikhil's restaurant: location=top left
  + Evan's restaurant: location=right top
  + William's restaurant: location=right bottom
  + Brice's restaurant: location=left top

Mika's restaurant is the only restaurant that can successfully order from the market when the cook runs out of inventory. The other restaurants have enough inventoy so it is not necessary. We do not animate this transaction.

#####PersonAgent:

#####GUI:
Hunter (William) created all of the GUI elements from scratch and spend a large amount helping others with integration and Git.

The super sexy looking main screen elements were created by Brice while the skeleton for implementing them was created by Hunter.

######SimCityRun:
This class creates a frame containing all of the JPanels and runs the program.

######SimCityPanel:
This is the Panel that contains all things related to animation including all of the Screen classes that will be discussed below. Every 10ms the program will update the current display and the positions of all the agent gui elements.  This class also checks where the mouse is clicked and updates the Screen accordingly. 

######Screen:
The Screen class is the crucial class to the Gui implementation. A Screen is populated with Gui elements and backgroundImages. These elements with then be displayed only when that particular screen is activated.  There are multiple classes that inherit from Screen:

  + MainScreen (The "city-level" screen)
  + BankAnimationPanel (Interior bank screen)
  + HomeAnimationPanel (Interior home screen)
  + MarketAnimationPanel (Interior market screen)
  + The individual restaurant animation panels


######ScreenFactory:
The ScreenFactory class was inspired by the RoleFactory discussed in class.  It allows for access to each of the Screens of the simulation if given a String.

######InfoPanel:
There is an information panel at the bottom of the window that contains information specific to the PersonAgent selected in the side pane.  Right now only name, location, and current money is listed.  In future versions this panel will allow for a user to override the scheduler of the person and cause him to perform certain actions.


######PersonSelectionPane:
This side pane is much similar to the ones in our restaurant simulation. When a new person is added to the simulation he is added to this panel. If you click on the button associated with the personAgent, its information will be displayed on the infoPanel.  There were plans for the personSelectionPane to only display persons that are currently on the screen and also to highlight the person when they are selected. These ideas will be implemented in later versions.


######NewPersonWindow:
When the Add Person button is pressed, a new window is opened that allows the user to customize the aspects of a given personAgent. Upon pressing done this person will be added to the simulation.  Only one newPerson window can be open at a time. These design decisions inspired my idea and implementation of the Robot class.


#####Core Data

PersonAgent:

Role:

CityClock timer:


We weren't able to find out the perfect "hour length" for our program to run realistically.  By that we mean, the program may run in a loop (e.g. the person keeps going to the restaurant and to the home repeatedly because every four hours he gets hungry and each hour could be 5 seconds but in 20 seconds he will need to go to the restaurant to eat again) due to short hour lengths. We kept it short for faster testing (and also for faster grading) since watching a program simulate for 5-15 minutes (representative of a full day) can take a toll on productivity.

RoleFactory:

#####Weird things.
  +Why there is a gettingStarted.pdf from dropbox? Nobody knows.

#####For V2

We are really excited about the many feature creeps that have come up as we have been building our city that we not necessarily have been able to implement because the infrastructure was not complete but time permitting we plan to implement in our second version (i.e. easter eggs).

We set up a lot of code to prepare for V2 even though it was not implemented in V1.
Although we have robots in now, all of the jobs are set up for roles so we can easily transition to sending people to work
to populate the business and have them running in V2.


