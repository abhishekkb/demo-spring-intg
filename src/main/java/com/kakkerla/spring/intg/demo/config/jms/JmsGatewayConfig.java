//package com.kakkerla.spring.intg.demo.config.jms;
//
//import com.kakkerla.spring.intg.demo.handler.PaymentGatewayMessageHandler;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.integration.annotation.ServiceActivator;
//import org.springframework.integration.channel.DirectChannel;
//import org.springframework.integration.channel.QueueChannel;
//import org.springframework.integration.config.EnableIntegration;
//import org.springframework.integration.jms.ChannelPublishingJmsMessageListener;
//import org.springframework.integration.jms.JmsInboundGateway;
//import org.springframework.integration.jms.JmsOutboundGateway;
//import org.springframework.jms.listener.SimpleMessageListenerContainer;
//import org.springframework.messaging.MessageChannel;
//
//import javax.jms.ConnectionFactory;
//
//@Configuration
//@EnableIntegration
//public class JmsGatewayConfig {
//    @Value("${destination.payment.preq.q}")
//    private String paymentRequestDestination;
//
//    @Value("${destination.payment.preq.q}")
//    private String paymentResponseDestination;
//
//    @Bean
//    public MessageChannel outboundPaymentRequestChannel() {
//        return new DirectChannel();
//    }
//
//    @Bean
//    public MessageChannel inboundPaymentRequestChannel() {
//        return new DirectChannel();
//    }
//
//    @Bean
//    public MessageChannel inboundPaymentResponseChannel() {
//        return new DirectChannel();
//    }
//
//    @Bean
//    public MessageChannel outboundPaymentResponseChannel() {
//        return new QueueChannel();
//    }
//
//    @Bean
//    @ServiceActivator(inputChannel = "inboundPaymentRequestChannel")
//    public PaymentGatewayMessageHandler orderService() {
//        return new PaymentGatewayMessageHandler();
//    }
//
//    ////////////////////////////////////////////////////////////////
//    @Bean
//    @ServiceActivator(inputChannel = "outboundPaymentRequestChannel")
//    public JmsOutboundGateway jmsOutboundGateway(
//            ConnectionFactory connectionFactory) {
//        JmsOutboundGateway gateway = new JmsOutboundGateway();
//        gateway.setConnectionFactory(connectionFactory);
//        gateway.setRequestDestinationName(paymentRequestDestination);
//        gateway.setReplyDestinationName(paymentResponseDestination);
//        gateway.setReplyChannel(outboundPaymentResponseChannel());
//
//        return gateway;
//    }
//
//    ////////////////////////////////////////////////////////////////
//    @Bean
//    public JmsInboundGateway jmsInboundGateway(
//            ConnectionFactory connectionFactory) {
//        JmsInboundGateway gateway = new JmsInboundGateway(
//                simpleMessageListenerContainer2(connectionFactory),
//                channelPublishingJmsMessageListener2());
//        gateway.setRequestChannel(inboundPaymentRequestChannel());
//
//        return gateway;
//    }
//
//    @Bean
//    public SimpleMessageListenerContainer simpleMessageListenerContainer2(
//            ConnectionFactory connectionFactory) {
//        SimpleMessageListenerContainer container =
//                new SimpleMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        container.setDestinationName(paymentRequestDestination);
//        return container;
//    }
//
//    @Bean
//    public ChannelPublishingJmsMessageListener channelPublishingJmsMessageListener2() {
//        ChannelPublishingJmsMessageListener channelPublishingJmsMessageListener =
//                new ChannelPublishingJmsMessageListener();
//        channelPublishingJmsMessageListener.setExpectReply(true);
//
//        return channelPublishingJmsMessageListener;
//    }
//
//}
