package com.cenfotec.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ProductPage {

    private final WebDriver driver;
    private final WebDriverWait wait;


    private final By productLayout =
            By.id(
                    "productLayout"
            );


    private final By productError =
            By.id(
                    "productError"
            );


    private final By productTitle =
            By.cssSelector(
                    ".product-title"
            );


    private final By currentPrice =
            By.cssSelector(
                    ".product-price-large .price-current"
            );


    private final By quantity =
            By.id(
                    "quantity"
            );


    private final By qtyPlus =
            By.id(
                    "qtyPlus"
            );


    private final By qtyMinus =
            By.id(
                    "qtyMinus"
            );


    private final By addToCart =
            By.id(
                    "addToCart"
            );


    private final By cartCount =
            By.cssSelector(
                    ".cart-count"
            );


    public ProductPage(
            WebDriver driver
    ) {

        this.driver =
                driver;

        this.wait =
                new WebDriverWait(
                        driver,
                        Duration.ofSeconds(3)
                );

    }


    public boolean isProductLayoutVisible() {

        return isVisible(
                productLayout
        );

    }


    public boolean isProductErrorVisible() {

        try {

            List<WebElement> elements =
                    driver.findElements(
                            productError
                    );


            return !elements.isEmpty()
                    && elements
                    .get(0)
                    .isDisplayed();

        } catch (
                Exception exception
        ) {

            return false;

        }

    }


    public String getProductTitle() {

        return getText(
                productTitle
        );

    }


    public String getCurrentPrice() {

        return getText(
                currentPrice
        );

    }


    public int getQuantity() {

        String value =
                wait.until(
                        ExpectedConditions
                                .presenceOfElementLocated(
                                        quantity
                                )
                )
                        .getAttribute(
                                "value"
                        );


        return Integer.parseInt(
                value
        );

    }


    public void increaseQuantity() {

        int before =
                getQuantity();


        wait.until(
                ExpectedConditions
                        .elementToBeClickable(
                                qtyPlus
                        )
        ).click();


        wait.until(
                webDriver ->
                        getQuantity()
                                > before
        );

    }


    public void decreaseQuantity() {

        wait.until(
                ExpectedConditions
                        .elementToBeClickable(
                                qtyMinus
                        )
        ).click();

    }


    public void addToCart() {

        wait.until(
                ExpectedConditions
                        .elementToBeClickable(
                                addToCart
                        )
        ).click();

    }


    public int getCartCount() {

        try {

            List<WebElement> elements =
                    driver.findElements(
                            cartCount
                    );


            if (
                    elements.isEmpty()
            ) {

                return 0;

            }


            String value =
                    elements
                            .get(0)
                            .getText()
                            .replaceAll(
                                    "[^0-9]",
                                    ""
                            );


            return value.isBlank()
                    ? 0
                    : Integer.parseInt(
                            value
                    );

        } catch (
                Exception exception
        ) {

            return 0;

        }

    }


    private String getText(
            By locator
    ) {

        return wait.until(
                ExpectedConditions
                        .visibilityOfElementLocated(
                                locator
                        )
        )
                .getText()
                .trim();

    }


    private boolean isVisible(
            By locator
    ) {

        try {

            return wait.until(
                    ExpectedConditions
                            .visibilityOfElementLocated(
                                    locator
                            )
            ).isDisplayed();

        } catch (
                Exception exception
        ) {

            return false;

        }

    }

}