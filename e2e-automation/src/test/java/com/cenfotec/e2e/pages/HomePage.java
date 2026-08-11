package com.cenfotec.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePage {

    private final WebDriver driver;
    private final WebDriverWait wait;


    private final By logo =
            By.cssSelector(
                    ".logo"
            );


    private final By shopLink =
            By.cssSelector(
                    "a[href='shop.html']"
            );


    private final By searchButton =
            By.id(
                    "searchBtn"
            );


    private final By wishlistButton =
            By.id(
                    "wishlistBtn"
            );


    private final By hero =
            By.cssSelector(
                    ".hero"
            );


    private final By heroSlides =
            By.cssSelector(
                    ".hero-slide"
            );


    /*
     * Modal REAL usado actualmente.
     */

    private final By storeModalOverlay =
            By.id(
                    "storeModalOverlay"
            );


    private final By storeModalTitle =
            By.id(
                    "storeModalTitle"
            );


    private final By globalSearchInput =
            By.id(
                    "globalSearchInput"
            );


    private final By searchResults =
            By.cssSelector(
                    "#searchResults .store-result-item"
            );


    public HomePage(
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


    public boolean isLogoVisible() {

        return isVisible(
                logo
        );

    }


    public boolean isOnHomePage() {

        String url =
                driver.getCurrentUrl();


        return url.contains(
                "/teknovation/"
        )
                && (
                url.contains(
                        "index.html"
                )
                || url.endsWith(
                        "/teknovation/"
                )
        );

    }


    public boolean isHeroVisible() {

        return isVisible(
                hero
        );

    }


    public int getHeroSlideCount() {

        return driver
                .findElements(
                        heroSlides
                )
                .size();

    }


    public void clickShop() {

        clickFirstVisible(
                shopLink
        );

    }


    public boolean isOnShopPage() {

        return driver
                .getCurrentUrl()
                .contains(
                        "shop.html"
                );

    }


    public void clickSearch() {

        wait.until(
                ExpectedConditions
                        .elementToBeClickable(
                                searchButton
                        )
        ).click();

    }


    public boolean isSearchModalVisible() {

        try {

            WebElement overlay =
                    wait.until(
                            ExpectedConditions
                                    .visibilityOfElementLocated(
                                            storeModalOverlay
                                    )
                    );


            return overlay
                    .getAttribute(
                            "class"
                    )
                    .contains(
                            "active"
                    );

        } catch (
                Exception exception
        ) {

            return false;

        }

    }


    public void searchProduct(
            String text
    ) {

        WebElement input =
                wait.until(
                        ExpectedConditions
                                .visibilityOfElementLocated(
                                        globalSearchInput
                                )
                );


        input.clear();

        input.sendKeys(
                text
        );


        wait.until(
                webDriver ->
                        webDriver
                                .findElements(
                                        searchResults
                                )
                                .size()
                                > 0
        );

    }


    public int getSearchResultCount() {

        return driver
                .findElements(
                        searchResults
                )
                .size();

    }


    public void clickWishlist() {

        wait.until(
                ExpectedConditions
                        .elementToBeClickable(
                                wishlistButton
                        )
        ).click();

    }


    public boolean isFavoritesModalVisible() {

        try {

            WebElement overlay =
                    wait.until(
                            ExpectedConditions
                                    .visibilityOfElementLocated(
                                            storeModalOverlay
                                    )
                    );


            WebElement title =
                    wait.until(
                            ExpectedConditions
                                    .visibilityOfElementLocated(
                                            storeModalTitle
                                    )
                    );


            return overlay
                    .getAttribute(
                            "class"
                    )
                    .contains(
                            "active"
                    )
                    &&
                    title
                            .getText()
                            .trim()
                            .equals(
                                    "Mis favoritos"
                            );

        } catch (
                Exception exception
        ) {

            return false;

        }

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


    private void clickFirstVisible(
            By locator
    ) {

        List<WebElement> elements =
                driver.findElements(
                        locator
                );


        for (
                WebElement element :
                elements
        ) {

            if (
                    element.isDisplayed()
                    && element.isEnabled()
            ) {

                element.click();

                return;

            }

        }


        wait.until(
                ExpectedConditions
                        .elementToBeClickable(
                                locator
                        )
        ).click();

    }

}