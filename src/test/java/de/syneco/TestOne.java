package de.syneco;

import de.qytera.qtaf.core.config.ConfigurationFactory;
import de.qytera.qtaf.core.config.annotations.TestFeature;
import de.qytera.qtaf.core.io.DirectoryHelper;
import de.qytera.qtaf.core.selenium.DriverFactory;
import de.qytera.qtaf.testng.context.QtafTestNGContext;
import org.openqa.selenium.HasDownloads;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.sleep;

@TestFeature(
        name = "TestCase One",
        description = "First test"
)
public class TestOne extends QtafTestNGContext {

    public static String addDriverOptionArgument(String newArgument, String currentArguments){
        currentArguments = currentArguments.replace("[", "").replace("]", "");

        List<String> argumentList = new ArrayList<>(Arrays.asList(currentArguments.split(",")));
        argumentList.add(newArgument.trim());

        String result = String.join(", ", argumentList);
        return "[" + result + "]";
    }

    public static String removeAnyArgumentsThatIncludesHead(String arguments) {
        arguments = arguments.replace("[", "").replace("]", "");

        List<String> argumentList = Arrays.asList(arguments.split(","));
        List<String> filteredArguments = argumentList.stream()
                .map(String::trim)  // Trim any extra spaces
                .filter(arg -> !arg.contains("head"))
                .collect(Collectors.toList());

        String result = String.join(", ", filteredArguments);
        return "[" + result + "]";
    }

    @Test(testName = "Test download file")
    public void download() throws IOException {
        System.out.println("TEST BEGIN");

        // Configure Driver Options
        String driverOptions = config.getValue("driver.options").toString();
        System.out.println("Old Options: " + driverOptions);

        driverOptions = removeAnyArgumentsThatIncludesHead(driverOptions);
        driverOptions = addDriverOptionArgument("--headless", driverOptions);
        config.setString("driver.options", driverOptions);

        // Rest Driver
        DriverFactory.getDriver(true);

        driverOptions = config.getValue("driver.options").toString();
        System.out.println("New Options: " + driverOptions);


        if(driver.getClass() == RemoteWebDriver.class) {
            assertTrue(((HasDownloads) driver).getDownloadableFiles().isEmpty(), "There should be no files downloaded");
        }

        DownloadPage downloadPage = load(DownloadPage.class);
        driver.get("https://people.sc.fsu.edu/~jburkardt/data/csv/csv.html");
        sleep(9000);
        downloadPage.simpleDocButton().click();
        sleep(3000);
        System.out.println("Download ended");


        if(driver.getClass() == RemoteWebDriver.class) {

            assertFalse(((HasDownloads) driver).getDownloadableFiles().isEmpty(), "There should be a file downloaded");
            List<String> files = ((HasDownloads) driver).getDownloadableFiles();
            System.out.println("== downloaded files ==");
            System.out.println("size: " + files.size());
            System.out.println("first file: " + files.get(0));

            // current time stamp in format of yyyy-mm-dd-hh-mm-ss
            String currentTime = java.time.LocalDateTime.now().toString().replace(":", "-").replace(".", "-");

            Path dir = Paths.get("/var/jenkins_home/workspace/HeadlessIssue");
            Path targetDirectory = Files.createTempDirectory(dir, "download" + currentTime);

            ((HasDownloads) driver).downloadFile(files.get(0), targetDirectory);

            String fileContent = String.join("", Files.readAllLines(targetDirectory.resolve(files.get(0))));
            System.out.println(fileContent);
        }
        System.out.println("TEST END");
    }
}
