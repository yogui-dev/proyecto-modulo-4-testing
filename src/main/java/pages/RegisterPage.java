package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Clase Page Object que representa la página de registro de usuarios.
 * Encapsula todos los elementos y acciones relacionadas con el registro de nuevos usuarios.
 * Implementa el patrón Page Object Model para separar la lógica de UI de la lógica de pruebas.
 */
public class RegisterPage {

    /** Instancia del WebDriver */
    private WebDriver driver;

    /** Localizadores de elementos en la página */
    private final By emailField = By.id("reg_email");
    private final By registerButton = By.cssSelector("button[name='register']");

    /**
     * Constructor que inicializa la página de registro.
     *
     * @param driver Instancia de WebDriver a utilizar
     */
    public RegisterPage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Realiza el proceso de registro de un nuevo usuario con el email proporcionado.
     * En el sitio de prueba, solo se requiere el email para el registro inicial.
     * El sistema generará una contraseña temporal y la enviará por correo.
     *
     * @param email Dirección de correo electrónico para el registro
     */
    public void register(String email) {
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(registerButton).click();
    }
}
