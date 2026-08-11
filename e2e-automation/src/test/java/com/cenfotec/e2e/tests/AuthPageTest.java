package com.cenfotec.e2e.tests;

import com.cenfotec.e2e.base.BaseTest;
import com.cenfotec.e2e.pages.AuthPage;

import org.openqa.selenium.JavascriptExecutor;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class AuthPageTest extends BaseTest {

    private AuthPage authPage;


    /*
     * ============================================================
     * PREPARACIÓN
     * ============================================================
     */

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


    /*
     * ============================================================
     * TEST 1
     * Login visible por defecto
     * ============================================================
     */

    @Test
    public void loginPanelShouldBeVisibleByDefault() {

        Assert.assertTrue(
            authPage.isLoginPanelVisible(),
            "El panel de login debería estar visible por defecto."
        );

    }


    /*
     * ============================================================
     * TEST 2
     * Abrir pestaña de registro
     * ============================================================
     */

    @Test
    public void shouldOpenRegisterTab() {

        authPage.openRegisterTab();


        Assert.assertTrue(
            authPage.isRegisterPanelVisible(),
            "El panel de registro debería mostrarse."
        );

    }


    /*
     * ============================================================
     * TEST 3
     * Registrar un usuario nuevo
     * ============================================================
     */

    @Test
    public void shouldRegisterNewUser() {

        String uniqueEmail =
            "usuario"
            + System.currentTimeMillis()
            + "@teknovation.test";


        authPage.register(
            "Alejandro",
            "Soto",
            uniqueEmail,
            "Prueba123!"
        );


        Assert.assertTrue(
            authPage.isAuthSuccessVisible(),
            "Debería mostrarse la pantalla de éxito después del registro."
        );


        String successTitle =
            authPage.getSuccessTitle();


        Assert.assertFalse(
            successTitle.isBlank(),
            "Debería mostrarse un título de confirmación."
        );

    }


    /*
     * ============================================================
     * TEST 4
     * Login válido
     * ============================================================
     */

    @Test
    public void shouldLoginWithValidCredentials() {

        String email =
            "selenium@teknovation.test";


        String password =
            "Prueba123!";


        createTestUser(
            email,
            password
        );


        driver.navigate()
            .refresh();


        authPage =
            new AuthPage(
                driver
            );


        authPage.login(
            email,
            password
        );


        Assert.assertTrue(
            authPage.isAuthSuccessVisible(),
            "El login válido debería mostrar la pantalla de éxito."
        );


        Assert.assertTrue(
            authPage.getSuccessTitle()
                .toLowerCase()
                .contains(
                    "bienvenido"
                ),
            "El título debería indicar que el usuario inició sesión."
        );

    }


    /*
     * ============================================================
     * TEST 5
     * Login inválido
     * ============================================================
     */

    @Test
    public void shouldRejectInvalidLogin() {

        authPage.login(
            "noexiste@teknovation.test",
            "Password123!"
        );


        String message =
            authPage.getLoginGlobalMessage();


        Assert.assertFalse(
            message.isBlank(),
            "Debería aparecer un mensaje de error."
        );


        Assert.assertTrue(
            message
                .toLowerCase()
                .contains(
                    "incorrect"
                ),
            "El mensaje debería indicar que las credenciales son incorrectas."
        );

    }


    /*
     * ============================================================
     * TEST 6
     * Mostrar y ocultar contraseña del login
     * ============================================================
     */

    @Test
    public void shouldToggleLoginPasswordVisibility() {

        Assert.assertEquals(
            authPage.getLoginPasswordType(),
            "password",
            "La contraseña debería estar oculta inicialmente."
        );


        authPage.toggleLoginPassword();


        Assert.assertEquals(
            authPage.getLoginPasswordType(),
            "text",
            "La contraseña debería mostrarse después del primer clic."
        );


        authPage.toggleLoginPassword();


        Assert.assertEquals(
            authPage.getLoginPasswordType(),
            "password",
            "La contraseña debería volver a ocultarse."
        );

    }


    /*
     * ============================================================
     * CREAR USUARIO DE PRUEBA EN LOCALSTORAGE
     * ============================================================
     */

    private void createTestUser(
        String email,
        String password
    ) {

        JavascriptExecutor javascript =
            (JavascriptExecutor) driver;


        javascript.executeScript(
            """
            const users = [
                {
                    id: 'selenium-user',
                    firstName: 'Selenium',
                    lastName: 'Test',
                    email: arguments[0],
                    phone: '',
                    password: arguments[1],
                    newsletter: false,
                    registeredAt: new Date().toISOString()
                }
            ];

            localStorage.setItem(
                'users',
                JSON.stringify(users)
            );
            """,
            email,
            password
        );

    }

}