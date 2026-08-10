package com.cenfotec.e2e.tests;

import com.cenfotec.e2e.base.BaseTest;
import com.cenfotec.e2e.config.ConfigReader;
import com.cenfotec.e2e.pages.ShopPage;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ShopPageTest extends BaseTest {

    private final String SHOP_URL =
            ConfigReader.get("teknovation.url")
                    + "/shop.html";

    @Test
    public void validarCantidadDeProductos() {

        driver.get(SHOP_URL);

        ShopPage shopPage =
                new ShopPage(driver);

        Assert.assertEquals(
                shopPage.obtenerCantidadProductos(),
                "8",
                "La cantidad inicial de productos no es correcta."
        );
    }

    @Test
    public void filtrarProductosInalambricos() {

        driver.get(SHOP_URL);

        ShopPage shopPage =
                new ShopPage(driver);

        int cantidadAntes =
                shopPage.obtenerCantidadProductosVisibles();

        Assert.assertTrue(
                cantidadAntes > 0,
                "Debe haber productos antes de aplicar el filtro."
        );

        shopPage.filtrarWireless();

        int cantidadDespues =
                shopPage.obtenerCantidadProductosVisibles();

        Assert.assertTrue(
                cantidadDespues < cantidadAntes,
                "El filtro Wireless no modificó la cantidad de productos visibles."
        );
    }

    @Test
    public void abrirProductoSuperlight() {

        driver.get(SHOP_URL);

        ShopPage shopPage =
                new ShopPage(driver);

        shopPage.abrirSuperlight();

        Assert.assertTrue(
                driver.getCurrentUrl()
                        .contains(
                                "product.html?id=pro-x-superlight-2"
                        ),
                "No se abrió correctamente PRO X SUPERLIGHT 2."
        );
    }
}