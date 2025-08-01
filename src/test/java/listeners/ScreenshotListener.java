package listeners;

import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;
import tests.BaseTest;
import utils.ScreenshotUtil;

public class ScreenshotListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        Object currentClass = result.getInstance();

        if (currentClass instanceof BaseTest baseTest) {
            WebDriver driver = baseTest.getDriverInstance();
            if (driver != null) {
                ScreenshotUtil.takeScreenshot(driver, result.getName());
            } else {
                System.err.println("⚠️ No se pudo capturar screenshot porque el driver es null");
            }
        } else {
            System.err.println("⚠️ La clase del test no es instancia de BaseTest");
        }
    }
}
