package simcity.Home.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import simcity.PersonAgent;
import simcity.Home.Food;
import simcity.Home.Home;
import simcity.Home.ResidentRole;
import simcity.Home.gui.ResidentGui;


public class HomePanel extends JFrame{
        AnimationPanel animationPanel = new AnimationPanel();
        PersonAgent agentCust = new PersonAgent("Resident");
        ResidentRole resident = new ResidentRole(agentCust);
        ResidentGui residentGui = new ResidentGui(resident);
        //private List<String> foods = new ArrayList<String>();
        Home home = new Home("House_1");
        public HomePanel(){
                
                resident.setGui(residentGui);
                animationPanel.addGui(residentGui);
                agentCust.addRole(resident);
                setTitle("House");
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setVisible(true);
                add(animationPanel);
                agentCust.startThread();
        //        System.out.println(agentCust.roles.size());
                /*
                foods.add("Chicken");
                foods.add("Rice");
                marketCustomer.getGroceries(foods);
                marketCustomer2.getGroceries(foods);
                        Item beef = new Item("Beef", 10.99, 10);
                        Item chicken = new Item("Chicken", 8.99, 10);
                        Item rice = new Item("Rice", 6.99, 10);
                        Item potato = new Item("Potato", 5.99, 10);
                        marketCashier.getInventory().put("Beef", beef);
                        marketCashier.getInventory().put("Chicken", chicken);
                        marketCashier.getInventory().put("Rice", rice);
                        marketCashier.getInventory().put("Potato", potato);
                        marketWorker.getInventory().put("Beef", beef);
                        marketWorker.getInventory().put("Chicken", chicken);
                        marketWorker.getInventory().put("Rice", rice);
                        marketWorker.getInventory().put("Potato", potato);
                market.setCashier(marketCashier);
                market.addWorker(marketWorker);
                */
                
        }
        
        
}