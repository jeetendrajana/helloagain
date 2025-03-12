package com.jj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws InterruptedException {
        logger.info("Hello and welcome!!!");
        for (int i = 1; i <= 5; i++) {
            logger.info("i = {}", i);
        }
        testConcurrentLRUCache();
    }

    private static void testConcurrentLRUCache() throws InterruptedException {
        LRUCache<String, String> cache = new LRUCache<>(3);
        ExecutorService executor = Executors.newFixedThreadPool(5);
        // Simulate concurrent access
        for (int i = 1; i <= 5; i++) {
            int threadId = i;
            executor.submit(() -> {
                String key = "key" + threadId;
                cache.put(key, "value" + threadId);
                logger.info("Thread-" + threadId + " put " + key);
                // Access cache
                for (int j = 1; j <= 5; j++) {
                    String k = "key" + j;
                    String value = cache.get(k);
                    logger.info("Thread-" + threadId + " got " + k + ": " + value);
                }
            });
        }
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
    }
}