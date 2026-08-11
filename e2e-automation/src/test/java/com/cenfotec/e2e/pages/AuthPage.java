package com.cenfotec.e2e.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class AuthPage {

    private final WebDriverWait wait;


    /*
     * ============================================================
     * TABS
     * ============================================================
     */

    private final By loginTab =
        By.id(
            "loginTab"
        );


    private final By registerTab =
        By.id(
            "registerTab"
        );


    private final By loginPanel =
        By.id(
            "loginPanel"
        );


    private final By registerPanel =
        By.id(
            "registerPanel"
        );


    /*
     * ============================================================
     * LOGIN
     * ============================================================
     */

    private final By loginEmail =
        By.id(
            "loginEmail"
        );


    private final By loginPassword =
        By.id(
            "loginPassword"
        );


    private final By loginSubmit =
        By.id(
            "loginSubmit"
        );


    private final By loginGlobalMessage =
        By.id(
            "loginGlobalMessage"
        );


    /*
     * Selector real utilizado por auth.html.
     */

    private final By loginPasswordToggle =
        By.cssSelector(
            ".toggle-password[data-target='loginPassword']"
        );


    /*
     * ============================================================
     * REGISTRO
     * ============================================================
     */

    private final By firstName =
        By.id(
            "firstName"
        );


    private final By lastName =
        By.id(
            "lastName"
        );


    private final By registerEmail =
        By.id(
            "registerEmail"
        );


    private final By phone =
        By.id(
            "phone"
        );


    private final By registerPassword =
        By.id(
            "registerPassword"
        );


    private final By confirmPassword =
        By.id(
            "confirmPassword"
        );


    private final By acceptTerms =
        By.id(
            "acceptTerms"
        );


    private final By registerSubmit =
        By.id(
            "registerSubmit"
        );


    private final By registerGlobalMessage =
        By.id(
            "registerGlobalMessage"
        );


    private final By registerPasswordToggle =
        By.cssSelector(
            ".toggle-password[data-target='registerPassword']"
        );


    private final By confirmPasswordToggle =
        By.cssSelector(
            ".toggle-password[data-target='confirmPassword']"
        );


    /*
     * ============================================================
     * ÉXITO
     * ============================================================
     */

    private final By authSuccess =
        By.id(
            "authSuccess"
        );


    private final By successTitle =
        By.id(
            "successTitle"
        );


    private final By successMessage =
        By.id(
            "successMessage"
        );


    /*
     * ============================================================
     * CONSTRUCTOR
     * ============================================================
     */

    public AuthPage(
        WebDriver driver
    ) {

        this.wait =
            new WebDriverWait(
                driver,
                Duration.ofSeconds(
                    3
                )
            );

    }


    /*
     * ============================================================
     * TABS
     * ============================================================
     */

    public boolean isLoginPanelVisible() {

        try {

            WebElement panel =
                wait.until(
                    ExpectedConditions
                        .visibilityOfElementLocated(
                            loginPanel
                        )
                );


            return panel.isDisplayed();

        } catch (
            RuntimeException exception
        ) {

            return false;

        }

    }


    public void openLoginTab() {

        click(
            loginTab
        );

    }


    public void openRegisterTab() {

        click(
            registerTab
        );

    }


    public boolean isRegisterPanelVisible() {

        try {

            WebElement panel =
                wait.until(
                    ExpectedConditions
                        .visibilityOfElementLocated(
                            registerPanel
                        )
                );


            return panel.isDisplayed();

        } catch (
            RuntimeException exception
        ) {

            return false;

        }

    }


    /*
     * ============================================================
     * LOGIN
     * ============================================================
     */

    public void enterLoginEmail(
        String email
    ) {

        type(
            loginEmail,
            email
        );

    }


    public void enterLoginPassword(
        String password
    ) {

        type(
            loginPassword,
            password
        );

    }


    public void submitLogin() {

        click(
            loginSubmit
        );

    }


    public void login(
        String email,
        String password
    ) {

        enterLoginEmail(
            email
        );


        enterLoginPassword(
            password
        );


        submitLogin();

    }


    public String getLoginGlobalMessage() {

        try {

            return wait.until(
                    ExpectedConditions
                        .visibilityOfElementLocated(
                            loginGlobalMessage
                        )
                )
                .getText()
                .trim();

        } catch (
            RuntimeException exception
        ) {

            return "";

        }

    }


    /*
     * ============================================================
     * REGISTRO
     * ============================================================
     */

    public void register(
        String name,
        String surname,
        String email,
        String password
    ) {

        openRegisterTab();


        type(
            firstName,
            name
        );


        type(
            lastName,
            surname
        );


        type(
            registerEmail,
            email
        );


        type(
            registerPassword,
            password
        );


        type(
            confirmPassword,
            password
        );


        selectTerms();


        click(
            registerSubmit
        );

    }


    public void register(
        String name,
        String surname,
        String email,
        String phoneNumber,
        String password
    ) {

        openRegisterTab();


        type(
            firstName,
            name
        );


        type(
            lastName,
            surname
        );


        type(
            registerEmail,
            email
        );


        if (
            phoneNumber != null
            &&
            !phoneNumber.isBlank()
        ) {

            type(
                phone,
                phoneNumber
            );

        }


        type(
            registerPassword,
            password
        );


        type(
            confirmPassword,
            password
        );


        selectTerms();


        click(
            registerSubmit
        );

    }


    private void selectTerms() {

        WebElement checkbox =
            wait.until(
                ExpectedConditions
                    .elementToBeClickable(
                        acceptTerms
                    )
            );


        if (
            !checkbox.isSelected()
        ) {

            checkbox.click();

        }

    }


    public String getRegisterGlobalMessage() {

        try {

            return wait.until(
                    ExpectedConditions
                        .visibilityOfElementLocated(
                            registerGlobalMessage
                        )
                )
                .getText()
                .trim();

        } catch (
            RuntimeException exception
        ) {

            return "";

        }

    }


    /*
     * ============================================================
     * PASSWORD TOGGLES
     * ============================================================
     */

    public void toggleLoginPassword() {

        click(
            loginPasswordToggle
        );

    }


    public void toggleRegisterPassword() {

        click(
            registerPasswordToggle
        );

    }


    public void toggleConfirmPassword() {

        click(
            confirmPasswordToggle
        );

    }


    public String getLoginPasswordType() {

        return getAttribute(
            loginPassword,
            "type"
        );

    }


    public String getRegisterPasswordType() {

        return getAttribute(
            registerPassword,
            "type"
        );

    }


    public String getConfirmPasswordType() {

        return getAttribute(
            confirmPassword,
            "type"
        );

    }


    /*
     * ============================================================
     * ÉXITO
     * ============================================================
     */

    public boolean isAuthSuccessVisible() {

        try {

            WebElement success =
                wait.until(
                    ExpectedConditions
                        .visibilityOfElementLocated(
                            authSuccess
                        )
                );


            return success.isDisplayed();

        } catch (
            RuntimeException exception
        ) {

            return false;

        }

    }


    public String getSuccessTitle() {

        try {

            return wait.until(
                    ExpectedConditions
                        .visibilityOfElementLocated(
                            successTitle
                        )
                )
                .getText()
                .trim();

        } catch (
            RuntimeException exception
        ) {

            return "";

        }

    }


    public String getSuccessMessage() {

        try {

            return wait.until(
                    ExpectedConditions
                        .visibilityOfElementLocated(
                            successMessage
                        )
                )
                .getText()
                .trim();

        } catch (
            RuntimeException exception
        ) {

            return "";

        }

    }


    /*
     * ============================================================
     * UTILIDADES
     * ============================================================
     */

    private void click(
        By locator
    ) {

        wait.until(
                ExpectedConditions
                    .elementToBeClickable(
                        locator
                    )
            )
            .click();

    }


    private void type(
        By locator,
        String value
    ) {

        WebElement element =
            wait.until(
                ExpectedConditions
                    .visibilityOfElementLocated(
                        locator
                    )
            );


        element.clear();


        element.sendKeys(
            value
        );

    }


    private String getAttribute(
        By locator,
        String attribute
    ) {

        return wait.until(
                ExpectedConditions
                    .presenceOfElementLocated(
                        locator
                    )
            )
            .getAttribute(
                attribute
            );

    }

}