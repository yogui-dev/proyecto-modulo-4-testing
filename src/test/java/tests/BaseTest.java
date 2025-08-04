package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;

import java.time.Duration;

/**
 * Clase base para todos los tests de Selenium.
 * Proporciona métodos comunes para la inicialización y gestión del WebDriver.
 * Utiliza WebDriverManager para la configuración automática de drivers.
 */
public class BaseTest {

    /** Instancia del WebDriver que será utilizada por los tests */
    protected WebDriver driver;

    /**
     * Crea y configura una instancia de WebDriver según el navegador especificado.
     * 
     * @param browser Nombre del navegador a utilizar ("chrome" o "firefox")
     * @return Instancia configurada de WebDriver
     * @throws IllegalArgumentException Si se especifica un navegador no soportado
     */
    public WebDriver getDriver(String browser) {
        return switch (browser.toLowerCase()) {
            case "chrome" -> {
                WebDriverManager.chromedriver().setup();
                yield new ChromeDriver();
            }
            case "firefox" -> {
                WebDriverManager.firefoxdriver().setup();
                yield new FirefoxDriver();
            }
            default -> throw new IllegalArgumentException("Navegador no soportado: " + browser);
        };
    }

    /**
     * Inicializa el WebDriver con la configuración básica.
     * Maximiza la ventana y establece un tiempo de espera implícito.
     * 
     * @param browser Nombre del navegador a utilizar
     */
    public void setUp(String browser) {
        driver = getDriver(browser);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    /**
     * Cierra el WebDriver después de cada test.
     * Este método se ejecuta automáticamente después de cada método de test gracias a la anotación @AfterMethod.
     */
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null; // evita sesiones colgadas
        }
    }

    /**
     * Obtiene la instancia actual del WebDriver.
     * 
     * @return La instancia actual del WebDriver
     */
    public WebDriver getDriverInstance() {
        return driver;
    }
}
