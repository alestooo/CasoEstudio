package com.cenfotec.e2e.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public final class WaitUtils {

    private static final long DEFAULT_WAIT_SECONDS =
            10;


    private WaitUtils() {
        // Utility class.
    }


    public static WebElement waitForVisible(
            WebDriver driver,
            By locator
    ) {

        return waitForVisible(
                driver,
                locator,
                DEFAULT_WAIT_SECONDS
        );

    }


    public static WebElement waitForVisible(
            WebDriver driver,
            By locator,
            long seconds
    ) {

        return createWait(
                driver,
                seconds
        )
                .until(
                        ExpectedConditions
                                .visibilityOfElementLocated(
                                        locator
                                )
                );

    }


    public static WebElement waitForPresent(
            WebDriver driver,
            By locator
    ) {

        return waitForPresent(
                driver,
                locator,
                DEFAULT_WAIT_SECONDS
        );

    }


    public static WebElement waitForPresent(
            WebDriver driver,
            By locator,
            long seconds
    ) {

        return createWait(
                driver,
                seconds
        )
                .until(
                        ExpectedConditions
                                .presenceOfElementLocated(
                                        locator
                                )
                );

    }


    public static WebElement waitForClickable(
            WebDriver driver,
            By locator
    ) {

        return waitForClickable(
                driver,
                locator,
                DEFAULT_WAIT_SECONDS
        );

    }


    public static WebElement waitForClickable(
            WebDriver driver,
            By locator,
            long seconds
    ) {

        return createWait(
                driver,
                seconds
        )
                .until(
                        ExpectedConditions
                                .elementToBeClickable(
                                        locator
                                )
                );

    }


    public static boolean waitForUrlContains(
            WebDriver driver,
            String value
    ) {

        return waitForUrlContains(
                driver,
                value,
                DEFAULT_WAIT_SECONDS
        );

    }


    public static boolean waitForUrlContains(
            WebDriver driver,
            String value,
            long seconds
    ) {

        return createWait(
                driver,
                seconds
        )
                .until(
                        ExpectedConditions
                                .urlContains(
                                        value
                                )
                );

    }


    public static boolean waitForTitleContains(
            WebDriver driver,
            String value
    ) {

        return waitForTitleContains(
                driver,
                value,
                DEFAULT_WAIT_SECONDS
        );

    }


    public static boolean waitForTitleContains(
            WebDriver driver,
            String value,
            long seconds
    ) {

        return createWait(
                driver,
                seconds
        )
                .until(
                        ExpectedConditions
                                .titleContains(
                                        value
                                )
                );

    }


    public static boolean waitForInvisible(
            WebDriver driver,
            By locator
    ) {

        return waitForInvisible(
                driver,
                locator,
                DEFAULT_WAIT_SECONDS
        );

    }


    public static boolean waitForInvisible(
            WebDriver driver,
            By locator,
            long seconds
    ) {

        return createWait(
                driver,
                seconds
        )
                .until(
                        ExpectedConditions
                                .invisibilityOfElementLocated(
                                        locator
                                )
                );

    }


    public static void scrollToElement(
            WebDriver driver,
            WebElement element
    ) {

        ((JavascriptExecutor) driver)
                .executeScript(
                        """
                        arguments[0].scrollIntoView({
                            behavior: 'instant',
                            block: 'center',
                            inline: 'nearest'
                        });
                        """,
                        element
                );

    }


    public static void safeClick(
            WebDriver driver,
            By locator
    ) {

        WebElement element =
                waitForClickable(
                        driver,
                        locator
                );


        scrollToElement(
                driver,
                element
        );


        try {

            element.click();

        } catch (
                Exception exception
        ) {

            ((JavascriptExecutor) driver)
                    .executeScript(
                            "arguments[0].click();",
                            element
                    );

        }

    }


    public static void waitForPageReady(
            WebDriver driver
    ) {

        WebDriverWait wait =
                createWait(
                        driver,
                        DEFAULT_WAIT_SECONDS
                );


        wait.until(
                webDriver -> {

                    Object state =
                            ((JavascriptExecutor) webDriver)
                                    .executeScript(
                                            "return document.readyState;"
                                    );


                    return "complete"
                            .equals(
                                    state
                            );

                }
        );

    }


    private static WebDriverWait createWait(
            WebDriver driver,
            long seconds
    ) {

        return new WebDriverWait(
                driver,
                Duration.ofSeconds(
                        Math.max(
                                seconds,
                                1
                        )
                )
        );

    }

}