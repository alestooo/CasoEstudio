package com.cenfotec.e2e.tests;

import com.cenfotec.e2e.base.BaseTest;
import com.cenfotec.e2e.pages.CartPage;

import org.openqa.selenium.JavascriptExecutor;

import org.testng.Assert;
import org.testng.annotations.Test;

public class CartPageTest extends BaseTest {

    /*
     * =========================================================
     * 22. CHECKOUT COMPLETO
     * =========================================================
     */

    @Test
    public void checkoutShouldCompletePurchase() {

        /*
         * Abrimos una página del dominio primero
         * para poder utilizar localStorage.
         */

        openTeknovationPage(
                "index.html"
        );


        JavascriptExecutor js =
                (JavascriptExecutor) driver;


        /*
         * Usuario autenticado.
         */

        js.executeScript(
                """
                localStorage.setItem(
                    'currentUser',
                    JSON.stringify({
                        firstName: 'Alejandro',
                        lastName: 'Soto',
                        email: 'alejandro@teknovation.com'
                    })
                );
                """
        );


        /*
         * Producto de prueba en el carrito.
         */

        js.executeScript(
                """
                localStorage.setItem(
                    'cart',
                    JSON.stringify([
                        {
                            cartId: 'test-cart-1',
                            id: 'pro-x-superlight-2',
                            productId: 'pro-x-superlight-2',
                            name: 'PRO X SUPERLIGHT 2',
                            subtitle: 'Mouse gaming inalámbrico',
                            price: 159.99,
                            image: './assets/productos/PRO X SUPERLIGHT 2.jpg',
                            quantity: 1,
                            color: 'Negro'
                        }
                    ])
                );
                """
        );


        /*
         * Abrir carrito después de preparar
         * localStorage.
         */

        openTeknovationPage(
                "cart.html"
        );


        CartPage cartPage =
                new CartPage(
                        driver
                );


        Assert.assertTrue(
                cartPage.isCartContentVisible(),
                "El carrito debería mostrar el producto."
        );


        cartPage.clickCheckout();


        Assert.assertTrue(
                cartPage.isPurchaseCompletedAlertVisible(),
                "La compra debería finalizar correctamente."
        );


        Assert.assertTrue(
                cartPage
                        .getOrderNumberFromAlert()
                        .contains(
                                "TKN-"
                        ),
                "La compra debería generar un número de pedido."
        );
    }
}