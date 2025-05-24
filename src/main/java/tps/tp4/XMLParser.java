package tps.tp4;

import java.io.File;
import java.nio.file.Files;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import tps.tp4.settings.Settings;

public class XMLParser {

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

    public static void writeXMLSettings(String path) throws Exception {
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
        }
        Files.copy(new File(Settings.USER_SETTINGS_FILE).toPath(), file.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
    }

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
}
