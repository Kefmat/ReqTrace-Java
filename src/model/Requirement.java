package model;

public class Requirement {
    private String id;
    private String text;
    private String subsystem;
    private String priority;
    private boolean isCompliant;

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

    @Override
    public String toString() {
        return String.format("[%s] %-12s | Compliant: %-5s | %s", 
                id, subsystem, isCompliant, text);
    }
}