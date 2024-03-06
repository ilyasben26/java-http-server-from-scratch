package com.ilyasben.http;

public class HttpRequest extends HttpMessage {

    // Fields to store HTTP method, request target, original HTTP version, and best compatible HTTP version
    private HttpMethod method;
    private String requestTarget;
    private String originalHttpVersion;
    private HttpVersion bestCompatibleHttpVersion;

    // Constructor
    HttpRequest() {
    }

    // Getter methods
    public HttpMethod getMethod() {
        return method;
    }

    public String getRequestTarget() {
        return requestTarget;
    }

    public String getOriginalHttpVersion() {
        return originalHttpVersion;
    }

    public HttpVersion getBestCompatibleHttpVersion() {
        return bestCompatibleHttpVersion;
    }

    // Method to set the HTTP method
    void setMethod(String methodName) throws HttpParsingException {
        for (HttpMethod method : HttpMethod.values()) {
            if (methodName.equals(method.name())) {
                this.method = method;
                return;
            }
        }
        // Throw an exception if the HTTP method is not recognized
        throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
    }

    // Method to set the request target
    void setRequestTarget(String requestTarget) throws HttpParsingException {
        if (requestTarget == null || requestTarget.length() == 0) {
            // Throw an exception if the request target is empty
            throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }
        this.requestTarget = requestTarget;
    }

    // Method to set the original HTTP version and determine the best compatible version
    void setHttpVersion(String originalHttpVersion) throws BadHttpVersionException, HttpParsingException {
        this.originalHttpVersion = originalHttpVersion;
        // Determine the best compatible HTTP version
        this.bestCompatibleHttpVersion = HttpVersion.getBestCompatibleVersion(originalHttpVersion);
        if (this.bestCompatibleHttpVersion == null) {
            // Throw an exception if the HTTP version is not supported
            throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_505_HTTP_VERSION_NOT_SUPPORTED);
        }
    }
}
