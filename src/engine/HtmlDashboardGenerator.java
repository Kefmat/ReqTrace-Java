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
        // Beregn statistikk for visualisering
        long extreme = requirements.stream().filter(r -> r.getRiskLevel().contains("EXTREME")).count();
        long high = requirements.stream().filter(r -> r.getRiskLevel().contains("HIGH")).count();
        long other = requirements.size() - (extreme + high);
        long vague = requirements.stream().filter(Requirement::isVague).count();
        long compliant = requirements.stream().filter(Requirement::isCompliant).count();

        StringBuilder html = new StringBuilder();
        
        html.append("<!DOCTYPE html><html lang='no'><head><meta charset='UTF-8'>");
        html.append("<title>Aegis-X Mission Dashboard</title>");
        html.append("<script src='https://cdn.jsdelivr.net/npm/chart.js'></script>");
        html.append("<style>");
        html.append("body { font-family: 'Segoe UI', sans-serif; background: #0b0e14; color: #e0e0e0; padding: 20px; }");
        html.append(".container { max-width: 1100px; margin: auto; }");
        html.append("h1 { color: #00d4ff; border-bottom: 2px solid #004a5c; padding-bottom: 10px; text-transform: uppercase; }");
        html.append(".grid { display: grid; grid-template-columns: 1fr 1fr; gap: 20px; margin-bottom: 20px; }");
        html.append(".card { background: #1a1f29; padding: 20px; border-radius: 10px; border-bottom: 4px solid #00d4ff; }");
        html.append("table { width: 100%; border-collapse: collapse; background: #1a1f29; border-radius: 10px; overflow: hidden; }");
        html.append("th, td { padding: 15px; text-align: left; border-bottom: 1px solid #2d3442; }");
        html.append("th { background: #252c3a; color: #00d4ff; }");
        html.append(".badge { padding: 4px 8px; border-radius: 4px; font-size: 0.85em; background: #2d3442; }");
        html.append(".risk-ext { color: #ff4d4d; font-weight: bold; }");
        html.append(".status-vague { color: #ffa500; }");
        html.append("</style></head><body><div class='container'>");

        html.append("<h1>Aegis-X: Mission Dashboard</h1>");

        // Graf-seksjon
        html.append("<div class='grid'>");
        html.append("<div class='card'><canvas id='riskChart' height='200'></canvas></div>");
        html.append("<div class='card'><canvas id='qualityChart' height='200'></canvas></div>");
        html.append("</div>");

        // Tabell
        html.append("<table><thead><tr><th>ID</th><th>Statement</th><th>Subsystem</th><th>Risk</th><th>Quality</th></tr></thead><tbody>");
        for (Requirement r : requirements) {
            String riskClass = r.getRiskLevel().contains("EXTREME") ? "class='risk-ext'" : "";
            String qualityClass = r.isVague() ? "class='status-vague'" : "";
            
            html.append("<tr>");
            html.append("<td>").append(r.getId()).append("</td>");
            html.append("<td>").append(r.getText()).append("</td>");
            html.append("<td><span class='badge'>").append(r.getSubsystem()).append("</span></td>");
            html.append("<td ").append(riskClass).append(">").append(r.getRiskLevel()).append("</td>");
            html.append("<td ").append(qualityClass).append(">").append(r.getQualityStatus()).append("</td>");
            html.append("</tr>");
        }
        html.append("</tbody></table>");

        // Chart Scripts
        html.append("<script>");
        html.append("new Chart(document.getElementById('riskChart'), { type: 'doughnut', data: { ");
        html.append("labels: ['Extreme', 'High', 'Other'], datasets: [{ ");
        html.append("data: [").append(extreme).append(",").append(high).append(",").append(other).append("], ");
        html.append("backgroundColor: ['#ff4d4d', '#ffa500', '#2d3442'], borderWidth: 0 }] }, ");
        html.append("options: { plugins: { legend: { labels: { color: '#fff' } } } } });");

        html.append("new Chart(document.getElementById('qualityChart'), { type: 'bar', data: { ");
        html.append("labels: ['Compliant', 'Vague'], datasets: [{ ");
        html.append("label: 'Requirement Quality', data: [").append(compliant).append(",").append(vague).append("], ");
        html.append("backgroundColor: ['#00d4ff', '#ffa500'] }] }, ");
        html.append("options: { scales: { y: { beginAtZero: true, grid: { color: '#2d3442' } } } } });");
        html.append("</script>");

        html.append("</div></body></html>");

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(html.toString());
            System.out.println("Mission Dashboard generated: " + filePath);
        } catch (IOException e) {
            System.err.println("Error generating dashboard: " + e.getMessage());
        }
    }
}