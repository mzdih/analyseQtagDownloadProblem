package de.syneco;

import com.codeborne.selenide.SelenideElement;
import de.qytera.qtaf.core.config.annotations.TestFeature;
import de.qytera.qtaf.testng.context.QtafTestNGContext;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

@TestFeature(name = "Download page")
public class DownloadPage extends QtafTestNGContext {

    public SelenideElement simpleDocButton() {
        return $(By.xpath("//a[text()='airtravel.csv']"));
    }
}
