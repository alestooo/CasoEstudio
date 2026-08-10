package com.cenfotec.e2e.tests;

import com.cenfotec.e2e.base.BaseTest;
import com.cenfotec.e2e.config.ConfigReader;
import com.cenfotec.e2e.data.LoginData;
import com.cenfotec.e2e.pages.AuthPage;
import com.cenfotec.e2e.utils.JsonDataReader;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

public class AuthPageTest extends BaseTest {

    private final String AUTH_URL =
            ConfigReader.get("base.url")
                    + "/auth.html";

    @DataProvider(name = "loginData")
    public Object[][] loginData() {

        List<LoginData> datos = JsonDataReader.leerDatosLogin();

        Object[][] data = new Object[datos.size()][2];

        for (int i = 0; i < datos.size(); i++) {
            data[i][0] = datos.get(i).getEmail();
            data[i][1] = datos.get(i).getPassword();
        }

        return data;
    }

    @Test
    public void validarCamposObligatoriosLogin() {

        driver.get(AUTH_URL);

        AuthPage authPage = new AuthPage(driver);

        Assert.assertTrue(
                authPage.emailLoginEsObligatorio(),
                "El campo de correo debería ser obligatorio."
        );

        Assert.assertTrue(
                authPage.passwordLoginEsObligatorio(),
                "El campo de contraseña debería ser obligatorio."
        );
    }

    @Test(dataProvider = "loginData")
    public void loginConCredencialesInvalidas(
            String email,
            String password) {

        driver.get(AUTH_URL);

        AuthPage authPage = new AuthPage(driver);

        authPage.ingresarEmailLogin(email);
        authPage.ingresarPasswordLogin(password);
        authPage.iniciarSesion();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("auth.html"),
                "El sistema permitió navegar fuera del login con credenciales inválidas."
        );
    }

    @Test
    public void cambiarARegistro() {

        driver.get(AUTH_URL);

        AuthPage authPage = new AuthPage(driver);

        authPage.abrirRegistro();

        Assert.assertTrue(
                authPage.registroEstaVisible(),
                "El formulario de registro no se mostró correctamente."
        );
    }
}