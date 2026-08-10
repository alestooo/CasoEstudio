package com.cenfotec.e2e.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class ScreenshotUtils {

    public static void tomarCaptura(
            WebDriver driver,
            String nombrePrueba) {

        if (driver == null) {
            return;
        }

        try {

            File screenshot =
                    ((TakesScreenshot) driver)
                            .getScreenshotAs(OutputType.FILE);

            Path carpeta =
                    Path.of("target", "screenshots");

            Files.createDirectories(carpeta);

            Path destino =
                    carpeta.resolve(
                            nombrePrueba + ".png"
                    );

            Files.copy(
                    screenshot.toPath(),
                    destino,
                    StandardCopyOption.REPLACE_EXISTING
            );

            System.out.println(
                    "[SCREENSHOT] Guardado en: "
                            + destino
            );

        } catch (IOException e) {

            System.err.println(
                    "[ERROR] No se pudo guardar screenshot: "
                            + e.getMessage()
            );
        }
    }
}