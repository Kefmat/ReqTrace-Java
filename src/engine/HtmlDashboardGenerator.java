package engine;

import model.Requirement;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Spesialisert generator for å lage et visuelt Mission Dashboard.
 * Skiller presentasjonslag fra datalogikk.
 */
public class HtmlDashboardGenerator {

    public void create(List<Requirement> requirements, String filePath) {
        StringBuilder html = new StringBuilder();
        
        // Header & CSS
        html.append("<!DOCTYPE html><html lang='no'><head><meta charset='UTF-8'>");
        html.append("<title>Aegis-X Mission Dashboard</title>");
        html.append("<style>");
        html.append("body { font-family: 'Segoe UI', sans-serif; background: #0b0e14; color: #e0e0e0; padding: 20px; }");
        html.append(".container { max-width: 1100px; margin: auto; }");
        html.append("h1 { color: #00d4ff; border-bottom: 2px solid #004a5c; padding-bottom: 10px; text-transform: uppercase; }");
        html.append(".stats-grid { display: grid; grid-template-columns: repeat(4, 1fr); gap: 20px; margin: 20px 0; }");
        html.append(".card { background: #1a1f29; padding: 20px; border-radius: 10px; text-align: center; border-bottom: 4px solid #00d4ff; }");
        html.append(".extreme { border-color: #ff4d4d; color: #ff4d4d; }");
        html.append(".high { border-color: #ffa500; color: #ffa500; }");
        html.append("table { width: 100%; border-collapse: collapse; background: #1a1f29; border-radius: 10px; overflow: hidden; }");
        html.append("th, td { padding: 15px; text-align: left; border-bottom: 1px solid #2d3442; }");
        html.append("th { background: #252c3a; color: #00d4ff; }");
        html.append(".badge { padding: 4px 8px; border-radius: 4px; font-size: 0.85em; background: #2d3442; }");
        html.append("</style></head><body><div class='container'>");

        html.append("<h1>Aegis-X: Mission Dashboard</h1>");

        // Statistikk
        long extreme = requirements.stream().filter(r -> r.getRiskLevel().equalsIgnoreCase("EXTREME")).count();
        long high = requirements.stream().filter(r -> r.getRiskLevel().equalsIgnoreCase("HIGH")).count();
        long vague = requirements.stream().filter(Requirement::isVague).count();

        // Dashboard Stats
        html.append("<div class='stats-grid'>");
        html.append("<div class='card'>Total Requirements<br><h2>").append(requirements.size()).append("</h2></div>");
        html.append("<div class='card extreme'>Extreme Risk<br><h2>").append(extreme).append("</h2></div>");
        html.append("<div class='card high'>High Risk<br><h2>").append(high).append("</h2></div>");
        html.append("<div class='card'>Vague Terms<br><h2>").append(vague).append("</h2></div>");
        html.append("</div>");

        // Tabell
        html.append("<table><thead><tr><th>ID</th><th>Statement</th><th>Subsystem</th><th>Risk</th></tr></thead><tbody>");
        for (Requirement r : requirements) {
            String riskColor = r.getRiskLevel().equalsIgnoreCase("EXTREME") ? "style='color:#ff4d4d; font-weight:bold;'" : "";
            html.append("<tr>");
            html.append("<td>").append(r.getId()).append("</td>");
            html.append("<td>").append(r.getText()).append("</td>");
            html.append("<td><span class='badge'>").append(r.getSubsystem()).append("</span></td>");
            html.append("<td ").append(riskColor).append(">").append(r.getRiskLevel()).append("</td>");
            html.append("</tr>");
        }
        html.append("</tbody></table></div></body></html>");

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(html.toString());
            System.out.println("Mission Dashboard generated: " + filePath);
        } catch (IOException e) {
            System.err.println("Error generating dashboard: " + e.getMessage());
        }
    }
}