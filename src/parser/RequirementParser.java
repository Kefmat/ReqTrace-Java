package parser;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import model.Requirement;

/**
 * Ansvarlig for å lese og tolke XML-filer som inneholder systemkrav.
 */
public class RequirementParser {

    /**
     * Leser en XML-fil og returnerer en liste med krav.
     * @param filePath Stien til XML-filen.
     * @return En liste over Requirement-objekter.
     */
    public List<Requirement> parse(String filePath) { // Endret navn fra parseRequirements til parse
        List<Requirement> requirements = new ArrayList<>();
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                System.err.println("Error: XML file not found.");
                return requirements;
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("ITEM");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    
                    String id = element.getAttribute("ID");
                    String text = element.getElementsByTagName("TEXT").item(0).getTextContent();
                    String subsystem = element.getElementsByTagName("SUBSYSTEM").item(0).getTextContent();
                    String priority = element.getElementsByTagName("PRIORITY").item(0).getTextContent();

                    requirements.add(new Requirement(id, text, subsystem, priority));
                }
            }
        } catch (Exception e) {
            System.err.println("Error parsing XML: " + e.getMessage());
        }
        return requirements;
    }
}