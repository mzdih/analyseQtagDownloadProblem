package de.syneco;

import de.qytera.qtaf.core.config.annotations.TestFeature;
import de.qytera.qtaf.testng.context.QtafTestNGContext;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.sleep;

@TestFeature(
        name = "TestCase One",
        description = "First test"
)
public class TestOne extends QtafTestNGContext {
    @Test(testName = "Test download file")
    public void download() {
        System.out.println("TEST BEGIN");
        DownloadPage downloadPage = load(DownloadPage.class);
        driver.get("https://people.sc.fsu.edu/~jburkardt/data/csv/csv.html");
        sleep(9000);
        downloadPage.simpleDocButton().click();
        System.out.println("TEST END");
    }
}
