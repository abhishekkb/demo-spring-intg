package com.kakkerla.spring.intg.demo.config.jms;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.kakkerla.spring.intg.demo.DemoSpringIntgApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.support.JmsHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DirtiesContext
public class JmsChannelConfigTest {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(DemoSpringIntgApplication.class);

    @Value("${destination.integration}")
    private String integrationDestination;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private CountDownLatchHandler countDownLatchHandler;

    @Test
    public void testIntegration() throws Exception {
        MessageChannel channel1 = applicationContext
                .getBean("channel1", MessageChannel.class);

        Map<String, Object> headers = Collections.singletonMap(
                JmsHeaders.DESTINATION, integrationDestination);

        LOGGER.info("sending 10 messages");
        for (int i = 0; i < 10; i++) {
            GenericMessage<String> message = new GenericMessage<>(
                    "Hello Spring Integration JMS " + i + "!", headers);
            channel1.send(message);
            LOGGER.info("sent message='{}'", message);
        }

        countDownLatchHandler.getLatch().await(10000,
                TimeUnit.MILLISECONDS);
        assertThat(countDownLatchHandler.getLatch().getCount())
                .isEqualTo(0);
    }
}
