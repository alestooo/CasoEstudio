package com.cenfotec.e2e.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public final class JsonDataReader {

    private static final ObjectMapper OBJECT_MAPPER =
            new ObjectMapper();


    private JsonDataReader() {
        // Utility class.
    }


    public static JsonNode readJson(
            String fileName
    ) {

        try (
                InputStream inputStream =
                        JsonDataReader
                                .class
                                .getClassLoader()
                                .getResourceAsStream(
                                        fileName
                                )
        ) {

            if (
                    inputStream == null
            ) {

                throw new IllegalArgumentException(
                        "No se encontró el archivo JSON: "
                                + fileName
                );

            }


            return OBJECT_MAPPER
                    .readTree(
                            inputStream
                    );

        } catch (
                IOException exception
        ) {

            throw new RuntimeException(
                    "No se pudo leer el archivo JSON: "
                            + fileName,
                    exception
            );

        }

    }


    public static JsonNode readNode(
            String fileName,
            String nodeName
    ) {

        JsonNode root =
                readJson(
                        fileName
                );


        JsonNode node =
                root.get(
                        nodeName
                );


        if (
                node == null
        ) {

            throw new IllegalArgumentException(
                    "No existe el nodo '"
                            + nodeName
                            + "' en "
                            + fileName
            );

        }


        return node;

    }


    public static String getString(
            JsonNode node,
            String field
    ) {

        validateField(
                node,
                field
        );


        return node
                .get(field)
                .asText();

    }


    public static int getInt(
            JsonNode node,
            String field
    ) {

        validateField(
                node,
                field
        );


        return node
                .get(field)
                .asInt();

    }


    public static long getLong(
            JsonNode node,
            String field
    ) {

        validateField(
                node,
                field
        );


        return node
                .get(field)
                .asLong();

    }


    public static double getDouble(
            JsonNode node,
            String field
    ) {

        validateField(
                node,
                field
        );


        return node
                .get(field)
                .asDouble();

    }


    public static boolean getBoolean(
            JsonNode node,
            String field
    ) {

        validateField(
                node,
                field
        );


        return node
                .get(field)
                .asBoolean();

    }


    private static void validateField(
            JsonNode node,
            String field
    ) {

        if (
                node == null
        ) {

            throw new IllegalArgumentException(
                    "El nodo JSON no puede ser null."
            );

        }


        if (
                !node.has(
                        field
                )
        ) {

            throw new IllegalArgumentException(
                    "No existe el campo JSON: "
                            + field
            );

        }

    }

}