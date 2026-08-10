package com.cenfotec.e2e.pages;

import com.cenfotec.e2e.utils.WaitUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {

    private final WebDriver driver;
    private final WaitUtils wait;

    private final By inicioLink =
            By.cssSelector("a[href='index.html']");

    private final By tiendaLink =
            By.cssSelector(
                    ".nav-menu a[href='shop.html']"
            );

    private final By ofertasLink =
            By.id("nav-offers");

    private final By soporteLink =
            By.id("nav-support");

    private final By cuentaButton =
            By.cssSelector(
                    "button[aria-label='Mi cuenta']"
            );

    private final By carritoButton =
            By.cssSelector(
                    "button[aria-label='Carrito']"
            );

    private final By cartCount =
            By.className("cart-count");

    private final By comprarAhoraButton =
            By.cssSelector(
                    "a[href='product.html?id=pro-x2-superstrike']"
            );

    private final By categoriaMice =
            By.cssSelector(
                    "a[href='shop.html?category=mice']"
            );

    private final By categoriaKeyboards =
            By.cssSelector(
                    "a[href='shop.html?category=keyboards']"
            );

    private final By categoriaHeadsets =
            By.cssSelector(
                    "a[href='shop.html?category=headsets']"
            );

    public HomePage(WebDriver driver) {

        this.driver = driver;
        this.wait = new WaitUtils(driver);
    }

    public void irAInicio() {

        wait.esperarClickeable(
                inicioLink
        ).click();
    }

    public void irATienda() {

        wait.esperarClickeable(
                tiendaLink
        ).click();
    }

    public void abrirOfertas() {

        wait.esperarClickeable(
                ofertasLink
        ).click();
    }

    public void irASoporte() {

        wait.esperarClickeable(
                soporteLink
        ).click();
    }

    public void abrirCuenta() {

        wait.esperarClickeable(
                cuentaButton
        ).click();
    }

    public void abrirCarrito() {

        wait.esperarClickeable(
                carritoButton
        ).click();
    }

    public String obtenerCantidadCarrito() {

        return wait.esperarVisible(
                cartCount
        ).getText();
    }

    public void comprarProductoDestacado() {

        wait.esperarClickeable(
                comprarAhoraButton
        ).click();
    }

    public void abrirCategoriaMice() {

        wait.esperarClickeable(
                categoriaMice
        ).click();
    }

    public void abrirCategoriaTeclados() {

        wait.esperarClickeable(
                categoriaKeyboards
        ).click();
    }

    public void abrirCategoriaAuriculares() {

        wait.esperarClickeable(
                categoriaHeadsets
        ).click();
    }
}