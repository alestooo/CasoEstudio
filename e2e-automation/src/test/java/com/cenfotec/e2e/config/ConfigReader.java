package com.cenfotec.e2e.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Lee las propiedades de:
 *
 * src/test/resources/config.properties
 */
public class ConfigReader {

    private final Properties properties;


    /* =========================================================
       CONSTRUCTOR
       ========================================================= */

    public ConfigReader() {

        properties =
                new Properties();


        loadProperties();

    }


    /* =========================================================
       CARGAR CONFIG
       ========================================================= */

    private void loadProperties() {

        try (
                InputStream inputStream =
                        getClass()
                                .getClassLoader()
                                .getResourceAsStream(
                                        "config.properties"
                                )
        ) {

            if (
                    inputStream == null
            ) {

                throw new IllegalStateException(
                        "No se encontró config.properties "
                                + "en src/test/resources"
                );

            }


            properties.load(
                    inputStream
            );

        } catch (
                IOException exception
        ) {

            throw new RuntimeException(
                    "Error al leer config.properties",
                    exception
            );

        }

    }


    /* =========================================================
       NAVEGADOR
       ========================================================= */

    public String getBrowser() {

        return getProperty(
                "browser",
                "chrome"
        );

    }


    /* =========================================================
       URL BASE
       ========================================================= */

    public String getBaseUrl() {

        return removeTrailingSlash(
                getProperty(
                        "base.url",
                        "http://127.0.0.1:5500"
                )
        );

    }


    /* =========================================================
       TEKNOVATION URL
       ========================================================= */

    public String getTeknovationUrl() {

        return removeTrailingSlash(
                getProperty(
                        "teknovation.url",
                        getBaseUrl()
                                + "/teknovation"
                )
        );

    }


    /* =========================================================
       IMPLICIT WAIT
       ========================================================= */

    public long getImplicitWait() {

        return getLongProperty(
                "implicit.wait",
                0
        );

    }


    /* =========================================================
       EXPLICIT WAIT
       ========================================================= */

    public long getExplicitWait() {

        return getLongProperty(
                "explicit.wait",
                10
        );

    }


    /* =========================================================
       PAUSA AL ABRIR
       ========================================================= */

    public long getOpenPauseSeconds() {

        return getLongProperty(
                "open.pause.seconds",
                0
        );

    }


    /* =========================================================
       PAUSA AL NAVEGAR
       ========================================================= */

    public long getNavigationPauseSeconds() {

        return getLongProperty(
                "navigation.pause.seconds",
                0
        );

    }


    /* =========================================================
       DURACIÓN MÍNIMA
       ========================================================= */

    public long getMinimumTestSeconds() {

        return getLongProperty(
                "minimum.test.seconds",
                0
        );

    }


    /* =========================================================
       PAUSA ANTES DE CERRAR
       ========================================================= */

    public long getClosePauseSeconds() {

        return getLongProperty(
                "close.pause.seconds",
                0
        );

    }


    /* =========================================================
       GET PROPERTY
       ========================================================= */

    public String getProperty(
            String key,
            String defaultValue
    ) {

        String value =
                properties.getProperty(
                        key
                );


        if (
                value == null
                || value.isBlank()
        ) {

            return defaultValue;

        }


        return value.trim();

    }


    /* =========================================================
       REQUIRED PROPERTY
       ========================================================= */

    public String getRequiredProperty(
            String key
    ) {

        String value =
                properties.getProperty(
                        key
                );


        if (
                value == null
                || value.isBlank()
        ) {

            throw new IllegalStateException(
                    "Falta la propiedad requerida: "
                            + key
            );

        }


        return value.trim();

    }


    /* =========================================================
       LONG PROPERTY
       ========================================================= */

    private long getLongProperty(
            String key,
            long defaultValue
    ) {

        String value =
                properties.getProperty(
                        key
                );


        if (
                value == null
                || value.isBlank()
        ) {

            return defaultValue;

        }


        try {

            return Long.parseLong(
                    value.trim()
            );

        } catch (
                NumberFormatException exception
        ) {

            throw new IllegalArgumentException(
                    "La propiedad "
                            + key
                            + " debe contener un número. "
                            + "Valor actual: "
                            + value,
                    exception
            );

        }

    }


    /* =========================================================
       LIMPIAR SLASH FINAL
       ========================================================= */

    private String removeTrailingSlash(
            String value
    ) {

        if (
                value == null
                || value.isBlank()
        ) {

            return "";

        }


        String result =
                value.trim();


        while (
                result.endsWith("/")
        ) {

            result =
                    result.substring(
                            0,
                            result.length() - 1
                    );

        }


        return result;

    }

}