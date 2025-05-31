package tps.tp4;

import java.io.File;
import java.nio.file.Files;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import tps.tp4.errors.FileLoadException;
import tps.tp4.settlers.Settler;
import tps.tp4.structures.Structure;
import tps.tp4.structures.StructureFactory;
import tps.tp4.structures.StructureTypes;

/**
 * Utility class for parsing and writing XML files for settings and colonies.
 */
public class XMLParser {

    /**
     * Loads game settings from an XML file and updates the Settings class.
     * @param file The XML file to load.
     * @throws Exception If loading or parsing fails.
     */
    public static void loadXMLSettings(File file) throws Exception {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setValidating(true);     
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);
        doc.getDocumentElement().normalize();

        XPathFactory xpFactory = XPathFactory.newInstance();
        XPath xpath = xpFactory.newXPath();

        Settings.INITIAL_MAX_POPULATION =  Integer.parseInt((String) xpath.evaluate("/settings/initial/maxPopulation", doc, XPathConstants.STRING));
        Settings.INITIAL_WOOD = Integer.parseInt((String) xpath.evaluate("/settings/initial/wood", doc, XPathConstants.STRING));
        Settings.INITIAL_FOOD = Integer.parseInt((String) xpath.evaluate("/settings/initial/food", doc, XPathConstants.STRING));
        Settings.INITIAL_STONE = Integer.parseInt((String) xpath.evaluate("/settings/initial/stone", doc, XPathConstants.STRING));
        Settings.INITIAL_METAL = Integer.parseInt((String) xpath.evaluate("/settings/initial/metal", doc, XPathConstants.STRING));
        Settings.INITIAL_ENTERTAINMENT = Integer.parseInt((String) xpath.evaluate("/settings/initial/entertainment", doc, XPathConstants.STRING));
        Settings.INITIAL_WOOD_PRODUCTION = Integer.parseInt((String) xpath.evaluate("/settings/initial/woodProduction", doc, XPathConstants.STRING));
        Settings.INITIAL_FOOD_PRODUCTION = Integer.parseInt((String) xpath.evaluate("/settings/initial/foodProduction", doc, XPathConstants.STRING));
        Settings.INITIAL_STONE_PRODUCTION = Integer.parseInt((String) xpath.evaluate("/settings/initial/stoneProduction", doc, XPathConstants.STRING));
        Settings.INITIAL_METAL_PRODUCTION = Integer.parseInt((String) xpath.evaluate("/settings/initial/metalProduction", doc, XPathConstants.STRING));

        Settings.HOUSE_COST = extractAttrhelper(xpath, doc, "/settings/building[@name='house']/cost/@*");
        Settings.HOUSE_PRODUCTION = extractAttrhelper(xpath, doc, "/settings/building[@name='house']/production/@*");
        Settings.HOUSE_POPULATION_INCREASE = Integer.parseInt((String) xpath.evaluate("/settings/building[@name='house']/populationIncrease", doc, XPathConstants.STRING));

        Settings.FARM_COST = extractAttrhelper(xpath, doc, "/settings/building[@name='farm']/cost/@*");
        Settings.FARM_PRODUCTION = extractAttrhelper(xpath, doc, "/settings/building[@name='farm']/production/@*");

        Settings.FACTORY_COST = extractAttrhelper(xpath, doc, "/settings/building[@name='factory']/cost/@*");
        Settings.FACTORY_PRODUCTION = extractAttrhelper(xpath, doc, "/settings/building[@name='factory']/production/@*");

        Settings.HOSPITAL_COST = extractAttrhelper(xpath, doc, "/settings/building[@name='hospital']/cost/@*");
        Settings.HOSPITAL_PRODUCTION = extractAttrhelper(xpath, doc, "/settings/building[@name='hospital']/production/@*");

        Settings.WOODCUTTER_LODGE_COST = extractAttrhelper(xpath, doc, "/settings/building[@name='woodcutterLodge']/cost/@*");
        Settings.WOODCUTTER_LODGE_PRODUCTION = extractAttrhelper(xpath, doc, "/settings/building[@name='woodcutterLodge']/production/@*");

        Settings.MINE_COST = extractAttrhelper(xpath, doc, "/settings/building[@name='mine']/cost/@*");
        Settings.MINE_PRODUCTION = extractAttrhelper(xpath, doc, "/settings/building[@name='mine']/production/@*");

        Settings.BARRACKS_COST = extractAttrhelper(xpath, doc, "/settings/building[@name='barracks']/cost/@*");
        Settings.BARRACKS_PRODUCTION = extractAttrhelper(xpath, doc, "/settings/building[@name='barracks']/production/@*");
        Settings.BARRACKS_WARRIORS_INCREASE = Integer.parseInt((String) xpath.evaluate("/settings/building[@name='barracks']/warriorsIncrease", doc, XPathConstants.STRING));

        File userSettings = new File(Settings.USER_SETTINGS_FILE);
        if (!userSettings.exists()) {
            userSettings.createNewFile();
        }
        Files.copy(file.toPath(), userSettings.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * Writes the current user settings to an XML file at the specified path.
     * @param path The directory to write the settings file to.
     * @throws Exception If writing fails.
     */
    public static void writeXMLSettings(String path) throws Exception {
        File file = new File(path + "/userSettings.xml");
        if (!file.exists()) {
            file.createNewFile();
        }
        Files.copy(new File(Settings.USER_SETTINGS_FILE).toPath(), file.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * Helper method to extract integer attribute values from XML using XPath.
     * @param xpath The XPath instance.
     * @param doc The XML document.
     * @param expression The XPath expression.
     * @return An array of extracted integer values.
     */
    private static int[] extractAttrhelper(XPath xpath, Document doc, String expression) {
        try {
            NodeList nodes = (NodeList) xpath.evaluate(expression, doc, XPathConstants.NODESET);
            int[] values = new int[nodes.getLength()];
            for (int i = 0; i < nodes.getLength(); i++) {
                values[i] = Integer.parseInt(nodes.item(i).getNodeValue());
            }
            return values;
        } catch (Exception e) {
            e.printStackTrace();
            return new int[0];
        }
    }

    /**
     * Parses a colony from an XML file and returns a Colony object.
     * @param colonyFile The XML file containing the colony data.
     * @return The parsed Colony object.
     * @throws FileLoadException If parsing fails.
     */
    public static Colony parseColony(File colonyFile) throws FileLoadException {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setValidating(true);
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(colonyFile);
            doc.getDocumentElement().normalize();

            // Colony
            String colonyName = doc.getElementsByTagName("colonyName").item(0).getTextContent();
            Colony colony = new Colony(colonyName);

            // Current day
            int currDay = Integer.parseInt(doc.getElementsByTagName("currDay").item(0).getTextContent());
            colony.setCurrDay(currDay);

            // Resources
            Element resources = (Element) doc.getElementsByTagName("resources").item(0);
            colony.setWood(Integer.parseInt(resources.getElementsByTagName("wood").item(0).getTextContent()));
            colony.setFood(Integer.parseInt(resources.getElementsByTagName("food").item(0).getTextContent()));
            colony.setStone(Integer.parseInt(resources.getElementsByTagName("stone").item(0).getTextContent()));
            colony.setMetal(Integer.parseInt(resources.getElementsByTagName("metal").item(0).getTextContent()));
            colony.setEntertainment(Integer.parseInt(resources.getElementsByTagName("entertainment").item(0).getTextContent()));

            // Production
            Element production = (Element) doc.getElementsByTagName("production").item(0);
            colony.setWoodProduction(Integer.parseInt(production.getElementsByTagName("woodProduction").item(0).getTextContent()));
            colony.setFoodProduction(Integer.parseInt(production.getElementsByTagName("foodProduction").item(0).getTextContent()));
            colony.setStoneProduction(Integer.parseInt(production.getElementsByTagName("stoneProduction").item(0).getTextContent()));
            colony.setMetalProduction(Integer.parseInt(production.getElementsByTagName("metalProduction").item(0).getTextContent()));

            // Structures
            NodeList structures = doc.getElementsByTagName("structures").item(0).getChildNodes();
            for (int i = 0; i < structures.getLength(); i++) {
                if (structures.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element structureElement = (Element) structures.item(i);
                    String structureName = structureElement.getElementsByTagName("type").item(0).getTextContent();
                    int count = Integer.parseInt(structureElement.getElementsByTagName("count").item(0).getTextContent());
                    for (int j = 0; j < count; j++) {
                        Structure structure = StructureFactory.createStructure(StructureTypes.valueOf(structureName.toUpperCase()));
                        colony.getStructures().add(structure);
                    }
                }
            }

            // Settlers
            NodeList settlers = doc.getElementsByTagName("settlers").item(0).getChildNodes();
            for (int i = 0; i < settlers.getLength(); i++) {
                if (settlers.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element settlerElement = (Element) settlers.item(i);
                    String settlerName = settlerElement.getElementsByTagName("name").item(0).getTextContent();
                    Settler settler = new Settler(settlerName, colony);
                    settler.setAge(Integer.parseInt(settlerElement.getElementsByTagName("age").item(0).getTextContent()));
                    settler.setHealth(Integer.parseInt(settlerElement.getElementsByTagName("health").item(0).getTextContent()));
                    settler.setHappiness(Double.parseDouble(settlerElement.getElementsByTagName("happiness").item(0).getTextContent()));
                    settler.setEntertainmentImpact(Double.parseDouble(settlerElement.getElementsByTagName("entertainmentImpact").item(0).getTextContent()));
                    settler.setFoodImpact(Double.parseDouble(settlerElement.getElementsByTagName("foodImpact").item(0).getTextContent()));
                    settler.setHealthImpact(Double.parseDouble(settlerElement.getElementsByTagName("healthImpact").item(0).getTextContent()));

                    colony.addSettler(settler);
                }
            }

            return colony;
        } catch (Exception e) {
            e.printStackTrace();
            throw new FileLoadException("Error loading colony file: " + colonyFile.getName(), e);
        }
    }

    /**
     * Exports the given colony to an XML file at the specified export path.
     * @param colony The colony to export.
     * @param exportPath The directory to export the XML file to.
     * @throws Exception If writing fails.
     */
    public static void exportColonyToXML(Colony colony, String exportPath) throws Exception {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setValidating(true);
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // Create root element
            Element colonyElement = doc.createElement("colony");
            doc.appendChild(colonyElement);

            // Colony name
            Element colonyName = doc.createElement("colonyName");
            colonyName.appendChild(doc.createTextNode(colony.getColonyName()));
            colonyElement.appendChild(colonyName);

            // Current day
            Element currDay = doc.createElement("currDay");
            currDay.appendChild(doc.createTextNode(String.valueOf(colony.getCurrDay())));
            colonyElement.appendChild(currDay);

            // Resources
            Element resources = doc.createElement("resources");
            resources.appendChild(createElementWithText(doc, "wood", String.valueOf(colony.getWood())));
            resources.appendChild(createElementWithText(doc, "food", String.valueOf(colony.getFood())));
            resources.appendChild(createElementWithText(doc, "stone", String.valueOf(colony.getStone())));
            resources.appendChild(createElementWithText(doc, "metal", String.valueOf(colony.getMetal())));
            resources.appendChild(createElementWithText(doc, "entertainment", String.valueOf(colony.getEntertainment())));
            colonyElement.appendChild(resources);

            // Production
            Element production = doc.createElement("production");
            production.appendChild(createElementWithText(doc, "woodProduction", String.valueOf(colony.getWoodProduction())));
            production.appendChild(createElementWithText(doc, "foodProduction", String.valueOf(colony.getFoodProduction())));
            production.appendChild(createElementWithText(doc, "stoneProduction", String.valueOf(colony.getStoneProduction())));
            production.appendChild(createElementWithText(doc, "metalProduction", String.valueOf(colony.getMetalProduction())));
            colonyElement.appendChild(production);

            // Structures
            Element structures = doc.createElement("structures");
            for (Structure structure : colony.getStructures()) {
                Element structureElement = doc.createElement("structure");
                structureElement.appendChild(createElementWithText(doc, "type", structure.getName()));
                structureElement.appendChild(createElementWithText(doc, "count", String.valueOf(1))); // Assuming one of each type for simplicity
                structures.appendChild(structureElement);
            }
            colonyElement.appendChild(structures);

            // Settlers
            Element settlers = doc.createElement("settlers");
            for (Settler settler : colony.getSettlers()) {
                Element settlerElement = doc.createElement("settler");
                settlerElement.appendChild(createElementWithText(doc, "name", settler.getName()));
                settlerElement.appendChild(createElementWithText(doc, "age", String.valueOf(settler.getAge())));
                settlerElement.appendChild(createElementWithText(doc, "health", String.valueOf(settler.getHealth())));
                settlerElement.appendChild(createElementWithText(doc, "happiness", String.valueOf(settler.getHappiness())));
                settlerElement.appendChild(createElementWithText(doc, "entertainmentImpact", String.valueOf(settler.getEntertainmentImpact())));
                settlerElement.appendChild(createElementWithText(doc, "foodImpact", String.valueOf(settler.getFoodImpact())));
                settlerElement.appendChild(createElementWithText(doc, "healthImpact", String.valueOf(settler.getHealthImpact())));
                settlers.appendChild(settlerElement);
            }
            colonyElement.appendChild(settlers);

            // Write to file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(exportPath + "/" + colony.getColonyName() + ".xml"));
            transformer.transform(source, result);

        } catch (Exception e) {
            throw e;
        }
    }
    
    /**
     * Helper method to create an XML element with text content.
     * @param doc The XML document.
     * @param string The element name.
     * @param valueOf The text content.
     * @return The created Node.
     */
    private static Node createElementWithText(Document doc, String string, String valueOf) {
        Element element = doc.createElement(string);
        element.appendChild(doc.createTextNode(valueOf));
        return element;
    }
}

