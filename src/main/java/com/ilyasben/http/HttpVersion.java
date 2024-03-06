package com.ilyasben.http;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum HttpVersion {
    // Enum value representing HTTP/1.1
    HTTP_1_1("HTTP/1.1", 1, 1);

    // Fields to store the literal, major, and minor versions
    public final String LITERAL;
    public final int MAJOR;
    public final int MINOR;

    // Constructor
    HttpVersion(String LITERAL, int MAJOR, int MINOR) {
        this.LITERAL = LITERAL;
        this.MAJOR = MAJOR;
        this.MINOR = MINOR;
    }

    // Regex pattern to match HTTP version strings
    private static final Pattern httpVersionRegexPattern = Pattern.compile("^HTTP/(?<major>\\d+).(?<minor>\\d+)");

    // Method to get the best compatible HTTP version from a literal version string
    public static HttpVersion getBestCompatibleVersion(String literalVersion) throws BadHttpVersionException {
        // Create a matcher for the regex pattern
        Matcher matcher = httpVersionRegexPattern.matcher(literalVersion);
        // Check if the literal version matches the pattern and has major and minor version numbers
        if (!matcher.find() || matcher.groupCount() != 2) {
            throw new BadHttpVersionException();
        }
        // Parse the major and minor version numbers from the literal version
        int major = Integer.parseInt(matcher.group("major"));
        int minor = Integer.parseInt(matcher.group("minor"));

        // Temporary variable to store the best compatible version found so far
        HttpVersion tempBestCompatible = null;
        // Iterate through each HTTP version enum value
        for (HttpVersion version : HttpVersion.values()) {
            // Check if the literal version matches the current enum value
            if (version.LITERAL.equals(literalVersion)) {
                return version; // Exact match found, return the version
            } else {
                // If the major version matches, check if the minor version is compatible
                if (version.MAJOR == major) {
                    if (version.MINOR < minor) {
                        tempBestCompatible = version; // Store the version as a potential compatible version
                    }
                }
            }
        }
        return tempBestCompatible; // Return the best compatible version found
    }
}
