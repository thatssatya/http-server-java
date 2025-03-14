package com.samsepiol.server.impl.http.thread.factory;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class HttpServerThreadFactory implements ThreadFactory {
    private static final String POOL_NAME = "http-server-pool";
    private static final AtomicInteger poolNumber = new AtomicInteger(1);
    private static final String THREAD_NAME = "thread";
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private static final HttpServerThreadFactory INSTANCE = new HttpServerThreadFactory();
    private static final String PREFIX = String.format("%s-%s-%s-", POOL_NAME, poolNumber.getAndIncrement(), THREAD_NAME);

    @Override
    public Thread newThread(Runnable r) {
        return Thread.ofVirtual().name(String.format("%s-%s", PREFIX, threadNumber.getAndIncrement())).unstarted(r);
    }

    public static HttpServerThreadFactory getInstance() {
        return INSTANCE;
    }
}
