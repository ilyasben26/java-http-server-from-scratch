package com.ilyasben.httpserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListenerThread extends Thread {
    // Logger for logging server events
    private final static Logger LOGGER = LoggerFactory.getLogger(ServerListenerThread.class);

    private int port; // Port number the server listens on
    private String webroot; // Root directory of the server

    private ServerSocket serverSocket; // Server socket for listening to incoming connections

    // Constructor to initialize port and webroot
    public ServerListenerThread(int port, String webroot) throws IOException {
        this.port = port;
        this.webroot = webroot;
        // Create a server socket bound to the specified port
        this.serverSocket = new ServerSocket(this.port);
    }

    @Override
    public void run() {
        try {
            // Loop to accept incoming connections
            while (serverSocket.isBound() && !serverSocket.isClosed()) {
                // Accept an incoming connection
                Socket socket = serverSocket.accept();

                // Log connection acceptance
                LOGGER.info("* Connection Accepted: " + socket.getInetAddress());

                // Create and start a worker thread to handle the connection
                HttpConnectionWorkerThread workerThread = new HttpConnectionWorkerThread(socket);
                workerThread.start();
            }
        } catch (IOException e) {
            // Log any exceptions that occur while setting up the socket
            LOGGER.error("Problem with setting socket", e);
        } finally {
            // Close the server socket when done
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    // Log any exceptions that occur while closing the socket
                    LOGGER.error("Error closing server socket", e);
                }
            }
        }
    }
}
