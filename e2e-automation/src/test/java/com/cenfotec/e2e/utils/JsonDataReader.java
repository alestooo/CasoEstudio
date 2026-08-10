package com.cenfotec.e2e.utils;

import com.cenfotec.e2e.data.LoginData;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.List;

public class JsonDataReader {

    public static List<LoginData> leerDatosLogin() {

        try {
            ObjectMapper mapper = new ObjectMapper();

            InputStream inputStream =
                    JsonDataReader.class
                            .getClassLoader()
                            .getResourceAsStream("login-data.json");

            if (inputStream == null) {
                throw new RuntimeException(
                        "No se encontró login-data.json"
                );
            }

            return mapper.readValue(
                    inputStream,
                    new TypeReference<List<LoginData>>() {}
            );

        } catch (Exception e) {
            throw new RuntimeException(
                    "Error al leer login-data.json",
                    e
            );
        }
    }
}