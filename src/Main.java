import model.Requirement;
import parser.RequirementParser;
import engine.TraceabilityReporter;
import java.util.List;

/**
 * Hovedklassen for Missilsystemets Sporbarhetsanalyse.
 * Koordinerer parsing, analyse og generering av rapporter.
 */
public class Main {
    public static void main(String[] args) {
        RequirementParser parser = new RequirementParser();
        TraceabilityReporter reporter = new TraceabilityReporter();

        List<Requirement> requirements = parser.parse("data/system_reqs.xml");

        reporter.generateMarkdownReport(requirements, "Traceability_Report.md");
        reporter.exportToJson(requirements, "analysis_output.json");

        printMissionBriefing(requirements);
    }

    /**
     * Skriver ut en militær statusrapport basert på analysen.
     */
    private static void printMissionBriefing(List<Requirement> reqs) {
        int extreme = 0;
        int high = 0;
        int vague = 0;
        int nonCompliant = 0;

        for (Requirement r : reqs) {
            if (r.getRiskLevel().contains("EXTREME")) extreme++;
            else if (r.getRiskLevel().contains("HIGH")) high++;
            
            if (r.isVague()) vague++;
            if (!r.isCompliant()) nonCompliant++;
        }

        System.out.println("\n===========================================");
        System.out.println("   MISSILE SYSTEM ANALYSIS BRIEFING");
        System.out.println("===========================================");
        System.out.println("Totalt antall krav analysert: " + reqs.size());
        System.out.println("-------------------------------------------");
        System.out.println("EKSTREM RISIKO (Kritisk):     " + extreme);
        System.out.println("HØY RISIKO (Sikkerhet):       " + high);
        System.out.println("Uklare krav (Vague):          " + vague);
        System.out.println("Ikke-formelle krav (No shall): " + nonCompliant);
        System.out.println("-------------------------------------------");
        
        if (extreme > 0) {
            System.out.println("STATUS: HANDLING KREVES - Kritiske feil funnet.");
        } else {
            System.out.println("STATUS: SYSTEM KLART FOR VIDERE TESTING.");
        }
        System.out.println("===========================================\n");
    }
}