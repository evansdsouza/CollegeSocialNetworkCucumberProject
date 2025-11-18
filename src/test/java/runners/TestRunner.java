package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterSuite;
import utilities.ExtentManager;

@CucumberOptions(
    features = "src/test/resources/features",
    glue = {"stepDefinitions", "utilities"},
    plugin = {
        "pretty",
        "html:target/cucumber-reports/cucumber.html",
        "json:target/cucumber-reports/cucumber.json"
    },
    monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {
    @AfterSuite
    public void tearDownSuite() {
        // This ensures the ExtentReport is properly written to file
        ExtentManager.getInstance().flush();
    }
}
