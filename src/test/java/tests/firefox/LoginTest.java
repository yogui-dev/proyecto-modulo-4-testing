package tests.firefox;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.LoginPage;
import tests.BaseTest;
import listeners.ScreenshotListener; // Importar la clase ScreenshotListener

import java.io.*;
import java.lang.reflect.Method;

/**
 * Clase de prueba para validar la funcionalidad de inicio de sesión en Firefox.
 * Utiliza datos de prueba desde un archivo CSV para ejecutar múltiples escenarios.
 * Implementa el mismo flujo de pruebas que su contraparte en Chrome pero específico para Firefox.
 */
@Listeners({ScreenshotListener.class}) // Corregir la anotación @Listeners
public class LoginTest extends BaseTest {

    /**
     * Prueba el proceso de inicio de sesión con diferentes combinaciones de datos en Firefox.
     * Valida tanto casos de éxito (redirección correcta) como casos de error (mensajes de error).
     * 
     * @param username Nombre de usuario para el login
     * @param password Contraseña para el login
     * @param expectedMessage Mensaje esperado (URL o mensaje de error)
     * @param selector Selector CSS o XPath para localizar elementos (en casos de error)
     * @param type Tipo de selector (css, xpath)
     * @param testType Tipo de prueba (success, error)
     */
    @Test(dataProvider = "csvData")
    public void testLogin(String username, String password, String expectedMessage,
                          String selector, String type, String testType) {

        setUp("firefox");
        getDriverInstance().get("https://tienda.demo.yoguilab.space/mi-cuenta/");

        LoginPage login = new LoginPage(getDriverInstance());
        login.login(username, password);

        switch (testType.toLowerCase()) {
            case "success":
                // Login exitoso: redirección a una URL esperada
                String currentUrl = getDriverInstance().getCurrentUrl();
                Assert.assertTrue(currentUrl.contains(expectedMessage),
                        "La URL después del login no contiene la cadena esperada.");
                break;

            case "error":
                // Login fallido: verificar mensaje de error usando selector
                try {
                    String actualMessage = "";

                    switch (type.toLowerCase()) {
                        case "css":
                            actualMessage = getDriverInstance()
                                    .findElement(By.cssSelector(selector))
                                    .getText()
                                    .trim();
                            break;
                        case "xpath":
                            actualMessage = getDriverInstance()
                                    .findElement(By.xpath(selector))
                                    .getText()
                                    .trim();
                            break;
                        default:
                            Assert.fail("Tipo de selector no soportado: " + type);
                    }

                    Assert.assertEquals(actualMessage, expectedMessage,
                            "El mensaje de error no coincide con el esperado.");

                } catch (Exception e) {
                    Assert.fail("No se pudo encontrar el mensaje con el selector proporcionado: " + selector);
                }
                break;

            default:
                Assert.fail("Tipo de test no reconocido: " + testType);
        }
    }

    /**
     * Proveedor de datos para las pruebas de login en Firefox.
     * Lee los datos desde un archivo CSV con el formato:
     * username,password,expectedMessage,selector,type,testType
     * 
     * @param method Método de prueba que utilizará los datos
     * @return Array bidimensional con los datos de prueba
     * @throws IOException Si hay problemas al leer el archivo CSV
     */
    @DataProvider(name = "csvData")
    public Object[][] getCsvData(Method method) throws IOException {

        InputStream input = getClass().getClassLoader().getResourceAsStream("data/login.csv");
        if (input == null) {
            throw new FileNotFoundException("Archivo CSV no encontrado en resources/data/");
        }

        Reader reader = new InputStreamReader(input);
        CSVFormat format = CSVFormat.DEFAULT.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .setTrim(true)
                .build();
        CSVParser parser = new CSVParser(reader, format);

        var records = parser.getRecords();
        Object[][] data = new Object[records.size()][6];

        int index = 0;
        for (CSVRecord record : records) {
            data[index][0] = record.get("username");
            data[index][1] = record.get("password");
            data[index][2] = record.get("expectedMessage");
            data[index][3] = record.get("selector");
            data[index][4] = record.get("type");
            data[index][5] = record.get("testType");
            index++;
        }

        reader.close();
        parser.close();
        return data;
    }
}
