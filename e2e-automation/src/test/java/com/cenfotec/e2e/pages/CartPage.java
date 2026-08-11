package com.cenfotec.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CartPage {

    private final WebDriver driver;
    private final WebDriverWait wait;


    /* =========================================================
       CART
       ========================================================= */

    private final By cartPageTitle =
            By.id("cartPageTitle");

    private final By clearCartBtn =
            By.id("clearCartBtn");

    private final By cartEmpty =
            By.id("cartEmpty");

    private final By cartContent =
            By.id("cartContent");

    private final By cartRows =
            By.cssSelector(
                    "#cartTableBody tr"
            );


    /* =========================================================
       HEADER
       ========================================================= */

    private final By cartCount =
            By.cssSelector(
                    ".cart-count"
            );

    private final By wishlistCount =
            By.cssSelector(
                    ".wishlist-count"
            );

    private final By cartButton =
            By.cssSelector(
                    ".cart-btn, #cartBtn"
            );

    private final By wishlistButton =
            By.id("wishlistBtn");

    private final By searchButton =
            By.id("searchBtn");

    private final By accountButton =
            By.cssSelector(
                    "a[href='auth.html'], #accountBtn"
            );


    /* =========================================================
       CART ROW
       ========================================================= */

    private final By productNames =
            By.cssSelector(
                    "#cartTableBody .cart-product-name, " +
                    "#cartTableBody .product-name, " +
                    "#cartTableBody h3, " +
                    "#cartTableBody h4"
            );

    private final By plusButtons =
            By.cssSelector(
                    "#cartTableBody [data-cart-action='plus'], " +
                    "#cartTableBody .qty-plus"
            );

    private final By minusButtons =
            By.cssSelector(
                    "#cartTableBody [data-cart-action='minus'], " +
                    "#cartTableBody .qty-minus"
            );

    private final By removeButtons =
            By.cssSelector(
                    "#cartTableBody [data-cart-action='remove'], " +
                    "#cartTableBody .remove-item"
            );


    /* =========================================================
       COUPON
       ========================================================= */

    private final By couponInput =
            By.id("couponInput");

    private final By applyCoupon =
            By.id("applyCoupon");

    private final By couponFeedback =
            By.id("couponFeedback");


    /* =========================================================
       SUMMARY
       ========================================================= */

    private final By summaryItemCount =
            By.id("summaryItemCount");

    private final By summarySubtotal =
            By.id("summarySubtotal");

    private final By discountRow =
            By.id("discountRow");

    private final By summaryDiscount =
            By.id("summaryDiscount");

    private final By summaryShipping =
            By.id("summaryShipping");

    private final By summaryTotal =
            By.id("summaryTotal");

    private final By checkoutBtn =
            By.id("checkoutBtn");


    /* =========================================================
       SWEET ALERT
       ========================================================= */

    private final By sweetAlert =
            By.cssSelector(
                    ".swal2-popup"
            );

    private final By sweetAlertTitle =
            By.cssSelector(
                    ".swal2-title"
            );

    private final By sweetAlertHtml =
            By.cssSelector(
                    ".swal2-html-container"
            );

    private final By sweetAlertConfirm =
            By.cssSelector(
                    ".swal2-confirm"
            );

    private final By sweetAlertCancel =
            By.cssSelector(
                    ".swal2-cancel"
            );


    /* =========================================================
       CONSTRUCTOR
       ========================================================= */

    public CartPage(
            WebDriver driver
    ) {

        this.driver =
                driver;

        this.wait =
                new WebDriverWait(
                        driver,
                        Duration.ofSeconds(10)
                );

    }


    /* =========================================================
       EMPTY / CONTENT
       ========================================================= */

    public boolean isCartEmptyVisible() {

        return isVisible(
                cartEmpty
        );

    }


    public boolean isCartContentVisible() {

        return isVisible(
                cartContent
        );

    }


    public String getEmptyCartTitle() {

        try {

            return wait.until(
                    ExpectedConditions
                            .visibilityOfElementLocated(
                                    By.cssSelector(
                                            "#cartEmpty h2"
                                    )
                            )
            )
                    .getText()
                    .trim();

        } catch (
                Exception exception
        ) {

            return "";

        }

    }


    public int getNumberOfCartRows() {

        return driver
                .findElements(
                        cartRows
                )
                .size();

    }


    public String getCartPageTitleCount() {

        return getText(
                cartPageTitle
        );

    }


    /* =========================================================
       PRODUCT
       ========================================================= */

    public String getProductName(
            int index
    ) {

        List<WebElement> elements =
                driver.findElements(
                        productNames
                );


        if (
                index < 0
                || index >= elements.size()
        ) {

            return "";

        }


        return elements
                .get(index)
                .getText()
                .trim();

    }


    public void increaseQuantity(
            int index
    ) {

        clickByIndex(
                plusButtons,
                index
        );

    }


    public void decreaseQuantity(
            int index
    ) {

        clickByIndex(
                minusButtons,
                index
        );

    }


    public void removeProduct(
            int index
    ) {

        clickByIndex(
                removeButtons,
                index
        );

    }


    /* =========================================================
       CLEAR CART
       ========================================================= */

    public void clearCart() {

        wait.until(
                ExpectedConditions
                        .elementToBeClickable(
                                clearCartBtn
                        )
        ).click();

    }


    public boolean isClearCartButtonVisible() {

        return isVisible(
                clearCartBtn
        );

    }


    /* =========================================================
       COUPON
       ========================================================= */

    public void enterCoupon(
            String coupon
    ) {

        WebElement input =
                wait.until(
                        ExpectedConditions
                                .visibilityOfElementLocated(
                                        couponInput
                                )
                );


        input.clear();

        input.sendKeys(
                coupon
        );

    }


    public void clickApplyCoupon() {

        wait.until(
                ExpectedConditions
                        .elementToBeClickable(
                                applyCoupon
                        )
        ).click();

    }


    public void applyCoupon(
            String coupon
    ) {

        enterCoupon(
                coupon
        );

        clickApplyCoupon();

    }


    public String getCouponFeedback() {

        return getText(
                couponFeedback
        );

    }


    public boolean isCouponSuccess() {

        String message =
                getCouponFeedback()
                        .toLowerCase();


        return message.contains("aplic")
                || message.contains("descuento")
                || message.contains("10%")
                || message.contains("20%")
                || message.contains("30%");

    }


    public boolean isCouponError() {

        String message =
                getCouponFeedback()
                        .toLowerCase();


        return message.contains("invál")
                || message.contains("inval")
                || message.contains("no existe")
                || message.contains("incorrect");

    }


    /* =========================================================
       SUMMARY
       ========================================================= */

    public String getSummaryItemCount() {

        return getText(
                summaryItemCount
        );

    }


    public String getSummarySubtotal() {

        return getText(
                summarySubtotal
        );

    }


    public String getSummaryDiscount() {

        return getText(
                summaryDiscount
        );

    }


    public String getSummaryShipping() {

        return getText(
                summaryShipping
        );

    }


    public String getSummaryTotal() {

        return getText(
                summaryTotal
        );

    }


    public boolean isDiscountVisible() {

        return isVisible(
                discountRow
        );

    }


    /* =========================================================
       CHECKOUT
       ========================================================= */

    public void clickCheckout() {

        wait.until(
                ExpectedConditions
                        .elementToBeClickable(
                                checkoutBtn
                        )
        ).click();

    }


    /* =========================================================
       SWEET ALERT
       ========================================================= */

    public boolean isSweetAlertVisible() {

        return isVisible(
                sweetAlert
        );

    }


    public String getSweetAlertTitle() {

        return getText(
                sweetAlertTitle
        );

    }


    public String getSweetAlertText() {

        return getText(
                sweetAlertHtml
        );

    }


    public void confirmSweetAlert() {

        wait.until(
                ExpectedConditions
                        .elementToBeClickable(
                                sweetAlertConfirm
                        )
        ).click();

    }


    public void cancelSweetAlert() {

        wait.until(
                ExpectedConditions
                        .elementToBeClickable(
                                sweetAlertCancel
                        )
        ).click();

    }


    public boolean isLoginRequiredAlertVisible() {

        if (
                !isSweetAlertVisible()
        ) {

            return false;

        }


        String text =
                (
                        getSweetAlertTitle()
                                + " "
                                + getSweetAlertText()
                )
                        .toLowerCase();


        return text.contains(
                "inicia sesión"
        )
                || text.contains(
                "iniciar sesión"
        );

    }


    public boolean isPurchaseCompletedAlertVisible() {

        if (
                !isSweetAlertVisible()
        ) {

            return false;

        }


        String text =
                (
                        getSweetAlertTitle()
                                + " "
                                + getSweetAlertText()
                )
                        .toLowerCase();


        return text.contains(
                "compra finalizada"
        )
                || text.contains(
                "pedido"
        );

    }


    public String getOrderNumberFromAlert() {

        String text =
                getSweetAlertText();


        int position =
                text.indexOf(
                        "TKN-"
                );


        if (
                position < 0
        ) {

            return "";

        }


        String value =
                text.substring(
                        position
                );


        String[] parts =
                value.split(
                        "\\s+"
                );


        return parts.length > 0
                ? parts[0]
                : "";

    }


    /* =========================================================
       HEADER
       ========================================================= */

    public int getCartCountAsInt() {

        return getCounter(
                cartCount
        );

    }


    public int getCartCount() {

        return getCartCountAsInt();

    }


    public int getWishlistCount() {

        return getCounter(
                wishlistCount
        );

    }


    public void clickCart() {

        click(
                cartButton
        );

    }


    public void clickWishlist() {

        click(
                wishlistButton
        );

    }


    public void clickSearch() {

        click(
                searchButton
        );

    }


    public void clickAccount() {

        click(
                accountButton
        );

    }


    /* =========================================================
       URL
       ========================================================= */

    public String getCurrentUrl() {

        return driver.getCurrentUrl();

    }


    public boolean isOnCartPage() {

        return getCurrentUrl()
                .contains(
                        "cart.html"
                );

    }


    public boolean isOnAuthPage() {

        return getCurrentUrl()
                .contains(
                        "auth.html"
                );

    }


    /* =========================================================
       HELPERS
       ========================================================= */

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


    private String getText(
            By locator
    ) {

        try {

            return wait.until(
                    ExpectedConditions
                            .visibilityOfElementLocated(
                                    locator
                            )
            )
                    .getText()
                    .trim();

        } catch (
                Exception exception
        ) {

            return "";

        }

    }


    private int getCounter(
            By locator
    ) {

        try {

            List<WebElement> elements =
                    driver.findElements(
                            locator
                    );


            if (
                    elements.isEmpty()
            ) {

                return 0;

            }


            String text =
                    elements
                            .get(0)
                            .getText()
                            .replaceAll(
                                    "[^0-9]",
                                    ""
                            );


            if (
                    text.isBlank()
            ) {

                return 0;

            }


            return Integer.parseInt(
                    text
            );

        } catch (
                Exception exception
        ) {

            return 0;

        }

    }


    private void click(
            By locator
    ) {

        wait.until(
                ExpectedConditions
                        .elementToBeClickable(
                                locator
                        )
        ).click();

    }


    private void clickByIndex(
            By locator,
            int index
    ) {

        List<WebElement> elements =
                wait.until(
                        ExpectedConditions
                                .presenceOfAllElementsLocatedBy(
                                        locator
                                )
                );


        if (
                index < 0
                || index >= elements.size()
        ) {

            throw new IllegalArgumentException(
                    "Índice inválido: "
                            + index
            );

        }


        wait.until(
                ExpectedConditions
                        .elementToBeClickable(
                                elements.get(
                                        index
                                )
                        )
        ).click();

    }

}