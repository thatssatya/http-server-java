package com.samsepiol.server.impl.http;

import com.samsepiol.server.impl.AbstractTcpServer;
import com.samsepiol.server.impl.http.thread.factory.HttpServerThreadFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpServer extends AbstractTcpServer {
    private static final String SERVER_NAME = "Http";
    private static final Integer PORT = 4221;
//    private static final String PING = "PING";
    private static final String PONG = "HTTP/1.1 200 OK\r\n\r\n";
    private static final HttpServer INSTANCE = createInstance();

    private HttpServer() {
        super(PORT, 3, SERVER_NAME, HttpServerThreadFactory.getInstance());
    }

    @Override
    protected String outputMessage(String input) {
//        if (PING.equals(input)) {
            return pong();
//        }
//        return null;
    }

    private static HttpServer createInstance() {
        return new HttpServer();
    }

    public static HttpServer start() {
        return INSTANCE;
    }

    private static String pong() {
        return PONG;
    }
}
