package model;

public class Requirement {
    private String id;
    private String text;
    private String subsystem;
    private String priority;
    private boolean isCompliant;
    private int complexityScore;

    public Requirement(String id, String text, String subsystem, String priority) {
        this.id = id;
        this.text = text;
        this.subsystem = subsystem;
        this.priority = priority;
        
        this.isCompliant = text.toLowerCase().contains("shall");
        
        this.complexityScore = text.split("\\s+").length;
    }

    public String getId() { return id; }
    public String getText() { return text; }
    public String getSubsystem() { return subsystem; }
    public String getPriority() { return priority; }
    public boolean isCompliant() { return isCompliant; }
    
    public String getComplexityStatus() {
        return (complexityScore > 15) ? "High" : "Low";
    }

    public String getImpactLevel() {
        String t = text.toLowerCase();
        if (t.contains("safety") || t.contains("hazard") || t.contains("critical")) {
            return "HIGH (Safety Critical)";
        } else if (priority.equalsIgnoreCase("Critical")) {
            return "MEDIUM (High Priority)";
        }
        return "Low";
    }

    @Override
    public String toString() {
        return String.format("[%s] %-12s | Compliant: %-5b | %s", 
                id, subsystem, isCompliant, text);
    }
}