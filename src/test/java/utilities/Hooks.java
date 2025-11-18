package utilities;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class Hooks {

    WebDriver driver;
    ExtentTest test;

    @Before
    public void setUp(Scenario scenario) {
        ConfigReader.loadConfig();
        driver = DriverFactory.getDriver();
        driver.get(PropertyReader.get("url"));

        // Start Extent test for this scenario
        test = ExtentManager.getInstance().createTest(scenario.getName());
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            ScreenshotUtil.captureScreenshot(scenario.getName().replaceAll(" ", "_"));
            test.fail("Scenario Failed: " + scenario.getName());
        } else {
            test.pass("Scenario Passed: " + scenario.getName());
        }

        DriverFactory.quitDriver();

        // Important: Only flush once after all tests
        // This flush call can go in an @AfterAll hook or in TestNG runner
    }
}
