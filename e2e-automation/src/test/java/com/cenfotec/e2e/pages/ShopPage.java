package com.cenfotec.e2e.pages;

import com.cenfotec.e2e.utils.WaitUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class ShopPage {

    private final WebDriver driver;
    private final WaitUtils wait;

    private final By productCount =
            By.id("productCount");

    private final By sortSelect =
            By.id("sortSelect");

    private final By productCards =
            By.cssSelector(".product-card");

    private final By wirelessCheckbox =
            By.cssSelector(
                    "input[name='connectivity'][value='wireless']"
            );

    private final By wiredCheckbox =
            By.cssSelector(
                    "input[name='connectivity'][value='wired']"
            );

    private final By proSeriesCheckbox =
            By.cssSelector(
                    "input[name='series'][value='pro']"
            );

    private final By g5SeriesCheckbox =
            By.cssSelector(
                    "input[name='series'][value='g5']"
            );

    private final By firstProduct =
            By.cssSelector(
                    "a[href='product.html?id=pro-x2-superstrike']"
            );

    private final By superlightProduct =
            By.cssSelector(
                    "a[href='product.html?id=pro-x-superlight-2']"
            );

    private final By addSuperlightToCart =
            By.cssSelector(
                    "button[data-product-id='pro-x-superlight-2']"
            );

    private final By cartCount =
            By.className("cart-count");

    public ShopPage(WebDriver driver) {

        this.driver = driver;
        this.wait = new WaitUtils(driver);
    }

    public String obtenerCantidadProductos() {

        return wait.esperarVisible(
                productCount
        ).getText();
    }

    public int obtenerCantidadProductosVisibles() {

        List<WebElement> productos =
                driver.findElements(productCards);

        return (int) productos.stream()
                .filter(WebElement::isDisplayed)
                .count();
    }

    public void ordenarPorPrecioMenorAMayor() {

        WebElement elemento =
                wait.esperarVisible(sortSelect);

        Select select =
                new Select(elemento);

        select.selectByValue(
                "price-asc"
        );
    }

    public void filtrarWireless() {

        int cantidadInicial =
                obtenerCantidadProductosVisibles();

        wait.esperarClickeable(
                wirelessCheckbox
        ).click();

        wait.esperarCantidadVisiblesMenorQue(
                productCards,
                cantidadInicial
        );
    }

    public void filtrarWired() {

        wait.esperarClickeable(
                wiredCheckbox
        ).click();
    }

    public void filtrarProSeries() {

        wait.esperarClickeable(
                proSeriesCheckbox
        ).click();
    }

    public void filtrarG5Series() {

        wait.esperarClickeable(
                g5SeriesCheckbox
        ).click();
    }

    public void abrirPrimerProducto() {

        wait.esperarClickeable(
                firstProduct
        ).click();
    }

    public void abrirSuperlight() {

        wait.esperarClickeable(
                superlightProduct
        ).click();
    }

    public void agregarSuperlightAlCarrito() {

        wait.esperarClickeable(
                addSuperlightToCart
        ).click();

        wait.esperarContadorCarritoMayorQueCero(
                cartCount
        );
    }

    public String obtenerCantidadCarrito() {

        return wait.esperarVisible(
                cartCount
        ).getText();
    }
}