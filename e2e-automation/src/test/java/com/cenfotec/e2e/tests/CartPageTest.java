package com.cenfotec.e2e.tests;

import com.cenfotec.e2e.base.BaseTest;
import com.cenfotec.e2e.config.ConfigReader;
import com.cenfotec.e2e.pages.CartPage;

import org.testng.Assert;
import org.testng.annotations.Test;

public class CartPageTest extends BaseTest {

    private final String CART_URL =
            ConfigReader.get("base.url")
                    + "/cart.html";

    @Test
    public void validarCarritoVacio() {

        driver.get(CART_URL);

        CartPage cartPage =
                new CartPage(driver);

        Assert.assertTrue(
                cartPage.carritoVacioEstaVisible(),
                "No se mostró correctamente el carrito vacío."
        );
    }

    @Test
    public void validarMensajeCarritoVacio() {

        driver.get(CART_URL);

        CartPage cartPage =
                new CartPage(driver);

        Assert.assertEquals(
                cartPage.obtenerMensajeCarritoVacio(),
                "Tu carrito está vacío",
                "El mensaje del carrito vacío no es correcto."
        );
    }

    @Test
    public void navegarDesdeCarritoATienda() {

        driver.get(CART_URL);

        CartPage cartPage =
                new CartPage(driver);

        cartPage.explorarProductos();

        Assert.assertTrue(
                driver.getCurrentUrl()
                        .contains("shop.html"),
                "No se navegó correctamente desde el carrito hacia la tienda."
        );
    }
}