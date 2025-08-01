package tests;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.RegisterPage;

import java.io.*;

public class RegisterTest extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(RegisterTest.class);

    @Test(dataProvider = "csvData")
    @Parameters("browser")
    public void testRegister(String browser, String email, String expectedMessage) {
        log.info("Iniciando test en [{}] con email: {}", browser, email);

        setUp(browser); // Inicializa el navegador
        getDriverInstance().get("https://tienda.demo.yoguilab.space/mi-cuenta/");

        RegisterPage register = new RegisterPage(getDriverInstance());
        register.register(email);

        String currentUrl = getDriverInstance().getCurrentUrl();
        log.info("URL actual después del registro: {}", currentUrl);

        boolean contieneMensaje = getDriverInstance()
                .getPageSource()
                .contains(expectedMessage);

        Assert.assertTrue(contieneMensaje,
                "Tu cuenta de Tienda Demo está utilizando una contraseña temporal. Te hemos enviado por correo electrónico un enlace para cambiar tu contraseña.");

        // tearDown() lo hace TestNG automáticamente con @AfterMethod
    }


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
        Object[][] data = new Object[records.size()][2];

        int index = 0;
        for (CSVRecord record : records) {
            data[index][0] = record.get("email");
            data[index][1] = record.get("expectedMessage");
            index++;
        }

        reader.close();
        parser.close();
        return data;
    }

}
