package stepDefinitions;

import io.cucumber.java.en.*;
//import org.openqa.selenium.By;
import org.testng.Assert;
import pages.LoginPage;
import utilities.ConfigReader;
import utilities.DriverFactory;

public class LoginSteps {

    LoginPage loginPage = new LoginPage(DriverFactory.getDriver());

    @Given("I am on the login page")
    public void i_am_on_the_login_page() {
        DriverFactory.getDriver().get(ConfigReader.getProperty("url"));
    }

    @When("I enter valid username and password")
    public void i_enter_valid_username_and_password() {
        loginPage.enterUsername(ConfigReader.getProperty("username"));
        loginPage.enterPassword(ConfigReader.getProperty("password"));
    }

    @When("I enter invalid username and password")
    public void i_enter_invalid_username_and_password() {
        loginPage.enterUsername("wronguser");
        loginPage.enterPassword("wrongpass");
    }

    @When("I leave username and password blank")
    public void i_leave_username_and_password_blank() {
        loginPage.enterUsername("");
        loginPage.enterPassword("");
    }

    @When("I click on sign in button")
    public void i_click_on_sign_in_button() {
        loginPage.clickSignIn();
        
    }

    @Then("I should be redirected to the student page")
    public void i_should_be_redirected_to_the_student_page() {
        Assert.assertTrue(loginPage.isStudentMenuPresent(), "Student menu not found, login may have failed");
    }

    @Then("I should see an error message {string}")
    public void i_should_see_an_error_message(String expectedMessage) {
        String actualMessage = loginPage.getErrorMessage();
        Assert.assertEquals(actualMessage, expectedMessage, "Error message does not match!");
    }

}
