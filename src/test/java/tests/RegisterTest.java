package tests;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.RegisterPage;

import java.io.*;

public class RegisterTest extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(RegisterTest.class);

    @Test(dataProvider = "csvRegisterData")
    public void testLogin(String email, String expectedMessage) {
        log.info("Iniciando test de login con usuario: {}", email);

        getDriver().get("https://tienda.demo.yoguilab.space/mi-cuenta/");

        RegisterPage register = new RegisterPage(getDriver());
        register.register(email);

        String currentUrl = getDriver().getCurrentUrl();
        log.info("URL actual después del login: {}", currentUrl);

        Assert.assertTrue(getDriver().getPageSource().contains(expectedMessage),
                "Tu cuenta de Tienda Demo está utilizando una contraseña temporal. Te hemos enviado por correo electrónico un enlace para cambiar tu contraseña.");
    }


    @DataProvider(name = "csvRegisterData")
    public Object[][] getDataFromCsv() throws IOException {
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

        int i = 0;
        for (CSVRecord record : records) {
            data[i][0] = record.get("email");
            data[i][1] = record.get("expectedMessage");
            i++;
        }

        reader.close();
        parser.close();
        log.info("Datos leídos del archivo CSV: {}", data.length);
        return data;
    }
}
