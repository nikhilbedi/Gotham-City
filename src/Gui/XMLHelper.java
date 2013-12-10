package Gui;

import agent.Role;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import simcity.Building;
import simcity.CityClock;
import simcity.PersonAgent;
import simcity.PersonGui;
import simcity.TheCity;
//import simcity.TheCity;
import simcity.Home.Home;

/**
 * A class that assists in using XML files for simcity
 * @author nikhil
 *
 */

public class XMLHelper { 

	/**
	 * Using a custom standard, this function generates people and adds them to simcity using data from a file
	 * @param filepath The file's name, which currently must be located under a source folder (and under "bin/" in the eclipse workspace)
	 */
	public static void createPeople(String filepath) {

		MainScreen mainScreen = ScreenFactory.getMainScreen();

		try {
			File fXmlFile = new File("bin/" + filepath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

			System.out.println("\nRoot element: "
					+ doc.getDocumentElement().getNodeName());

			NodeList personList = doc.getElementsByTagName("person");

			System.out.println("----------------------------");

			for (int i = 0; i < personList.getLength(); i++) {
				Node nNode = personList.item(i);
				System.out.println("Current Element: " + nNode.getNodeName());
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element personElement = (Element) nNode;

					//Get the name
					System.out.println("Person Name: " + personElement.getAttribute("name"));
					String name = personElement.getAttribute("name");
					
					// Creating a person agent using data from XML
					PersonGui personGui = new PersonGui();
					PersonAgent personXML = new PersonAgent(name, personGui, mainScreen);
					personGui.setAgent(personXML);
					personXML.setGui(personGui);	
					personXML.setRestaurants(TheCity.getRestaurantList());
					personXML.setMarkets(TheCity.getMarketList());
					personXML.setBank(TheCity.getBank());
					personXML.setGrid(TheCity.getGrid());
					mainScreen.addGui(personGui);
					CityClock.addPersonAgent(personXML);

					//Get the money
					System.out.println("Money: "+ personElement.getElementsByTagName("money").item(0).getTextContent());
					double money = Double.parseDouble(personElement.getElementsByTagName("money").item(0).getTextContent());
					personXML.addMoney(money); 
					
					//Get what kind of job (or 'no job')
					Element jobElement = (Element) personElement.getElementsByTagName("job").item(0);
					if(jobElement != null){
						System.out.println("Job building: " + jobElement.getElementsByTagName("building").item(0).getTextContent());
						Building workplace = TheCity.getBuildingFromString(jobElement.getElementsByTagName("building").item(0).getTextContent());
						System.out.println("Job position: " + jobElement.getElementsByTagName("position").item(0).getTextContent());
						Role jobRole = workplace.getRoleFromString(jobElement.getElementsByTagName("position").item(0).getTextContent());
						System.out.println("Job shift: " + jobElement.getElementsByTagName("shift").item(0).getTextContent());
						int shift = Integer.parseInt(jobElement.getElementsByTagName("shift").item(0).getTextContent());
						//Set the job here
						personXML.setJob(jobRole, workplace, shift);
					}
					
					//Get what home he lives in (or 'no home')
					Element homeElement = (Element) personElement.getElementsByTagName("residency").item(0);
					Building home;
					if(homeElement != null) {
						System.out.println("Resident building: " + homeElement.getElementsByTagName("home").item(0).getTextContent());
						home = TheCity.getBuildingFromString(homeElement.getElementsByTagName("home").item(0).getTextContent());
						personXML.setHome((Home)home);
					}

					//Get whatever properties may be owned (for loop)
					Element propertyElement = (Element) personElement.getElementsByTagName("property").item(0);
					if(propertyElement != null) {
						NodeList propertiesList = propertyElement.getElementsByTagName("address");
						System.out.println("Number of properties owned: " + propertiesList.getLength());
						for(int j = 0; j < propertiesList.getLength(); j++) {
							System.out.println("\tProperty: " + propertiesList.item(j).getTextContent());
						}
					}

					//Get preferred transportation (walk, car, bus)
					//If it is a car, the person is spawned owning a car
					System.out.println("Transportation preference: "+ personElement.getElementsByTagName("transportation").item(0).getTextContent());

					
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
