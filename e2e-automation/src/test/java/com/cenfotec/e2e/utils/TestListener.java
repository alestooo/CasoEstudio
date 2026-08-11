package com.cenfotec.e2e.utils;

import org.openqa.selenium.WebDriver;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.lang.reflect.Field;

public class TestListener
        implements ITestListener {

    private int successfulTests;
    private int failedTests;
    private int skippedTests;


    @Override
    public void onStart(
            ITestContext context
    ) {

        successfulTests = 0;
        failedTests = 0;
        skippedTests = 0;


        System.out.println();
        System.out.println(
                "=========================================="
        );

        System.out.println(
                "INICIANDO SUITE: "
                        + context.getName()
        );

        System.out.println(
                "=========================================="
        );

        System.out.println();

    }


    @Override
    public void onTestStart(
            ITestResult result
    ) {

        System.out.println(
                "▶ Ejecutando: "
                        + result.getName()
        );

    }


    @Override
    public void onTestSuccess(
            ITestResult result
    ) {

        successfulTests++;


        System.out.println(
                "✓ PASÓ: "
                        + result.getName()
        );

    }


    @Override
    public void onTestFailure(
            ITestResult result
    ) {

        failedTests++;


        System.out.println(
                "✗ FALLÓ: "
                        + result.getName()
        );


        Throwable throwable =
                result.getThrowable();


        if (
                throwable != null
        ) {

            System.out.println(
                    "Motivo: "
                            + throwable.getMessage()
            );

        }


        WebDriver driver =
                extractDriver(
                        result
                );


        if (
                driver != null
        ) {

            String path =
                    ScreenshotUtils
                            .takeScreenshot(
                                    driver,
                                    result.getName()
                            );


            if (
                    !path.isBlank()
            ) {

                System.out.println(
                        "Screenshot guardado en:"
                );

                System.out.println(
                        path
                );

            }

        }

    }


    @Override
    public void onTestSkipped(
            ITestResult result
    ) {

        skippedTests++;


        System.out.println(
                "○ OMITIDO: "
                        + result.getName()
        );

    }


    @Override
    public void onFinish(
            ITestContext context
    ) {

        System.out.println();
        System.out.println(
                "=========================================="
        );

        System.out.println(
                "SUITE FINALIZADA: "
                        + context.getName()
        );

        System.out.println(
                "Exitosas: "
                        + successfulTests
        );

        System.out.println(
                "Fallidas: "
                        + failedTests
        );

        System.out.println(
                "Omitidas: "
                        + skippedTests
        );

        System.out.println(
                "=========================================="
        );

        System.out.println();

    }


    private WebDriver extractDriver(
            ITestResult result
    ) {

        Object instance =
                result.getInstance();


        if (
                instance == null
        ) {

            return null;

        }


        Class<?> currentClass =
                instance.getClass();


        while (
                currentClass != null
        ) {

            try {

                Field driverField =
                        currentClass
                                .getDeclaredField(
                                        "driver"
                                );


                driverField.setAccessible(
                        true
                );


                Object value =
                        driverField.get(
                                instance
                        );


                if (
                        value
                                instanceof WebDriver webDriver
                ) {

                    return webDriver;

                }

            } catch (
                    NoSuchFieldException exception
            ) {

                currentClass =
                        currentClass
                                .getSuperclass();


                continue;

            } catch (
                    IllegalAccessException exception
            ) {

                return null;

            }


            currentClass =
                    currentClass
                            .getSuperclass();

        }


        return null;

    }

}