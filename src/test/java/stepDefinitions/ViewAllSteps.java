package stepDefinitions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.LoginPage;
import pages.StudentPage;
import pages.ViewAllPage;
import utilities.ConfigReader;
import utilities.DriverFactory;


public class ViewAllSteps {

    LoginPage loginPage = new LoginPage(DriverFactory.getDriver());
    StudentPage studentPage = new StudentPage(DriverFactory.getDriver());
    ViewAllPage viewAllPage = new ViewAllPage(DriverFactory.getDriver());
    private String lastrollno = "1000";
    private WebDriver driver = DriverFactory.getDriver();
    Map<String, String> data = new HashMap<>();

    @When("I click on student menu")
    public void I_click_on_Student_menu() {
        viewAllPage.clickStudentMenu();
    }

    @When("I click on View All Students")
    public void I_click_on_View_All_Students() {
        viewAllPage.clickViewAllLink();
    }

    @When("I register a student with valid credentials:")
    public void I_register_a_student_with_valid_credentials(io.cucumber.datatable.DataTable dataTable){
        studentPage.clickStudentMenu();
        studentPage.clickRegisterLink();
        data = dataTable.asMaps().get(0);
        studentPage.enterStudentName(data.get("name"));
        studentPage.enterMobileNumber(data.get("mobile"));
        studentPage.enterEmail(data.get("email"));
        studentPage.enterCgpa(data.get("cgpa"));
        studentPage.enterDepartment(data.get("department"));
        studentPage.enterBacklog(data.get("backlog"));
        studentPage.clickRegisterButton();
        try {
            Thread.sleep(5000);
        } 
        catch (InterruptedException ex) {
        }
        lastrollno = studentPage.getSuccessMessage().substring(studentPage.getSuccessMessage().lastIndexOf(" ") + 1);
        lastrollno = lastrollno.substring(0, lastrollno.length() - 1);
    }

    @Then("I see that the student entered is present in the table")
    public void I_see_that_the_student_is_in_table(){
        try {
            Thread.sleep(15000);
        } 
        catch (InterruptedException ex) {
        }
        boolean found = viewAllPage.detailmatch(lastrollno, data);
        System.out.println(found);
        Assert.assertTrue(found, "Expected student to be visible with correct details, but it was not. " );
    }
    @When("I reload the page")
    public void i_reload_the_page() {
        DriverFactory.getDriver().navigate().refresh();
        try {
            Thread.sleep(3000); // Let the page reload
        } catch (InterruptedException ignored) {}
    }

    @Then("I should still see the student list table")
    public void i_should_still_see_the_student_list_table() {
        if (viewAllPage.isviewalltablethere()){
        WebElement table = driver.findElement(By.id("viewTable"));
        Assert.assertTrue(table.isDisplayed(), "View table is not displayed after reload.");}

        else{
            Assert.fail("table not found!!");
        }


    }

    @Then("I should see that the student details are displayed without overflow")
    public void i_should_see_student_details_without_overflow() {
        // Locate the row for the recently added roll number
        try {
            Thread.sleep(15000);
        } 
        catch (InterruptedException ex) {
        }
        String xpath = "//table[@id='viewTable']//td[text()='" + lastrollno + "']/parent::tr";
        WebElement studentRow = driver.findElement(By.xpath(xpath));
        
        // Get all cells in that row
        List<WebElement> cells = studentRow.findElements(By.tagName("td"));
        
        for (WebElement cell : cells) {
            String overflow = cell.getCssValue("overflow");
            Assert.assertNotEquals(overflow, "hidden", 
                "Text overflow detected in cell: " + cell.getText());
        }
    }

    @Given("I am logged in with valid credentials on a mobile viewport")
    public void i_am_logged_in_with_mobile_viewport() {
        // Set mobile viewport
    DriverFactory.getDriver().manage().window().setSize(new Dimension(375, 812));

    // Navigate after resizing
    DriverFactory.getDriver().get(ConfigReader.getProperty("url"));

    // Safe check: is username field present and visible?
    List<WebElement> usernameElements = DriverFactory.getDriver().findElements(By.id("username"));
    if (usernameElements.isEmpty() || !usernameElements.get(0).isDisplayed()) {
        Assert.fail("Username field not found or not visible at mobile resolution.");
    }

    // Safe check: is password field present and visible?
    List<WebElement> passwordElements = DriverFactory.getDriver().findElements(By.id("password"));
    if (passwordElements.isEmpty() || !passwordElements.get(0).isDisplayed()) {
        Assert.fail("Password field not found or not visible at mobile resolution.");
    }

    // Proceed only if both fields are accessible
    try {
        loginPage.enterUsername(ConfigReader.getProperty("username"));
        loginPage.enterPassword(ConfigReader.getProperty("password"));
        loginPage.clickSignIn();
    } catch (Exception e) {
        Assert.fail("Login failed on mobile viewport: " + e.getMessage());
    }

    Assert.assertTrue(loginPage.isStudentMenuPresent(), "Student menu not found — login might have failed.");
    }

    @Then("The student table should be responsive")
    public void the_student_table_should_be_responsive() {
        WebElement table = driver.findElement(By.id("viewTable"));
        int width = table.getSize().getWidth();
        Assert.assertTrue(width < 375, "Table is not adapting to mobile screen width.");
    }

    @Then("I should see correct table headers")
    public void i_should_see_correct_table_headers() {
        List<String> expectedHeaders = Arrays.asList("Roll No", "Name", "Mobile Number", "Email Id", "CGPA", "Department Name", "Backlog Count", "Status");
        List<WebElement> headers = driver.findElements(By.xpath("//table[@id='viewTable']//tr[1]/td"));
        for (int i = 0; i < expectedHeaders.size(); i++) {
            Assert.assertEquals(headers.get(i).getText().trim(), expectedHeaders.get(i),
                "Header mismatch at index " + i);
        }
    }
}