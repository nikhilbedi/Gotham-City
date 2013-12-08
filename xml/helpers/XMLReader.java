package helpers;


import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import simcity.CityClock;
import simcity.PersonAgent;
 
/**
 * A class that assists in reading premade XML files
 * @author nikhil
 *
 */

public class XMLReader {
	public static void createPeople(String filepath) {
		try {
			File fXmlFile = new File("xml/" + filepath);
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

					System.out.println("Person Name: "
							+ eElement.getAttribute("name"));
					String name = eElement.getAttribute("name");
					System.out.println("Money: "
							+ eElement.getElementsByTagName("money").item(0)
									.getTextContent());
					// Creating a person agent using data from XML
					PersonAgent personXML = new PersonAgent(name);
					CityClock.addPersonAgent(personXML);
					personXML.startThread();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
