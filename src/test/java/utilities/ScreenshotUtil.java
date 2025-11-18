package utilities;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

    public static void captureScreenshot(String scenarioName) {
        WebDriver driver = DriverFactory.getDriver();
        try {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File destFile = new File("screenshots/" + scenarioName + "_" + timestamp + ".png");
            FileUtils.copyFile(srcFile, destFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
