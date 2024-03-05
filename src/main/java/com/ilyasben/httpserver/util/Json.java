package com.ilyasben.httpserver.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

/**
 * Utility class for JSON operations using Jackson ObjectMapper.
 */
public class Json {
    private static ObjectMapper myObjectMapper = defaultObjectMapper();

    private static ObjectMapper defaultObjectMapper() {
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // not crash if a property is missing
        return om;
    }

    /**
     * Parses JSON string into a JsonNode.
     * @param jsonSrc JSON string to parse
     * @return JsonNode representing the parsed JSON structure
     * @throws JsonProcessingException if there's an issue processing JSON
     */
    public static JsonNode parse(String jsonSrc) throws JsonProcessingException {
        return myObjectMapper.readTree(jsonSrc);
    }

    /**
     * Converts JsonNode to an instance of the specified class.
     * @param node JsonNode to convert
     * @param clazz Class to convert JsonNode into
     * @param <A> Type of the class to convert JsonNode into
     * @return An instance of the specified class
     * @throws JsonProcessingException if there's an issue processing JSON
     */
    public static <A> A fromJson(JsonNode node, Class<A> clazz) throws JsonProcessingException {
        return myObjectMapper.treeToValue(node, clazz);
    }

    /**
     * Converts an object into a JsonNode.
     * @param obj Object to convert into JSON
     * @return JsonNode representing the object
     */
    public static JsonNode toJson(Object obj) {
        return myObjectMapper.valueToTree(obj);
    }

    // Generates JSON string from an object, optionally pretty printing it
    private static String generateJson(Object obj, boolean pretty) throws JsonProcessingException {
        ObjectWriter objectWriter = myObjectMapper.writer();
        if (pretty) {
            objectWriter = objectWriter.with(SerializationFeature.INDENT_OUTPUT);
        }
        return objectWriter.writeValueAsString(obj);
    }

    /**
     * Converts a JsonNode into a JSON string.
     * @param node JsonNode to stringify
     * @return JSON string representation of the JsonNode
     * @throws JsonProcessingException if there's an issue processing JSON
     */
    public static String stringify(JsonNode node) throws JsonProcessingException {
        return generateJson(node, false);
    }

    /**
     * Converts a JsonNode into a prettified JSON string.
     * @param node JsonNode to stringify
     * @return Prettified JSON string representation of the JsonNode
     * @throws JsonProcessingException if there's an issue processing JSON
     */
    public static String stringifyPretty(JsonNode node) throws JsonProcessingException {
        return generateJson(node, true);
    }
}
