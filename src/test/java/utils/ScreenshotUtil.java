package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utilidad para capturar y guardar screenshots durante la ejecución de pruebas.
 * Proporciona métodos estáticos para tomar capturas de pantalla del navegador
 * y guardarlas en disco con nombres basados en la fecha y el nombre del test.
 */
public class ScreenshotUtil {

    /** Directorio donde se guardarán las capturas de pantalla */
    private static final String SCREENSHOT_DIR = "test-output/screenshots/";
    
    /** Formato de fecha y hora para los nombres de archivo */
    private static final String DATE_FORMAT = "yyyy-MM-dd_HH-mm-ss";

    /**
     * Captura una imagen del estado actual del navegador y la guarda en disco.
     * El archivo se guarda en el directorio de screenshots con un nombre que incluye
     * el nombre del test y una marca de tiempo legible.
     *
     * @param driver Instancia de WebDriver desde la que se tomará la captura
     * @param testName Nombre del test que falló, usado para nombrar el archivo
     * @return Path del archivo de screenshot guardado, o null si ocurrió un error
     */
    public static Path takeScreenshot(WebDriver driver, String testName) {
        if (driver == null) {
            System.err.println("No se puede tomar screenshot: WebDriver es null");
            return null;
        }

        // Crear directorio si no existe
        try {
            Files.createDirectories(Paths.get(SCREENSHOT_DIR));
        } catch (IOException e) {
            System.err.println("Error al crear directorio de screenshots: " + e.getMessage());
            return null;
        }

        // Generar nombre de archivo con fecha y hora legible
        String timestamp = new SimpleDateFormat(DATE_FORMAT).format(new Date());
        String fileName = SCREENSHOT_DIR + timestamp + "_" + testName + ".png";
        Path screenshotPath = Paths.get(fileName);

        try {
            // Tomar screenshot
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            
            // Guardar archivo
            Files.copy(screenshotFile.toPath(), screenshotPath);
            System.out.println("Screenshot guardado: " + screenshotPath.toAbsolutePath());
            return screenshotPath;
        } catch (IOException e) {
            System.err.println("Error al guardar screenshot: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("Error al tomar screenshot: " + e.getMessage());
            return null;
        }
    }
}
