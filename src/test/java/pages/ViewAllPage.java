package pages;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ViewAllPage{
    private WebDriver driver;
    private WebDriverWait wait;

    private By viewallLink = By.xpath("//*[@id='viewAllStudentLink']/span");
    private By studentMenu = By.xpath("//*[@id='student']");
    private WebElement currrow;
    public ViewAllPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickViewAllLink(){
        wait.until(ExpectedConditions.elementToBeClickable(viewallLink)).click();
    }

    public void clickStudentMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(studentMenu)).click();
    }

    public boolean isviewalltablethere(){
        List<WebElement> elements = driver.findElements(By.id("viewTable"));
        return !elements.isEmpty();
    }

    public void rollnum2xpath(String rollnum){ 
        String path = "//table[@id='viewTable']//td[text()='" + rollnum +"']";
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path)));  
        currrow = driver.findElement(By.xpath(path));
    }

    public boolean detailmatch(String last, Map<String,String> data){
        rollnum2xpath(last);
        return data.get("name").equals(currrow.findElement(By.xpath("following-sibling::td[1]")).getText()) &&
               data.get("mobile").equals(currrow.findElement(By.xpath("following-sibling::td[2]")).getText()) &&
               data.get("email").equals(currrow.findElement(By.xpath("following-sibling::td[3]")).getText()) &&
               data.get("cgpa").equals(currrow.findElement(By.xpath("following-sibling::td[4]")).getText()) &&
               data.get("department").equals(currrow.findElement(By.xpath("following-sibling::td[5]")).getText()) &&
               data.get("backlog").equals(currrow.findElement(By.xpath("following-sibling::td[6]")).getText()) ;
    }
}