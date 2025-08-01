package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterPage {


    WebDriver driver;

    By emailField = By.id("reg_email");
    By registerButton = By.cssSelector("button[name='register']");

    /**
     * Constructor to initialize the LoginPage object.
     *
     * @param driver the WebDriver instance to use.
     */
    public RegisterPage(WebDriver driver) {
        this.driver = driver;
    }


    /**
     * Registers a new user on the WordPress site with the given email address.
     *
     * @param email the email address to register with.
     */
    public void register(String email) {
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(registerButton).click();
    }
}
