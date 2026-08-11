package com.cenfotec.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ShopPage {

    private final WebDriver driver;
    private final WebDriverWait wait;


    private final By productCards =
            By.cssSelector(
                    ".product-card"
            );


    private final By productCount =
            By.id(
                    "productCount"
            );


    private final By wirelessFilter =
            By.cssSelector(
                    "input[name='connectivity'][value='wireless']"
            );


    private final By sortSelect =
            By.id(
                    "sortSelect"
            );


    private final By proXSuperlight2Link =
            By.cssSelector(
                    "a[href*='product.html?id=pro-x-superlight-2']"
            );


    private final By proXSuperlight2AddCart =
            By.cssSelector(
                    ".btn-add-cart[data-product-id='pro-x-superlight-2']"
            );


    private final By favoriteButtons =
            By.cssSelector(
                    ".product-card .favorite-btn"
            );


    private final By cartCount =
            By.cssSelector(
                    ".cart-count"
            );


    public ShopPage(
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


    public int getProductCardCount() {

        wait.until(
                ExpectedConditions
                        .presenceOfAllElementsLocatedBy(
                                productCards
                        )
        );


        return driver
                .findElements(
                        productCards
                )
                .size();

    }


    public int getDisplayedProductCountAsInt() {

        String value =
                wait.until(
                        ExpectedConditions
                                .visibilityOfElementLocated(
                                        productCount
                                )
                )
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

    }


    public void selectWirelessFilter() {

        WebElement checkbox =
                wait.until(
                        ExpectedConditions
                                .elementToBeClickable(
                                        wirelessFilter
                                )
                );


        if (
                !checkbox.isSelected()
        ) {

            checkbox.click();

        }

    }


    public boolean isWirelessFilterSelected() {

        return driver
                .findElement(
                        wirelessFilter
                )
                .isSelected();

    }


    public void sortPriceLowToHigh() {

        Select select =
                new Select(
                        wait.until(
                                ExpectedConditions
                                        .visibilityOfElementLocated(
                                                sortSelect
                                        )
                        )
                );


        select.selectByValue(
                "price-asc"
        );

    }


    public String getSelectedSortValue() {

        Select select =
                new Select(
                        driver.findElement(
                                sortSelect
                        )
                );


        return select
                .getFirstSelectedOption()
                .getAttribute(
                        "value"
                );

    }


    public boolean isProXSuperlight2Visible() {

        return !driver
                .findElements(
                        proXSuperlight2Link
                )
                .isEmpty();

    }


    public void openProXSuperlight2() {

        wait.until(
                ExpectedConditions
                        .elementToBeClickable(
                                proXSuperlight2Link
                        )
        ).click();

    }


    public boolean isOnProductPage() {

        return driver
                .getCurrentUrl()
                .contains(
                        "product.html"
                );

    }


    public String getCurrentUrl() {

        return driver
                .getCurrentUrl();

    }


    public int getCartCount() {

        try {

            String value =
                    driver
                            .findElement(
                                    cartCount
                            )
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


    public void addProXSuperlight2ToCart() {

        wait.until(
                ExpectedConditions
                        .elementToBeClickable(
                                proXSuperlight2AddCart
                        )
        ).click();

    }


    public void toggleFavorite(
            int index
    ) {

        List<WebElement> buttons =
                wait.until(
                        ExpectedConditions
                                .presenceOfAllElementsLocatedBy(
                                        favoriteButtons
                                )
                );


        if (
                index < 0
                || index >= buttons.size()
        ) {

            throw new IllegalArgumentException(
                    "Índice inválido: "
                            + index
            );

        }


        WebElement button =
                buttons.get(
                        index
                );


        wait.until(
                ExpectedConditions
                        .elementToBeClickable(
                                button
                        )
        ).click();

    }


    public boolean isFavoriteActive(
            int index
    ) {

        List<WebElement> buttons =
                driver.findElements(
                        favoriteButtons
                );


        if (
                index < 0
                || index >= buttons.size()
        ) {

            return false;

        }


        String icon =
                buttons
                        .get(index)
                        .findElement(
                                By.cssSelector(
                                        ".material-icons"
                                )
                        )
                        .getText()
                        .trim();


        return icon.equals(
                "favorite"
        );

    }


    public boolean hasFavoritesInStorage() {

        try {

            Object result =
                    ((JavascriptExecutor) driver)
                            .executeScript(
                                    """
                                    const favorites =
                                        JSON.parse(
                                            localStorage.getItem('favorites')
                                            || '[]'
                                        );

                                    return favorites.length > 0;
                                    """
                            );


            return Boolean.TRUE
                    .equals(
                            result
                    );

        } catch (
                Exception exception
        ) {

            return false;

        }

    }

}