package com.cenfotec.e2e.utils;

import com.cenfotec.e2e.config.ConfigReader;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class WaitUtils {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public WaitUtils(WebDriver driver) {

        this.driver = driver;

        int timeout =
                ConfigReader.getInt("explicit.wait");

        this.wait =
                new WebDriverWait(
                        driver,
                        Duration.ofSeconds(timeout)
                );
    }

    public WebElement esperarVisible(By locator) {

        return wait.until(
                ExpectedConditions
                        .visibilityOfElementLocated(locator)
        );
    }

    public WebElement esperarClickeable(By locator) {

        return wait.until(
                ExpectedConditions
                        .elementToBeClickable(locator)
        );
    }

    public boolean esperarUrlContenga(String texto) {

        return wait.until(
                ExpectedConditions
                        .urlContains(texto)
        );
    }

    public boolean esperarTexto(
            By locator,
            String texto) {

        return wait.until(
                ExpectedConditions
                        .textToBePresentInElementLocated(
                                locator,
                                texto
                        )
        );
    }

    public boolean esperarAtributoContenga(
            By locator,
            String atributo,
            String valor) {

        return wait.until(
                ExpectedConditions
                        .attributeContains(
                                locator,
                                atributo,
                                valor
                        )
        );
    }

    public void esperarCantidadVisiblesMenorQue(
            By locator,
            int cantidadInicial) {

        wait.until(driver -> {

            List<WebElement> elementos =
                    driver.findElements(locator);

            long visibles =
                    elementos.stream()
                            .filter(WebElement::isDisplayed)
                            .count();

            return visibles < cantidadInicial;
        });
    }

    public void esperarContadorCarritoMayorQueCero(
            By locator) {

        wait.until(driver -> {

            String texto =
                    driver.findElement(locator)
                            .getText()
                            .trim();

            if (texto.isEmpty()) {
                return false;
            }

            try {

                return Integer.parseInt(texto) > 0;

            } catch (NumberFormatException e) {

                return false;
            }
        });
    }
}