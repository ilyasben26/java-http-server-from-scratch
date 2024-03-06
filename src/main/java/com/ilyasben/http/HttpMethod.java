package com.ilyasben.http;

public enum HttpMethod {
    GET, // HTTP GET method
    HEAD; // HTTP HEAD method

    // Maximum length among HTTP method names
    public static final int MAX_LENGTH;

    // Static initializer block to compute the maximum length among method names
    static {
        int tempMaxLength = -1;
        // Iterate through each HTTP method enum value
        for (HttpMethod method : values()) {
            // Check if the length of the method name is greater than the current maximum length
            if (method.name().length() > tempMaxLength) {
                // Update the maximum length
                tempMaxLength = method.name().length();
            }
        }
        // Set the maximum length
        MAX_LENGTH = tempMaxLength;
    }
}
