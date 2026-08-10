package com.cenfotec.e2e.pages;

import com.cenfotec.e2e.utils.WaitUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage {

    private final WebDriver driver;
    private final WaitUtils wait;

    private final By cartEmpty =
            By.id("cartEmpty");

    private final By cartContent =
            By.id("cartContent");

    private final By cartTableBody =
            By.id("cartTableBody");

    private final By emptyCartTitle =
            By.cssSelector(
                    "#cartEmpty h2"
            );

    private final By explorarProductos =
            By.cssSelector(
                    "#cartEmpty a[href='shop.html']"
            );

    private final By summaryItemCount =
            By.id("summaryItemCount");

    private final By summarySubtotal =
            By.id("summarySubtotal");

    private final By summaryTotal =
            By.id("summaryTotal");

    private final By checkoutButton =
            By.id("checkoutBtn");

    public CartPage(WebDriver driver) {

        this.driver = driver;
        this.wait = new WaitUtils(driver);
    }

    public boolean carritoVacioEstaVisible() {

        return wait.esperarVisible(
                cartEmpty
        ).isDisplayed();
    }

    public String obtenerMensajeCarritoVacio() {

        return wait.esperarVisible(
                emptyCartTitle
        ).getText();
    }

    public void explorarProductos() {

        wait.esperarClickeable(
                explorarProductos
        ).click();
    }

    public boolean contenidoCarritoEstaVisible() {

        return wait.esperarVisible(
                cartContent
        ).isDisplayed();
    }

    public String obtenerProductosDelCarrito() {

        return wait.esperarVisible(
                cartTableBody
        ).getText();
    }

    public String obtenerCantidadArticulos() {

        return wait.esperarVisible(
                summaryItemCount
        ).getText();
    }

    public String obtenerSubtotal() {

        return wait.esperarVisible(
                summarySubtotal
        ).getText();
    }

    public String obtenerTotal() {

        return wait.esperarVisible(
                summaryTotal
        ).getText();
    }

    public boolean checkoutEstaVisible() {

        return wait.esperarVisible(
                checkoutButton
        ).isDisplayed();
    }
}