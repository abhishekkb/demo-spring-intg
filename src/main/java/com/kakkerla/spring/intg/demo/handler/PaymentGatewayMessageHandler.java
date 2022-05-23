package com.kakkerla.spring.intg.demo.handler;

import lombok.extern.slf4j.Slf4j;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;


@Slf4j
public class PaymentGatewayMessageHandler {

    public Message<?> handle(Message<?> paymentMessage) {
        log.info("received order='{}'", paymentMessage);

        Message<?> respMsg = MessageBuilder.withPayload("Accepted")
                .setHeader("jms_correlationId",
                        paymentMessage.getHeaders().get("jms_messageId"))
                .setReplyChannelName("inboundPaymentResponseChannel").build();
        log.info("sending status='{}'", respMsg);

        return respMsg;
    }
}