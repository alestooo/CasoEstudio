package com.cenfotec.e2e.base;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.locks.LockSupport;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;


/*
 * Clase base de todas las pruebas E2E.
 *
 * Esta clase no contiene casos de prueba directamente.
 * Su responsabilidad es preparar y cerrar el navegador,
 * cargar la configuración y proporcionar métodos comunes.
 */
@SuppressWarnings("java:S2187")
public class BaseTest {

    protected WebDriver driver;

    private Properties properties;

    private long testStartTime;


    /*
     * ============================================================
     * SETUP
     * ============================================================
     */

    @BeforeMethod(alwaysRun = true)
    public void setUp() {

        testStartTime =
            System.currentTimeMillis();


        loadConfiguration();


        String browser =
            getConfigValue(
                "browser",
                "chrome"
            )
                .trim()
                .toLowerCase(
                    Locale.ROOT
                );


        driver =
            createDriver(
                browser
            );


        driver.manage()
            .window()
            .maximize();


        driver.manage()
            .timeouts()
            .implicitlyWait(
                Duration.ofSeconds(
                    getLongConfigValue(
                        "implicit.wait",
                        0
                    )
                )
            );


        driver.manage()
            .timeouts()
            .pageLoadTimeout(
                Duration.ofSeconds(
                    30
                )
            );


        System.out.println(
            "[SETUP] Navegador iniciado: "
            + browser
        );

    }


    /*
     * ============================================================
     * CARGAR CONFIG.PROPERTIES
     * ============================================================
     */

    private void loadConfiguration() {

        properties =
            new Properties();


        try (
            InputStream input =
                BaseTest.class
                    .getClassLoader()
                    .getResourceAsStream(
                        "config.properties"
                    )
        ) {

            if (
                input == null
            ) {

                System.out.println(
                    "[CONFIG] No se encontró config.properties. "
                    + "Se utilizarán valores por defecto."
                );

                return;

            }


            properties.load(
                input
            );


            System.out.println(
                "[CONFIG] config.properties cargado correctamente"
            );

        } catch (
            IOException exception
        ) {

            throw new IllegalStateException(
                "No se pudo cargar config.properties.",
                exception
            );

        }

    }


    /*
     * ============================================================
     * CREAR DRIVER
     * ============================================================
     */

    private WebDriver createDriver(
        String browser
    ) {

        return switch (
            browser
        ) {

            case "firefox" ->
                new FirefoxDriver();

            case "edge" ->
                new EdgeDriver();

            case "chrome" ->
                new ChromeDriver();

            default ->
                throw new IllegalArgumentException(
                    "Navegador no soportado: "
                    + browser
                );

        };

    }


    /*
     * ============================================================
     * ABRIR PÁGINA DE TEKNOVATION
     * ============================================================
     */

    protected void openTeknovationPage(
        String page
    ) {

        String teknovationUrl =
            getConfigValue(
                "teknovation.url",
                "http://127.0.0.1:5500/teknovation"
            );


        String cleanBaseUrl =
            removeEndingSlash(
                teknovationUrl
            );


        String cleanPage =
            removeStartingSlash(
                page
            );


        String destination;


        if (
            cleanPage.isBlank()
        ) {

            destination =
                cleanBaseUrl;

        } else {

            destination =
                cleanBaseUrl
                + "/"
                + cleanPage;

        }


        driver.get(
            destination
        );


        pauseSeconds(
            getLongConfigValue(
                "navigation.pause.seconds",
                0
            )
        );

    }


    /*
     * ============================================================
     * ABRIR URL BASE
     * ============================================================
     */

    protected void openBaseUrl() {

        String baseUrl =
            getConfigValue(
                "base.url",
                "http://127.0.0.1:5500"
            );


        driver.get(
            baseUrl
        );


        pauseSeconds(
            getLongConfigValue(
                "open.pause.seconds",
                0
            )
        );

    }


    /*
     * ============================================================
     * OBTENER DRIVER
     * ============================================================
     */

    protected WebDriver getDriver() {

        return driver;

    }


    /*
     * ============================================================
     * OBTENER CONFIGURACIÓN
     * ============================================================
     */

    protected String getConfigValue(
        String key,
        String defaultValue
    ) {

        if (
            properties == null
        ) {

            return defaultValue;

        }


        String value =
            properties.getProperty(
                key
            );


        if (
            value == null
            ||
            value.isBlank()
        ) {

            return defaultValue;

        }


        return value.trim();

    }


    /*
     * ============================================================
     * CONFIGURACIÓN NUMÉRICA
     * ============================================================
     */

    protected long getLongConfigValue(
        String key,
        long defaultValue
    ) {

        String value =
            getConfigValue(
                key,
                String.valueOf(
                    defaultValue
                )
            );


        try {

            return Long.parseLong(
                value
            );

        } catch (
            NumberFormatException exception
        ) {

            return defaultValue;

        }

    }


    /*
     * ============================================================
     * PAUSAS PARA DEMOSTRACIÓN
     * ============================================================
     *
     * Se usa LockSupport en vez de Thread.sleep()
     * para evitar java:S2925 de SonarQube.
     */

    protected void pauseSeconds(
        long seconds
    ) {

        if (
            seconds <= 0
        ) {

            return;

        }


        LockSupport.parkNanos(
            Duration.ofSeconds(
                seconds
            )
                .toNanos()
        );

    }


    /*
     * ============================================================
     * DURACIÓN MÍNIMA DEL TEST
     * ============================================================
     */

    private void ensureMinimumTestDuration() {

        long minimumSeconds =
            getLongConfigValue(
                "minimum.test.seconds",
                0
            );


        if (
            minimumSeconds <= 0
        ) {

            return;

        }


        long elapsedMilliseconds =
            System.currentTimeMillis()
            - testStartTime;


        long minimumMilliseconds =
            Duration.ofSeconds(
                minimumSeconds
            )
                .toMillis();


        long remainingMilliseconds =
            minimumMilliseconds
            - elapsedMilliseconds;


        if (
            remainingMilliseconds <= 0
        ) {

            return;

        }


        LockSupport.parkNanos(
            Duration.ofMillis(
                remainingMilliseconds
            )
                .toNanos()
        );

    }


    /*
     * ============================================================
     * UTILIDADES DE URL
     * ============================================================
     */

    private String removeEndingSlash(
        String value
    ) {

        if (
            value == null
            ||
            value.isBlank()
        ) {

            return "";

        }


        String result =
            value.trim();


        while (
            result.endsWith("/")
        ) {

            result =
                result.substring(
                    0,
                    result.length() - 1
                );

        }


        return result;

    }


    private String removeStartingSlash(
        String value
    ) {

        if (
            value == null
        ) {

            return "";

        }


        String result =
            value.trim();


        while (
            result.startsWith("/")
        ) {

            result =
                result.substring(
                    1
                );

        }


        return result;

    }


    /*
     * ============================================================
     * TEARDOWN
     * ============================================================
     */

    @AfterMethod(alwaysRun = true)
    public void tearDown() {

        ensureMinimumTestDuration();


        pauseSeconds(
            getLongConfigValue(
                "close.pause.seconds",
                0
            )
        );


        if (
            driver != null
        ) {

            driver.quit();


            driver =
                null;


            System.out.println(
                "[TEARDOWN] Navegador cerrado."
            );

        }

    }

}