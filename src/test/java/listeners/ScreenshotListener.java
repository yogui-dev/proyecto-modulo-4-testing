package listeners;

import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;
import tests.BaseTest;
import utils.ScreenshotUtil;

/**
 * Listener de TestNG que captura screenshots automáticamente durante la ejecución de pruebas.
 * Implementa la interfaz ITestListener y sobrescribe varios métodos para
 * capturar imágenes del estado del navegador en diferentes momentos de la ejecución.
 * 
 * Esta clase trabaja en conjunto con la utilidad ScreenshotUtil para guardar las capturas
 * y requiere que la clase de prueba extienda de BaseTest para acceder al WebDriver.
 */
public class ScreenshotListener implements ITestListener {

    /**
     * Se ejecuta automáticamente cuando una prueba comienza.
     * Captura una imagen del estado inicial del navegador.
     * 
     * @param result Objeto ITestResult que contiene información sobre la prueba
     */
    @Override
    public void onTestStart(ITestResult result) {
        captureScreenshot(result, "START");
    }

    /**
     * Se ejecuta automáticamente cuando una prueba finaliza con éxito.
     * Captura una imagen del estado final del navegador tras una prueba exitosa.
     * 
     * @param result Objeto ITestResult que contiene información sobre la prueba exitosa
     */
    @Override
    public void onTestSuccess(ITestResult result) {
        captureScreenshot(result, "SUCCESS");
    }

    /**
     * Se ejecuta automáticamente cuando una prueba falla.
     * Captura una imagen del estado del navegador en el momento del fallo.
     * 
     * @param result Objeto ITestResult que contiene información sobre la prueba fallida
     */
    @Override
    public void onTestFailure(ITestResult result) {
        captureScreenshot(result, "FAILURE");
    }

    /**
     * Se ejecuta automáticamente cuando una prueba es omitida.
     * Captura una imagen del estado del navegador cuando se omite una prueba.
     * 
     * @param result Objeto ITestResult que contiene información sobre la prueba omitida
     */
    @Override
    public void onTestSkipped(ITestResult result) {
        captureScreenshot(result, "SKIPPED");
    }

    /**
     * Método auxiliar para capturar screenshots.
     * Extrae el WebDriver de la instancia de BaseTest y llama a ScreenshotUtil.
     * 
     * @param result Objeto ITestResult que contiene información sobre la prueba
     * @param status Estado de la prueba (START, SUCCESS, FAILURE, SKIPPED)
     */
    private void captureScreenshot(ITestResult result, String status) {
        Object currentClass = result.getInstance();

        if (currentClass instanceof BaseTest baseTest) {
            WebDriver driver = baseTest.getDriverInstance();
            if (driver != null) {
                ScreenshotUtil.takeScreenshot(driver, result.getName() + "_" + status);
            } else {
                System.err.println("⚠️ No se pudo capturar screenshot porque el driver es null");
            }
        } else {
            System.err.println("⚠️ La clase del test no es instancia de BaseTest");
        }
    }
}
