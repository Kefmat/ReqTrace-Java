import model.Requirement;
import parser.RequirementParser;
import engine.TraceabilityReporter;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Initialiser komponenter
        RequirementParser parser = new RequirementParser();
        TraceabilityReporter reporter = new TraceabilityReporter();

        // Parse data fra XML   
        System.out.println("Reading system requirements...");
        List<Requirement> reqs = parser.parseRequirements("data/system_reqs.xml");

        // Generer rapport
        System.out.println("Analyzing traceability and compliance...");
        reporter.generateMarkdownReport(reqs, "Traceability_Report.md");

        System.out.println("Process complete.");
    }
}