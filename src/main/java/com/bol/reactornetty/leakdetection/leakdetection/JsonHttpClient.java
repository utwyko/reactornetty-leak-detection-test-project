package com.bol.reactornetty.leakdetection.leakdetection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.ipc.netty.http.client.HttpClient;

import java.time.Duration;

@Component
public class JsonHttpClient {

    private static final Logger log = LoggerFactory.getLogger(JsonHttpClient.class);

    private static final HttpClient httpClient = HttpClient.create(ops ->
            ops
                    .connect("jsonplaceholder.typicode.com", 80));

    public Mono<String> getString() {
        return httpClient.get("/posts", request ->
                request
                        .addHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE))
                .then(response -> response.receive().aggregate().asString())
                .timeout(Duration.ofMillis(80));
    }
}
