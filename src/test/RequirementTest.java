package test;

import model.Requirement;

public class RequirementTest {
    public static void main(String[] args) {
        testRiskCalculation();
        testSubsystemValidation();
        System.out.println("Missile System Tests: All passed successfully.");
    }

    private static void testRiskCalculation() {
        Requirement warheadReq = new Requirement("M-01", "The warhead shall detonate on impact.", "Warhead", "Critical");
        if (!warheadReq.getRiskLevel().contains("EXTREME")) {
            throw new RuntimeException("Failed: Warhead risk not calculated correctly");
        }
    }

    private static void testSubsystemValidation() {
        Requirement valid = new Requirement("M-02", "Text", "Guidance", "Low");
        Requirement invalid = new Requirement("M-03", "Text", "Coffee Machine", "Low");

        if (!valid.isValidSubsystem()) throw new RuntimeException("Failed: Valid subsystem rejected");
        if (invalid.isValidSubsystem()) throw new RuntimeException("Failed: Invalid subsystem accepted");
    }
}