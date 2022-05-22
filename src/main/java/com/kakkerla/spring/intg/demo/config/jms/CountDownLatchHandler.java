package com.kakkerla.spring.intg.demo.config.jms;

import java.util.concurrent.CountDownLatch;

import lombok.extern.slf4j.Slf4j;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;

@Slf4j
public class CountDownLatchHandler implements MessageHandler {

    private CountDownLatch latch = new CountDownLatch(10);

    public CountDownLatch getLatch() {
        return latch;
    }

    @Override
    public void handleMessage(Message<?> message) {
        log.info("received message='{}'", message);
        latch.countDown();
    }
}