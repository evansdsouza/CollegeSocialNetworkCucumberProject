package stepDefinitions;

import java.util.Map;

import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.LoginPage;
import pages.StudentPage;
import utilities.ConfigReader;
import utilities.DriverFactory;

public class RegisterSteps {

    LoginPage loginPage = new LoginPage(DriverFactory.getDriver());
    StudentPage studentPage = new StudentPage(DriverFactory.getDriver());

    @Given("I am logged in with valid credentials")
    public void i_am_logged_in_with_valid_credentials() {
        DriverFactory.getDriver().get(ConfigReader.getProperty("url"));
        loginPage.enterUsername(ConfigReader.getProperty("username"));
        loginPage.enterPassword(ConfigReader.getProperty("password"));
        loginPage.clickSignIn();
        
        Assert.assertTrue(loginPage.isStudentMenuPresent());
    }

    @When("I click on student menu and then register")
    public void i_click_on_student_menu_and_then_register() {
        studentPage.clickStudentMenu();
        studentPage.clickRegisterLink();
    }

    @When("I fill student details:")
    public void i_fill_student_details(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> data = dataTable.asMaps().get(0);
        studentPage.enterStudentName(data.get("name"));
        studentPage.enterMobileNumber(data.get("mobile"));
        studentPage.enterEmail(data.get("email"));
        studentPage.enterCgpa(data.get("cgpa"));
        studentPage.enterDepartment(data.get("department"));
        studentPage.enterBacklog(data.get("backlog"));
    }

    @When("I click register")
    public void i_click_register() {
        studentPage.clickRegisterButton();
    }
    
    @Then("I should see an error message")
    public void i_should_see_an_error_message() {
        String errorMsg = studentPage.getErrorMessage();
        Assert.assertTrue(errorMsg != null && !errorMsg.isEmpty(), "Expected an error message, but got none.");
    }


    @Then("I should see a success message with roll number")
    public void i_should_see_a_success_message_with_roll_number() {
        String successMsg = studentPage.getSuccessMessage();
        Assert.assertTrue(successMsg.contains("Registration was successful"));
    }
}
