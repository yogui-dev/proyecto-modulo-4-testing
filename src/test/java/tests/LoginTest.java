package tests;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.LoginPage;

import java.io.*;
import java.lang.reflect.Method;

@Listeners(listeners.ScreenshotListener.class)
public class LoginTest extends BaseTest {

    @Test(dataProvider = "csvData")
    @Parameters("browser")
    public void testLogin(String browser, String username, String password, String expectedUrl) {

        setUp(browser);
        getDriverInstance().get("https://tienda.demo.yoguilab.space/wp-login.php");

        LoginPage login = new LoginPage(getDriverInstance());
        login.login(username, password);

        String currentUrl = getDriverInstance().getCurrentUrl();

        Assert.assertEquals(currentUrl, expectedUrl,
                "La URL después del login no coincide con la esperada.");
    }

    @DataProvider(name = "csvData")
    public Object[][] getCsvData(Method method) throws IOException {
        // Leer el parámetro "browser" desde el test actual
        String browser = method
                .getDeclaringClass()
                .getAnnotation(org.testng.annotations.Test.class)
                .testName();

        if (browser == null || browser.isEmpty()) {
            browser = "chrome"; // fallback
        } else {
            // Extraer navegador desde el nombre del test si lo escribiste así en el XML
            browser = browser.replace("LoginYRegistro_", "").toLowerCase();
        }

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
        Object[][] data = new Object[records.size()][4];

        int index = 0;
        for (CSVRecord record : records) {
            data[index][0] = browser;
            data[index][1] = record.get("username");
            data[index][2] = record.get("password");
            data[index][3] = record.get("expectedUrl");
            index++;
        }

        reader.close();
        parser.close();
        return data;
    }


}
