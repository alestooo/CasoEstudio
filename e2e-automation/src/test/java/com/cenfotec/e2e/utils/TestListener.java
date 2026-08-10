package com.cenfotec.e2e.utils;

import com.cenfotec.e2e.base.BaseTest;

import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {

        System.out.println(
                "[TEST INICIADO] "
                        + result.getMethod().getMethodName()
        );
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        System.out.println(
                "[TEST EXITOSO] "
                        + result.getMethod().getMethodName()
        );
    }

    @Override
    public void onTestFailure(ITestResult result) {

        System.err.println(
                "[TEST FALLIDO] "
                        + result.getMethod().getMethodName()
        );

        Object instancia = result.getInstance();

        if (instancia instanceof BaseTest) {

            BaseTest test = (BaseTest) instancia;

            ScreenshotUtils.tomarCaptura(
                    test.getDriver(),
                    result.getMethod().getMethodName()
            );
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        System.out.println(
                "[TEST OMITIDO] "
                        + result.getMethod().getMethodName()
        );
    }
}