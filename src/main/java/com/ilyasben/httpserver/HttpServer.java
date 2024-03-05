package com.ilyasben.httpserver;

import com.ilyasben.httpserver.config.Configuration;
import com.ilyasben.httpserver.config.ConfigurationManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * Driver Class for the Http Server
 *
 */
public class HttpServer {
    public static void main(String[] args) {

        System.out.println("Server starting...");

        // using the Configuration Manager singleton to load a configuration file
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");

        // fetching the loaded configuration
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();
        System.out.println("Using Port: " + conf.getPort());
        System.out.println("Using WebRoot: " + conf.getWebroot());

        // TCP
        try {
            ServerSocket serverSocket = new ServerSocket(conf.getPort());
            Socket socket = serverSocket.accept(); // waits for a connection

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
            serverSocket.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
