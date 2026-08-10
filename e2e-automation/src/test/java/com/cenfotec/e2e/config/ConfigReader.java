package com.cenfotec.e2e.config;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static final Properties properties = new Properties();

    static {
        cargarPropiedades();
    }

    private static void cargarPropiedades() {

        try (InputStream inputStream =
                     ConfigReader.class
                             .getClassLoader()
                             .getResourceAsStream("config.properties")) {

            if (inputStream == null) {
                throw new RuntimeException(
                        "No se encontró config.properties en src/test/resources"
                );
            }

            properties.load(inputStream);

            System.out.println(
                    "[CONFIG] config.properties cargado correctamente"
            );

        } catch (Exception e) {
            throw new RuntimeException(
                    "Error al cargar config.properties",
                    e
            );
        }
    }

    public static String get(String key) {

        String value = properties.getProperty(key);

        if (value == null || value.trim().isEmpty()) {
            throw new RuntimeException(
                    "No existe la propiedad: " + key
            );
        }

        return value.trim();
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }
}