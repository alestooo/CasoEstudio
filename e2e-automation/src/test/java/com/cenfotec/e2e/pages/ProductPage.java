package com.cenfotec.e2e.pages;

import com.cenfotec.e2e.utils.WaitUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductPage {

    private final WebDriver driver;
    private final WaitUtils wait;

    private final By productTitle =
            By.className("product-title");

    private final By productPrice =
            By.cssSelector(
                    ".product-price-large .price-current"
            );

    private final By quantityInput =
            By.id("quantity");

    private final By quantityPlus =
            By.id("qtyPlus");

    private final By colorBlanco =
            By.cssSelector(
                    "button[data-color='white']"
            );

    public ProductPage(WebDriver driver) {

        this.driver = driver;
        this.wait = new WaitUtils(driver);
    }

    public String obtenerTituloProducto() {

        return wait.esperarVisible(
                productTitle
        ).getText();
    }

    public String obtenerPrecioProducto() {

        return wait.esperarVisible(
                productPrice
        ).getText();
    }

    public String obtenerCantidad() {

        return wait.esperarVisible(
                        quantityInput
                )
                .getAttribute("value");
    }

    public void incrementarCantidad() {

        wait.esperarClickeable(
                quantityPlus
        ).click();
    }

    public void seleccionarColorBlanco() {

        wait.esperarClickeable(
                colorBlanco
        ).click();
    }

    public boolean colorBlancoEstaSeleccionado() {

        return wait.esperarVisible(
                        colorBlanco
                )
                .getAttribute("class")
                .contains("active");
    }
}