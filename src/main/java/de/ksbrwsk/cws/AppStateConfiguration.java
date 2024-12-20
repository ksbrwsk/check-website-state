package de.ksbrwsk.cws;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class AppStateConfiguration {

    @Bean
    public RestClient restClient() {
        return RestClient
                .builder()
                .requestFactory(clientHttpRequestFactory())
                .build();
    }

    @Bean
    public HttpComponentsClientHttpRequestFactory clientHttpRequestFactory() {
        var factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(5000);
        factory.setReadTimeout(5000);
        return factory;
    }
}
