package com.cenfotec.e2e.base;

import com.cenfotec.e2e.config.ConfigReader;
import com.cenfotec.e2e.utils.TestListener;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

@Listeners(TestListener.class)
public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {

        String browser = ConfigReader.get("browser");

        if (browser.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver();
        } else {
            throw new RuntimeException(
                    "Navegador no soportado: " + browser
            );
        }

        driver.manage().window().maximize();

        System.out.println(
                "[SETUP] Navegador iniciado: " + browser
        );
    }
//Ctrl + K + C para comentar

//Ctrl + K + U para descomentar

    // @AfterMethod(alwaysRun = true)
    // public void tearDown() {

    //     if (driver != null) {

    //         driver.quit();

    //         System.out.println(
    //                 "[TEARDOWN] Navegador cerrado."
    //         );
    //     }
    // }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {

        if (driver != null) {

            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            driver.quit();

            System.out.println(
                    "[TEARDOWN] Navegador cerrado."
            );
        }
    }

    public WebDriver getDriver() {
        return driver;
    }
}