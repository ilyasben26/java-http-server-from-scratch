package com.ilyasben.http;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HttpParserTest {

    private HttpParser httpParser;

    @BeforeAll
    public void beforeClass() {
        httpParser = new HttpParser();
    }

    @Test
    void parseHttpRequest() {
        HttpRequest request = null;
        try {
            request = httpParser.parseHttpRequest(
                    generateValidGETTestCase()
            );
        } catch (HttpParsingException e) {
            fail(e);
        }

        assertEquals(request.getMethod(), HttpMethod.GET);
    }

    @Test
    void parseHttpRequestBadMethod1() {
        HttpRequest request = null;
        try {
            request = httpParser.parseHttpRequest(
                    generateBadGETestCaseMethodName1()
            );
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
        }

    }

    private InputStream generateValidGETTestCase() {
        String rawData = "GET / HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:123.0) Gecko/20100101 Firefox/123.0\r\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8\r\n" +
                "Accept-Language: en-US,en;q=0.5\r\n" +
                "Accept-Encoding: gzip, deflate, br\r\n" +
                "Connection: keep-alive\r\n" +
                "Cookie: username-localhost-8888=\"2|1:0|10:1707125737|23:username-localhost-8888|44:ZWVkMmQ3NzkxODI3NDNmY2E4N2M4OGRlODhhNmM5ODc=|13cf79703dfc7d136ec8d73dff7c67f313ac50d77b7ccd0526d26f1ca2776fc7\"; _xsrf=2|ddcf667a|cd0cb140bd34b6b7d9f04f9370d1cae0|1707125737; JSESSIONID=274B6E98BD679A74671E50997DABB23F\r\n" +
                "Upgrade-Insecure-Requests: 1\r\n" +
                "Sec-Fetch-Dest: document\r\n" +
                "Sec-Fetch-Mode: navigate\r\n" +
                "Sec-Fetch-Site: none\r\n" +
                "Sec-Fetch-User: ?1";

        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );

        return inputStream;
    }

    private InputStream generateBadGETestCaseMethodName1() {
        String rawData = "GeT / HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:123.0) Gecko/20100101 Firefox/123.0\r\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8\r\n" +
                "Accept-Language: en-US,en;q=0.5\r\n";

        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );

        return inputStream;
    }
}