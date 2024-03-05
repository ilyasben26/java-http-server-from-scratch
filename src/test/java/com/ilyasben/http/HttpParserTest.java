package com.ilyasben.http;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.ByteArrayInputStream;
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
        httpParser.parseHttpRequest(
                generateValidTestCase()
        );
    }

    private InputStream generateValidTestCase() {
        String rawData = "GET / HTTP/1.1\n\r" +
                "Host: localhost:8080\n\r" +
                "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:123.0) Gecko/20100101 Firefox/123.0\n\r" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8\n\r" +
                "Accept-Language: en-US,en;q=0.5\n\r" +
                "Accept-Encoding: gzip, deflate, br\n\r" +
                "Connection: keep-alive\n\r" +
                "Cookie: username-localhost-8888=\"2|1:0|10:1707125737|23:username-localhost-8888|44:ZWVkMmQ3NzkxODI3NDNmY2E4N2M4OGRlODhhNmM5ODc=|13cf79703dfc7d136ec8d73dff7c67f313ac50d77b7ccd0526d26f1ca2776fc7\"; _xsrf=2|ddcf667a|cd0cb140bd34b6b7d9f04f9370d1cae0|1707125737; JSESSIONID=274B6E98BD679A74671E50997DABB23F\n\r" +
                "Upgrade-Insecure-Requests: 1\n\r" +
                "Sec-Fetch-Dest: document\n\r" +
                "Sec-Fetch-Mode: navigate\n\r" +
                "Sec-Fetch-Site: none\n\r" +
                "Sec-Fetch-User: ?1";

        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );

        return inputStream;
    }
}