package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class StudentPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public StudentPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private By studentMenu = By.xpath("//*[@id='student']");
    private By registerLink = By.xpath("//a[@id='registerLink']");
    private By studentName = By.id("studentName");
    private By mobileNumber = By.id("mobileNumber");
    private By emailId = By.id("emailId");
    private By cgpa = By.id("cgpa");
    private By deptName = By.id("deptName");
    private By backlogCount = By.id("backlogCount");
    private By registerButton = By.xpath("//button[@id='register']");
    private By successMsg = By.id("result");
    private By errorMsg = By.xpath("//*[@id=\"error\"]/text()");

    public String getErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMsg)).getText();
    }


    public void clickStudentMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(studentMenu)).click();
    }

    public void clickRegisterLink() {
        wait.until(ExpectedConditions.elementToBeClickable(registerLink)).click();
    }

    public void enterStudentName(String name) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(studentName)).sendKeys(name);
    }

    public void enterMobileNumber(String mobile) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(mobileNumber)).sendKeys(mobile);
    }

    public void enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailId)).sendKeys(email);
    }

    public void enterCgpa(String cgpaValue) {
    	wait.until(ExpectedConditions.visibilityOfElementLocated(cgpa)).clear();
        wait.until(ExpectedConditions.visibilityOfElementLocated(cgpa)).sendKeys(cgpaValue);
    }

    public void enterDepartment(String department) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(deptName)).sendKeys(department);
    }

    public void enterBacklog(String backlog) {
    	wait.until(ExpectedConditions.visibilityOfElementLocated(backlogCount)).clear();
        wait.until(ExpectedConditions.visibilityOfElementLocated(backlogCount)).sendKeys(backlog);
    }

    public void clickRegisterButton() {
        wait.until(ExpectedConditions.elementToBeClickable(registerButton)).click();
    }

    public String getSuccessMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successMsg)).getText();
    }
}
