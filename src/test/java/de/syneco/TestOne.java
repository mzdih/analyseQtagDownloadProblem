package de.syneco;

import de.qytera.qtaf.core.config.ConfigurationFactory;
import de.qytera.qtaf.core.config.annotations.TestFeature;
import de.qytera.qtaf.core.io.DirectoryHelper;
import de.qytera.qtaf.testng.context.QtafTestNGContext;
import org.openqa.selenium.HasDownloads;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.codeborne.selenide.Selenide.sleep;

@TestFeature(
        name = "TestCase One",
        description = "First test"
)
public class TestOne extends QtafTestNGContext {
    @Test(testName = "Test download file")
    public void download() throws IOException {
        System.out.println("TEST BEGIN");
        assertTrue(((HasDownloads) driver).getDownloadableFiles().isEmpty(), "There should be no files downloaded");
        DownloadPage downloadPage = load(DownloadPage.class);
        /*
        String downloadDirectory = ConfigurationFactory.getInstance().getValue("driver.preferences.download.default_directory").toString();
        System.out.println("== downloadDirectory ==");
        System.out.println(downloadDirectory);

        String downloadDirectoryWithHelper = DirectoryHelper.preparePath(config.getString("driver.preferences.download.default_directory"));
        System.out.println("== downloadDirectoryWithHelper ==");
        System.out.println(downloadDirectoryWithHelper);
         */


        driver.get("https://people.sc.fsu.edu/~jburkardt/data/csv/csv.html");
        sleep(9000);
        downloadPage.simpleDocButton().click();
        sleep(3000);
        System.out.println("Download ended");
        assertFalse(((HasDownloads) driver).getDownloadableFiles().isEmpty(), "There should be a file downloaded");

        List<String> files = ((HasDownloads) driver).getDownloadableFiles();
        System.out.println("== downloaded files ==");
        System.out.println("size: " + files.size());
        System.out.println("first file: " + files.get(0));

        // current time stamp in format of yyyy-mm-dd-hh-mm-ss
        String currentTime = java.time.LocalDateTime.now().toString().replace(":", "-").replace(".", "-");


        Path dir = Paths.get("/var/jenkins_home/workspace/DownloadIssue");
        Path targetDirectory = Files.createTempDirectory(dir, "download" + currentTime);

        ((HasDownloads) driver).downloadFile(files.get(0), targetDirectory);

        String fileContent = String.join("", Files.readAllLines(targetDirectory.resolve(files.get(0))));
        System.out.println(fileContent);

        System.out.println("TEST END");
    }
}
