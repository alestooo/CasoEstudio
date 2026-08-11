package com.cenfotec.e2e.base;

import com.cenfotec.e2e.config.ConfigReader;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

/**
 * Clase base de todas las pruebas E2E de Teknovation.
 *
 * Se encarga de:
 *
 * - Leer config.properties.
 * - Abrir el navegador.
 * - Configurar tiempos de espera.
 * - Abrir la aplicación.
 * - Permitir pausas visuales configurables.
 * - Mantener una duración mínima por prueba.
 * - Cerrar el navegador.
 */
public class BaseTest {

    protected WebDriver driver;

    protected ConfigReader config;

    /*
     * Momento en que inicia cada prueba.
     */
    private long testStartTime;


    /* =========================================================
       SETUP
       ========================================================= */

    @BeforeMethod
    public void setUp() {

        /*
         * Registrar hora de inicio.
         */
        testStartTime =
                System.currentTimeMillis();


        /*
         * Leer configuración.
         */
        config =
                new ConfigReader();


        /*
         * Obtener navegador.
         */
        String browser =
                config
                        .getBrowser()
                        .trim()
                        .toLowerCase();


        /*
         * Crear WebDriver.
         */
        driver =
                createDriver(
                        browser
                );


        /*
         * Maximizar ventana.
         */
        driver.manage()
                .window()
                .maximize();


        /*
         * Espera implícita.
         *
         * Recomendado:
         * implicit.wait=0
         *
         * porque usamos WebDriverWait.
         */
        driver.manage()
                .timeouts()
                .implicitlyWait(
                        Duration.ofSeconds(
                                config.getImplicitWait()
                        )
                );


        /*
         * Tiempo máximo de carga.
         */
        driver.manage()
                .timeouts()
                .pageLoadTimeout(
                        Duration.ofSeconds(
                                30
                        )
                );


        /*
         * Abrir servidor base.
         */
        driver.get(
                config.getBaseUrl()
        );


        /*
         * Pausa opcional después
         * de abrir Chrome.
         */
        pause(
                config.getOpenPauseSeconds()
        );

    }


    /* =========================================================
       CREAR DRIVER
       ========================================================= */

    private WebDriver createDriver(
            String browser
    ) {

        switch (browser) {

            case "chrome":

                ChromeOptions chromeOptions =
                        new ChromeOptions();


                chromeOptions.addArguments(
                        "--disable-notifications"
                );


                chromeOptions.addArguments(
                        "--disable-popup-blocking"
                );


                return new ChromeDriver(
                        chromeOptions
                );


            case "firefox":

                FirefoxOptions firefoxOptions =
                        new FirefoxOptions();


                return new FirefoxDriver(
                        firefoxOptions
                );


            case "edge":

                EdgeOptions edgeOptions =
                        new EdgeOptions();


                edgeOptions.addArguments(
                        "--disable-notifications"
                );


                return new EdgeDriver(
                        edgeOptions
                );


            default:

                throw new IllegalArgumentException(
                        "Navegador no soportado: "
                                + browser
                );

        }

    }


    /* =========================================================
       ABRIR PÁGINAS TEKNOVATION
       ========================================================= */

    protected void openTeknovationPage(
            String page
    ) {

        String teknovationUrl =
                config.getTeknovationUrl();


        /*
         * Si no se especifica página,
         * abre solamente /teknovation.
         */
        if (
                page == null
                || page.isBlank()
        ) {

            driver.get(
                    teknovationUrl
            );


            pause(
                    config.getNavigationPauseSeconds()
            );


            return;

        }


        /*
         * Evitar doble slash.
         */
        String cleanPage =
                page.startsWith("/")
                        ? page.substring(1)
                        : page;


        /*
         * Navegar.
         */
        driver.get(
                teknovationUrl
                        + "/"
                        + cleanPage
        );


        /*
         * Pausa visual opcional.
         */
        pause(
                config.getNavigationPauseSeconds()
        );

    }


    /* =========================================================
       LOCAL STORAGE
       ========================================================= */

    protected void clearLocalStorage() {

        if (
                driver == null
        ) {

            return;

        }


        driver.navigate()
                .to(
                        config.getTeknovationUrl()
                                + "/index.html"
                );


        ((JavascriptExecutor) driver)
                .executeScript(
                        "window.localStorage.clear();"
                );

    }


    /* =========================================================
       SESSION STORAGE
       ========================================================= */

    protected void clearSessionStorage() {

        if (
                driver == null
        ) {

            return;

        }


        ((JavascriptExecutor) driver)
                .executeScript(
                        "window.sessionStorage.clear();"
                );

    }


    /* =========================================================
       PAUSA CONFIGURABLE
       ========================================================= */

    protected void pause(
            long seconds
    ) {

        /*
         * 0 = sin pausa.
         */
        if (
                seconds <= 0
        ) {

            return;

        }


        try {

            Thread.sleep(
                    seconds * 1000
            );

        } catch (
                InterruptedException exception
        ) {

            Thread.currentThread()
                    .interrupt();

        }

    }


    /* =========================================================
       DURACIÓN MÍNIMA DEL TEST
       ========================================================= */

    private void ensureMinimumTestDuration() {

        long minimumSeconds =
                config
                        .getMinimumTestSeconds();


        /*
         * 0 = comportamiento normal.
         */
        if (
                minimumSeconds <= 0
        ) {

            return;

        }


        long elapsedMilliseconds =
                System.currentTimeMillis()
                        - testStartTime;


        long minimumMilliseconds =
                minimumSeconds
                        * 1000;


        long remainingMilliseconds =
                minimumMilliseconds
                        - elapsedMilliseconds;


        /*
         * El test ya duró más del mínimo.
         */
        if (
                remainingMilliseconds <= 0
        ) {

            return;

        }


        try {

            Thread.sleep(
                    remainingMilliseconds
            );

        } catch (
                InterruptedException exception
        ) {

            Thread.currentThread()
                    .interrupt();

        }

    }


    /* =========================================================
       GET DRIVER
       ========================================================= */

    protected WebDriver getDriver() {

        return driver;

    }


    /* =========================================================
       TEARDOWN
       ========================================================= */

    @AfterMethod(
            alwaysRun = true
    )
    public void tearDown() {

        if (
                driver == null
        ) {

            return;

        }


        /*
         * Garantizar una duración mínima.
         */
        ensureMinimumTestDuration();


        /*
         * Pausa antes de cerrar Chrome.
         */
        pause(
                config.getClosePauseSeconds()
        );


        /*
         * Cerrar navegador.
         */
        driver.quit();


        driver =
                null;

    }

}