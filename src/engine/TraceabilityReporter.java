package engine;

import model.Requirement;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Ansvarlig for å generere rapporter og eksportere data
 * fra sporbarhetsanalysen av missilsystemet.
 */
public class TraceabilityReporter {

    /**
     * Genererer en Markdown-fil med en detaljert oversikt over alle krav,
     * inkludert risikovurdering og kvalitetsstatus.
     * * @param requirements Listen med krav som skal inkluderes i rapporten.
     * @param filePath Stien til filen som skal opprettes.
     */
    public void generateMarkdownReport(List<Requirement> requirements, String filePath) {
        StringBuilder sb = new StringBuilder();
        sb.append("# Missile System Traceability & Risk Report\n\n");
        
        sb.append("| ID | Subsystem | Risk Level | Quality | Compliant | Description |\n");
        sb.append("|----|-----------|------------|---------|-----------|-------------|\n");

        for (Requirement req : requirements) {
            sb.append("| ").append(req.getId())
              .append(" | ").append(req.getSubsystem())
              .append(" | ").append(req.getRiskLevel())
              .append(" | ").append(req.getQualityStatus())
              .append(" | ").append(req.isCompliant() ? "YES" : "NO")
              .append(" | ").append(req.getText())
              .append(" |\n");
        }

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(sb.toString());
            System.out.println("Military-grade report generated: " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing report: " + e.getMessage());
        }
    }

    /**
     * Eksporterer analyseresultatene til en JSON-fil for bruk i andre systemer.
     * Inkluderer risikonivå og kvalitetsstatus for hvert krav.
     * * @param requirements Listen med krav som skal eksporteres.
     * @param filePath Stien til JSON-filen som skal opprettes.
     */
    public void exportToJson(List<Requirement> requirements, String filePath) {
        StringBuilder json = new StringBuilder();
        json.append("[\n");

        for (int i = 0; i < requirements.size(); i++) {
            Requirement req = requirements.get(i);
            json.append("  {\n");
            json.append("    \"id\": \"").append(req.getId()).append("\",\n");
            json.append("    \"subsystem\": \"").append(req.getSubsystem()).append("\",\n");
            json.append("    \"risk\": \"").append(req.getRiskLevel()).append("\",\n");
            json.append("    \"quality\": \"").append(req.getQualityStatus()).append("\",\n");
            json.append("    \"compliant\": ").append(req.isCompliant()).append(",\n");
            json.append("    \"description\": \"").append(req.getText().replace("\"", "\\\"")).append("\"\n");
            json.append("  }");
            
            if (i < requirements.size() - 1) {
                json.append(",");
            }
            json.append("\n");
        }

        json.append("]");

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(json.toString());
            System.out.println("JSON export completed: " + filePath);
        } catch (IOException e) {
            System.err.println("Error exporting JSON: " + e.getMessage());
        }
    }
}