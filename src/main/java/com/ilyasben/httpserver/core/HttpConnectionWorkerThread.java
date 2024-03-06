package com.ilyasben.httpserver.core;

import com.ilyasben.http.HttpParser;
import com.ilyasben.http.HttpParsingException;
import com.ilyasben.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HttpConnectionWorkerThread extends Thread {
    // Logger for logging server events
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpConnectionWorkerThread.class);

    private Socket socket; // Socket representing the connection to the client
    private HttpParser httpParser = new HttpParser(); // Parser for parsing HTTP requests

    // Constructor to initialize the socket
    public HttpConnectionWorkerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            // Obtain input and output streams from the socket
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            // Read the HTTP request from the client
            try {
                HttpRequest request = httpParser.parseHttpRequest(inputStream);
                // TODO: Handle the request to determine the appropriate response
            } catch (HttpParsingException e) {
                // Log any errors that occur during request parsing
                LOGGER.error("Problem with request parsing", e);
            }

            // TODO: process the request and return an appropriate response


            // Prepare the predefined HTTP response
            String html = "<html><head><title>Simple HTTP Server</title><body><h1>Hello, World!</h1></body></head></html>";
            final String CRLF = "\n\r"; // Carriage return and line feed characters
            String response =
                    "HTTP/1.1 200 OK" + CRLF + // Status Line: HTTP_VERSION RESPONSE_CODE RESPONSE_MESSAGE
                            "Content-Length: " + html.getBytes().length + CRLF + // Header: Content length
                            CRLF + // Extra CRLF to indicate end of header
                            html + CRLF + // HTML content
                            CRLF; // Extra CRLF to indicate end of HTML content

            // Send the HTTP response to the client
            outputStream.write(response.getBytes());

            LOGGER.info("* Connection Processing Finished.");
        } catch (IOException e) {
            // Log any communication problems that occur
            LOGGER.error("Problem with communication", e);
        } finally {
            // Close the input stream
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    // Log any exceptions that occur while closing the input stream
                    LOGGER.error("Error closing input stream", e);
                }
            }
            // Close the output stream
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    // Log any exceptions that occur while closing the output stream
                    LOGGER.error("Error closing output stream", e);
                }
            }
            // Close the socket
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    // Log any exceptions that occur while closing the socket
                    LOGGER.error("Error closing socket", e);
                }
            }
        }
    }
}
