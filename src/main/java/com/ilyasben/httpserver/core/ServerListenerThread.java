package com.ilyasben.httpserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListenerThread extends Thread {
    private final static Logger LOGGER = LoggerFactory.getLogger(ServerListenerThread.class);  // Logger
    private int port;
    private String webroot;
    ServerSocket serverSocket;

    public ServerListenerThread(int port, String webroot) throws IOException {
        this.port = port;
        this.webroot = webroot;
        this.serverSocket = new ServerSocket(this.port);
    }

    @Override
    public void run() {
        try {

            while ( serverSocket.isBound() && !serverSocket.isClosed()) {
                Socket socket = serverSocket.accept(); // waits for a connection

                LOGGER.info("* Connection Accepted: " + socket.getInetAddress());

                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();

                // TODO: reading request

                // writing response
                String html = "<html><head><title>Simple HTTP Server</title><body><h1>Hello, World!</h1></body></head></html>";

                final String CRLF = "\n\r"; // 13, 10

                String response =
                        "HTTP/1.1 200 OK" + CRLF + // Status Line : HTTP_VERSION RESPONSE_CODE RESPONSE_MESSAGE
                                "Content-Length: " + html.getBytes().length + CRLF + // HEADER
                                CRLF + // extra CRLF to say that header is done
                                html + CRLF + // HTML
                                CRLF ; // extra CRLF to say that HTML is done

                // sending the response
                outputStream.write(response.getBytes());

                inputStream.close();
                outputStream.close();
                socket.close();

            }
            // serverSocket.close(); // TODO: Handle close

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
