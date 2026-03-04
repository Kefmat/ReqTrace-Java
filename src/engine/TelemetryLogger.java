package engine;

import model.Requirement;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Ansvarlig for aa logge historiske data fra analysene.
 * Dette gjoer det mulig aa spore forbedringer i kravkvalitet over tid.
 */
public class TelemetryLogger {
    private static final String LOG_FILE = "analysis_history.log";

    public void logAnalysis(List<Requirement> reqs) {
        long extreme = reqs.stream().filter(r -> r.getRiskLevel().contains("EXTREME")).count();
        long high = reqs.stream().filter(r -> r.getRiskLevel().contains("HIGH")).count();
        long vague = reqs.stream().filter(Requirement::isVague).count();
        
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        String logEntry = String.format("%s - Total: %d | Extreme: %d | High: %d | Vague: %d\n",
                now.format(formatter), reqs.size(), extreme, high, vague);

        try (FileWriter fw = new FileWriter(LOG_FILE, true)) { // 'true' betyr append (legg til i slutten)
            fw.write(logEntry);
            System.out.println("Telemetry data saved to: " + LOG_FILE);
        } catch (IOException e) {
            System.err.println("Error saving telemetry: " + e.getMessage());
        }
    }
}