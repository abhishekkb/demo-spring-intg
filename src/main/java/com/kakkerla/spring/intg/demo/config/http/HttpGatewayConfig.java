package com.kakkerla.spring.intg.demo.config.http;


import com.kakkerla.spring.intg.demo.model.CatFact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.http.dsl.Http;

@Configuration
public class HttpGatewayConfig {


    @Bean
    public DirectChannel sampleChannel() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow inbound() {
        return IntegrationFlows.from(Http.inboundGateway("gateway/inbound")
                .requestMapping(requestMappingSpec -> requestMappingSpec.methods(HttpMethod.POST))
                .mappedRequestHeaders("sample-header")
                .id("idInGate"))
                .enrichHeaders(h -> h.header("header", "inboundHeader"))
                .channel("sampleChannel")
                .get();
    }

    @Bean
    public IntegrationFlow outbound(){
        return IntegrationFlows.from("sampleChannel")
                .handle(Http.outboundGateway("https://catfact.ninja/fact") // {pathParam} appended would consider value from next step
                        //.uriVariable("pathParam", "header[customHeader]") // Fetch header value from incoming request and store in pathParam
                        .httpMethod(HttpMethod.GET)
                        .expectedResponseType(CatFact.class))
                .get(); // replace '.get()' instead of 'logAndReply()' if printing in log is to be avoided
    }
}
