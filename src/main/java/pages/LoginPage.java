package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Clase Page Object que representa la página de inicio de sesión.
 * Encapsula todos los elementos y acciones relacionadas con el login de usuarios.
 * Implementa esperas explícitas y manejo de excepciones para interacciones robustas.
 */
public class LoginPage {

    /** Instancia del WebDriver */
    private final WebDriver driver;

    /** Localizadores de elementos en la página */
    private final By usernameField = By.id("username");
    private final By passwordField = By.id("password");
    private final By loginButton = By.cssSelector("button[name='login']");

    /**
     * Constructor que inicializa la página de login.
     *
     * @param driver Instancia de WebDriver a utilizar
     */
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Realiza el proceso de inicio de sesión con las credenciales proporcionadas.
     * Implementa esperas explícitas para asegurar que los elementos estén disponibles
     * y maneja posibles excepciones durante la interacción.
     *
     * @param username Nombre de usuario o email para el login
     * @param password Contraseña del usuario
     * @throws RuntimeException Si no se puede completar el proceso de login debido a elementos no interactuables
     */
    public void login(String username, String password) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Esperar campo de usuario visible y llenar
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField)).sendKeys(username);

        // Llenar campo de contraseña
        driver.findElement(passwordField).sendKeys(password);

        // Esperar botón clickeable
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(loginButton));

        // Scroll hasta el botón
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);

        // Intentar hacer clic
        try {
            button.click();
        } catch (ElementClickInterceptedException e) {
            // Esperar a que desaparezca un posible overlay o notificación flotante
            try {
                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("woocommerce-message") // <- Cambia esto si usas otro overlay
                ));
                button.click(); // Reintento
            } catch (Exception ex) {
                throw new RuntimeException("No se pudo hacer clic en el botón de login. Posible overlay.", ex);
            }
        }
    }
}
