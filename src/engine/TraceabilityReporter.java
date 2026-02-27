package engine;

import model.Requirement;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class TraceabilityReporter {
    public void generateMarkdownReport(List<Requirement> requirements, String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("# Aegis-X Traceability & Validation Report");
            writer.println("Generated automatically by ReqTrace-Java\n");
            
            writer.println("## Executive Summary");
            long compliant = requirements.stream().filter(Requirement::isCompliant).count();
            writer.println("- **Total Requirements:** " + requirements.size());
            writer.println("- **Compliant:** " + compliant);
            writer.println("- **Non-Compliant:** " + (requirements.size() - compliant));
            writer.println("\n---");

            writer.println("## Requirements Matrix");
            writer.println("| ID | Subsystem | Status | Requirement Text |");
            writer.println("| :--- | :--- | :--- | :--- |");

            for (Requirement r : requirements) {
                String status = r.isCompliant() ? " Valid" : " No 'shall'";
                writer.println(String.format("| %s | %s | %s | %s |", 
                               r.getId(), r.getSubsystem(), status, r.getText()));
            }

            writer.println("\n## Risk Assessment");
            if (compliant < requirements.size()) {
                writer.println("> **Warning:** System contains non-compliant formal language. High risk for certification.");
            } else {
                writer.println("> **Success:** All requirements meet formal engineering standards.");
            }

            System.out.println("Report generated: " + fileName);
        } catch (IOException e) {
            System.err.println("Error writing report: " + e.getMessage());
        }
    }
}