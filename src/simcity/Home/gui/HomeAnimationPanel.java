package simcity.Home.gui;

import javax.swing.*;

import simcity.PersonAgent;
import simcity.Home.Home;
import simcity.Home.ResidentRole;
import Gui.Screen;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class HomeAnimationPanel extends Screen {

    public HomeAnimationPanel() {
           populate(); 
    }


    public void populate() {
    	PersonAgent agentCust = new PersonAgent("Resident");
        ResidentRole resident = new ResidentRole(agentCust);
        ResidentGui residentGui = new ResidentGui(resident);
        resident.setGui(residentGui);
        agentCust.addRole(resident);
        agentCust.startThread();
        
        addGui(residentGui);
        resident.gotHungry();
        
        //Home home = new Home("House_1");
    }

}