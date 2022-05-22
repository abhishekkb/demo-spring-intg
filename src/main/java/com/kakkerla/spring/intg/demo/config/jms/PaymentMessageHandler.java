package com.kakkerla.spring.intg.demo.config.jms;

import com.kakkerla.spring.intg.demo.entity.Payment;
import com.kakkerla.spring.intg.demo.repo.PaymentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;

import java.util.concurrent.CountDownLatch;

@Slf4j
public class PaymentMessageHandler implements MessageHandler {

    private CountDownLatch latch = new CountDownLatch(10);

    public CountDownLatch getLatch() {
        return latch;
    }

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentMessageHandler(PaymentRepository paymentRepository){
        this.paymentRepository = paymentRepository;
    }

    @Override
    public void handleMessage(Message<?> message) {
        log.info("received message='{}'", message);
        //save to H2 database
//        System.out.println("class type = " + message.getPayload().getClass());
        Payment payment = (Payment) message.getPayload();
        Payment savedPayment = paymentRepository.save(payment);
        System.out.println("Saved in DB - " + savedPayment);
        latch.countDown();
    }
}