package com.cenfotec.e2e.tests;

import com.cenfotec.e2e.base.BaseTest;
import com.cenfotec.e2e.pages.HomePage;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HomePageTest extends BaseTest {

    private HomePage homePage;


    @BeforeMethod
    public void openHomePage() {

        openTeknovationPage(
                "index.html"
        );


        homePage =
                new HomePage(
                        driver
                );

    }


    @Test
    public void homePageShouldLoad() {

        Assert.assertTrue(
                homePage.isOnHomePage()
        );


        Assert.assertTrue(
                homePage.isLogoVisible()
        );

    }


    @Test
    public void heroShouldBeVisible() {

        Assert.assertTrue(
                homePage.isHeroVisible()
        );


        Assert.assertTrue(
                homePage.getHeroSlideCount()
                        > 0
        );

    }


    @Test
    public void shopNavigationShouldWork() {

        homePage.clickShop();


        Assert.assertTrue(
                homePage.isOnShopPage()
        );

    }


    @Test
    public void searchShouldFindProduct() {

        homePage.clickSearch();


        Assert.assertTrue(
                homePage.isSearchModalVisible()
        );


        homePage.searchProduct(
                "SUPERLIGHT"
        );


        Assert.assertTrue(
                homePage.getSearchResultCount()
                        > 0
        );

    }


    @Test
    public void favoritesShouldOpen() {

        homePage.clickWishlist();


        Assert.assertTrue(
                homePage.isFavoritesModalVisible()
        );

    }

}