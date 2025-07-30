package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    WebDriver driver;

    By usernameField = By.id("user_login");
    By passwordField = By.id("user_pass");
    By loginButton = By.id("wp-submit");

    /**
     * Constructor to initialize the LoginPage object.
     *
     * @param driver the WebDriver instance to use.
     */
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Logs in to the WordPress site with the given username and password.
     *
     * @param username the username to log in with.
     * @param password the password to log in with.
     */
    public void login(String username, String password) {
        driver.findElement(usernameField).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(loginButton).click();
    }
}
