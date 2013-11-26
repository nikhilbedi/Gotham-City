package Gui;


import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Subpanel of restaurantPanel.
 * This holds the scroll panes for the customers and, later, for waiters
 */
public class PersonSelectionPane extends JPanel implements ActionListener {

	public JScrollPane pane =
			new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

	private JPanel view = new JPanel();//Panel that displays the list of buttons

	private List<JButton> list = new ArrayList<JButton>();//Tracks the buttons to allow for actionlistener use



	private JButton newPersonButton = new JButton("add person");//New person button
	NewPersonWindow npWindow; //Window created when you click the button
	


	public PersonSelectionPane() {
		//format the button a bit
		newPersonButton.addActionListener(this);
		newPersonButton.setAlignmentX(CENTER_ALIGNMENT);


		setLayout(new BoxLayout((Container) this, BoxLayout.Y_AXIS));
		//add(new JLabel("<html><pre> <u>" + type + "</u><br></pre></html>"));

		view.setLayout(new BoxLayout((Container) view, BoxLayout.Y_AXIS));
		view.add(newPersonButton);
		pane.setViewportView(view);
		add(pane);
	}

	/**
	 * Method from the ActionListener interface.
	 * Handles the event of the add button being pressed
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == newPersonButton ){
			System.out.println("howdy partner");
			npWindow = new NewPersonWindow(ScreenFactory.getMainScreen(), newPersonButton);
			npWindow.setVisible(true);
			newPersonButton.setEnabled(false);
		}
		else {
			for (JButton temp:list){
				if (e.getSource() == temp){}
					//restPanel.showInfo(type, temp.getText());
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
	public void addPerson(String name, boolean hungry) {
		if (name != null) {
			String temp;


			JButton button = new JButton(name);
			button.setBackground(Color.white);
			Dimension paneSize = pane.getSize();
			Dimension buttonSize = new Dimension(paneSize.width - 20,
					(int) (paneSize.height / 7));
			button.setPreferredSize(buttonSize);
			button.setMinimumSize(buttonSize);
			button.setMaximumSize(buttonSize);
			button.addActionListener(this);
			list.add(button);
			view.add(button);

			validate();
		}
	}
}
