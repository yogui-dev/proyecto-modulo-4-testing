package tests;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;

import java.io.*;

public class LoginTest {

    private static final Logger log = LoggerFactory.getLogger(LoginTest.class);

    WebDriver driver;

    @BeforeMethod
    public void setup() {
        driver = new FirefoxDriver();
        driver.get("https://tienda.demo.yoguilab.space/wp-login.php");
    }

    @Test(dataProvider = "csvLoginData")
    public void testLogin(String user, String pass, String expectedUrl) {
        log.info("Iniciando test de login con usuario: {}", user);
        LoginPage login = new LoginPage(driver);
        login.login(user, pass);

        String currentUrl = driver.getCurrentUrl();
        log.info("URL actual después del login: {}", currentUrl);

        Assert.assertEquals(currentUrl, expectedUrl,
                "La URL después del login no coincide con la esperada.");
    }

    @DataProvider(name = "csvLoginData")
    public Object[][] getDataFromCsv() throws IOException {
        InputStream input = getClass().getClassLoader().getResourceAsStream("data/users.csv");
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
        Object[][] data = new Object[records.size()][3];

        int i = 0;
        for (CSVRecord record : records) {
            data[i][0] = record.get("username");
            data[i][1] = record.get("password");
            data[i][2] = record.get("expectedUrl");
            i++;
        }

        reader.close();
        parser.close();
        log.info("Datos leidos del archivo CSV: {}", data.length);
        return data;
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
