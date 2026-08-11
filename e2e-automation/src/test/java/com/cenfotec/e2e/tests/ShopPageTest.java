package com.cenfotec.e2e.tests;

import com.cenfotec.e2e.base.BaseTest;
import com.cenfotec.e2e.pages.ShopPage;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ShopPageTest extends BaseTest {

    private ShopPage shopPage;


    @BeforeMethod
    public void openShopPage() {

        openTeknovationPage(
                "shop.html"
        );


        shopPage =
                new ShopPage(
                        driver
                );

    }


    @Test
    public void shopShouldDisplayProducts() {

        Assert.assertTrue(
                shopPage.getProductCardCount()
                        > 0
        );

    }


    @Test
    public void wirelessFilterShouldWork() {

        shopPage.selectWirelessFilter();


        Assert.assertTrue(
                shopPage.isWirelessFilterSelected()
        );


        Assert.assertTrue(
                shopPage.getProductCardCount()
                        > 0
        );

    }


    @Test
    public void sortingByPriceShouldWork() {

        shopPage.sortPriceLowToHigh();


        Assert.assertEquals(
                shopPage.getSelectedSortValue(),
                "price-asc"
        );

    }


    @Test
    public void shouldOpenSpecificProduct() {

        Assert.assertTrue(
                shopPage.isProXSuperlight2Visible()
        );


        shopPage.openProXSuperlight2();


        Assert.assertTrue(
                shopPage.isOnProductPage()
        );


        Assert.assertTrue(
                shopPage
                        .getCurrentUrl()
                        .contains(
                                "pro-x-superlight-2"
                        )
        );

    }


    @Test
    public void shouldAddProductToCart() {

        int before =
                shopPage.getCartCount();


        shopPage.addProXSuperlight2ToCart();


        Assert.assertTrue(
                shopPage.getCartCount()
                        > before
        );

    }


    @Test
    public void shouldAddFavorite() {

        shopPage.toggleFavorite(
                0
        );


        Assert.assertTrue(
                shopPage.isFavoriteActive(
                        0
                ),
                "El corazón debe quedar activo."
        );


        Assert.assertTrue(
                shopPage.hasFavoritesInStorage(),
                "El favorito debe guardarse en localStorage."
        );

    }

}