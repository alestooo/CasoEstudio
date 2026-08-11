package com.cenfotec.e2e.data;
/**
 * Datos utilizados en las pruebas E2E de autenticación
 * de Teknovation.
 *
 * Centralizar los datos aquí evita colocar correos,
 * contraseñas y nombres directamente dentro de los tests.
 */
public final class LoginData {

    /*
     * Evita que la clase sea instanciada.
     */
    private LoginData() {
    }


    /* =========================================================
       USUARIO VÁLIDO
       ========================================================= */

    public static final String FIRST_NAME =
            "Alejandro";

    public static final String LAST_NAME =
            "Soto";

    public static final String PHONE =
            "8888-8888";

    public static final String PASSWORD =
            "Tekno123!";

    public static final String CONFIRM_PASSWORD =
            PASSWORD;


    /* =========================================================
       CORREOS
       ========================================================= */

    /**
     * Correo base.
     *
     * Para pruebas de registro es mejor utilizar
     * generateUniqueEmail() para evitar que un usuario
     * existente en localStorage haga fallar la prueba.
     */
    public static final String EMAIL =
            "alejandro@teknovation.test";


    public static final String INVALID_EMAIL =
            "correo-invalido";


    public static final String NON_EXISTING_EMAIL =
            "noexiste@teknovation.test";


    /* =========================================================
       CONTRASEÑAS INCORRECTAS
       ========================================================= */

    public static final String WRONG_PASSWORD =
            "PasswordIncorrecto123!";


    public static final String SHORT_PASSWORD =
            "123";


    public static final String DIFFERENT_PASSWORD =
            "OtraPassword123!";


    /* =========================================================
       CAMPOS VACÍOS
       ========================================================= */

    public static final String EMPTY_EMAIL =
            "";

    public static final String EMPTY_PASSWORD =
            "";

    public static final String EMPTY_FIRST_NAME =
            "";

    public static final String EMPTY_LAST_NAME =
            "";


    /* =========================================================
       GENERAR CORREO ÚNICO
       ========================================================= */

    /**
     * Genera un correo diferente en cada ejecución.
     *
     * Ejemplo:
     * test.1786400000000@teknovation.test
     *
     * Esto es especialmente útil porque Teknovation
     * almacena los usuarios registrados en localStorage.
     */
    public static String generateUniqueEmail() {

        return "test."
                + System.currentTimeMillis()
                + "@teknovation.test";

    }


    /* =========================================================
       GENERAR CORREO ÚNICO CON PREFIJO
       ========================================================= */

    /**
     * Permite identificar fácilmente qué prueba creó
     * determinado usuario.
     *
     * Ejemplo:
     * registro.1786400000000@teknovation.test
     */
    public static String generateUniqueEmail(
            String prefix
    ) {

        String safePrefix =
                prefix == null
                        || prefix.isBlank()
                        ? "test"
                        : prefix
                            .trim()
                            .toLowerCase()
                            .replaceAll(
                                    "[^a-z0-9]+",
                                    "."
                            );


        return safePrefix
                + "."
                + System.currentTimeMillis()
                + "@teknovation.test";

    }

}