package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    WebDriver driver;

    By usernameField = By.id("user_login");
    By passwordField = By.id("user_pass");
    By loginButton = By.id("wp-submit");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void login(String username, String password) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField))
                .sendKeys(username);

        driver.findElement(passwordField).sendKeys(password);

        // Espera nuevamente y busca el botón en ese momento
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

}
