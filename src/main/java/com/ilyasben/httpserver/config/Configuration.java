package com.ilyasben.httpserver.config;

/**
 * Represents the configuration settings for the HTTP server.
 */
public class Configuration {
    private int port; // Port number the server listens on
    private String webroot; // Root directory of the server's files

    /**
     * Get the port number.
     * @return The port number.
     */
    public int getPort() {
        return port;
    }

    /**
     * Set the port number.
     * @param port The port number to set.
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Get the webroot directory.
     * @return The webroot directory.
     */
    public String getWebroot() {
        return webroot;
    }

    /**
     * Set the webroot directory.
     * @param webroot The webroot directory to set.
     */
    public void setWebroot(String webroot) {
        this.webroot = webroot;
    }
}
