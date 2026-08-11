package com.cenfotec.e2e.tests;

import com.cenfotec.e2e.base.BaseTest;
import com.cenfotec.e2e.pages.ProductPage;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ProductPageTest extends BaseTest {

    private ProductPage productPage;


    @BeforeMethod
    public void openProductPage() {

        openTeknovationPage(
                "product.html?id=pro-x-superlight-2"
        );

        productPage =
                new ProductPage(
                        driver
                );
    }


    @Test
    public void productShouldLoad() {

        Assert.assertTrue(
                productPage.isProductLayoutVisible()
        );

        Assert.assertFalse(
                productPage.isProductErrorVisible()
        );

        Assert.assertFalse(
                productPage
                        .getProductTitle()
                        .isBlank()
        );

        Assert.assertFalse(
                productPage
                        .getCurrentPrice()
                        .isBlank()
        );
    }


    @Test
    public void quantityShouldIncrease() {

        int before =
                productPage.getQuantity();

        productPage.increaseQuantity();

        int after =
                productPage.getQuantity();

        Assert.assertEquals(
                after,
                before + 1,
                "Un clic debe aumentar solamente una unidad."
        );
    }


    @Test
    public void addToCartShouldUpdateHeader() {

        int before =
                productPage.getCartCount();

        productPage.addToCart();

        int after =
                productPage.getCartCount();

        Assert.assertTrue(
                after > before,
                "El contador del carrito debería aumentar después de añadir el producto."
        );
    }
}