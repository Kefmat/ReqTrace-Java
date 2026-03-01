package model;

import java.util.Arrays;
import java.util.List;

/**
 * Representerer et teknisk krav i et missilsystem.
 */
public class Requirement {
    private String id;
    private String text;
    private String subsystem;
    private String priority;
    private boolean isCompliant;
    
    private static final List<String> VALID_SUBSYSTEMS = Arrays.asList(
        "Propulsion", "Guidance", "Warhead", "Telemetry", "Launch Control"
    );

    private static final String[] VAGUE_WORDS = {
        "raskt", "effektivt", "optimalt", "kanskje", "bør", "snarest"
    };

    public Requirement(String id, String text, String subsystem, String priority) {
        this.id = id;
        this.text = text;
        this.subsystem = subsystem;
        this.priority = priority;
        this.isCompliant = text.toLowerCase().contains("shall");
    }

    public String getId() { return id; }
    public String getText() { return text; }
    public String getSubsystem() { return subsystem; }
    public String getPriority() { return priority; }
    public boolean isCompliant() { return isCompliant; }

    /**
     * Sjekker om kravet inneholder vage ord.
     * @return true hvis kravet er uklart.
     */
    public boolean isVague() {
        for (String word : VAGUE_WORDS) {
            if (text.toLowerCase().contains(word)) return true;
        }
        return false;
    }

    public boolean isValidSubsystem() {
        return VALID_SUBSYSTEMS.contains(subsystem);
    }

    public String getRiskLevel() {
        String t = text.toLowerCase();
        if ((subsystem.equals("Warhead") || subsystem.equals("Propulsion")) && priority.equalsIgnoreCase("Critical")) {
            return "EXTREME (Catastrophic Impact)";
        } else if (t.contains("safety") || t.contains("explosion") || t.contains("hazard")) {
            return "HIGH (Safety Critical)";
        }
        return "MEDIUM/LOW";
    }

    public String getQualityStatus() {
        if (isVague()) return "Vague (Refine for Military Specs)";
        if (!isValidSubsystem()) return "Invalid Subsystem";
        return "Clear";
    }

    @Override
    public String toString() {
        return String.format("[%s] %-15s | Risk: %-10s | %s", 
                id, subsystem, getRiskLevel(), text);
    }
}