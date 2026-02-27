package parser;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import model.Requirement;

public class RequirementParser {
    public List<Requirement> parseRequirements(String filePath) {
        List<Requirement> requirements = new ArrayList<>();
        try {
            File file = new File(filePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("ITEM");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                
                String id = element.getAttribute("ID");
                String text = element.getElementsByTagName("TEXT").item(0).getTextContent();
                String subsystem = element.getElementsByTagName("SUBSYSTEM").item(0).getTextContent();
                String priority = element.getElementsByTagName("PRIORITY").item(0).getTextContent();

                requirements.add(new Requirement(id, text, subsystem, priority));
            }
        } catch (Exception e) {
            System.err.println("Error parsing XML: " + e.getMessage());
        }
        return requirements;
    }
}