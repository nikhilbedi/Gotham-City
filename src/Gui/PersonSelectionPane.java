package Gui;


import javax.swing.*;

import agent.Role;
import simcity.CityClock;
import simcity.PersonAgent;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Subpanel of restaurantPanel.
 * This holds the scroll panes for the customers and, later, for waiters
 */
public class PersonSelectionPane extends JPanel implements ActionListener {
	private boolean firstTime = true;
	public JScrollPane pane =
			new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

	private JPanel view = new JPanel();//Panel that displays the list of buttons

	private List<JButton> list = new ArrayList<JButton>();//Tracks the buttons to allow for actionlistener use



	private JButton newPersonButton = new JButton("add person");//New person button
	private JButton refresh = new JButton("temp refresher");
	NewPersonWindow npWindow; //Window created when you click the button

	SimCityPanel city;

	InfoPanel info;
	
	//default name iterator info
	int newPersonCounter = 0;
	String defaultName = "Person 0";
	
	public PersonSelectionPane(SimCityPanel p) {
		setPreferredSize(new Dimension(150,600));
		setMinimumSize(new Dimension(150,600));
		setMaximumSize(new Dimension(150,600));
		
		//format the button a bit
		city = p;

		newPersonButton.addActionListener(this);
		newPersonButton.setAlignmentX(CENTER_ALIGNMENT);

		refresh.addActionListener(this);
		refresh.setAlignmentX(CENTER_ALIGNMENT);


		setLayout(new BoxLayout((Container) this, BoxLayout.Y_AXIS));
		//add(new JLabel("<html><pre> <u>" + type + "</u><br></pre></html>"));

		view.setLayout(new BoxLayout((Container) view, BoxLayout.Y_AXIS));
		view.add(newPersonButton);
		//view.add(refresh);

		pane.setViewportView(view);
		add(pane);
	}

	/**
	 * Method from the ActionListener interface.
	 * Handles the event of the add button being pressed
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == newPersonButton ){
			if(firstTime) {
				npWindow = new NewPersonWindow(newPersonButton, this);
				newPersonCounter++;
				defaultName = "Person " + newPersonCounter;
				npWindow.updateDefaultText(defaultName);
				npWindow.setVisible(true);
				newPersonButton.setEnabled(false);
				
			}
		}
		else {
			for (JButton temp:list){
				if (e.getSource() == temp){
					Vector<PersonAgent> peopleList = CityClock.getPeople();
					for(PersonAgent person: peopleList){
						//System.err.println("Person name:" + person.getName() + "person text " + temp.getText());
						if(person.getName() == (temp.getText())){//TODO this is soooo easy to break I hate it.
							info.updateInfoPanel(person);

						}
					}
				}
				//tell the info/interactino pane to show information for that specific person
			}
		}
	}

	/**
	 * If the add button is pressed, this function creates
	 * a spot for it in the scroll pane, and tells the restaurant panel
	 * to add a new person.
	 *
	 * @param name name of new person
	 */
	public void addPerson(String name) {
		if (name != null) {
			JButton button = new JButton(name);
			button.setBackground(Color.white);
			Dimension paneSize = pane.getSize();
			Dimension buttonSize = new Dimension(paneSize.width - 20,
					(int) (paneSize.height / 20));
			button.setPreferredSize(buttonSize);
			button.setMinimumSize(buttonSize);
			button.setMaximumSize(buttonSize);
			button.addActionListener(this);
			button.setAlignmentX(CENTER_ALIGNMENT);
			list.add(button);
			view.add(button);

			validate();
		}
	}
	public void refresh(){
		view.removeAll();
		view.add(newPersonButton);
		list.clear();
		Vector<PersonAgent> peopleList = CityClock.getPeople();
		for(PersonAgent person: peopleList){
			addPerson(person.getName());
		}
	}

	public void setInfoPanel(InfoPanel info) {
		this.info = info;

	}

	/*public void refresh(List<RoleGui> guis) {
		view.removeAll();
		view.add(newPersonButton);
		list.clear();
		for(RoleGui gui: guis){
			if(gui.role.getPersonAgent() != null){
				addPerson(gui.role.getPersonAgent().getName());
			}
		}
		
	}*/

	//this is old code to get all of the person gui elements on screen
	/*public void refresh(){
		view.removeAll();
		list.clear();
		List<RoleGui> guiList = city.currentScreen.getGuis();//List of guis that should be on screen
		Vector<PersonAgent> peopleList = CityClock.getPeople();

		for(PersonAgent person: peopleList){//go over every person 
			List<Role> roles = person.getRoles();
			Role activeRole = null;//find that person's active role
			for(Role r: roles){
				if(r.isActive()){
					activeRole = r;
				}
			}
			RoleGui activeRoleGui = activeRole.getGui();//gets the gui of the active role
			for(RoleGui gui: guiList){//Go over the list of currently displayed guis
				if(gui.equals(activeRoleGui)){//See if that gui is current being displayed
					addPerson(person.getName());//here is where the person is finally added
				}
			}
		}
	}*/
}

