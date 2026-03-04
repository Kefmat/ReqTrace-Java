import model.Requirement;
import parser.RequirementParser;
import engine.TraceabilityReporter;
import engine.HtmlDashboardGenerator;
import engine.TelemetryLogger;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Hovedklassen for Missilsystemets Sporbarhetsanalyse.
 * Koordinerer parsing, analyse og generering av rapporter med arkivering.
 */
public class Main {
    public static void main(String[] args) {
        cleanupOldReports();

        // Generer tidsstempel for kjøringen
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmm"));

        RequirementParser parser = new RequirementParser();
        TraceabilityReporter reporter = new TraceabilityReporter();
        HtmlDashboardGenerator dashboard = new HtmlDashboardGenerator();
        TelemetryLogger logger = new TelemetryLogger();

        // Leser krav fra XML-filen
        List<Requirement> requirements = parser.parse("data/system_reqs.xml");

        // 1. Generer standardrapporter (overskrives ved hver kjøring)
        reporter.generateMarkdownReport(requirements, "Traceability_Report.md");
        reporter.exportToJson(requirements, "analysis_output.json");
        dashboard.create(requirements, "dashboard.html");

        // 2. Generer arkivkopier (beholdes med unikt tidsstempel)
        reporter.generateMarkdownReport(requirements, "archive/Report_" + timestamp + ".md");
        dashboard.create(requirements, "archive/Dashboard_" + timestamp + ".html");

        // Logger telemetri for historisk sporing
        logger.logAnalysis(requirements);

        // Utfør briefing og lagre antall kritiske feil
        int extremeRisks = printMissionBriefing(requirements);

        // QUALITY GATE: Stopper bygget hvis det finnes ekstrem risiko
        if (extremeRisks > 0) {
            System.err.println("\n[!] QUALITY GATE FAILED: " + extremeRisks + " extreme risk(s) detected.");
            System.err.println("[!] Action: Deployment halted. Fix requirements in data/system_reqs.xml.");
            System.exit(1);
        }
    }

    /**
     * Sletter gamle rapportfiler og sikrer at archive-mappen eksisterer.
     */
    private static void cleanupOldReports() {
        File archiveDir = new File("archive");
        if (!archiveDir.exists()) {
            archiveDir.mkdir();
        }

        String[] filesToClean = {"dashboard.html", "analysis_output.json", "Traceability_Report.md"};
        for (String fileName : filesToClean) {
            File file = new File(fileName);
            if (file.exists()) {
                file.delete();
            }
        }
        System.out.println("System cleanup complete: Old reports removed and archive ready.");
    }

    /**
     * Skriver ut en militaer statusrapport basert paa analysen.
     * @param reqs Listen med krav som skal briefes.
     * @return Antall krav med ekstrem risiko funnet.
     */
    private static int printMissionBriefing(List<Requirement> reqs) {
        int extreme = 0;
        int high = 0;
        int vague = 0;
        int nonCompliant = 0;

        for (Requirement r : reqs) {
            String risk = r.getRiskLevel().toUpperCase();
            if (risk.contains("EXTREME")) extreme++;
            else if (risk.contains("HIGH")) high++;
            
            if (r.isVague()) vague++;
            if (!r.isCompliant()) nonCompliant++;
        }

        System.out.println("\n===========================================");
        System.out.println("   MISSILE SYSTEM ANALYSIS BRIEFING");
        System.out.println("===========================================");
        System.out.println("Totalt antall krav analysert: " + reqs.size());
        System.out.println("-------------------------------------------");
        System.out.println("EKSTREM RISIKO (Kritisk):     " + extreme);
        System.out.println("HØY RISIKO (Sikkerhet):      " + high);
        System.out.println("Uklare krav (Vague):          " + vague);
        System.out.println("Ikke-formelle krav (No shall): " + nonCompliant);
        System.out.println("-------------------------------------------");
        
        if (extreme > 0) {
            System.out.println("STATUS: HANDLING KREVES - Kritiske feil funnet.");
        } else {
            System.out.println("STATUS: SYSTEM KLART FOR VIDERE TESTING.");
        }
        System.out.println("===========================================\n");

        return extreme;
    }
}