package com.cenfotec.e2e;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class ChromeTest {

    @Test
    public void abrirChrome() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.google.com");

        System.out.println("Título: " + driver.getTitle());

        driver.quit();
    }
}