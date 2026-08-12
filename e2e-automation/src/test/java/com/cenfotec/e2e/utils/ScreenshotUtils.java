package com.cenfotec.e2e.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ScreenshotUtils {

    private static final String SCREENSHOT_DIRECTORY =
            "target/screenshots";


    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern(
                    "yyyyMMdd_HHmmss_SSS"
            );


    private ScreenshotUtils() {
        // Utility class.
    }


    public static String takeScreenshot(
            WebDriver driver,
            String testName
    ) {

        if (
                driver == null
        ) {

            return "";
        }


        if (
                !(driver instanceof TakesScreenshot)
        ) {

            return "";
        }


        try {

            File source =
                    ((TakesScreenshot) driver)
                            .getScreenshotAs(
                                    OutputType.FILE
                            );


            Path directory =
                    Path.of(
                            SCREENSHOT_DIRECTORY
                    );


            Files.createDirectories(
                    directory
            );


            String safeTestName =
                    sanitizeFileName(
                            testName
                    );


            String timestamp =
                    LocalDateTime
                            .now()
                            .format(
                                    FORMATTER
                            );


            String fileName =
                    safeTestName
                            + "_"
                            + timestamp
                            + ".png";


            Path destination =
                    directory.resolve(
                            fileName
                    );


            Files.copy(
                    source.toPath(),
                    destination,
                    StandardCopyOption.REPLACE_EXISTING
            );


            return destination
                    .toAbsolutePath()
                    .toString();

        } catch (
                IOException exception
        ) {

            System.err.println(
                    "No se pudo guardar el screenshot: "
                            + exception.getMessage()
            );

            return "";

        } catch (
                WebDriverException exception
        ) {

            System.out.println(
                    "[SCREENSHOT] No se pudo tomar la captura de "
                            + testName
                            + ". El navegador puede tener un alert abierto."
            );

            return "";
        }
    }


    private static String sanitizeFileName(
            String value
    ) {

        if (
                value == null
                || value.isBlank()
        ) {

            return "test";
        }


        return value
                .replaceAll(
                        "[^a-zA-Z0-9._-]",
                        "_"
                );
    }
}