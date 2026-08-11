package com.cenfotec.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AuthPage {

    private final WebDriver driver;
    private final WebDriverWait wait;


    private final By loginTab =
            By.id("loginTab");

    private final By registerTab =
            By.id("registerTab");

    private final By loginPanel =
            By.id("loginPanel");

    private final By registerPanel =
            By.id("registerPanel");


    private final By loginEmail =
            By.id("loginEmail");

    private final By loginPassword =
            By.id("loginPassword");

    private final By loginSubmit =
            By.id("loginSubmit");

    private final By loginGlobalMessage =
            By.id("loginGlobalMessage");


    private final By firstName =
            By.id("firstName");

    private final By lastName =
            By.id("lastName");

    private final By registerEmail =
            By.id("registerEmail");

    private final By phone =
            By.id("phone");

    private final By registerPassword =
            By.id("registerPassword");

    private final By confirmPassword =
            By.id("confirmPassword");

    private final By acceptTerms =
            By.id("acceptTerms");

    private final By registerSubmit =
            By.id("registerSubmit");


    private final By authSuccess =
            By.id("authSuccess");

    private final By logoutBtn =
            By.id("logoutBtn");


    /*
     * SELECTORES REALES DE LOS OJOS
     */

    private final By loginPasswordToggle =
            By.cssSelector(
                    ".toggle-password[data-target='loginPassword']"
            );

    private final By registerPasswordToggle =
            By.cssSelector(
                    ".toggle-password[data-target='registerPassword']"
            );

    private final By confirmPasswordToggle =
            By.cssSelector(
                    ".toggle-password[data-target='confirmPassword']"
            );


    public AuthPage(
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


    public void openLoginTab() {

        wait.until(
                ExpectedConditions
                        .elementToBeClickable(
                                loginTab
                        )
        ).click();

    }


    public void openRegisterTab() {

        wait.until(
                ExpectedConditions
                        .elementToBeClickable(
                                registerTab
                        )
        ).click();

    }


    public boolean isLoginPanelVisible() {

        return isVisible(
                loginPanel
        );

    }


    public boolean isRegisterPanelVisible() {

        return isVisible(
                registerPanel
        );

    }


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


    public void clickLogin() {

        wait.until(
                ExpectedConditions
                        .elementToBeClickable(
                                loginSubmit
                        )
        ).click();

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

        clickLogin();

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
                Exception exception
        ) {

            return "";

        }

    }


    public void enterFirstName(
            String value
    ) {

        type(
                firstName,
                value
        );

    }


    public void enterLastName(
            String value
    ) {

        type(
                lastName,
                value
        );

    }


    public void enterRegisterEmail(
            String value
    ) {

        type(
                registerEmail,
                value
        );

    }


    public void enterPhone(
            String value
    ) {

        type(
                phone,
                value
        );

    }


    public void enterRegisterPassword(
            String value
    ) {

        type(
                registerPassword,
                value
        );

    }


    public void enterConfirmPassword(
            String value
    ) {

        type(
                confirmPassword,
                value
        );

    }


    public void acceptTerms() {

        WebElement checkbox =
                wait.until(
                        ExpectedConditions
                                .presenceOfElementLocated(
                                        acceptTerms
                                )
                );


        if (
                !checkbox.isSelected()
        ) {

            checkbox.click();

        }

    }


    public void clickRegister() {

        wait.until(
                ExpectedConditions
                        .elementToBeClickable(
                                registerSubmit
                        )
        ).click();

    }


    public void register(
            String first,
            String last,
            String email,
            String phoneNumber,
            String password
    ) {

        openRegisterTab();


        enterFirstName(
                first
        );

        enterLastName(
                last
        );

        enterRegisterEmail(
                email
        );

        enterPhone(
                phoneNumber
        );

        enterRegisterPassword(
                password
        );

        enterConfirmPassword(
                password
        );

        acceptTerms();

        clickRegister();

    }


    public boolean isAuthSuccessVisible() {

        return isVisible(
                authSuccess
        );

    }


    public void logout() {

        wait.until(
                ExpectedConditions
                        .elementToBeClickable(
                                logoutBtn
                        )
        ).click();

    }


    public void toggleLoginPassword() {

        wait.until(
                ExpectedConditions
                        .elementToBeClickable(
                                loginPasswordToggle
                        )
        ).click();

    }


    public void toggleRegisterPassword() {

        wait.until(
                ExpectedConditions
                        .elementToBeClickable(
                                registerPasswordToggle
                        )
        ).click();

    }


    public void toggleConfirmPassword() {

        wait.until(
                ExpectedConditions
                        .elementToBeClickable(
                                confirmPasswordToggle
                        )
        ).click();

    }


    public String getLoginPasswordType() {

        return wait.until(
                ExpectedConditions
                        .presenceOfElementLocated(
                                loginPassword
                        )
        ).getAttribute(
                "type"
        );

    }


    public String getRegisterPasswordType() {

        return wait.until(
                ExpectedConditions
                        .presenceOfElementLocated(
                                registerPassword
                        )
        ).getAttribute(
                "type"
        );

    }


    public String getConfirmPasswordType() {

        return wait.until(
                ExpectedConditions
                        .presenceOfElementLocated(
                                confirmPassword
                        )
        ).getAttribute(
                "type"
        );

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

}