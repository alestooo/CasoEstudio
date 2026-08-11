package com.cenfotec.e2e.tests;

import com.cenfotec.e2e.base.BaseTest;
import com.cenfotec.e2e.pages.AuthPage;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AuthPageTest extends BaseTest {

    private AuthPage authPage;


    @BeforeMethod
    public void openAuthPage() {

        openTeknovationPage(
                "auth.html"
        );


        authPage =
                new AuthPage(
                        driver
                );

    }


    @Test
    public void loginPanelShouldBeVisibleByDefault() {

        Assert.assertTrue(
                authPage.isLoginPanelVisible()
        );

    }


    @Test
    public void shouldOpenRegisterTab() {

        authPage.openRegisterTab();


        Assert.assertTrue(
                authPage.isRegisterPanelVisible()
        );

    }


    @Test
    public void shouldRegisterNewUser() {

        String email =
                uniqueEmail();


        authPage.register(
                "Alejandro",
                "Soto",
                email,
                "8888-8888",
                "Tekno123!"
        );


        Assert.assertTrue(
                authPage.isAuthSuccessVisible()
        );

    }


    @Test
    public void shouldLoginWithValidCredentials() {

        String email =
                uniqueEmail();


        String password =
                "Tekno123!";


        authPage.register(
                "Alejandro",
                "Soto",
                email,
                "8888-8888",
                password
        );


        Assert.assertTrue(
                authPage.isAuthSuccessVisible()
        );


        authPage.logout();


        authPage.login(
                email,
                password
        );


        Assert.assertTrue(
                authPage.isAuthSuccessVisible()
        );

    }


    @Test
    public void shouldRejectInvalidLogin() {

        authPage.login(
                "noexiste@teknovation.com",
                "Incorrecta123!"
        );


        Assert.assertTrue(
                authPage
                        .getLoginGlobalMessage()
                        .toLowerCase()
                        .contains(
                                "incorrect"
                        ),
                "Debe mostrar correo o contraseña incorrectos."
        );

    }


    @Test
    public void shouldToggleLoginPasswordVisibility() {

        Assert.assertEquals(
                authPage.getLoginPasswordType(),
                "password"
        );


        authPage.toggleLoginPassword();


        Assert.assertEquals(
                authPage.getLoginPasswordType(),
                "text"
        );

    }


    private String uniqueEmail() {

        return "alejandro"
                + System.currentTimeMillis()
                + "@teknovation.com";

    }

}