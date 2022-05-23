package com.kakkerla.spring.intg.demo.config.jms;

import com.kakkerla.spring.intg.demo.handler.PaymentChannelMessageHandler;
import com.kakkerla.spring.intg.demo.repo.PaymentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.jms.ChannelPublishingJmsMessageListener;
import org.springframework.integration.jms.JmsMessageDrivenEndpoint;
import org.springframework.integration.jms.JmsSendingMessageHandler;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.SimpleMessageListenerContainer;
import org.springframework.messaging.MessageHandler;

import javax.jms.ConnectionFactory;

@Configuration
public class JmsChannelConfig {

    @Value("${destination.integration}")
    private String integrationDestination;

    // channel 1 config
    @Bean
    public DirectChannel channel1() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "channel1")
    public MessageHandler jmsMessageHandler(JmsTemplate jmsTemplate) {
        JmsSendingMessageHandler handler =
                new JmsSendingMessageHandler(jmsTemplate);
        handler.setDestinationName(integrationDestination);

        return handler;
    }
    //////////////////////////////////////////////////////////////////////////////////////////
    // channel 2 config
    @Bean
    public DirectChannel channel2() {
        return new DirectChannel();
    }

    @Bean
    public JmsMessageDrivenEndpoint jmsMessageDrivenEndpoint(
            ConnectionFactory connectionFactory) {
        JmsMessageDrivenEndpoint endpoint = new JmsMessageDrivenEndpoint(
                simpleMessageListenerContainer(connectionFactory),
                channelPublishingJmsMessageListener());
        endpoint.setOutputChannel(channel2());

        return endpoint;
    }

    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer(
            ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container =
                new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setDestinationName(integrationDestination);
        return container;
    }

    @Bean
    public ChannelPublishingJmsMessageListener channelPublishingJmsMessageListener() {
        return new ChannelPublishingJmsMessageListener();
    }

    @Bean
    @ServiceActivator(inputChannel = "channel2")
    public PaymentChannelMessageHandler paymentMessageHandler(PaymentRepository paymentRepository) {
        return new PaymentChannelMessageHandler(paymentRepository);
    }
}
