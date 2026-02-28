package test;

import model.Requirement;

public class RequirementTest {
    public static void main(String[] args) {
        testComplianceValidation();
        testComplexityScoring();
        testImpactAnalysis();
        System.out.println("All tests passed successfully.");
    }

    private static void testComplianceValidation() {
        Requirement validReq = new Requirement("1", "The system shall explode.", "Test", "High");
        Requirement invalidReq = new Requirement("2", "The system should explode.", "Test", "Low");

        if (!validReq.isCompliant()) throw new RuntimeException("Failed: Shall-requirement marked invalid");
        if (invalidReq.isCompliant()) throw new RuntimeException("Failed: Should-requirement marked valid");
    }

    private static void testComplexityScoring() {
        String longText = "This is a very long requirement text that should definitely be flagged as high complexity because it has too many words.";
        Requirement complexReq = new Requirement("3", longText, "Test", "Low");
        
        if (!complexReq.getComplexityStatus().equals("High")) {
            throw new RuntimeException("Failed: Long requirement not marked as High complexity");
        }
    }

    private static void testImpactAnalysis() {
        Requirement safetyReq = new Requirement("4", "The safety valve must close.", "Safety", "Critical");
        if (!safetyReq.getImpactLevel().contains("HIGH")) {
            throw new RuntimeException("Failed: Safety keyword not detected in impact analysis");
        }
    }
}