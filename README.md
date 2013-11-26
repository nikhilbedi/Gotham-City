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
NOTE:For V1 we did not use any code that was given out publicly via professors or CPs and designed and built everything from scratch on our own.

As you can see in our GUI we have 4 different types of buildings: Market (top right), Bank (top middle), Restaurant (top left and sides), Homes (bottom row).

When you click on a building, the screen zooms in and gets replaced with the GUI and animation of that building. There is a small black box on the top left of each zoomed in building view that can be clicked to return to the city view.

To run a simulation of our city use the add Person buttom on the 

Our character that moves around is batman. We underlayed fillRect behind the batman characters to distinguish the roles and customers from the robots.

When a person is added you can set the amount of money they have using a slider on the pop up menu.  The minimum amount of
money a person can start with is $250 and the maximum is $1000.
#####Home:
######HomeResident Role:
######Landlord Role:

There is only one Home in our city and only one new person can live there.  If more than one person in our city is created, the others are spawned in a temporary location
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
  
#####Market:
######CashierRole:
######MarketWorkerRole:
#####Restaurants:
Every one of our team member's restaurants are implemented into the city. We made this one of our biggest goals to complete by V1. Although including all team member's restaurants into the city was a difficult task, we thought if done successfully it would set us apart from other teams. None of our restaurants include the producer-consumer revolving stand. We knew that this would not be important to the main functionality of the city and we wanted to focus on getting our city running before focusing on smaller details like this. This is something we decided early on was not vital to our V1 and could be easily implemented in V2.

  + Mika's restaurant: location=left bottom
  + Nikhil's restaurant: location=top left
  + Evan's restaurant: location=right top
  + William's restaurant: location=right bottom
  + Brice's restaurant: location=left top

Mika's restaurant is the only restaurant that can successfully order from the market when the cook runs out of inventory. The other restaurants have enough inventoy so it is not necessary.

#####PersonAgent:

#####GUI:
Hunter
ScreenFactory:

CityClock timer:


We weren't able to find out the perfect "hour length" for our program to run realistically.  By that we mean, the program may run in a loop (e.g. the person keeps going to the restaurant and to the home repeatedly because every four hours he gets hungry and each hour could be 5 seconds but in 20 seconds he will need to go to the restaurant to eat again) due to short hour lengths. We kept it short for faster testing (and also for faster grading) since watching a program simulate for 5-15 minutes (representative of a full day) can take a toll on productivity.

RoleFactory:

#####For V2

We are really excited about the many feature creeps that have come up as we have been building our city that we not necessarily have been able to implement because the infrastructure was not complete but time permitting we plan to implement in our second version (i.e. easter eggs).

We set up a lot of code to prepare for V2 even though it was not implemented in V1.
Although we have robots in now, all of the jobs are set up for roles so we can easily transition to sending people to work
to populate the business and have them running in V2.


