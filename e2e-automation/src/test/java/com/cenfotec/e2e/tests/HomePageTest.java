package com.cenfotec.e2e.tests;

import com.cenfotec.e2e.base.BaseTest;
import com.cenfotec.e2e.config.ConfigReader;
import com.cenfotec.e2e.pages.HomePage;

import org.testng.Assert;
import org.testng.annotations.Test;

public class HomePageTest extends BaseTest {

    private final String HOME_URL =
            ConfigReader.get("teknovation.url")
                    + "/index.html";

    @Test
    public void navegarATienda() {

        driver.get(HOME_URL);

        HomePage homePage =
                new HomePage(driver);

        homePage.irATienda();

        Assert.assertTrue(
                driver.getCurrentUrl()
                        .contains("shop.html"),
                "No se navegó correctamente hacia la tienda."
        );
    }

    @Test
    public void navegarACategoriaMice() {

        driver.get(HOME_URL);

        HomePage homePage =
                new HomePage(driver);

        homePage.abrirCategoriaMice();

        Assert.assertTrue(
                driver.getCurrentUrl()
                        .contains(
                                "shop.html?category=mice"
                        ),
                "No se abrió correctamente la categoría de mice."
        );
    }

    @Test
    public void abrirProductoDestacado() {

        driver.get(HOME_URL);

        HomePage homePage =
                new HomePage(driver);

        homePage.comprarProductoDestacado();

        Assert.assertTrue(
                driver.getCurrentUrl()
                        .contains(
                                "product.html?id=pro-x2-superstrike"
                        ),
                "No se abrió correctamente el producto destacado."
        );
    }
}