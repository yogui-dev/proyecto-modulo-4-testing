package tests.chrome;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.RegisterPage;
import tests.BaseTest;

import java.io.*;

/**
 * Clase de prueba para validar la funcionalidad de registro de usuarios en Chrome.
 * Utiliza datos de prueba desde un archivo CSV para ejecutar múltiples escenarios.
 * Verifica tanto casos de éxito como casos de error en el proceso de registro.
 */
public class RegisterTest extends BaseTest {

    /**
     * Prueba el proceso de registro de usuarios con diferentes combinaciones de datos.
     * Valida tanto casos de éxito (redirección correcta) como casos de error (mensajes de error).
     * 
     * @param email Dirección de correo electrónico para el registro
     * @param expectedMessage Mensaje esperado (URL o mensaje de error)
     * @param selector Selector CSS o XPath para localizar elementos (en casos de error)
     * @param type Tipo de selector (css, xpath)
     * @param testType Tipo de prueba (success, error)
     */
    @Test(dataProvider = "csvData")
    public void testRegister(String email, String expectedMessage, String selector, String type, String testType) {
        setUp("chrome");
        getDriverInstance().get("https://tienda.demo.yoguilab.space/mi-cuenta/");

        RegisterPage register = new RegisterPage(getDriverInstance());
        register.register(email);

        switch (testType.toLowerCase()) {
            case "success":
                // Registro exitoso: validamos redirección o mensaje de éxito
                String currentUrl = getDriverInstance().getCurrentUrl();
                Assert.assertTrue(currentUrl.contains(expectedMessage),
                        "La URL después del registro no contiene la cadena esperada.");
                break;

            case "error":
                // Registro fallido: validamos mensaje de error
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
                    Assert.fail("No se pudo encontrar el mensaje con el selector proporcionado: " + selector + ". Error: " + e.getMessage());
                }
                break;

            default:
                Assert.fail("Tipo de test no reconocido: " + testType);
        }
    }

    /**
     * Proveedor de datos para las pruebas de registro.
     * Lee los datos desde un archivo CSV con el formato:
     * email,expectedMessage,selector,type,testType
     * 
     * @return Array bidimensional con los datos de prueba
     * @throws IOException Si hay problemas al leer el archivo CSV
     */
    @DataProvider(name = "csvData")
    public Object[][] getCsvData() throws IOException {
        InputStream input = getClass().getClassLoader().getResourceAsStream("data/register.csv");
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
        Object[][] data = new Object[records.size()][5];

        int index = 0;
        for (CSVRecord record : records) {
            data[index][0] = record.get("email");
            data[index][1] = record.get("expectedMessage");
            data[index][2] = record.get("selector");
            data[index][3] = record.get("type");
            data[index][4] = record.get("testType");
            index++;
        }

        reader.close();
        parser.close();
        return data;
    }
}
