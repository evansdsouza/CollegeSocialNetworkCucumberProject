package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    private By usernameInput = By.id("username");
    private By passwordInput = By.id("password");
    private By signInButton = By.id("signIn");
    private By studentMenu = By.id("student");
    private By errorMessage = By.id("error"); // Changed from xpath to id for better performance

    public void enterUsername(String username) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput)).clear();
        driver.findElement(usernameInput).sendKeys(username);
    }

    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput)).clear();
        driver.findElement(passwordInput).sendKeys(password);
    }

    public void clickSignIn() {
        wait.until(ExpectedConditions.elementToBeClickable(signInButton)).click();
    }

    public boolean isStudentMenuPresent() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(studentMenu));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getErrorMessage() {
        try {
            // Method 1: Wait for specific text to be present
            wait.until(ExpectedConditions.textToBePresentInElementLocated(
                errorMessage, "Invalid username/password"));
            
            WebElement el = driver.findElement(errorMessage);
            return el.getAttribute("innerText").trim();
            
        } catch (TimeoutException e) {
            try {
                // Method 2: Custom wait for any non-empty text
                WebElement errorElement = wait.until(new ExpectedCondition<WebElement>() {
                    @Override
                    public WebElement apply(WebDriver driver) {
                        try {
                            WebElement el = driver.findElement(errorMessage);
                            if (el.isDisplayed()) {
                                String text = el.getAttribute("innerText");
                                if (text != null && !text.trim().isEmpty()) {
                                    return el;
                                }
                                // Also try textContent
                                text = el.getAttribute("textContent");
                                if (text != null && !text.trim().isEmpty()) {
                                    return el;
                                }
                            }
                        } catch (Exception ex) {
                            // Element not found or not visible yet
                        }
                        return null;
                    }
                });
                
                // Try multiple text retrieval methods
                String text = errorElement.getAttribute("innerText").trim();
                if (text.isEmpty()) {
                    text = errorElement.getAttribute("textContent").trim();
                }
                if (text.isEmpty()) {
                    text = errorElement.getText().trim();
                }
                
                return text;
                
            } catch (TimeoutException ex) {
                System.out.println("Error message did not appear with text content.");
                
                // Debug information
                try {
                    List<WebElement> errorElements = driver.findElements(errorMessage);
                    if (!errorElements.isEmpty()) {
                        WebElement el = errorElements.get(0);
                        System.out.println("Element found but text methods return:");
                        System.out.println("getText(): '" + el.getText() + "'");
                        System.out.println("innerText: '" + el.getAttribute("innerText") + "'");
                        System.out.println("textContent: '" + el.getAttribute("textContent") + "'");
                        System.out.println("innerHTML: '" + el.getAttribute("innerHTML") + "'");
                        System.out.println("isDisplayed: " + el.isDisplayed());
                    } else {
                        System.out.println("No element with id='error' found");
                    }
                } catch (Exception debugEx) {
                    System.out.println("Error during debugging: " + debugEx.getMessage());
                }
                
                return "";
            }
        }
    }
}
