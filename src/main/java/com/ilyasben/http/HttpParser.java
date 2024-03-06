package com.ilyasben.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class HttpParser {
    // Logger for logging parsing events
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpParser.class);

    // Constants for special characters
    private final static int SP = 0x20; // 32
    private final static int CR = 0x0D; // 13
    private final static int LF = 0x0A; // 10

    // Constructor
    public HttpParser() {
    }

    // Method to parse HTTP request
    public HttpRequest parseHttpRequest(InputStream inputStream) throws HttpParsingException {
        // Create an InputStreamReader to read bytes from the input stream
        InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.US_ASCII);

        // Create a new HttpRequest object to store the parsed request
        HttpRequest request = new HttpRequest();

        try {
            // Parse the request line
            parseRequestLine(reader, request);
        } catch (HttpParsingException e) {
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Parse headers
        parseHeaders(reader, request);

        // Parse body
        parseBody(reader, request);

        return request;
    }

    // Method to parse the request line
    private void parseRequestLine(InputStreamReader reader, HttpRequest request) throws IOException, HttpParsingException {
        StringBuilder processingDataBuffer = new StringBuilder();

        boolean methodParsed = false;
        boolean requestTargetParsed = false;

        int _byte;
        while ((_byte = reader.read()) >= 0) {
            if (_byte == CR) {
                _byte = reader.read();
                if (_byte == LF) {
                    // Check if both method and request target are parsed
                    if (!methodParsed || !requestTargetParsed) {
                        throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
                    }

                    // Set the HTTP version of the request
                    try {
                        request.setHttpVersion(processingDataBuffer.toString());
                    } catch (BadHttpVersionException e) {
                        throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
                    }

                    return;
                } else {
                    throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
                }
            }

            if (_byte == SP) {
                // Parse method and request target
                if (!methodParsed) {
                    request.setMethod(processingDataBuffer.toString());
                    methodParsed = true;
                } else if (!requestTargetParsed) {
                    request.setRequestTarget(processingDataBuffer.toString());
                    requestTargetParsed = true;
                } else {
                    throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
                }
                processingDataBuffer.delete(0, processingDataBuffer.length());
            } else {
                processingDataBuffer.append((char) _byte);
                // Limit input length for method
                if (!methodParsed) {
                    if (processingDataBuffer.length() > HttpMethod.MAX_LENGTH) {
                        throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
                    }
                }
            }
        }
    }

    // Method to parse HTTP headers
    private void parseHeaders(InputStreamReader reader, HttpRequest request) {
        // TODO: implement Header Parser
    }

    // Method to parse HTTP body
    private void parseBody(InputStreamReader reader, HttpRequest request) {
        // TODO: implement Body Parser
    }
}
