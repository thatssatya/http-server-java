package com.samsepiol.server.impl.http;

import com.samsepiol.server.impl.AbstractTcpServer;
import com.samsepiol.server.impl.http.thread.factory.HttpServerThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Set;

@Slf4j
public class HttpServer extends AbstractTcpServer {
    private static final String SERVER_NAME = "Http";
    private static final Integer PORT = 4221;
    private static final Set<String> ENDPOINTS = Set.of("/");
    private static final String HTTP_1_DOT_1 = "HTTP/1.1";
    private static final Map<Integer, String> HTTP_STATUS_MAP = Map.of(200, "OK", 404, "Not Found");
    private static final String CRLF = "\r\n\r\n";
    private static final String OK_RESPONSE = String.format("%s %s %s%s", HTTP_1_DOT_1, 200, HTTP_STATUS_MAP.get(200), CRLF);
    private static final String NOT_FOUND_RESPONSE = String.format("%s %s %s%s", HTTP_1_DOT_1, 404, HTTP_STATUS_MAP.get(404), CRLF);
    private static final HttpServer INSTANCE = createInstance();

    private HttpServer() {
        super(PORT, 3, SERVER_NAME, HttpServerThreadFactory.getInstance());
    }

    private static String okResponse() {
        return OK_RESPONSE;
    }

    private static HttpServer createInstance() {
        return new HttpServer();
    }

    public static HttpServer start() {
        return INSTANCE;
    }

    private static String notFoundResponse() {
        return NOT_FOUND_RESPONSE;
    }

    @Override
    protected String outputMessage(String input) {
        if (input.endsWith(HTTP_1_DOT_1)) {
            var parts = input.split(" ");
            var endpoint = parts.length >= 2 ? parts[1] : null;

            if (StringUtils.isNotBlank(endpoint) && ENDPOINTS.contains(endpoint)) {
                return okResponse();
            }
            return notFoundResponse();
        }
        return null;
    }
}
