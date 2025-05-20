package tps.tp4;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public class XMLParser {
    
    public static Colony parseColony(File xmlFile) {
        try {
            DocumentBuilderFactory DBFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = DBFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            // TODO: Parse the XML document and create a Colony object
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

}
