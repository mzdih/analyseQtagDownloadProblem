package de.syneco;

import de.qytera.qtaf.core.config.annotations.TestFeature;
import de.qytera.qtaf.testng.context.QtafTestNGContext;
import org.testng.annotations.Test;

@TestFeature(
        name = "TestCase One",
        description = "First test"
)
public class TestOne extends QtafTestNGContext {
    @Test(testName = "Test Success", description = "This test should pass")
    public void testSuccess() {
        assertEquals(2 * 2, 4);
    }

    @Test(testName = "Test Failure", description = "This test should fail")
    public void testFailure() {
        assertEquals(2 * 2, 3);
    }
}
