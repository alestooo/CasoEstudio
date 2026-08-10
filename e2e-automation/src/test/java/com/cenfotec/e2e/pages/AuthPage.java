package com.cenfotec.e2e.pages;

import com.cenfotec.e2e.utils.WaitUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AuthPage {

    private final WebDriver driver;
    private final WaitUtils wait;

    private final By loginEmail =
            By.id("loginEmail");

    private final By loginPassword =
            By.id("loginPassword");

    private final By loginSubmit =
            By.id("loginSubmit");

    private final By registerTab =
            By.id("registerTab");

    private final By registerPanel =
            By.id("registerPanel");

    public AuthPage(WebDriver driver) {

        this.driver = driver;
        this.wait = new WaitUtils(driver);
    }

    public void ingresarEmailLogin(String email) {

        wait.esperarVisible(loginEmail).clear();

        wait.esperarVisible(loginEmail)
                .sendKeys(email);
    }

    public void ingresarPasswordLogin(
            String password) {

        wait.esperarVisible(loginPassword)
                .clear();

        wait.esperarVisible(loginPassword)
                .sendKeys(password);
    }

    public void iniciarSesion() {

        wait.esperarClickeable(
                loginSubmit
        ).click();
    }

    public boolean emailLoginEsObligatorio() {

        return wait.esperarVisible(
                        loginEmail
                )
                .getAttribute("required") != null;
    }

    public boolean passwordLoginEsObligatorio() {

        return wait.esperarVisible(
                        loginPassword
                )
                .getAttribute("required") != null;
    }

    public void abrirRegistro() {

        wait.esperarClickeable(
                registerTab
        ).click();
    }

    public boolean registroEstaVisible() {

        return wait.esperarVisible(
                        registerPanel
                )
                .getAttribute("class")
                .contains("active");
    }
}