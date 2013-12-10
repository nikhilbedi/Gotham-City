package Gui;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class CityTabbedPane extends JPanel {
	JTabbedPane tabbedPane;
	public CityTabbedPane() {
		super(new GridLayout(1, 1));

		tabbedPane = new JTabbedPane();
		add(tabbedPane);
		
		setPreferredSize(new Dimension(200, 860));
		//animation.setMaximumSize(new Dimension(800, 800));
		setMinimumSize(new Dimension(200, 860));

		//The following line enables to use scrolling tabs.
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	}
	public void addPanel(JPanel panel, String s){
		tabbedPane.addTab(s, panel);
	}
	protected JComponent makeTextPanel(String text) {
		JPanel panel = new JPanel(false);
		JLabel filler = new JLabel(text);
		filler.setHorizontalAlignment(JLabel.CENTER);
		panel.setLayout(new GridLayout(1, 1));
		panel.add(filler);
		return panel;
	}
}