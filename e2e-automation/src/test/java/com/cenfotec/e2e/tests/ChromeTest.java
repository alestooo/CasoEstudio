package com.cenfotec.e2e.tests;

import com.cenfotec.e2e.base.BaseTest;

import org.openqa.selenium.By;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class ChromeTest extends BaseTest {


    /*
     * =========================================================
     * 1. CHROME ABRE TEKNOVATION
     * =========================================================
     */

    @Test
    public void chromeShouldOpenTeknovation() {

        openTeknovationPage(
                "index.html"
        );


        WebDriverWait wait =
                new WebDriverWait(
                        driver,
                        Duration.ofSeconds(10)
                );


        wait.until(
                ExpectedConditions
                        .urlContains(
                                "/teknovation/"
                        )
        );


        wait.until(
                ExpectedConditions
                        .presenceOfElementLocated(
                                By.tagName(
                                        "body"
                                )
                        )
        );


        String currentUrl =
                driver.getCurrentUrl();


        Assert.assertTrue(
                currentUrl.contains(
                        "/teknovation/"
                ),
                "Chrome debería abrir Teknovation."
        );


        Assert.assertTrue(
                currentUrl.contains(
                        "index.html"
                ),
                "Chrome debería abrir index.html."
        );


        Assert.assertTrue(
                driver.findElement(
                        By.tagName(
                                "body"
                        )
                ).isDisplayed(),
                "La página debería tener contenido visible."
        );


        String source =
                driver
                        .getPageSource()
                        .toLowerCase();


        Assert.assertFalse(
                source.contains(
                        "404 not found"
                ),
                "La página no debería devolver error 404."
        );

    }

}