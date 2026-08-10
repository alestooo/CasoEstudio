package com.cenfotec.e2e.tests;

import com.cenfotec.e2e.base.BaseTest;
import com.cenfotec.e2e.pages.ProductPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.cenfotec.e2e.config.ConfigReader;

public class ProductPageTest extends BaseTest {

        private final String PRODUCT_URL =
                ConfigReader.get("teknovation.url")
                        + "/product.html?id=pro-x2-superstrike";

    @Test
    public void validarInformacionDelProducto() {

        driver.get(PRODUCT_URL);

        ProductPage productPage = new ProductPage(driver);

        Assert.assertEquals(
                productPage.obtenerTituloProducto(),
                "PRO X2 SUPERSTRIKE",
                "El nombre del producto no es correcto."
        );

        Assert.assertEquals(
                productPage.obtenerPrecioProducto(),
                "179,99 €",
                "El precio del producto no es correcto."
        );
    }

    @Test
    public void incrementarCantidadProducto() {

        driver.get(PRODUCT_URL);

        ProductPage productPage = new ProductPage(driver);

        Assert.assertEquals(
                productPage.obtenerCantidad(),
                "1"
        );

        productPage.incrementarCantidad();

        Assert.assertEquals(
                productPage.obtenerCantidad(),
                "2",
                "La cantidad no aumentó correctamente."
        );
    }

    @Test
    public void seleccionarColorBlanco() {

        driver.get(PRODUCT_URL);

        ProductPage productPage = new ProductPage(driver);

        productPage.seleccionarColorBlanco();

        Assert.assertTrue(
                productPage.colorBlancoEstaSeleccionado(),
                "El color blanco no quedó seleccionado."
        );
    }
}