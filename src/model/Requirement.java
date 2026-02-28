package model;

import java.util.Arrays;
import java.util.List;

/**
 * Representerer et teknisk krav i et missilsystem.
 * Klassen håndterer validering av undersystemer, kvalitetsanalyse av tekst
 * og beregning av risikonivå basert på militære spesifikasjoner.
 */
public class Requirement {
    private String id;
    private String text;
    private String subsystem;
    private String priority;
    private boolean isCompliant;
    
    /** Liste over godkjente undersystemer for missilet. */
    private static final List<String> VALID_SUBSYSTEMS = Arrays.asList(
        "Propulsion", "Guidance", "Warhead", "Telemetry", "Launch Control"
    );

    /** Ord som anses som for vage for militære kravspesifikasjoner. */
    private static final String[] VAGUE_WORDS = {
        "raskt", "effektivt", "optimalt", "kanskje", "bør", "snarest"
    };

    /**
     * Konstruerer et nytt Requirement-objekt.
     * * @param id Unik identifikator for kravet.
     * @param text Selve kravteksten.
     * @param subsystem Hvilken del av missilet kravet tilhører.
     * @param priority Prioritetsnivå (f.eks. Critical, High, Low).
     */
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
    
    /**
     * Sjekker om kravet bruker det formelle ordet 'shall'.
     * @return true hvis kravet er formelt korrekt (compliant).
     */
    public boolean isCompliant() { return isCompliant; }

    /**
     * Validerer om undersystemet tilhører den godkjente listen for missilsystemet.
     * @return true hvis undersystemet er gyldig.
     */
    public boolean isValidSubsystem() {
        return VALID_SUBSYSTEMS.contains(subsystem);
    }

    /**
     * Beregner risikonivået for kravet basert på komponentens viktighet og tekstinnhold.
     * Kritiske komponenter som 'Warhead' gir høyere utslag.
     * * @return En streng som beskriver risikonivået (EXTREME, HIGH eller MEDIUM/LOW).
     */
    public String getRiskLevel() {
        String t = text.toLowerCase();
        if ((subsystem.equals("Warhead") || subsystem.equals("Propulsion")) && priority.equalsIgnoreCase("Critical")) {
            return "EXTREME (Catastrophic Impact)";
        } else if (t.contains("safety") || t.contains("explosion") || t.contains("hazard")) {
            return "HIGH (Safety Critical)";
        }
        return "MEDIUM/LOW";
    }

    /**
     * Analyserer teksten for uklarheter eller ugyldige undersystemer.
     * * @return En statusmelding om kravets kvalitet.
     */
    public String getQualityStatus() {
        for (String word : VAGUE_WORDS) {
            if (text.toLowerCase().contains(word)) return "Vague (Refine for Military Specs)";
        }
        if (!isValidSubsystem()) return "Invalid Subsystem";
        return "Clear";
    }

    @Override
    public String toString() {
        return String.format("[%s] %-15s | Risk: %-10s | %s", 
                id, subsystem, getRiskLevel(), text);
    }
}