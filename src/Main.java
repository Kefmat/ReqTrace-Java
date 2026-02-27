import model.Requirement;
import parser.RequirementParser;
import engine.TraceabilityReporter;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting ReqTrace-Java Analysis...");

        RequirementParser parser = new RequirementParser();
        TraceabilityReporter reporter = new TraceabilityReporter();

        List<Requirement> reqs = parser.parseRequirements("data/system_reqs.xml");

        if (reqs.isEmpty()) {
            System.out.println("Analysis aborted: No requirements found.");
            return;
        }

        reporter.generateMarkdownReport(reqs, "Traceability_Report.md");
        reporter.exportToJson(reqs, "data/analysis_output.json");

        System.out.println("Processing complete. Report and JSON export generated.");
    }
}