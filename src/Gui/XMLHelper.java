package Gui;


import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import simcity.CityClock;
import simcity.PersonAgent;
import simcity.PersonGui;

/**
 * A class that assists in using XML files for simcity
 * @author nikhil
 *
 */

public class XMLHelper { 
	
	/**
	 * Using a custom standard, this function generates people and adds them to simcity using data from a file
	 * @param filepath The file's name, which currently must be located under a source folder (and under "bin/" in the workspace)
	 */
	public static void createPeople(String filepath) {
		
		MainScreen mainScreen = SimCityPanel.getCityScreen();
		
		try {
			File fXmlFile = new File("bin/" + filepath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

			System.out.println("Root element :"
					+ doc.getDocumentElement().getNodeName());

			NodeList nList = doc.getElementsByTagName("person");

			System.out.println("----------------------------");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				System.out.println("Current Element: " + nNode.getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					//Get the name
					System.out.println("Person Name: "
							+ eElement.getAttribute("name"));
					String name = eElement.getAttribute("name");

					//Get the money
					System.out.println("Money: "
							+ eElement.getElementsByTagName("money").item(0)
							.getTextContent());
					double money = Double.parseDouble(eElement.getElementsByTagName("money").item(0).getTextContent());
					
					//Get what kind of job (or 'no job') ID = workplace
					//Shift
					//Get what home he lives in (or 'no home')
					//Get whatever properties may be owned (for loop)
					//Get preferred transportation (walk, car, bus)

					
					// Creating a person agent using data from XML
					PersonGui personGui = new PersonGui();
					PersonAgent personXML = new PersonAgent(name, personGui, mainScreen);
					personGui.setAgent(personXML);
					personXML.setGui(personGui);	
					personXML.setRestaurants(mainScreen.getRestaurantList());
					personXML.setMarkets(mainScreen.getMarketList());
					personXML.setBank(mainScreen.getBank());
					mainScreen.addGui(personGui);
					CityClock.addPersonAgent(personXML);
					personXML.addMoney(money); 
					personXML.startThread();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Utilizing what buildings are available and what workers are needed for a full city,
	 * this function creates an XML that fulfills every need
	 */
	public static void generateFullCityFile() {

	}
}
