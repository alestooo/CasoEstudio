package com.cenfotec.e2e.tests;

import com.cenfotec.e2e.base.BaseTest;
import org.testng.annotations.Test;

public class ChromeTest extends BaseTest {

    @Test
    public void abrirChrome() {

        driver.get("https://www.google.com");

        System.out.println("Título: " + driver.getTitle());
    }
}